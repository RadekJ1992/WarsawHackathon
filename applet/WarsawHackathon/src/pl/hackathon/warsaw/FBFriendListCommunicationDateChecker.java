/**
 * created on 16:38:43 25 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.hackathon.warsaw;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import facebook4j.Comment;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Friend;
import facebook4j.Friendlist;
import facebook4j.InboxResponseList;
import facebook4j.Message;
import facebook4j.Reading;
import facebook4j.ResponseList;

public class FBFriendListCommunicationDateChecker {

    private Facebook facebook;
    private Date oldestDate;
    private Integer index;
    private HashMap<String, FriendContainer> friendsMap;
    private HashSet<String> friendsToRemoveNames;
    private HashSet<String> friendsWithoutConversationNames;
    
    {
        index = 0;
        oldestDate = new Date();
        friendsToRemoveNames = new HashSet<String>();
        friendsWithoutConversationNames = new HashSet<String>();
    }
    
    
    /**
     * @return the friendsToRemoveNames
     */
    public Vector<String> getFriendsToRemoveNames() {
        Vector<String> result = new Vector<String>(friendsToRemoveNames);
        java.util.Collections.sort(result);
        return result;
    }
    
   

    /**
     * @return the friendsWithoutConversationNames
     */
    public Vector <String> getFriendsWithoutConversationNames() {
        Vector<String> result = new Vector<String>(friendsWithoutConversationNames);
        java.util.Collections.sort(result);
        return result;
    }



    /**
     * 
     */
    public FBFriendListCommunicationDateChecker(Facebook facebook, HashMap<String, FriendContainer> friendsMap) {
        this.facebook = facebook;
        this.friendsMap = friendsMap;
    }
    
    public void updateFriendsMap() {
        try {
            getFriends();
            updateCommunicationDates();
            
        } catch (FacebookException e) {
            e.printStackTrace();
        }
    }
    
    public void getFriends () throws FacebookException {
        ResponseList<Friendlist> friends = facebook.getFriendlists();
        for (Friendlist f : friends) {
            ResponseList<Friend> friendsFromList = facebook.getFriendlistMembers(f.getId());
            for (Friend fr : friendsFromList) {
                FriendContainer frcon = new FriendContainer(fr);
                friendsMap.put(fr.getName(), frcon);
                friendsWithoutConversationNames.add(fr.getName());
            }
        }
    }
    
    public void updateCommunicationDates() throws FacebookException {
        InboxResponseList<Message> messages = facebook.getInbox(new Reading().until(oldestDate).limit(100));
        index++;
        for (Message m : messages) {
            for (Comment c : m.getComments()) {
                if (c !=null && c.getFrom() != null && c.getFrom().getName() != null) {
                    String name = c.getFrom().getName();
                    Date lastComDate = c.getCreatedTime();
                    if (friendsWithoutConversationNames.contains(name)) {
                        friendsWithoutConversationNames.remove(name);
                    }
                    if (lastComDate.before(oldestDate)) {
                        oldestDate = lastComDate;
                    }
                    if (friendsMap.containsKey(name)) {
                        friendsMap.get(name).setLastCommunicationDate(lastComDate);
                        if (lastComDate == null || friendsMap.get(name).getLastCommunicationDate().before(Constants.oldestCommunitactionDate)) {
                            friendsToRemoveNames.add(name);
                        } else {
                            if (friendsToRemoveNames.contains(name)) {
                                friendsToRemoveNames.remove(name);
                            }
                        }
                    } else {
                        FriendContainer fc = new FriendContainer();
                        fc.setName(name);
                        fc.setId("");
                        fc.setLastCommunicationDate(lastComDate);
                        friendsMap.put(name, fc);
                        if (lastComDate == null || friendsMap.get(name).getLastCommunicationDate().before(Constants.oldestCommunitactionDate)) {
                            friendsToRemoveNames.add(name);
                        } else {
                            if (friendsToRemoveNames.contains(name)) friendsToRemoveNames.remove(name);
                        }
                    }
                }
            }
        }
        if (index < 20) {
            updateCommunicationDates();
        }
    }
}
