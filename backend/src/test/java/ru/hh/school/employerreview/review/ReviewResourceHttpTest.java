package ru.hh.school.employerreview.review;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.hh.school.employerreview.HttpResourceTestBase;
import ru.hh.school.employerreview.area.Area;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.position.Position;
import ru.hh.school.employerreview.rating.Rating;
import ru.hh.school.employerreview.review.dto.ResponseBodyReviewId;
import ru.hh.school.employerreview.review.dto.ResponseBodyReviews;
import ru.hh.school.employerreview.review.dto.ReviewDto;
import ru.hh.school.employerreview.specializations.Specialization;
import ru.hh.school.employerreview.specializations.dto.SpecializationDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReviewResourceHttpTest extends HttpResourceTestBase {
  private Employer employer01 = new Employer("Employer 01", "URL 01", 1);
  private Position position01 = new Position("Position 01");
  private Position position02 = new Position("Position 02");
  private Area area01 = new Area("Area 01", 1, 0);

  private Rating rating01;
  private final List<SpecializationDto> specializations01 = new ArrayList<>();
  private final List<SpecializationDto> specializations02 = new ArrayList<>();
  private final static String REVIEW_TEXT_01 = "ReviewText 01";
  private final static Float REVIEW_RATING_01 = 2.5f;
  private final static String REVIEW_TEXT_02 = "ReviewText 02";
  private final static Float REVIEW_RATING_02 = 3f;
  private Specialization specialization01 = new Specialization("Specialization 01");
  private Specialization specialization02 = new Specialization("Specialization 02");

  @Before
  public void createEntites() {
    positionDao.save(position01);
    positionDao.save(position02);
    areaDao.save(area01);
    employer01.setArea(area01);
    employerDao.save(employer01);
    specializationDao.save(specialization01);
    specializationDao.save(specialization02);

    SpecializationDto specializationDto01 = new SpecializationDto(specialization01.getId(), specialization01.getName());
    SpecializationDto specializationDto02 = new SpecializationDto(specialization02.getId(), specialization02.getName());
    specializations01.add(specializationDto01);
    specializations01.add(specializationDto02);
    specializations02.add(specializationDto02);
  }

  @Test
  public void postReviewSimpleTest() throws IOException {
    CloseableHttpClient httpClient = httpClient();

    ReviewDto reviewDto = new ReviewDto();
    reviewDto.setEmployerId(employer01.getId());
    reviewDto.setText(REVIEW_TEXT_01);
    reviewDto.setRating(REVIEW_RATING_01);
    String reviewJson = OBJECT_MAPPER.writeValueAsString(reviewDto);

    HttpPost httpPost = new HttpPost(HOST + "review");
    HttpEntity entity = new ByteArrayEntity(reviewJson.getBytes("UTF-8"));
    httpPost.setHeader("Content-Type", "application/json");
    httpPost.setEntity(entity);
    HttpResponse response = httpClient.execute(httpPost);

    String result = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines().collect(Collectors.joining("\n"));
    ResponseBodyReviewId responseBody = OBJECT_MAPPER.readValue(result, ResponseBodyReviewId.class);

    List<Review> reviews = reviewDao.getPaginatedFilteredByEmployer(employer01.getId(), 0, 100, null);
    assertNotNull(reviews);
    assertEquals(1, reviews.size());

    Review review = reviews.get(0);
    assertEquals(responseBody.getReviewId(), review.getId());
    assertEquals(employer01, review.getEmployer());
    assertEquals(REVIEW_TEXT_01, review.getText());
    assertEquals(REVIEW_RATING_01, review.getRating());
  }

  @Test
  public void postReviewsWithSpecializationsTest() throws IOException {
    CloseableHttpClient httpClient = httpClient();

    ReviewDto reviewDto01 = new ReviewDto();
    reviewDto01.setEmployerId(employer01.getId());
    reviewDto01.setText(REVIEW_TEXT_01);
    reviewDto01.setRating(REVIEW_RATING_01);
    reviewDto01.setReviewType(ReviewType.INTERVIEWEE);
    reviewDto01.setPositionId(position01.getId());
    reviewDto01.setEmploymentDuration((short) 0);
    reviewDto01.setEmploymentTerminated(false);
    reviewDto01.setSalary(100_000);
    reviewDto01.setSpecializations(specializations01);
    String reviewJson01 = OBJECT_MAPPER.writeValueAsString(reviewDto01);

    HttpPost httpPost = new HttpPost(HOST + "review");
    HttpEntity entity = new ByteArrayEntity(reviewJson01.getBytes("UTF-8"));
    httpPost.setHeader("Content-Type", "application/json");
    httpPost.setEntity(entity);
    HttpResponse response = httpClient.execute(httpPost);

    String result = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines().collect(Collectors.joining("\n"));
    ResponseBodyReviewId responseBody01 = OBJECT_MAPPER.readValue(result, ResponseBodyReviewId.class);

    ReviewDto reviewDto02 = new ReviewDto();
    reviewDto02.setEmployerId(employer01.getId());
    reviewDto02.setText(REVIEW_TEXT_02);
    reviewDto02.setRating(REVIEW_RATING_02);
    reviewDto02.setReviewType(ReviewType.EMPLOYEE);
    reviewDto02.setPositionId(position02.getId());
    reviewDto02.setEmploymentDuration((short) 12);
    reviewDto02.setEmploymentTerminated(true);
    reviewDto02.setSalary(170_000);
    reviewDto02.setSpecializations(specializations02);
    String reviewJson02 = OBJECT_MAPPER.writeValueAsString(reviewDto02);

    httpPost = new HttpPost(HOST + "review");
    entity = new ByteArrayEntity(reviewJson02.getBytes("UTF-8"));
    httpPost.setHeader("Content-Type", "application/json");
    httpPost.setEntity(entity);
    response = httpClient.execute(httpPost);

    result = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines().collect(Collectors.joining("\n"));
    ResponseBodyReviewId responseBody02 = OBJECT_MAPPER.readValue(result, ResponseBodyReviewId.class);

    List<Review> reviews = reviewDao.getPaginatedFilteredByEmployerWithSpecializations(employer01.getId(), 0, 100, null);
    assertNotNull(reviews);
    assertEquals(2, reviews.size());

    Review review01 = reviews.stream().filter(review -> review.getId().equals(responseBody01.getReviewId())).findFirst().get();
    Review review02 = reviews.stream().filter(review -> review.getId().equals(responseBody02.getReviewId())).findFirst().get();

    assertNotNull(review01);
    assertEquals(responseBody01.getReviewId(), review01.getId());
    assertEquals(employer01, review01.getEmployer());
    assertEquals(REVIEW_TEXT_01, review01.getText());
    assertEquals(REVIEW_RATING_01, review01.getRating());
    assertEquals(ReviewType.INTERVIEWEE, review01.getReviewType());
    assertEquals(position01, review01.getPosition());
    assertEquals(Short.valueOf((short) 0), review01.getEmploymentDuration());
    assertEquals(false, review01.getEmploymentTerminated());
    assertEquals(Integer.valueOf(100_000), review01.getSalary());
    assertNotNull(review01.getSpecializations());
    assertEquals(2, review01.getSpecializations().size());
    assertTrue(review01.getSpecializations().contains(specialization01));
    assertTrue(review01.getSpecializations().contains(specialization02));

    assertNotNull(review02);
    assertEquals(responseBody02.getReviewId(), review02.getId());
    assertEquals(employer01, review02.getEmployer());
    assertEquals(REVIEW_TEXT_02, review02.getText());
    assertEquals(REVIEW_RATING_02, review02.getRating());
    assertEquals(ReviewType.EMPLOYEE, review02.getReviewType());
    assertEquals(position02, review02.getPosition());
    assertEquals(Short.valueOf((short) 12), review02.getEmploymentDuration());
    assertEquals(true, review02.getEmploymentTerminated());
    assertEquals(Integer.valueOf(170_000), review02.getSalary());
    assertNotNull(review02.getSpecializations());
    assertEquals(review02.getSpecializations().size(), 1);
    assertTrue(review02.getSpecializations().contains(specialization02));
  }

  @Ignore
  @Test
  public void postReviewsReviewTypeFiltrationTest() throws IOException {
    CloseableHttpClient httpClient = httpClient();

    ReviewDto reviewDto01 = new ReviewDto();
    reviewDto01.setEmployerId(employer01.getId());
    reviewDto01.setText(REVIEW_TEXT_01);
    reviewDto01.setRating(REVIEW_RATING_01);
    reviewDto01.setReviewType(ReviewType.INTERVIEWEE);
    reviewDto01.setPositionId(position01.getId());
    reviewDto01.setEmploymentDuration((short) 0);
    reviewDto01.setEmploymentTerminated(false);
    reviewDto01.setSalary(100_000);
    reviewDto01.setSpecializations(specializations01);
    String reviewJson01 = OBJECT_MAPPER.writeValueAsString(reviewDto01);

    HttpPost httpPost = new HttpPost(HOST + "review");
    HttpEntity entity = new ByteArrayEntity(reviewJson01.getBytes("UTF-8"));
    httpPost.setHeader("Content-Type", "application/json");
    httpPost.setEntity(entity);
    HttpResponse response = httpClient.execute(httpPost);

    ReviewDto reviewDto02 = new ReviewDto();
    reviewDto02.setEmployerId(employer01.getId());
    reviewDto02.setText(REVIEW_TEXT_02);
    reviewDto02.setRating(REVIEW_RATING_02);
    reviewDto02.setReviewType(ReviewType.EMPLOYEE);
    reviewDto02.setPositionId(position02.getId());
    reviewDto02.setEmploymentDuration((short) 12);
    reviewDto02.setEmploymentTerminated(true);
    reviewDto02.setSalary(170_000);
    reviewDto02.setSpecializations(specializations02);
    String reviewJson02 = OBJECT_MAPPER.writeValueAsString(reviewDto02);

    httpPost = new HttpPost(HOST + "review");
    entity = new ByteArrayEntity(reviewJson02.getBytes("UTF-8"));
    httpPost.setHeader("Content-Type", "application/json");
    httpPost.setEntity(entity);
    response = httpClient.execute(httpPost);

    ResponseBodyReviews responseBodyReviews = OBJECT_MAPPER
        .readValue(new URL("http://localhost:8081/review?employer_id=" + employer01.getId() + "&review_type=EMPLOYEE"), ResponseBodyReviews.class);

    assertNotNull(responseBodyReviews);
    List<ReviewDto> reviewDtos = responseBodyReviews.getReviews();
    assertNotNull(reviewDtos);
    assertEquals(1, reviewDtos.size());

    ReviewDto reviewDto = reviewDtos.get(0);

    assertNotNull(reviewDto);
    assertEquals(employer01.getId(), reviewDto.getEmployerId());
    assertEquals(REVIEW_TEXT_02, reviewDto.getText());
    assertEquals(REVIEW_RATING_02, reviewDto.getRating());
    assertEquals(ReviewType.EMPLOYEE, reviewDto.getReviewType());
    assertEquals(position02.getId(), reviewDto.getPositionId());
    assertEquals(Short.valueOf((short) 12), reviewDto.getEmploymentDuration());
    assertEquals(true, reviewDto.getEmploymentTerminated());
    assertEquals(Integer.valueOf(170_000), reviewDto.getSalary());
    assertNotNull(reviewDto.getSpecializations());
    assertEquals(reviewDto.getSpecializations().size(), 1);
  }

  @Test
  public void postReviewOnlyMandatoryFieldsTest() throws IOException {
    CloseableHttpClient httpClient = httpClient();

    String reviewJson01 = "{\"rating\":2.5,\"text\":\"ReviewText 01\",\"employer_id\":" + employer01.getId() + "}";
    HttpPost httpPost = new HttpPost(HOST + "review");
    HttpEntity entity = new ByteArrayEntity(reviewJson01.getBytes("UTF-8"));
    httpPost.setHeader("Content-Type", "application/json");
    httpPost.setEntity(entity);
    HttpResponse response = httpClient.execute(httpPost);

    String result = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines().collect(Collectors.joining("\n"));
    ResponseBodyReviewId responseBody01 = OBJECT_MAPPER.readValue(result, ResponseBodyReviewId.class);

    List<Review> reviews = reviewDao.getPaginatedFilteredByEmployer(employer01.getId(), 0, 100, null);
    assertNotNull(reviews);
    assertEquals(1, reviews.size());

    Review review = reviews.get(0);
    assertEquals(responseBody01.getReviewId(), review.getId());
    assertEquals(employer01, review.getEmployer());
    assertEquals(REVIEW_TEXT_01, review.getText());
    assertEquals(REVIEW_RATING_01, review.getRating());
  }

  @Test
  public void getEmptyListTest() throws IOException {
    ResponseBodyReviews responseBodyReviews = OBJECT_MAPPER
        .readValue(new URL("http://localhost:8081/review?employer_id=70707070"), ResponseBodyReviews.class);

    assertNotNull(responseBodyReviews);
    assertNotNull(responseBodyReviews.getReviews());
    assertEquals(0, responseBodyReviews.getReviews().size());
    assertEquals(Integer.valueOf(0), responseBodyReviews.getPage());
    assertEquals(Integer.valueOf(0), responseBodyReviews.getPages());
    assertEquals(Integer.valueOf(0), responseBodyReviews.getPerPage());
  }

  @After
  public void deleteEntities() {
    testQueryExecutorDao.executeQuery("delete from Review");
    testQueryExecutorDao.executeQuery("delete from Employer");
    testQueryExecutorDao.executeQuery("delete from Position");
    testQueryExecutorDao.executeQuery("delete from Specialization");
    testQueryExecutorDao.executeQuery("delete from Area");
  }
}
