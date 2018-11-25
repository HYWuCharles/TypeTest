import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by ComingWind on 2018/11/19.
 */
public class BeforeStart {
    private String instructions = "<html><body><p align=\"center\">Welcome to Typing Test!<br>" +
            "In this mode, your typing data will be collected<br>" +
            "You will be given a hundred randomly chosen words from common used English words<br>" +
            "Please try to type all the words given to you<br>" +
            "But don't worry if you could not finish<br>" +
            "You can always go back or quit by clicking \"Finish\"<br>" +
            "You can start your test by clicking \"Physical keyboard\" if you are using a physical keyboard<br>" +
            "Or choose \"Touch keyboard\"<br>" +
            "In physical keyboard mode, words will be give in the center of the right half of screen<br>" +
            "And in virtual keyboard mode, words will be give on the top of the right half of screen<br>" +
            "There is no fixed order of these two options, you can start with whichever you want<br>" +
            "There will be a inside timer calculate the time you use<br>" +
            "Once you click start, the timer will start<br>" +
            "But there will be no time limit :)<br>" +
            "And you don't need to type \"Return\" when finish one line<br>" +
            "All new lines are automatically generated when reach the end of previous line<br>" +
            "So enjoy XD</p></body></html>";

    private String userID;
    private String saveSpace;
    private MainInterFace mi = null;

    public BeforeStart(String userID){
        this.userID = userID;
        this.saveSpace = MainInterFace.saveSpace;
        initUI();
    }

    public BeforeStart(String userID, String saveSpace){
        this.userID = userID;
        this.saveSpace = saveSpace;
        initUI();
    }

    public BeforeStart(String userID, String saveSpace, MainInterFace mi){
        this.userID = userID;
        this.saveSpace = saveSpace;
        this.mi = mi;
        initUI();
    }

    public String getUserID() {
        return userID;
    }

    private void initUI(){
        System.out.println("BeforeStart: " + saveSpace);
        JFrame jf = new JFrame("Before you start...");
        jf.setSize(MainInterFace.width, MainInterFace.height);
        jf.setLocationRelativeTo(null);
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel jp = new JPanel(new BorderLayout());
        jf.setContentPane(jp);
        jf.setResizable(false);

        jf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
                if (mi != null){
                    mi.jf.setVisible(true);
                }else{
                    MainInterFace mi = new MainInterFace(saveSpace);
                }
            }
        });

        loadInstructions(jp);
        loadButtons(jp, jf);

        jf.setVisible(true);
    }

    private void loadInstructions(JPanel jp){
        JLabel ins = new JLabel();
        ins.setVerticalAlignment(SwingConstants.CENTER);
        ins.setHorizontalAlignment(SwingConstants.CENTER);
        ins.setFont(new Font(null, Font.PLAIN, 20));
        ins.setText(instructions);
        jp.add(ins, BorderLayout.CENTER);
    }

    private void loadButtons(JPanel jp, JFrame jf){
        JButton pk = new JButton("Physical Keyboard");
        JButton tk = new JButton("Touch Keyboard");
        JButton back = new JButton("Back");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int width = dimension.width;
        Box vbox = Box.createHorizontalBox();
        vbox.add(Box.createHorizontalStrut(2*width/3-400));
        vbox.add(pk);
        vbox.add(tk);
        vbox.add(back);
        vbox.setPreferredSize(new Dimension(width, 100));
        jp.add(vbox, BorderLayout.NORTH);

        back.addActionListener(new backButtonActionListener(jf));
        pk.addActionListener(new pkButtonActionListener(jf));
        tk.addActionListener(new tkButtonActionListener(jf));
    }

    private class backButtonActionListener implements ActionListener{
        private JFrame jf;

        public backButtonActionListener(JFrame jf){
            this.jf = jf;
        }

        public void actionPerformed(ActionEvent event){
            if (mi != null){
                mi.jf.setVisible(true);
            }else{
                MainInterFace mi = new MainInterFace(saveSpace);
            }
            jf.dispose();
        }
    }

    private class pkButtonActionListener implements ActionListener{
        private JFrame jf;

        public pkButtonActionListener(JFrame jf){
            this.jf = jf;
        }

        public void actionPerformed(ActionEvent event){
            PKUI pkui = new PKUI(0, getUserID(), saveSpace);
            jf.dispose();
        }
    }

    private class tkButtonActionListener implements ActionListener{
        private JFrame jf;

        public tkButtonActionListener(JFrame jf){
            this.jf = jf;
        }

        public void actionPerformed(ActionEvent event){
            PKUI pkui = new PKUI(1, getUserID(), saveSpace);
            jf.dispose();
        }
    }
}
