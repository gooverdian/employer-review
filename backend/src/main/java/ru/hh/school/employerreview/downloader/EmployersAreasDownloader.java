package ru.hh.school.employerreview.downloader;

import ru.hh.school.employerreview.area.Area;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.downloader.dto.AreaJson;
import ru.hh.school.employerreview.downloader.dto.EmployerJson;
import ru.hh.school.employerreview.downloader.dto.ResponseJson;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EmployersAreasDownloader extends AbstractDownloader {
  private static final String URL_AREAS = "https://api.hh.ru/areas";
  private static final String URL_EMPLOYERS = "https://api.hh.ru/employers";
  private static final int EMPLOYERS_PAGES = 5;
  private static final int EMPLOYERS_PAGE_SIZE = 1000;
  private static AreaDao areaDao;
  private static EmployerDao employerDao;
  private static int areaSizeCounter = 0;

  public static void main(String[] args) {

    int maxEmployersSize = Integer.MAX_VALUE;
    int maxAreasSize = Integer.MAX_VALUE;
    if (args.length == 2) {
      maxAreasSize = Integer.parseInt(args[0]);
      maxEmployersSize = Integer.parseInt(args[1]);
    }

    init();

    employerDao = applicationContext.getBean(EmployerDao.class);
    areaDao = applicationContext.getBean(AreaDao.class);

    AreaJson[] areas = getAreasFromApi();
    saveAreasToDb(areas, maxAreasSize, maxEmployersSize);
  }

  private static AreaJson[] getAreasFromApi() {
    try {
      return OBJECT_MAPPER.readValue(new URL(URL_AREAS), AreaJson[].class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static void saveAreasToDb(AreaJson[] areaJsons, int maxAreasSize, int maxEmployersSize) {
    for (AreaJson areaJson : areaJsons) {
      Area currentArea = areaJson.toArea();
      if (areaSizeCounter < maxAreasSize) {
        areaDao.save(currentArea);
        if (employerDao.countRows() < maxEmployersSize) {
          downloadEmployers(currentArea);
        }
        areaSizeCounter += 1;
        saveAreasToDb(areaJson.getAreas(), maxAreasSize, maxEmployersSize);
      }
    }
  }

  private static void downloadEmployers(Area area) {
    for (int page = 0; page < EMPLOYERS_PAGES; page++) {
      EmployerJson[] employerJsons = getEmployersPageFromApi(page, area.getId());
      if (employerJsons != null) {
        saveEmployersToDb(employerJsons, area);
      }
    }
  }

  private static EmployerJson[] getEmployersPageFromApi(int page, int areaId) {
    try {
      URL url = new URL(URL_EMPLOYERS + "?per_page=" + EMPLOYERS_PAGE_SIZE + "&page=" + page + "&area=" + areaId);
      ResponseJson responseJson = OBJECT_MAPPER.readValue(url, ResponseJson.class);
      return responseJson.getItems();
    } catch (Exception e) {
      return null;
    }
  }

  private static void saveEmployersToDb(EmployerJson[] employerJsons, Area area) {
    List<Employer> employers = new ArrayList<>();
    for (EmployerJson employerJson : employerJsons) {
      employers.add(employerJson.toHibernateObj(area));
    }
    employerDao.save(employers);
  }
}
