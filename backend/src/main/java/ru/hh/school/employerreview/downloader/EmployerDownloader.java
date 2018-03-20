package ru.hh.school.employerreview.downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;
import ru.hh.school.employerreview.downloader.response.EmployerJSON;
import ru.hh.school.employerreview.downloader.response.ResponseJSON;
import ru.hh.school.employerreview.employer.EmployerDAO;
import ru.hh.school.employerreview.employer.EmployerService;

public class EmployerDownloader {

  static SessionFactory sessionFactory;
  static int writeOperationsCounter = 0;
  static EmployerService employerService;
  static ObjectMapper objectMapper;

  public static void main(String[] args) {
    Map<String, String> params = new HashMap<>();
    params.put("per_page", "1000");
    params.put("page", "0");
    writeOperationsCounter = 0;
    sessionFactory = createSessionFactory();
    employerService = createUserService(sessionFactory);
    objectMapper = new ObjectMapper();
    int i = 0;
    while (true) {
        params.replace("page", String.valueOf(i));
        try {
          getEmployers(params);
        }catch (Exception e){
          break;
        }
        ++i;
    }
    sessionFactory.close();
    System.out.println(String.format("operations : %d", writeOperationsCounter));

  }

  public static void getEmployers(Map<String, String> parameters) throws Exception{

    try {
      StringBuilder reqUrlStr = new StringBuilder();
      reqUrlStr.append("https://api.hh.ru/employers");
      reqUrlStr.append(getParamsString(parameters));
      System.out.println(reqUrlStr.toString());

      URL url = new URL(reqUrlStr.toString());
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("Content-Type", "application/json");
      con.setConnectTimeout(5000);
      con.setReadTimeout(5000);
      con.setInstanceFollowRedirects(false);
      con.setDoOutput(true);

      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuffer content = new StringBuffer();
      while ((inputLine = in.readLine()) != null) {
        content.append(inputLine);
      }
      in.close();
      con.disconnect();

      parseResponse(content.toString());

    } catch (Exception e) {
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

  private static void parseResponse(String response) throws IOException {

    ResponseJSON responseJSON = objectMapper.readValue(response, ResponseJSON.class);

    for (EmployerJSON curItem : responseJSON.items) {
        employerService.save(curItem.toHibernateObj());
    }
    writeOperationsCounter += responseJSON.items.length;
  }

  private static SessionFactory createSessionFactory() {
    return HibernateConfigFactory.prod().buildSessionFactory();
  }

  private static EmployerService createUserService(final SessionFactory sessionFactory) {
    EmployerDAO EmployerDAO = new EmployerDAO(sessionFactory);
    return new EmployerService(sessionFactory, EmployerDAO);
  }
}

