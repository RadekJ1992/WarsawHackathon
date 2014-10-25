/**
 * created on 13:28:19 25 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.hackathon.warsaw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import facebook4j.BatchRequest;
import facebook4j.BatchRequests;
import facebook4j.BatchResponse;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Friend;
import facebook4j.Friendlist;
import facebook4j.ResponseList;
import facebook4j.User;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.internal.http.RequestMethod;
import facebook4j.internal.org.json.JSONObject;
import facebook4j.json.DataObjectFactory;

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
          .setOAuthPermissions(Constants.permissions);
        FacebookFactory ff = new FacebookFactory(cb.build());
        Facebook facebook = ff.getInstance();
        Map<String, Friend> friendMap = new HashMap<>();
        try {
            Integer count = 0;
            ResponseList<Friendlist> friends = facebook.getFriendlists();
            for (Friendlist f : friends) {
                System.out.println(f.getName());
                ResponseList<Friend> friendsies = facebook.getFriendlistMembers(f.getId());
                for (Friend fr : friendsies) {
                    friendMap.put(fr.getId(), fr);
                }
                
            }
            System.out.println(count);
        } catch (FacebookException e) {
            e.printStackTrace();
        }
    }

}
