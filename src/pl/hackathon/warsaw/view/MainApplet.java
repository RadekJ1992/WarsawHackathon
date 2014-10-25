/**
 * created on 17:34:12 25 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.hackathon.warsaw.view;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import pl.hackathon.warsaw.Constants;
import pl.hackathon.warsaw.FacebookConnector;
import pl.hackathon.warsaw.FriendContainer;

public class MainApplet extends JApplet implements ActionListener{
    
    HashMap<String, FriendContainer> friendsMap;
    Vector<String> friendsNames;
    
    JPanel contentPane;
    JComboBox friendsList;
    JLabel friendsListLabel;
    JLabel lastCommunicationTextLabel;
    /**
     * @throws HeadlessException
     */
    public MainApplet() throws HeadlessException {
        
    }
    
    public void init() {
        
        this.setLayout(null);
        if (this.getParameter(Constants.TOKEN_PARAMETER) != null) {
            Constants.setToken(this.getParameter(Constants.TOKEN_PARAMETER));
        }
        if (this.getParameter(Constants.APP_ID_PARAMETER) != null) {
            Constants.setAppId(this.getParameter(Constants.APP_ID_PARAMETER));
        }
        if (this.getParameter(Constants.APP_SECRET_PARAMETER) != null) {
            Constants.setAppSecret(this.getParameter(Constants.APP_SECRET_PARAMETER));
        }
        
        FacebookConnector fc = new FacebookConnector();
        friendsMap = fc.getFriendsMap();
        friendsNames = new Vector<String>(friendsMap.keySet());
        java.util.Collections.sort(friendsNames);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        friendsListLabel = new JLabel("Friends List:");
        friendsListLabel.setLocation(50,50);
        friendsListLabel.setSize(150, 20);
        contentPane.add(friendsListLabel);
        friendsList = new JComboBox(friendsNames);
        friendsList.setSelectedIndex(0);
        friendsList.addActionListener(this);
        friendsList.setLocation(50,100);
        friendsList.setSize(150, 20);
        contentPane.add(friendsList);
        lastCommunicationTextLabel = new JLabel("No conversations at all!");
        lastCommunicationTextLabel.setLocation(50,150);
        lastCommunicationTextLabel.setSize(600, 20);
        contentPane.add(lastCommunicationTextLabel);
        this.setContentPane(contentPane);
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String friendName = (String)cb.getSelectedItem();
        FriendContainer fc = (FriendContainer) friendsMap.get(friendName);
        if (fc != null) {
            updateLastCommunicationDateLabel(fc.getLastCommunicationDate());
        } else {
            updateLastCommunicationDateLabel(null);
        }
    }

    /**
     * @param object
     */
    private void updateLastCommunicationDateLabel(Date comDate) {
        if (comDate != null) {
            lastCommunicationTextLabel.setText("Date of last conversation: " + comDate.toString());
        } else {
            lastCommunicationTextLabel.setText("No Conversations at all!");
        }
    }

}
