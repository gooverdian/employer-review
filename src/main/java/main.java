import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import employer.*;
import org.hibernate.SessionFactory;
import response.EmployerJSON;
import response.ResponseJSON;

public class main {

    static boolean stopFlag = false;
    static SessionFactory sessionFactory;

    public static void main(String[] args){
        Map<String, String> params = new HashMap<>();
        params.put("per_page","1000");
        params.put("page","0");
        int i = 0;
        while (!stopFlag){
            params.replace("page", String.valueOf(i));
            getEmployers(params);
            ++i;
        }
    }

    public static void getEmployers(Map<String, String> parameters){

        sessionFactory = createSessionFactory();
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

            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            FileWriter fw = new FileWriter("resp.html");
            fw.write(content.toString());
            fw.close();
            parseResponse(content.toString());
            System.out.println(content.toString());
        }catch (Exception e){
            e.printStackTrace();
            stopFlag = true;
        }finally {
            sessionFactory.close();
        }
    }
    private static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        result.append("?");
        for(Map.Entry<String, String> entry : params.entrySet()){
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

    private static void parseResponse(String response){
        EmployerService employerService = createUserService(sessionFactory);
        Gson gson = new Gson();

        ResponseJSON responseJSON = gson.fromJson(response, ResponseJSON.class);

        int counter = 0;
        for (EmployerJSON curItem : responseJSON.items) {
            employerService.save(curItem.toHibernateObj());
            ++counter;
        }
        System.out.println(String.format("operations : %d" ,counter));
    }

    private static SessionFactory createSessionFactory() {
        return HibernateConfigFactory.prod().buildSessionFactory();
    }

    private static EmployerService createUserService(final SessionFactory sessionFactory) {
        EmployerDAO EmployerDAO = new EmployerDAO(sessionFactory);
        return new EmployerService(sessionFactory, EmployerDAO);
    }
}

