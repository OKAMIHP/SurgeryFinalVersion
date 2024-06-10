import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.awt.Font;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {

    private ArrayList<Organ> organs;
    private ArrayList<OrganSlot> organSlots;
    private Organ selectedOrgan;
    private Point dragOffset;
    private int lives = 3;
    private boolean operationFailed;
    private boolean gameWon = false;
    private Rectangle button;

    public DrawPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        organs = Organ.buildOrgans();
        organSlots = OrganSlot.buildOrganSlots();
        button = new Rectangle(147, 100, 160, 26);
        this.addMouseListener(this);
        dragOffset = new Point();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50;
        int y = 10;
        for (int i = 0; i < organs.size(); i++) {
            Organ o = organs.get(i);
            if (o.getHighlight()) {
                g.drawRect(x, y, o.getImage().getWidth(), o.getImage().getHeight());
            }
            o.setRectangleLocation(x, y);
            g.drawImage(o.getImage(), x, y, null);
            x = x + o.getImage().getWidth() + 10;
        }
        for (OrganSlot slot : organSlots) {
            g.drawRect((int) slot.getRectangle().getX(), (int) slot.getRectangle().getY(), (int) slot.getRectangle().getWidth(), (int) slot.getRectangle().getHeight());
        }
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("START OPERATION", 150, 120);
        g.drawRect((int) button.getX(), (int) button.getY(), (int) button.getWidth(), (int) button.getHeight());

        // Display game state
        g.drawString("Lives: " + lives, 50, 200);
        if (operationFailed) {
            g.drawString("Operation Failed!", 50, 250);
        }
        if (gameWon) {
            g.drawString("Game Won!", 50, 250);
        }
    }

    public void mousePressed(MouseEvent e) {
        Point clicked = e.getPoint();
        if (e.getButton() == 1) {
            if (button.contains(clicked)) {
                if (!gameWon && lives > 0) {
                    performOperation();
                }
            } else {
                for (Organ o : organs) {
                    if (o.getOrganHitBox().contains(clicked)) {
                        selectedOrgan = o;
                        dragOffset.setLocation(clicked.x - o.getOrganHitBox().x, clicked.y - o.getOrganHitBox().y);
                        break;
                    }
                }
            }
        }

        repaint();
    }

    public void mouseReleased(MouseEvent e) {
        if (selectedOrgan != null) {
            for (OrganSlot slot : organSlots) {
                if (slot.getRectangle().contains(selectedOrgan.getOrganHitBox().getLocation()) && !slot.isOccupied()) {
                    selectedOrgan.setRectangleLocation((int) slot.getRectangle().getX(), (int) slot.getRectangle().getY());
                    slot.setOccupied(true);
                    selectedOrgan.setCorrect(true);
                    break;
                }
            }
            selectedOrgan = null;
        }
        repaint();
    }

    public void performOperation() {
        if (organs.stream().allMatch(Organ::isCorrect)) {
            gameWon = true;
        } else {
            lives--;
            if (lives <= 0) {
                operationFailed = true;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) { }
    public void mouseMoved(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}