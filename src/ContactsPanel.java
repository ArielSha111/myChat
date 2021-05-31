import javax.swing.*;
import java.awt.*; 
public class ContactsPanel extends JPanel
{
    public ContactsPanel(JTextArea contacts_area)
    {
        JPanel contacts_panel = new JPanel();
        contacts_panel.setLayout(new BorderLayout()); 

        JPanel contacts_sub_panel = new JPanel();
        contacts_sub_panel.setLayout(new GridLayout(0,2));

        Color VERY_LIGHT_YELLOW = new Color(255,255,224);
        contacts_area.setBackground(VERY_LIGHT_YELLOW);
        
        Color MY_GRAY = new Color(204,204,204);

        contacts_sub_panel.add(new BackPanel2(MY_GRAY));
        contacts_sub_panel.add(contacts_area);

        JLabel jlabel = new JLabel("                        contacts are:");
        jlabel.setOpaque(true);
        jlabel.setBackground(MY_GRAY);

        contacts_panel.add(jlabel, BorderLayout.NORTH);
        contacts_panel.add(contacts_sub_panel, BorderLayout.CENTER);
        contacts_panel.add(new BackPanel2(MY_GRAY), BorderLayout.EAST);
        contacts_panel.add(new BackPanel2(MY_GRAY), BorderLayout.SOUTH);
        contacts_panel.add(new BackPanel2(MY_GRAY), BorderLayout.WEST);

        this.setLayout(new BorderLayout());
        this.add(contacts_panel, BorderLayout.CENTER);
    }
}
