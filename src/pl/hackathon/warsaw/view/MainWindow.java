/**
 * created on 17:34:12 25 paź 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
package pl.hackathon.warsaw.view;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.Policy.Parameters;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import facebook4j.Friend;
import pl.hackathon.warsaw.Constants;
import pl.hackathon.warsaw.FBFriendListCommunicationDateChecker;
import pl.hackathon.warsaw.FacebookConnector;
import pl.hackathon.warsaw.FriendContainer;

public class MainWindow extends JFrame implements ActionListener{
    
    HashMap<String, FriendContainer> friendsMap;
    Vector<String> friendsNames;
    Vector<String> friendsToRemoveNames;
    Vector<String> friendsWithoutConversationNames;
    {
        friendsNames = new Vector<String>();
        friendsToRemoveNames = new Vector<String>();
        friendsWithoutConversationNames = new Vector<String>();
    }
    
    JPanel contentPane;
    JComboBox friendsList;
    JLabel friendsListLabel;
    JLabel lastCommunicationTextLabel;
    JLabel friendIDLabel;
    
    JLabel friendPictureLabel;
    
    JLabel friendsToRemoveLabel;
    JComboBox friendsToRemoveComboBox;
    JLabel friendsWithoutConversationLabel;
    JComboBox friendsWithoutConversationComboBox;

    JTextField tokenField;
    JButton startButton;
    
    JLabel topFriendLabel;
    
    JTextField ContactAddressField;
    JLabel ContactAddressLabel;
    JButton openUrl;
    /**
     * @throws HeadlessException
     */
    public MainWindow() throws HeadlessException {
        setTitle("FBCleaner");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setResizable(false);
        setSize(750,500);
        // String url = getDocumentBase().toString();
        /*
        if (url.indexOf("?") > -1) {
            String paramaters = url.substring(url.indexOf("?") + 1);
            String token = paramaters.replace("token=", "");
            if (!token.isEmpty()) {
                Constants.setToken(token);
            }
        }*/
        this.setLayout(null);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        
        tokenField = new JTextField();
        tokenField.setLocation(25,25);
        tokenField.setSize(300,25);
        tokenField.setEnabled(true);
        tokenField.setEditable(true);
        contentPane.add(tokenField);
        startButton = new JButton("Get Friends");
        startButton.setSize(100, 25);
        startButton.setLocation(350, 25);
        startButton.setActionCommand("start_scan");
        startButton.addActionListener(this);
        contentPane.add(startButton);
        friendsListLabel = new JLabel("Friends List:");
        friendsListLabel.setLocation(25,50);
        friendsListLabel.setSize(250, 20);
        contentPane.add(friendsListLabel);
        
        friendsList = new JComboBox(friendsNames);
        friendsList.addActionListener(this);
        friendsList.setLocation(25,75);
        friendsList.setSize(250, 20);
        contentPane.add(friendsList);
        
        friendsToRemoveLabel = new JLabel("Friends with last conversation before "+Constants.oldestCommunitactionDate.toString()+" :");
        friendsToRemoveLabel.setLocation(25,100);
        friendsToRemoveLabel.setSize(600, 20);
        contentPane.add(friendsToRemoveLabel);
        friendsToRemoveComboBox = new JComboBox(friendsToRemoveNames);
        friendsToRemoveComboBox.addActionListener(this);
        friendsToRemoveComboBox.setLocation(25,125);
        friendsToRemoveComboBox.setSize(250, 20);
        contentPane.add(friendsToRemoveComboBox);
        
        friendsWithoutConversationLabel = new JLabel("Friends you have never spoken to"); 
        friendsWithoutConversationLabel.setLocation(25,150);
        friendsWithoutConversationLabel.setSize(250, 20);
        contentPane.add(friendsWithoutConversationLabel);
        
        friendsWithoutConversationComboBox = new JComboBox(friendsWithoutConversationNames);
        friendsWithoutConversationComboBox.addActionListener(this);
        friendsWithoutConversationComboBox.setLocation(25,175);
        friendsWithoutConversationComboBox.setSize(250, 20);
        contentPane.add(friendsWithoutConversationComboBox);
        
        lastCommunicationTextLabel = new JLabel("No conversations at all!");
        lastCommunicationTextLabel.setLocation(320,75);
        lastCommunicationTextLabel.setSize(600, 20);
        contentPane.add(lastCommunicationTextLabel);
        ContactAddressLabel = new JLabel("Address Link:");
        ContactAddressLabel.setLocation(25, 200);
        ContactAddressLabel.setSize(250,20);
        contentPane.add(ContactAddressLabel);
        ContactAddressField = new JTextField();
        ContactAddressField.setEnabled(true);
        ContactAddressField.setEditable(false);
        ContactAddressField.setLocation(25, 225);
        ContactAddressField.setSize(400,20);
        contentPane.add(ContactAddressField);
        
        openUrl = new JButton("Open FaceBook Page");
        openUrl.setSize(200,20);
        openUrl.setLocation(445, 225);
        openUrl.setActionCommand("open");
        openUrl.addActionListener(this);
        contentPane.add(openUrl);
        
        topFriendLabel = new JLabel();
        topFriendLabel.setLocation(25, 250);
        topFriendLabel.setSize(400, 20);
        contentPane.add(topFriendLabel);
        
        friendPictureLabel = new JLabel();
        friendPictureLabel.setSize(140, 140);
        friendPictureLabel.setLocation(25, 275);
        friendPictureLabel.setIcon(new ImageIcon((new ImageIcon("logo.png")).getImage().getScaledInstance(140, 140,
                java.awt.Image.SCALE_SMOOTH)));
        contentPane.add(friendPictureLabel);
        
        this.setContentPane(contentPane);
        
    }
    
    
    public void init() {
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("start_scan")) {
            if (tokenField.getText()!=null && !tokenField.getText().isEmpty() && tokenField.getText().trim().length() != 0) {
                Constants.setToken(tokenField.getText());
            }
            FacebookConnector fc = new FacebookConnector();
            friendsMap = fc.getFriendsMap();
            friendsNames = new Vector<String>(friendsMap.keySet());
            friendsToRemoveNames = fc.getFriendsToRemoveNames();
            friendsWithoutConversationNames = fc.getFriendsWithoutConversationNames();
            java.util.Collections.sort(friendsNames);
            friendsList.setModel(new DefaultComboBoxModel<String>(friendsNames));
            friendsToRemoveComboBox.setModel(new DefaultComboBoxModel<String>(friendsToRemoveNames));
            friendsWithoutConversationComboBox.setModel(new DefaultComboBoxModel<String>(friendsWithoutConversationNames));
            Collection<FriendContainer> friends = friendsMap.values();
            int topMsgCount = 0;
            int secondTopMsgCount = 0;
            String topName = "";
            String secondTopName = "";
            for (FriendContainer fcon : friends) {
                if (fcon.getMsgCount() > topMsgCount) {
                    secondTopMsgCount = topMsgCount;
                    secondTopName = topName;
                    topMsgCount = fcon.getMsgCount();
                    topName = fcon.getName();
                }
            }
            topFriendLabel.setText("Your best friend is " + secondTopName + " with " + secondTopMsgCount + " conversations!");
            
            this.repaint();
            return;
        }
        if (e.getActionCommand().equals("open")) {
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URI(ContactAddressField.getText()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return;
        }
        JComboBox cb = (JComboBox)e.getSource();
        String friendName = (String)cb.getSelectedItem();
        FriendContainer fc = (FriendContainer) friendsMap.get(friendName);
        if (fc != null) {
            updateLastCommunicationDateLabel(fc.getLastCommunicationDate());
            ContactAddressField.setText("http://www.facebook.com/" + fc.getId());
            ContactAddressLabel.setText(fc.getName() + " Address Link:");
            if (fc.getId() != null && !fc.getId().isEmpty() && fc.getId().trim().length() != 0) {
                try {
                File destinationFile = new File("img.jpg");
                
                HttpURLConnection con = (HttpURLConnection)(new URL( "http://graph.facebook.com/" + fc.getId() + "/picture?width=140&height=140"  ).openConnection());
                con.setInstanceFollowRedirects( false );
                con.connect();
                int responseCode = con.getResponseCode();
                System.out.println( responseCode );
                String location = con.getHeaderField( "Location" );
                System.out.println( location );
                
                java.net.URL url;
                url = new URL(location);
                InputStream is = url.openStream();
                OutputStream os= new FileOutputStream(destinationFile);
    
                byte[] b = new byte[2048];
                int length;
    
                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }
                os.close();
                is.close();
                friendPictureLabel.setIcon(new ImageIcon((new ImageIcon("img.jpg")).getImage().getScaledInstance(140, 140,
                        java.awt.Image.SCALE_SMOOTH)));
                this.repaint();
                } catch ( IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
                
            }
        } else {
            updateLastCommunicationDateLabel(null);
            ContactAddressField.setText("http://www.facebook.com/" + fc.getId());
            ContactAddressLabel.setText(fc.getName() + " Address Link:");
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
    

public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow ex = new MainWindow();
                ex.setVisible(true);
            }
        });
    }

}
