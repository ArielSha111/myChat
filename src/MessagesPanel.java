import javax.swing.*;
import java.awt.*; 
public class MessagesPanel extends JPanel
{
    public MessagesPanel(JTextArea chat_area)
    {
        JPanel chat_panel = new JPanel();
        chat_panel.setLayout(new BorderLayout()); 

        Color MY_GRAY = new Color(204,204,204);

        JLabel jlabel = new JLabel("   the chat is:");
        jlabel.setOpaque(true);
        jlabel.setBackground(MY_GRAY);

        chat_panel.add(jlabel, BorderLayout.NORTH);
        chat_panel.add(chat_area, BorderLayout.CENTER);
        chat_panel.add(new BackPanel2(MY_GRAY), BorderLayout.EAST);
        chat_panel.add(new BackPanel2(MY_GRAY), BorderLayout.SOUTH);
        chat_panel.add(new BackPanel2(MY_GRAY), BorderLayout.WEST);

        this.setLayout(new BorderLayout());
        this.add(chat_panel, BorderLayout.CENTER);
    }
}
