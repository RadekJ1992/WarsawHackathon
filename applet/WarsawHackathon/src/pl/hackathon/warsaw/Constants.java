/**
 * created on 13:17:24 25 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.hackathon.warsaw;

import java.util.Calendar;
import java.util.Date;

public class Constants {

    public static String appId = "766825526711978";
    public static String appSecret = "18724218ddac01255de6ea4421293f25";
    public static String token = "CAACEdEose0cBAC5upzacNsRDzcI5XpAs0tcrEK1Uisz0dbV20EkkENUFr0NBf249iwKCoHo3gb6w9EwAZB4R2RAm4ZAk6VZAcJSnC1LSrllKdYWE3lW8AdeDzRX9iEYHvE9dyqY2NYKNYhzLB0HidqeSvD2oVXyBu1fH7HxYFMrtehWFjkZC7nvBz0ZAXwWeZCGMlGJs7gpAMvZA7aOmabxLPQjwLAMVJoZD";
    public static String permissions = "publish_actions,user_friends,user_about_me,user_activities,user_status,read_friendlists,user_location,email,read_mailbox,read_page_mailboxes,read_stream";

    
    public static String APP_ID_PARAMETER = "APP_ID";
    public static String APP_SECRET_PARAMETER = "APP_SECRET";
    public static String TOKEN_PARAMETER = "TOKEN";
    
    public static void setAppId(String newAppId) {
        Constants.appId = newAppId;
    }

    public static void setAppSecret(String newAppId) {
        Constants.appId = newAppId;
    }

    public static void setToken(String newAppId) {
        Constants.appId = newAppId;
    }
    public static Date oldestCommunitactionDate;
    
    static {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -60);
        oldestCommunitactionDate = cal.getTime();
    }
}
