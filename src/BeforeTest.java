import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by ComingWind on 2018/11/19.
 */
public class BeforeTest {

    private String instructions = "<html><body><p align=\"center\">Welcome to Test Mode!<br>" +
            "In this mode, you can get start to know this typing test<br>" +
            "Your typing result will not be recorded<br>" +
            "The words given will be random as in real typing test<br>" +
            "And also in this mode there is no time counting<br>" +
            "So enjoy typing :)</p></body></html>";

    private String saveSpace;
    private JFrame mi = null;

    public BeforeTest(String saveSpace){
        this.saveSpace = saveSpace;
        initUI();
    }

    public BeforeTest(String saveSpace, JFrame mi){
        this.saveSpace = saveSpace;
        this.mi = mi;
        initUI();
    }

    private void initUI(){
        System.out.println("BeforeTest: " + saveSpace);
        JFrame jf = new JFrame("Before you start...");
        jf.setSize(MainInterFace.width, MainInterFace.height);
        jf.setLocationRelativeTo(null);
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel jp = new JPanel(new BorderLayout());
        jf.setContentPane(jp);
        loadInstruction(jp);
        addButtons(jp, jf);
        jf.setResizable(false);

        jf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
                if (mi != null){
                    jf.setVisible(true);
                    jf.dispose();
                }else{
                    MainInterFace mi = new MainInterFace(saveSpace);
                }
            }
        });

        jf.setVisible(true);
    }

    private void loadInstruction(JPanel jp){
        JLabel ins = new JLabel();
        ins.setFont(new Font(null, Font.PLAIN, 20));
        ins.setHorizontalAlignment(SwingConstants.CENTER);
        ins.setVerticalAlignment(SwingConstants.CENTER);
        ins.setText(instructions);
        jp.add(ins, BorderLayout.CENTER);
    }

    private void addButtons(JPanel jp, JFrame jf){
        JButton back = new JButton("Back");
        JButton forward = new JButton("Start");

        Box vbox = Box.createHorizontalBox();
        vbox.add(Box.createHorizontalStrut(840));
        vbox.add(back);
        vbox.add(forward);

        back.addActionListener(new backButtonActionListener(jf));
        forward.addActionListener(new forwardButtonActionListener(jf));

        jp.add(vbox, BorderLayout.SOUTH);
    }

    private class backButtonActionListener implements ActionListener{
        private JFrame jf;

        public backButtonActionListener(JFrame jf){
            this.jf = jf;
        }

        public void actionPerformed(ActionEvent event){
            if (mi != null){
                mi.setVisible(true);
            }else{
                MainInterFace mi = new MainInterFace(saveSpace);
            }
            this.jf.dispose();
        }
    }

    private class forwardButtonActionListener implements ActionListener{
        private JFrame jf;

        public forwardButtonActionListener(JFrame jf){
            this.jf = jf;
        }
        public void actionPerformed(ActionEvent event){
            TestUI testUI = new TestUI(saveSpace);
            this.jf.dispose();
        }
    }

}
