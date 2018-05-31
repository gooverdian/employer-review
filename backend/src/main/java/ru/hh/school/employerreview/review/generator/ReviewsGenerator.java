package ru.hh.school.employerreview.review.generator;

import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.rating.RatingDao;
import ru.hh.school.employerreview.review.Review;
import ru.hh.school.employerreview.review.ReviewDao;
import ru.hh.school.employerreview.review.ReviewType;
import ru.hh.school.employerreview.specializations.Specialization;
import ru.hh.school.employerreview.specializations.SpecializationDao;
import ru.hh.school.employerreview.statistic.main.MainPageStatisticDao;
import ru.hh.school.employerreview.webextractor.ExternalReview;
import ru.hh.school.employerreview.webextractor.ExternalReviewDao;
import ru.hh.school.employerreview.webextractor.ExternalReviewPositivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

public class ReviewsGenerator {
  private final static Logger LOGGER = Logger.getLogger(ReviewsGenerator.class.getName());
  private static final String CANDIDATE_CHARS = " ABC DEF GHI JKL MNO PQR STU VWX YZ ";
  private static final int MAX_DATE_INTERVAL = 30;
  private static final int MAX_ESTIMATE = 5;
  private static final float MIN_ESTIMATE = 0.5f;
  private static final int MAX_TEXT_LENGTH = 100;
  private static final float POSITIVITY_THRESHOLD = 3.5f;
  private static final int BASE_SALARY = 9500;
  private static final int SALARY_SCALE_FACTOR = 500;
  private static final int AVG_SALARY_MULTIPLIER = 160;
  private static final int SPECIALIZATIONS_THRESHOLD = 4;

  private int employersToProcessLimit;
  private float perEmployerAvgReviewsCount;
  private float perEmployerReviewsCountDeviation;
  private int employersPerPage;
  private final EmployerDao employerDao;
  private final MainPageStatisticDao mainPageStatisticDao;
  private final ReviewDao reviewDao;
  private final RatingDao ratingDao;
  private final ExternalReviewDao externalReviewDao;
  private final SpecializationDao specializationDao;
  private final Random randomGenerator = new Random();
  private final int maxExternalReviewId;
  private final int maxSpecializationId;

  public ReviewsGenerator(EmployerDao employerDao,
                          MainPageStatisticDao mainPageStatisticDao,
                          ReviewDao reviewDao,
                          RatingDao ratingDao,
                          ExternalReviewDao externalReviewDao,
                          SpecializationDao specializationDao) {
    this.employerDao = employerDao;
    this.mainPageStatisticDao = mainPageStatisticDao;
    this.reviewDao = reviewDao;
    this.ratingDao = ratingDao;
    this.externalReviewDao = externalReviewDao;
    this.specializationDao = specializationDao;

    maxExternalReviewId = externalReviewDao.getMaxId();
    maxSpecializationId = specializationDao.getMaxId();
  }

  public void process() {
    int page = 0;
    while (employersPerPage * page < employersToProcessLimit) {
      generateReviewsOnCurrentPage(employerDao.findEmployers("", page, employersPerPage, true),
          perEmployerAvgReviewsCount, perEmployerReviewsCountDeviation);
      page += 1;
    }
  }

  public void setEmployersToProcessLimit(int employersToProcessLimit) {
    this.employersToProcessLimit = employersToProcessLimit;
  }

  public void setPerEmployerAvgReviewsCount(float perEmployerAvgReviewsCount) {
    this.perEmployerAvgReviewsCount = perEmployerAvgReviewsCount;
  }

  public void setPerEmployerReviewsCountDeviation(float perEmployerReviewsCountDeviation) {
    this.perEmployerReviewsCountDeviation = perEmployerReviewsCountDeviation;
  }

  public void setEmployersPerPage(int employersPerPage) {
    this.employersPerPage = employersPerPage;
  }

  private void generateReviewsOnCurrentPage(List<Employer> employers,
                                            Float mathExpectedReviewCount,
                                            Float deviationReviewCount) {
    for (Employer employer : employers) {
      generateReviewsOnCurrentEmployer(employer, getReviewCount(mathExpectedReviewCount, deviationReviewCount), getEmployerMeanEstimate());
      LOGGER.info("Employer\t\"" + employer.getName() + "\"\t processed");
    }
  }

  private void generateReviewsOnCurrentEmployer(Employer employer, int reviewCount, Float mathExpectedEstimate) {
    for (int i = 0; i < reviewCount; i++) {
      mainPageStatisticDao.addReviewCount();
      if (employer.getRating() == null) {
        mainPageStatisticDao.addEmployerWithReviewCount();
      }
      reviewDao.save(getReview(employer, mathExpectedEstimate));
      ratingDao.addNewEstimate(employer, getEstimate(mathExpectedEstimate));
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(Calendar.DATE, -randomGenerator.nextInt(MAX_DATE_INTERVAL));
      employerDao.addEmployerVisitCounter(employer, calendar.getTime());
    }
  }

