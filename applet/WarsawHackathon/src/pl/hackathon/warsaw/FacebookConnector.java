/**
 * created on 17:32:30 25 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.hackathon.warsaw;

import java.util.HashMap;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.conf.ConfigurationBuilder;

public class FacebookConnector {

    public HashMap<String, FriendContainer> getFriendsMap() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthAppId(Constants.appId)
          .setOAuthAppSecret(Constants.appSecret)
          .setOAuthAccessToken(Constants.token)
          .setOAuthPermissions(Constants.permissions);
        FacebookFactory ff = new FacebookFactory(cb.build());
        Facebook facebook = ff.getInstance();
        //maps username to whole friend object 
        HashMap<String, FriendContainer> friendsMap = new HashMap<>();
        
        FBFriendListCommunicationDateChecker fbfriendListCommunicationDateChecker = new FBFriendListCommunicationDateChecker(facebook, friendsMap);
        fbfriendListCommunicationDateChecker.updateFriendsMap();
        /*
        for (FriendContainer friend : friendsMap.values()) {
            if (friend.getLastCommunicationDate() != null) {
                if (friend.getLastCommunicationDate().after(Constants.oldestCommunitactionDate)) {
                    System.out.println("Komunikowałeś się z " + friend.getName());
                }
            }
        }*/
        return friendsMap;

    }

}
