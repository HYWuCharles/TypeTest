import sun.applet.Main;

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
            "You will be give a bunch of randomly chosen words<br>" +
            "Please try to type all the words given to you<br>" +
            "But don't worry if you could not finish<br>" +
            "You can always go back or quit by clicking \"Finish\"<br>" +
            "You can start your test by clicking \"Physical keyboard\" if you are using a physical keyboard<br>" +
            "Or choose \"Touch keyboard\"<br>" +
            "There is no fixed order of these two options, you can start with whichever you want<br>" +
            "There will be a inside timer calculate the time you use<br>" +
            "Once you click start, the timer will start<br>" +
            "But there will be no time limit :)<br>" +
            "So enjoy XD</p></body></html>";

    private String userID;
    private String saveSpace;

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

    public String getUserID() {
        return userID;
    }

    private void initUI(){
        System.out.println("BeforeStart: " + saveSpace);
        JFrame jf = new JFrame("Before you start...");
        jf.setSize(MainInterFace.width, MainInterFace.height);
        jf.setLocationRelativeTo(null);
        JPanel jp = new JPanel(new BorderLayout());
        jf.setContentPane(jp);

        jf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
                MainInterFace mi = new MainInterFace();
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

        Box vbox = Box.createHorizontalBox();
        vbox.add(Box.createHorizontalStrut(600));
        vbox.add(pk);
        vbox.add(tk);
        vbox.add(back);
        jp.add(vbox, BorderLayout.SOUTH);

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
            MainInterFace mi = new MainInterFace(saveSpace);
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
