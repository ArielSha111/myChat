import java.io.*;
import javax.swing.*;  

public class ChatUserThreadInput extends Thread
{
    private BufferedReader in =null;     
    private JTextArea chat_area;
    private JTextArea contacts_area;
    private boolean mute[];
    private String name;
    private String name_list;
    private final int NAMES_END = 7;

    public ChatUserThreadInput(BufferedReader in, JTextArea chat_area, JTextArea contacts_area, boolean mute[])
    {        
        this.in = in;    
        this.chat_area = chat_area;
        this.contacts_area =  contacts_area;
        this.mute = mute;
    }

    /**
     * A run method override to take care of all the needed input for the users
     */
    public void run()
    {
        String s = "";
        String input="";
        while(true)
        {                                
            try
            {  
                input = in.readLine()+"\n";
                if(input.length() > NAMES_END && input.substring(0,NAMES_END+1).equals("/names:/"))
                {
                    name_list = input.substring(NAMES_END+1);
                    String temp_list = "";
                    for(int i=0;i<name_list.length();i++)
                    {
                        if(name_list.charAt(i)=='*')
                        {
                            temp_list+="\n";
                        }
                        else
                        {
                            temp_list+=name_list.charAt(i);
                        }
                    }
                    contacts_area.setText("names:\n" + temp_list);
                }
                else
                {
                    if(mute[0]==true)
                        input="";
                    if(chat_area.getText().equals(""))
                        s="";
                    s+= input;            
                    chat_area.setText(s);
                }
            }
            catch(IOException e)
            {
                //ex.printStackTrace();
            }
        }            
    }
}
