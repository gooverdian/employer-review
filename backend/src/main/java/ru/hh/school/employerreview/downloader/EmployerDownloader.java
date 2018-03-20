package ru.hh.school.employerreview.downloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static ru.hh.nab.core.util.PropertiesUtils.setSystemPropertyIfAbsent;
import ru.hh.school.employerreview.area.AreaDAO;
import ru.hh.school.employerreview.area.AreaService;
import ru.hh.school.employerreview.downloader.json.AreaJson;
import ru.hh.school.employerreview.downloader.json.EmployerJson;
import ru.hh.school.employerreview.downloader.json.ResponseJson;
import ru.hh.school.employerreview.employer.EmployerDAO;
import ru.hh.school.employerreview.employer.EmployerService;

public class EmployerDownloader {

  static SessionFactory sessionFactory;
  static int writeOperationsCounter = 0;
  static EmployerService employerService;
  static AreaService areaService;
  static ObjectMapper objectMapper;
  static AreaJson[] areaJson;
  static int currentAreaId = 113;

  public static void main(String[] args) {
    setSystemPropertyIfAbsent("settingsDir", "src/etc");
    ApplicationContext context = new AnnotationConfigApplicationContext(DownloaderConfig.class);

    SessionFactory sessionFactory = context.getBean(SessionFactory.class);
  }

  private static void doMain(ApplicationContext context) {
    Map<String, String> params = new HashMap<>();
    writeOperationsCounter = 0;
    employerService = createEmployerService(sessionFactory);
    areaService = createAreaService(sessionFactory);
    objectMapper = new ObjectMapper();

    try { // Get Areas;
      URL url = new URL("https://api.hh.ru/areas");
      areaJson = objectMapper.readValue(url, AreaJson[].class);
    }catch (Exception e){
      e.printStackTrace();
      return;
    }

    //for (AreaJson area : areaJson) {//Depth Search to put area id in employer entity later
    //  areaService.save(area.toArea());
    //  depthSearch(area);
    //}

    params.put("per_page", "1000");
    params.put("page", "0");

    int i = 0;
    while (true) {
      params.replace("page", String.valueOf(i));
      try {
        getEmployers("https://api.hh.ru/employers", params);
      }catch (Exception e){
        break;
      }
      ++i;
    }
    sessionFactory.close();
    session.close();
    System.out.println(String.format("operations : %d", writeOperationsCounter));
  }

  private static void depthSearch(AreaJson areaJson){
    for (AreaJson area : areaJson.areas){
      areaService.save(area.toArea());
      depthSearch(area);
    }
  }

  private static void getEmployers(String urlStr, Map<String, String> params) throws Exception{
    try {
      StringBuilder reqUrlStr = new StringBuilder();
      reqUrlStr.append(urlStr);
      reqUrlStr.append(getParamsString(params));
      System.out.println(reqUrlStr.toString());

      URL url = new URL(reqUrlStr.toString());
      ResponseJson responseJson = objectMapper.readValue(url, ResponseJson.class);

      for (EmployerJson curItem : responseJson.items) {
        employerService.save(curItem.toHibernateObj(currentAreaId));
      }
      writeOperationsCounter += responseJson.items.length;

    }catch (Exception e){
      e.printStackTrace();
      throw e;
    }
  }

  private static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
    StringBuilder result = new StringBuilder();
    boolean first = true;
    result.append("?");
    for (Map.Entry<String, String> entry : params.entrySet()) {
      if (first)
        first = false;
      else
        result.append("&");

      result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
      result.append("=");
      result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
    }
    return result.toString();
  }

  private static EmployerService createEmployerService(final SessionFactory sessionFactory) {
    EmployerDAO employerDAO = new EmployerDAO(sessionFactory);
    return new EmployerService(sessionFactory, employerDAO);
  }

  private static AreaService createAreaService(final SessionFactory sessionFactory) {
    AreaDAO areaDAO = new AreaDAO(sessionFactory);
    return new AreaService(sessionFactory, areaDAO);
  }
}

