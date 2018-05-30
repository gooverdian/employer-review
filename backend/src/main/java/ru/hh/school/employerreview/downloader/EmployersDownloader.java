package ru.hh.school.employerreview.downloader;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import ru.hh.school.employerreview.area.Area;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.downloader.dto.EmployerJson;
import ru.hh.school.employerreview.downloader.dto.ResponseJson;
import ru.hh.school.employerreview.employer.EmployerDao;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Данный класс предназначен для загрузки в бд работодателей.
 * Алгоритм загрузки осуществляет следующие шаги:
 * - проходит по алфавиту и для каждой буквы выбирает максимум работодателей,
 * доступных для загрузки по запросу text="текущая буква"
 * - складывает найденных работодателей в общую коллекцию
 * - сортирует коллекцию по количеству открытых вакансий
 * (приоритетнее работодатели с большим количесвом вакансий)
 * - сохраняет в бд количество работодателей ограниченное переменной RECORDS_LIMIT
 * <p>
 * В main() следует передать обязательный аргумент area="ID региона из которого загружать работодателей"
 * Так же в main() можно передать аргументы, которые может распознать api.hh в URL
 * Например: type=company
 */
public class EmployersDownloader extends AbstractDownloader {
  private final static Logger LOGGER = Logger.getLogger(AreaDownloader.class.getName());
  private static final String URL_EMPLOYERS = "https://api.hh.ru/employers";
  private static final int PER_PAGE = 1000;
  private static final int RECORDS_LIMIT = 5000;
  private static final int DEFAULT_AREA_ID = 1;
  private static final String[] ABC = {"а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "о",
      "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я", "a", "b", "c", "d", "e", "f",
      "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "y", "z", "1", "2", "3", "4",
      "5", "6", "7", "8", "9", "0"};

  public static void main(String... args) throws IOException {
    init();

    EmployerDao employerDao = applicationContext.getBean(EmployerDao.class);
    AreaDao areaDao = applicationContext.getBean(AreaDao.class);

    List<Integer> areasIdsToLoad = new ArrayList<>();
    for (String arg : args) {
      if (arg.contains("area")) {
        String[] items = arg.split("=");
        if (items.length > 1) {
          try {
            Integer areaId = Integer.parseInt(items[1]);
            areasIdsToLoad.add(areaId);
            LOGGER.info("Area id added - " + areaId);
          } catch (NumberFormatException e) {
            LOGGER.warning(e.getMessage());
          }
        }
      }
    }

    if (areasIdsToLoad.size() == 0) {
      areasIdsToLoad.add(DEFAULT_AREA_ID);
    }

    int totalEmployersInserted = 0;
    for (Integer areaId : areasIdsToLoad) {

      Set<EmployerJson> employers = new HashSet<>();
      StringBuilder requestUrlBuilder = new StringBuilder(URL_EMPLOYERS + "?area=" + areaId);

      for (String character : ABC) {
        List<EmployerJson> employersRequested = requestEmployers(requestUrlBuilder, character, areaId);
        employers.addAll(employersRequested);
      }

      List<EmployerJson> employersOrdered = new ArrayList<>(employers);
      employersOrdered.sort((employer1, employer2) -> {
        if (employer1.getOpenVacancies() > employer2.getOpenVacancies()) {
          return -1;
        }
        if (employer1.getOpenVacancies() < employer2.getOpenVacancies()) {
          return 1;
        }
        return 0;
      });

      int currentRecord = 0;
      while (currentRecord < employersOrdered.size() && currentRecord < RECORDS_LIMIT) {
        EmployerJson employerJson = employersOrdered.get(currentRecord);
        try {
          Area area = areaDao.getAreaById(areaId);
          employerDao.save(employerJson.toHibernateObj(area));
          currentRecord++;
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
          LOGGER.info("Employer duplicate key - " + employerJson.getId());
        }
      }
      LOGGER.info("Employers inserted - " + currentRecord + " for area " + areaId);
      totalEmployersInserted += currentRecord;
    }
    LOGGER.info("Employers inserted - " + totalEmployersInserted);
  }

  private static List<EmployerJson> requestEmployers(StringBuilder requestUrlBuilder,
                                                     String character,
                                                     Integer areaId) {

    List<EmployerJson> result = new ArrayList<>();
    int currentPage = 0;
    boolean limitReached = false;

    while (!limitReached) {

      String requestUrl = null;
      if (requestUrlBuilder.indexOf("=") == -1) {
        requestUrl = requestUrlBuilder.toString() + "per_page=";
      } else {
        requestUrl = requestUrlBuilder.toString() + "&per_page=";
      }
      requestUrl = requestUrl + PER_PAGE + "&page=" + currentPage + "&text=" + character + "&only_with_vacancies=true" + "&type=company";
      LOGGER.info("URL to request - " + requestUrl);

      try {
        ResponseJson responseJson = OBJECT_MAPPER.readValue(new URL(requestUrl), ResponseJson.class);
        EmployerJson[] employerJsons = responseJson.getItems();
        result.addAll(Arrays.asList(employerJsons));
        currentPage++;
      } catch (IOException e) {
        limitReached = true;
      }
    }
    LOGGER.info("Character - " + character + " for area " + areaId + " processed");
    return result;
  }

}
