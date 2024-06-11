// MainFrame.java
import javax.swing.JFrame;

public class MainFrame extends JFrame implements Runnable {
    private DrawPanel p;
    private Thread windowThread;

    public MainFrame(String display) {
        int frameWidth = 1920;
        int frameHeight = 1080;
        p = new DrawPanel();
        this.add(p);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameWidth, frameHeight);
        this.setLocation(0, 0);
        this.setVisible(true);
        startThread();
    }

    public void startThread() {
        windowThread = new Thread(this);
        windowThread.start();
    }

    public void run() {
        while (true) {
            p.repaint();
        }
    }
}