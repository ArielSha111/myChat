import javax.swing.*;
import java.awt.*; 
import java.awt.event.*; 
import java.net.*;
import java.io.*;
public class ChatPanel extends JPanel
{
    private JButton cmdJoinChat; 
    private JButton cmdLeaveChat;
    private JButton cmdLeaveProgrem;
    private JButton cmdUnmute;
    private JButton cmdMute;
    private JButton cmdClearChat;

    private  JTextArea type_area;
    private  JTextArea chat_area;
    private  JTextArea contacts_area;

    private ChatUserThreadInput in_thread;

    private JButton cmdSend; 
    private Color LIGHT_BLUE = new Color(51,153,255);

    private Socket socket = null;                       
    private String host ;
    private BufferedReader in =null; 
    private PrintWriter out = null;
    private String name;
    private boolean is_joined;
    private boolean mute[];
    private final int CONTACT_SIZE = 150;
    private final int PORT_NUM = 7777;

    /**
     * A constarctor for the chat panel to creat all the panels needed
     * on one main panel with the buttons the needs to be in use
     * 
     * @ param  String computer_name
     */
    public ChatPanel(String computer_name)
    {                                 
        host = computer_name;
        mute = new boolean[1];
        mute[0] = false;

        Listener lis = new Listener();
        cmdJoinChat = new JButton("join chat"); 
        cmdLeaveChat = new JButton("Leave chat");
        cmdLeaveProgrem = new JButton("leave program");
        cmdUnmute = new JButton("unmute");
        cmdMute = new JButton("mute");
        cmdClearChat = new JButton("clear chat");   
        cmdSend = new JButton("send");

        Color MY_GREEN = new Color(0,150,0);
        cmdSend.setBackground(MY_GREEN);

        cmdJoinChat.setBackground(LIGHT_BLUE);
        cmdLeaveChat.setBackground(LIGHT_BLUE);
        cmdLeaveProgrem.setBackground(LIGHT_BLUE);
        cmdUnmute.setBackground(LIGHT_BLUE);
        cmdMute.setBackground(LIGHT_BLUE);
        cmdClearChat.setBackground(LIGHT_BLUE);
        cmdSend.addActionListener(lis);

        cmdJoinChat.addActionListener(lis);
        cmdLeaveChat.addActionListener(lis);
        cmdLeaveProgrem.addActionListener(lis);
        cmdUnmute.addActionListener(lis);
        cmdMute.addActionListener(lis);
        cmdClearChat.addActionListener(lis);

        JPanel buttons_panel = new JPanel();
        buttons_panel.setLayout(new GridLayout(2,3));

        buttons_panel.add(cmdJoinChat);
        buttons_panel.add(cmdLeaveChat);
        buttons_panel.add(cmdLeaveProgrem);
        buttons_panel.add(cmdUnmute);
        buttons_panel.add(cmdMute);
        buttons_panel.add(cmdClearChat);

        Color MY_GRAY = new Color(204,204,204);

        JPanel center_panel = new JPanel();
        center_panel.setLayout(new GridLayout(2,1));

        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());

        chat_area = new JTextArea("hey this is the chat area");
        Color VERY_LIGHT_YELLOW = new Color(255,255,224);
        chat_area.setBackground(VERY_LIGHT_YELLOW);
        JPanel chat_panel = new MessagesPanel(chat_area);
        top.add(chat_panel, BorderLayout.CENTER);  

