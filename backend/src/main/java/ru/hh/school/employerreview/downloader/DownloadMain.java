package ru.hh.school.employerreview.downloader;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static ru.hh.nab.core.util.PropertiesUtils.setSystemPropertyIfAbsent;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.downloader.json.AreaJson;
import ru.hh.school.employerreview.downloader.json.EmployerJson;
import ru.hh.school.employerreview.downloader.json.ResponseJson;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;

public class DownloadMain {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String URL_AREAS = "https://api.hh.ru/areas";
  private static final String URL_EMPLOYERS = "https://api.hh.ru/employers";
  private static final String LOGOS_FOLDER = "employer_logos";
  private static final int EMPLOYERS_PAGES = 5;
  private static final int EMPLOYERS_PAGE_SIZE = 1000;
  private static int currentAreaId;
  private static String currentAreaName;
  private static AreaDao areaDao;
  private static EmployerDao employerDao;
  private static ApplicationContext applicationContext;

  public static void main(String[] args) {
    setSystemPropertyIfAbsent("settingsDir", "src/etc");
    applicationContext = new AnnotationConfigApplicationContext(DownloaderConfig.class);

    employerDao = applicationContext.getBean(EmployerDao.class);
    areaDao = applicationContext.getBean(AreaDao.class);

    AreaJson[] areas = getAreasFromApi();
    saveAreasToDb(areas);

    downloadLogos();
  }

  private static void downloadLogos(){
    Set<Employer> employers = employerDao.getAll();
    for (Employer employer : employers){
      downloadLogoFromUrl(employer.getLogoUrl90(), employer.getHhId());
    }
  }

  private static void downloadLogoFromUrl(String urlString, int hhId){
    try {
      URL url = new URL(urlString);
      ReadableByteChannel rbc = Channels.newChannel(url.openStream());
      FileOutputStream fos = new FileOutputStream( LOGOS_FOLDER + File.separator + hhId + ".png");
      fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    } catch (Exception e){
      return;
    }
  }

  private static AreaJson[] getAreasFromApi() {
    try {
      return OBJECT_MAPPER.readValue(new URL(URL_AREAS), AreaJson[].class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static void saveAreasToDb(AreaJson[] areaJsons) {
    for (AreaJson areaJson: areaJsons) {
      areaDao.save(areaJson.toArea());
      currentAreaId = Integer.parseInt(areaJson.getId());
      currentAreaName = areaJson.getName();
      downloadEmployers();
      saveAreasToDb(areaJson.getAreas());
    }
  }

  private static void downloadEmployers() {
    for (int page = 0; page < EMPLOYERS_PAGES; page++) {
      EmployerJson[] employerJsons = getEmployersPageFromApi(page);
      if (employerJsons != null) {
        saveEmployersToDb(employerJsons);
      }
    }
  }

  private static EmployerJson[] getEmployersPageFromApi(int page) {
    try {
      URL url = new URL(URL_EMPLOYERS + "?per_page=" + EMPLOYERS_PAGE_SIZE + "&page=" + page + "&area=" + currentAreaId);
      ResponseJson responseJson = OBJECT_MAPPER.readValue(url, ResponseJson.class);
      return responseJson.getItems();
    } catch (Exception e) {
      return null;
    }
  }

  private static void saveEmployersToDb(EmployerJson[] employerJsons) {
    List<Employer> employers = new ArrayList<>();
    for (EmployerJson employerJson: employerJsons) {
      employers.add(employerJson.toHibernateObj(currentAreaId, currentAreaName));
    }
    employerDao.save(employers);
  }
}

