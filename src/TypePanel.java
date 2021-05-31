import javax.swing.*;
import java.awt.*; 
public class TypePanel extends JPanel
{

    public TypePanel(JTextArea type_area)
    {
        JPanel type_panel = new JPanel();
        type_panel.setLayout(new BorderLayout());

        Color MY_GRAY = new Color(204,204,204);
        JLabel jlabel = new JLabel("   type your message here:");
        jlabel.setOpaque(true);
        jlabel.setBackground(MY_GRAY);

        

        JPanel type_sub_panel = new JPanel();
        type_sub_panel.setLayout(new GridLayout(2,1));

        type_sub_panel.add(jlabel);
        type_sub_panel.add(type_area);

        type_panel.add(type_sub_panel, BorderLayout.CENTER); 
        type_panel.add(new BackPanel2(MY_GRAY), BorderLayout.EAST);
        type_panel.add(new BackPanel2(MY_GRAY), BorderLayout.WEST);
        type_panel.add(new BackPanel2(MY_GRAY), BorderLayout.NORTH);
        type_panel.add(new BackPanel2(MY_GRAY), BorderLayout.SOUTH);

        this.setLayout(new BorderLayout());
        this.setLayout(new GridLayout(2,1));
        this.add(new BackPanel2(MY_GRAY));
        this.add(type_panel);
        this.setBackground(MY_GRAY);
    }
}