        contacts_area = new JTextArea("names:");
        JPanel contacts_panel = new ContactsPanel(contacts_area);
        contacts_panel.setPreferredSize(new Dimension(CONTACT_SIZE,CONTACT_SIZE)); 
        top.add(contacts_panel, BorderLayout.EAST);

        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1,2));

        type_area = new JTextArea("");
        type_area.setBackground(VERY_LIGHT_YELLOW);
        bottom.add(new TypePanel(type_area));
        bottom.add(new SendPanel(cmdSend));

        center_panel.add(top);
        center_panel.add(bottom);

        this.setLayout(new BorderLayout());
        this.add(buttons_panel, BorderLayout.SOUTH);
        this.add(center_panel, BorderLayout.CENTER);        
    }

    /**
     * A methos to add the user to the chat after everithing is built
     * 
     * @ param  String name,BufferedReader in, PrintWriter out
     */
    private  void addToChat(String name,BufferedReader in, PrintWriter out)
    {
        out.println(name); 
        in_thread = new ChatUserThreadInput(in, chat_area, contacts_area,mute);             
        in_thread.start();
    }

    private class Listener extends MouseAdapter implements ActionListener
    {
        private final int NAMES_END = 7;   
        /**
         * this method checks if a user name is valid. a non valid name may be an empty or null string 
         * or any of the program safe words such as names: or a name starts with '*' which can make problems when are used
         * 
         * @ param  String name
         */
        private  boolean isValidName(String name)
        {
            if(name==null)
            {
                JOptionPane.showMessageDialog(null,"error, try again");
                return false;
            }
            if(name.equals("")||name.equals("names:")||name.equals("/names:/"))
            {
                JOptionPane.showMessageDialog(null,"error, an empty string is invalid try again"); 
                return false;
            }
            for(int i=0;i<name.length();i++)
            {
                if(name.charAt(i)=='*')
                {
                    JOptionPane.showMessageDialog(null,"error, * is invalid character for the name try again");
                    return false;
                }
                if(name.charAt(i)==' ')
                {
                    JOptionPane.showMessageDialog(null,"error, space is  invalid character for the name try again");
                    return false;
                }
            }
            return true;
        }

        /**
         * this method checks if a message is valid. a non valid message may be an empty or null string 
         * or any of the program safe words such as names: 
         * 
         * @ param  String name
         */

        private  boolean isValidMsg(String message)
        {
            if(message.equals(""))
                return false;
            else if(message.length()>NAMES_END && (message.substring(0,NAMES_END+1).equals("/names:/")))
                return false;
            return true;
        }

        public void actionPerformed(ActionEvent e)
        {                                                            
            if(e.getSource() == cmdJoinChat)
            {
                if(is_joined)
                {
                    JOptionPane.showMessageDialog(null,"error, this chat is been activated already");  
                }
                else
                {
                    while(true)
                    {
                        String _name = JOptionPane.showInputDialog(null,"hey please enter your userName");
                        if(isValidName(_name))
                        {
                            is_joined = true;
                            String names = "";
                            try
                            { 
                                socket = new Socket(host, PORT_NUM);

                                in = new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()));

                                out = new PrintWriter(socket.getOutputStream(),true);
                                names = in.readLine();
                                while(true)
                                {
                                    if(isValidName(_name))
                                    {
                                        if(names.indexOf(_name)!=-1)
                                        {
                                            JOptionPane.showMessageDialog(null,"error, this name is taken try again"); 
                                            _name = JOptionPane.showInputDialog(null,"hey please enter your userName");
                                        }
                                        else
                                            break;
                                    }
                                }
                                name = _name;
                                addToChat(name,in,out);                        
                            }
                            catch(IOException ex)
                            {
                                is_joined = false;
                            }
                            break;
                        }
                    }
                }
            }
            else if(e.getSource()==cmdLeaveChat)
            {
                chat_area.setText("bey "+name);
                contacts_area.setText("");
                is_joined = false;
                in_thread.stop();
                out.println("");
            }
            else if(e.getSource()==cmdLeaveProgrem)
            {
                in_thread.stop();
                out.println("");                 
                System.exit(0);
            }
            else if(e.getSource()==cmdUnmute)
            { 
                mute[0] = false;
            }
            else if(e.getSource()==cmdMute)
            {
                mute[0] = true;
            }
            else if(e.getSource()==cmdClearChat)
            {
                chat_area.setText("");
            }
            else if(e.getSource() == cmdSend)
            {
                String message = type_area.getText();
                if(isValidMsg(message))
                {
                    if(message.charAt(message.length()-1)=='\n')
                        message+=" ";
                    out.println("from " + name + ":\n" + message+"\n ");
                }
                type_area.setText("");
            }
        }
    }
}
