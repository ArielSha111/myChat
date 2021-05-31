import javax.swing.*;
import java.awt.*; 
public class SendPanel extends JPanel
{
    private final int DIMENSIONS = 130;
    public SendPanel(JButton cmdSend)
    {
        JPanel send_panel = new JPanel();
        send_panel.setLayout(new BorderLayout()); 

        Color MY_GRAY = new Color(204,204,204);

        BackPanel2 bottom_background = new BackPanel2(MY_GRAY);

        bottom_background.setPreferredSize(new Dimension(DIMENSIONS,DIMENSIONS));

        JLabel jlabel = new JLabel("    type send to send the message:");
        jlabel.setOpaque(true);
        jlabel.setBackground(MY_GRAY);

        JPanel send_sub_panel = new JPanel();
        send_sub_panel.setLayout(new GridLayout(2,1));
        send_sub_panel.setBackground(MY_GRAY);

        JPanel button_panel = new JPanel();
        button_panel.setLayout(new GridLayout(1,3));
        button_panel.setBackground(MY_GRAY);

        button_panel.add(new BackPanel2(MY_GRAY));
        button_panel.add(cmdSend);
        button_panel.add(new BackPanel2(MY_GRAY));

        send_sub_panel.add(jlabel);
        send_sub_panel.add(button_panel);

        send_panel.add(send_sub_panel, BorderLayout.CENTER); 
        send_panel.add(new BackPanel2(MY_GRAY), BorderLayout.EAST);
        send_panel.add(new BackPanel2(MY_GRAY), BorderLayout.WEST);
        send_panel.add(new BackPanel2(MY_GRAY), BorderLayout.NORTH);
        send_panel.add(new BackPanel2(MY_GRAY), BorderLayout.SOUTH);
 

        this.setLayout(new BorderLayout());
        this.setLayout(new GridLayout(2,1));
        this.add(new BackPanel2(MY_GRAY));
        send_panel.setBackground(MY_GRAY);
        this.add(send_panel);
        this.setBackground(MY_GRAY);
    }
}
