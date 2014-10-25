/**
 * created on 16:10:49 25 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.hackathon.warsaw;

import facebook4j.Friend;
import java.util.Date;

public class FriendContainer {

    private Friend friend;
    private String id;
    private String name;
    private Date lastCommunicationDate;
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return the lastCommunicationDate
     */
    public Date getLastCommunicationDate() {
        return lastCommunicationDate;
    }
    /**
     * @param lastCommunicationDate the lastCommunicationDate to set
     */
    public void setLastCommunicationDate(Date lastCommunicationDate) {
        if (lastCommunicationDate != null) {
            if (this.lastCommunicationDate == null) {
                this.lastCommunicationDate = lastCommunicationDate;
            } else { 
                if (this.lastCommunicationDate.before(lastCommunicationDate)) {
                    this.lastCommunicationDate = lastCommunicationDate;
                }
            }
        }
    }
    
    /**
     * @param friend the friend to set
     */
    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the friend
     */
    public Friend getFriend() {
        return friend;
    }

    /**
     * 
     */
    public FriendContainer(Friend fr) {
        this.friend = fr;
        this.id = fr.getId();
        this.name = fr.getName();
    }

    public FriendContainer() {
    }
}
