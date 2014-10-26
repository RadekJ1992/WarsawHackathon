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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pl.hackathon.warsaw.Constants;
import pl.hackathon.warsaw.FBFriendListCommunicationDateChecker;
import pl.hackathon.warsaw.FacebookConnector;
import pl.hackathon.warsaw.FriendContainer;

public class MainApplet extends JApplet implements ActionListener{
    
    HashMap<String, FriendContainer> friendsMap;
    Vector<String> friendsNames;
    Vector<String> friendsToRemoveNames;
    Vector<String> friendsWithoutConversationNames;
    
    JPanel contentPane;
    JComboBox friendsList;
    JLabel friendsListLabel;
    JLabel lastCommunicationTextLabel;
    JLabel friendIDLabel;
    
    JButton startScanButton;
    JLabel friendsToRemoveLabel;
    JComboBox friendsToRemoveComboBox;
    JLabel friendsWithoutConversationLabel;
    JComboBox friendsWithoutConversationComboBox;
    JButton removeButton;
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
        friendsToRemoveNames = fc.getFriendsToRemoveNames();
        friendsWithoutConversationNames = fc.getFriendsWithoutConversationNames();
        java.util.Collections.sort(friendsNames);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        friendsListLabel = new JLabel("Friends List:");
        friendsListLabel.setLocation(50,50);
        friendsListLabel.setSize(250, 20);
        contentPane.add(friendsListLabel);
        friendsList = new JComboBox(friendsNames);
        friendsList.setSelectedIndex(0);
        friendsList.addActionListener(this);
        friendsList.setLocation(50,100);
        friendsList.setSize(250, 20);
        contentPane.add(friendsList);
        friendIDLabel = new JLabel("");
        friendIDLabel.setLocation(50,350);
        friendIDLabel.setSize(250,20);
    //    contentPane.add(friendIDLabel);
        startScanButton = new JButton("Start Scan!");
        startScanButton.addActionListener(this);
        startScanButton.setActionCommand("start_scan");
        startScanButton.setLocation(50, 200);
        startScanButton.setSize(250,40);
        //contentPane.add(startScanButton);
        friendsToRemoveLabel = new JLabel("Friends with last conversation before "+Constants.oldestCommunitactionDate.toString()+" :");
        friendsToRemoveLabel.setLocation(50,200);
        friendsToRemoveLabel.setSize(250, 20);
        contentPane.add(friendsToRemoveLabel);
        friendsToRemoveComboBox = new JComboBox(friendsToRemoveNames);
        friendsToRemoveComboBox.setSelectedIndex(0);
        friendsToRemoveComboBox.addActionListener(this);
        friendsToRemoveComboBox.setLocation(50,250);
        friendsToRemoveComboBox.setSize(250, 20);
        contentPane.add(friendsToRemoveComboBox);
        friendsWithoutConversationLabel = new JLabel("Friends without conversation"); 
        friendsWithoutConversationLabel.setLocation(50,300);
        friendsWithoutConversationLabel.setSize(250, 20);
        contentPane.add(friendsWithoutConversationLabel);
        friendsWithoutConversationComboBox = new JComboBox(friendsWithoutConversationNames);
        friendsWithoutConversationComboBox.setSelectedIndex(0);
        friendsWithoutConversationComboBox.addActionListener(this);
        friendsWithoutConversationComboBox.setLocation(50,350);
        friendsWithoutConversationComboBox.setSize(250, 20);
        contentPane.add(friendsWithoutConversationComboBox);
        lastCommunicationTextLabel = new JLabel("No conversations at all!");
        lastCommunicationTextLabel.setLocation(320,100);
        lastCommunicationTextLabel.setSize(600, 20);
        contentPane.add(lastCommunicationTextLabel);
        this.setContentPane(contentPane);
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("start_scan")) {
            return;
        }
        JComboBox cb = (JComboBox)e.getSource();
        String friendName = (String)cb.getSelectedItem();
        FriendContainer fc = (FriendContainer) friendsMap.get(friendName);
        if (fc != null) {
            updateLastCommunicationDateLabel(fc.getLastCommunicationDate());
            friendIDLabel.setText(fc.getId());
        } else {
            updateLastCommunicationDateLabel(null);
            friendIDLabel.setText(fc.getId());
        }
    }

    /**
     * @param object
     */
    private void updateLastCommunicationDateLabel(Date comDate) {
        if (comDate != null) {
            lastCommunicationTextLabel.setText("Date of last conversation: " + comDate.toString());
        } else {
            lastCommunicationTextLabel.setText("No conversations at all!");
        }
    }

}
