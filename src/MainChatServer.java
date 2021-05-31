import java.net.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;  
public class MainChatServer
{       
    private static ArrayList<String> user_names = new ArrayList<String>();
    private static ArrayList<PrintWriter> outs = new ArrayList<PrintWriter>();
    private static final int PORT_NUM = 7777;

    public static void main(String[]args)
    {
        ServerSocket srv = null;
        try
        {
            srv = new ServerSocket(PORT_NUM);  
            Socket socket = null;
            BufferedReader user_in = null;
            PrintWriter user_out = null;
            JOptionPane.showMessageDialog(null,"chat is active");
            while(true)
            {
                socket = srv.accept();

                user_in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

                user_out = new PrintWriter(socket.getOutputStream(), true);
                
                user_out.println(user_names.toString());
                
                String user_name = user_in.readLine();
                user_names.add(user_name);
                
                ChatServerThread new_user = new ChatServerThread(user_in,  user_name, user_out, outs, user_names, user_out);                
                new_user.start();
            }
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null,"couldn't open chat");
        }
    }
}
