/**
 * created on 16:38:43 25 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.hackathon.warsaw;

import java.util.Date;
import java.util.HashMap;

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
    {
        index = 0;
        oldestDate = new Date();
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
                    if (lastComDate.before(oldestDate)) {
                        oldestDate = lastComDate;
                    }
                    if (friendsMap.containsKey(name)) {
                        friendsMap.get(name).setLastCommunicationDate(lastComDate);
                    } else {
                        FriendContainer fc = new FriendContainer();
                        fc.setName(name);
                        fc.setId("");
                        fc.setLastCommunicationDate(lastComDate);
                        friendsMap.put(name, fc);
                    }
                }
            }
        }
        if (index < 10) {
            updateCommunicationDates();
        }
    }
}
