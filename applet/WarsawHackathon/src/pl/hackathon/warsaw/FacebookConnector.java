/**
 * created on 17:32:30 25 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.hackathon.warsaw;

import java.util.HashMap;
import java.util.Vector;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.conf.ConfigurationBuilder;

public class FacebookConnector {

    Facebook facebook;
    HashMap<String, FriendContainer> friendsMap;
    Vector<String> friendsToRemoveNames;
    Vector<String> friendsWithoutConversationNames;
    FBFriendListCommunicationDateChecker fbfriendListCommunicationDateChecker;

    public HashMap<String, FriendContainer> getFriendsMap() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthAppId(Constants.appId)
                .setOAuthAppSecret(Constants.appSecret)
                .setOAuthAccessToken(Constants.token)
                .setOAuthPermissions(Constants.permissions);
        FacebookFactory ff = new FacebookFactory(cb.build());
        facebook = ff.getInstance();
        // maps username to whole friend object
        friendsMap = new HashMap<>();
        fbfriendListCommunicationDateChecker = new FBFriendListCommunicationDateChecker(
                facebook, friendsMap);
        fbfriendListCommunicationDateChecker.updateFriendsMap();
        friendsToRemoveNames = fbfriendListCommunicationDateChecker
                .getFriendsToRemoveNames();
        friendsWithoutConversationNames = fbfriendListCommunicationDateChecker.
                getFriendsWithoutConversationNames();
        return friendsMap;
    }

    public Vector<String> getFriendsWithoutConversationNames() {
        return friendsWithoutConversationNames;
    }
    
    public Vector<String> getFriendsToRemoveNames() {
        return friendsToRemoveNames;
    }

    public HashMap<String, FriendContainer> updateFriendsMap() {
        FBFriendListCommunicationDateChecker fbfriendListCommunicationDateChecker = new FBFriendListCommunicationDateChecker(
                facebook, friendsMap);
        fbfriendListCommunicationDateChecker.updateFriendsMap();

        return friendsMap;

    }

}
