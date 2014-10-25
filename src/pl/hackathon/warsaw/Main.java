/**
 * created on 13:28:19 25 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.hackathon.warsaw;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.conf.ConfigurationBuilder;

public class Main {

    /**
     * 
     */
    public Main() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthAppId(Constants.appId)
          .setOAuthAppSecret(Constants.appSecret)
          .setOAuthAccessToken(Constants.token)
          .setOAuthPermissions("publish_actions,user_friends,user_about_me,user_status,read_friendlists");
        FacebookFactory ff = new FacebookFactory(cb.build());
        Facebook facebook = ff.getInstance();
        //facebook.setOAuthAccessToken(new AccessTo
        try {
            facebook.postStatusMessage("Hello World from Facebook4J.");
        } catch (FacebookException e) {
            e.printStackTrace();
        }
    }

}