  private int getReviewCount(float mathExpected, float deviation) {
    int reviewCount = (int) (randomGenerator.nextFloat() * deviation + mathExpected);
    return reviewCount > 0 ? reviewCount : 0;
  }

  private float getEmployerMeanEstimate() {
    return randomGenerator.nextFloat() * MAX_ESTIMATE;
  }

  private Review getReview(Employer employer, float mathExpectedEstimate) {
    Float rating = getEstimate(mathExpectedEstimate);
    String text = getText(rating);
    ReviewType reviewType = getReviewType();
    Review review = new Review(employer, rating, reviewType, text);
    review.setSalary(getSalary());
    review.setEmploymentDuration(getEmploymentDuration(reviewType));
    review.setEmploymentTerminated(getEmploymentTerminated());
    review.getSpecializations().addAll(getSpecializations());
    return review;
  }

  private float getEstimate(float mathExpected) {
    float estimate = (float) randomGenerator.nextGaussian() + mathExpected;
    float result = 2.5f;
    float minDifference = Float.MAX_VALUE;

    for (float starValue = MIN_ESTIMATE; starValue <= MAX_ESTIMATE; starValue += 0.5f) {
      if (minDifference > Math.abs(starValue - estimate)) {
        minDifference = Math.abs(starValue - estimate);
        result = starValue;
      }
    }
    return result;
  }

  private ReviewType getReviewType() {
    return randomGenerator.nextBoolean() ? ReviewType.EMPLOYEE : ReviewType.INTERVIEWEE;
  }

  private String getText() {
    return generateRandomChars(randomGenerator.nextInt(MAX_TEXT_LENGTH));
  }

  private String getText(Float rating) {

    if (maxExternalReviewId == 0) {
      return new String();
    }

    ExternalReviewPositivity positivity;
    if (rating < POSITIVITY_THRESHOLD) {
      positivity = ExternalReviewPositivity.NEGATIVE;
    } else {
      positivity = ExternalReviewPositivity.POSITIVE;
    }

    ExternalReview externalReview = new ExternalReview();
    if (externalReview.getExternalReviewPositivity() == null) {
      int id = randomGenerator.nextInt(maxExternalReviewId);
      externalReview = externalReviewDao.getById(id);
      if (externalReview != null) {
        return externalReview.getText();
      }
      return new String();
    }
    while (positivity != externalReview.getExternalReviewPositivity()) {
      int id = randomGenerator.nextInt(maxExternalReviewId);
      externalReview = externalReviewDao.getById(id);
    }

    return externalReview.getText();
  }

  private Integer getSalary() {
    return Math.toIntExact(BASE_SALARY + Math.round(Math.abs(randomGenerator.nextGaussian()) * AVG_SALARY_MULTIPLIER) * SALARY_SCALE_FACTOR);
  }

  private short getEmploymentDuration(ReviewType reviewType) {
    if (reviewType == ReviewType.INTERVIEWEE) {
      return 0;
    } else if (reviewType == ReviewType.EMPLOYEE) {
      return (short) Math.round(Math.abs(randomGenerator.nextGaussian()) * 12 * 10);
    }
    return 0;
  }

  private boolean getEmploymentTerminated() {
    return randomGenerator.nextBoolean();
  }

  private List<Specialization> getSpecializations() {
    if (maxSpecializationId == 0) {
      return new ArrayList<>();
    }

    int id = randomGenerator.nextInt(maxSpecializationId);

    int specializationsNumber = randomGenerator.nextInt(SPECIALIZATIONS_THRESHOLD);

    Specialization specialization = specializationDao.getById(id);
    if (specialization == null || specialization.getProfessionalField() == null
        || specialization.getProfessionalField().getSpecializations() == null) {
      return new ArrayList<>();
    }
    List<Specialization> specializations = specialization.getProfessionalField().getSpecializations();

    Set<Specialization> result = new HashSet<>();
    result.add(specialization);

    for (int i = 0; i < specializationsNumber - 1; i++) {
      specialization = specializations.get(randomGenerator.nextInt(specializations.size()));
      result.add(specialization);
    }

    return new ArrayList<>(result);
  }

  private String generateRandomChars(int length) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      int randomArrayIndex = randomGenerator.nextInt(CANDIDATE_CHARS.length());
      sb.append(CANDIDATE_CHARS.charAt(randomArrayIndex));
    }
    return sb.toString();
  }
}
