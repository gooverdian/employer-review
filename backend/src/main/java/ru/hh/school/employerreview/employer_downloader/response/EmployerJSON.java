package response;

import com.google.gson.annotations.SerializedName;
import employer.Employer;

public class EmployerJSON {
    String name;
    String url;
    String id;
    String area;
    LogoUrlsJSON logo_urls;

    @SerializedName(value = "alternate_url")
    String alternateUrl;

    public Employer toHibernateObj(){
        area = "113";
        Employer employer = new Employer(name , url, Integer.parseInt(id));
        employer.setAlternateUrl(alternateUrl);
        employer.setLogoUrl90(logo_urls.logo90);
        employer.setLogoUrl240(logo_urls.logo240);
        employer.setLogoUrlOriginal(logo_urls.logoOriginal);
        //employer.setAreaId();
        //employer.setDescription();
        //......
        return employer;
    }

    String parseLogoUrls(StringBuilder sb){
        int balanse = 0;
        StringBuilder jsonSb = new StringBuilder();
        if (new Character(sb.charAt(0)).equals('{')){
            balanse = 1;
            jsonSb.append(sb.charAt(0));
            sb.delete(0,1);
        }
        while (balanse != 0){
            if (new Character(sb.charAt(0)).equals('{')){
                balanse += 1;
            }
            if (new Character(sb.charAt(0)).equals('}')){
                balanse -= 1;
            }
            jsonSb.append(sb.charAt(0));
            sb.delete(0,1);
        }
        return jsonSb.toString();
    }
}
