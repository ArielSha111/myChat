import java.net.*;
import java.io.*;  
import java.util.ArrayList;
public class ChatServerThread extends Thread
{
    private Socket socket = null;
    private PrintWriter user_out;
    private BufferedReader user_in;
    private String user_name;
    private ArrayList<PrintWriter> outs;
    private  ArrayList<String> user_names;
    private PrintWriter out;

    public ChatServerThread(BufferedReader user_in, String user_name, PrintWriter user_out,ArrayList<PrintWriter> outs, ArrayList<String> user_names,PrintWriter out)
    {          
        this.user_in = user_in;
        this.user_name = user_name;
        this.user_out = user_out;                        
        this.outs = outs;   
        this.user_names = user_names;
        this.out = out;
    }

    /**
     * A run override method to run the server of the chat by
     * receiving messages send them to all the clients
     *
     */
    public void run()
    {
        boolean first=true;
        String message=" ";
        try
        {
            while(true)
            {
                if(first)
                {
                    first=false;
                    out.println("welcome " + user_name);
                    out.println("/names:/"+getNames());
                    joinedNotifyAll(user_name);
                    outs.add(user_out);
                }
                else
                {
                    message = user_in.readLine(); 
                    if(message.equals(""))
                        break;
                    messageAll(message); 
                }
            }
            leftNotifyAll(user_name);
        }
        catch(IOException e){} 
    }

    /**
     * A method to return all the names of the chat clients
     *
     *@return   String s the name list
     */
    private String getNames()
    {
        String s = "";
        for(int i = 0;i<user_names.size();i++)
        {
            s+=user_names.get(i)+'*';
        }
        return s;
    }

    /**
     * A method that updates the chat clients list for all the chat clients
     *
     */
    private void namesUpdateForAll()
    {
        String names = getNames();
        for(int i = 0;i<outs.size();i++)
        {
            outs.get(i).println("/names:/"+names);
        }
    }

    /**
     * A method that send a message to all the clients and let them know about the new joined client
     *
     */
    private void joinedNotifyAll(String name)
    {
        for(int i = 0;i<outs.size();i++)
        {
            outs.get(i).println(name + " joined");
        }
        namesUpdateForAll();
    }

    /**
     * A method that send a message to all the clients 
     * and let them know who left the chat
     * 
     * @ param  String name
     *
     */
    private void leftNotifyAll(String name)
    {
        for(int i = 0;i<user_names.size();i++)
        {
            if(user_names.get(i).equals(name))
            {
                user_names.remove(i);
                outs.remove(i);
                break;
            }
        }

        for(int i = 0;i<outs.size();i++)
        {            
            outs.get(i).println(name + " left");
        }
        namesUpdateForAll();
    }

    /**
     * A method that send a message to all the clients 
     * according to the string it gets
     * 
     * @ param  String msg
     *
     */
    private void messageAll(String msg)
    {
        for(int i = 0;i<outs.size();i++)
        {
            outs.get(i).println(msg);
        }
    }
}
