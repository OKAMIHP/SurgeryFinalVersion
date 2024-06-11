import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {
    private boolean showWelcomeScreen = true;
    private ArrayList<Organ> organs;
    private Organ selectedOrgan;
    private Point dragOffset;
    private int lives = 4;
    private boolean operationFailed;
    private boolean gameWon = false;
    private Rectangle button;
    private static final int TOLERANCE = 20; // Snap-on application

    // Life counter images
    private BufferedImage[] lifeImages = new BufferedImage[4];

    // Patient stage images
    private BufferedImage[] patientStages = new BufferedImage[4];

    // Alarm image and timer
    private BufferedImage alarmImage;
    private boolean showAlarm = false;
    private Timer alarmTimer;

    // Tweezer image for custom cursor
    private BufferedImage tweezerImage;

    public DrawPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        organs = Organ.buildOrgans();
        button = new Rectangle(150, 300, 200, 50);
        this.addMouseListener(this);
        dragOffset = new Point();

        // Load life images
        try {
            lifeImages[0] = ImageIO.read(new File("Resources/0lives.png"));
            lifeImages[1] = ImageIO.read(new File("Resources/1live.png"));
            lifeImages[2] = ImageIO.read(new File("Resources/2lives.png"));
            lifeImages[3] = ImageIO.read(new File("Resources/3lives.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load patient stage images
        try {
            patientStages[0] = ImageIO.read(new File("Resources/Stage1.png"));
            patientStages[1] = ImageIO.read(new File("Resources/Stage2.png"));
            patientStages[2] = ImageIO.read(new File("Resources/Stage3.png"));
            patientStages[3] = ImageIO.read(new File("Resources/Dead.png")); // Add Dead stage
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load alarm image
        try {
            alarmImage = ImageIO.read(new File("Resources/alarm.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load tweezer image and set custom cursor
        try {
            tweezerImage = ImageIO.read(new File("Resources/Tweezer.png"));
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Cursor customCursor = toolkit.createCustomCursor(tweezerImage, new Point(0, 0), "Tweezer Cursor");
            setCursor(customCursor);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set initial positions of organs on the "table" side
        int tableX = 20; // Adjust this value for table placement
        int tableY = 50;
        int yOffset = 10;

        for (Organ o : organs) {
            o.setRectangleLocation(tableX, tableY);
            tableY += o.getImage().getHeight() + yOffset;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (showWelcomeScreen) {
            drawWelcomeScreen(g);
        } else {
            // Determine the index for selecting the patient stage image
            int patientStageIndex = 3 - lives; // Ensure the index is within bounds
            if (lives <= 0) {
                patientStageIndex = 3; // Display Dead stage when no lives left
            }
            // Draw the patient image
            BufferedImage patientImage = patientStages[patientStageIndex];
            if (patientImage != null) {
                int panelWidth = getWidth() / 2; // Adjust to draw only on one side
                int panelHeight = getHeight();
                g.drawImage(patientImage, panelWidth, 0, panelWidth, panelHeight, null);
            }

            // Draw the "table" side with organ slots
            for (Organ o : organs) {
                if (o.getHighlight()) {
                    g.drawRect(o.getOrganHitBox().x, o.getOrganHitBox().y, o.getImage().getWidth(), o.getImage().getHeight());
                }
                g.drawImage(o.getImage(), o.getOrganHitBox().x, o.getOrganHitBox().y, null);
            }

            // Draw organ slots
            for (Organ o : organs) {
                OrganSlot slot = o.getSlot();
                if (slot != null) {
                    g.drawImage(slot.getSlotSprite(), slot.getX(), slot.getY(), null);
                }
            }

            // Display life counter image
            g.drawImage(lifeImages[lives], getWidth() - lifeImages[lives].getWidth() - 10, 10, null);

            if (showAlarm) {
                g.drawImage(alarmImage, getWidth() / 2 - alarmImage.getWidth() / 2, 0, null);
            }
            if (gameWon) {
                g.setFont(new Font("Courier New", Font.BOLD, 20));
                g.drawString("Game Won!", getWidth() / 2 + 20, getHeight() / 2);
            }
        }
    }



    public void mousePressed(MouseEvent e) {
        if (gameWon || lives <= 0) return;
        if (showWelcomeScreen) {
            Point clicked = e.getPoint();
            if (button.contains(clicked)) {
                showWelcomeScreen = false; // Switch to the game screen
                repaint();
            }
        } else {
            Point clicked = e.getPoint();
            if (e.getButton() == MouseEvent.BUTTON1) {
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
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (selectedOrgan != null) {
            // Check if the part is placed near the correct slot
            Rectangle slot = selectedOrgan.getSlot().getRectangle();
            if (isNearSlot(selectedOrgan.getOrganHitBox().getBounds2D(), slot)) {
                // Center the part on the slot
                double slotCenterX = slot.getCenterX() - selectedOrgan.getOrganHitBox().getBounds().getWidth() / 2;
                double slotCenterY = slot.getCenterY() - selectedOrgan.getOrganHitBox().getBounds2D().getHeight() / 2;
                selectedOrgan.setRectangleLocation((int) slotCenterX, (int) slotCenterY);
                selectedOrgan.setCorrect(true);
                selectedOrgan.getSlot().setOccupied(true);
            } else {
                lives--;
                operationFailed = true;
                showAlarm = true;
                startAlarmTimer();
                if (lives == 0) {
                    JOptionPane.showMessageDialog(null, "Game Over!");
                    System.exit(0); // Terminate the application
                } else {
                    JOptionPane.showMessageDialog(null, "You touched the edge! Lives left: " + lives);
                }
            }
            selectedOrgan = null;
            repaint();
            checkWinCondition();
        }
    }

    private void startAlarmTimer() {
        if (alarmTimer != null) {
            alarmTimer.stop();
        }
        alarmTimer = new Timer(2000, e -> {
            showAlarm = false;
            repaint();
        });
        alarmTimer.setRepeats(false);
        alarmTimer.start();
    }

    public void performOperation() {
        if (organs.stream().allMatch(Organ::isCorrect)) {
            gameWon = true;
            JOptionPane.showMessageDialog(null, "Congratulations! You won the game!");
        } else {
            lives--;
            showAlarm = true;
            startAlarmTimer();
            if (lives <= 0) {
                operationFailed = true;
                JOptionPane.showMessageDialog(null, "Game Over! You have no more lives.");
                System.exit(0); // Terminate the application
            }
        }
        repaint();
    }

    private boolean isNearSlot(Rectangle2D partBounds, Rectangle2D slot) {
        double centerX = partBounds.getCenterX();
        double centerY = partBounds.getCenterY();
        double slotCenterX = slot.getCenterX();
        double slotCenterY = slot.getCenterY();
        return Math.abs(centerX - slotCenterX) <= TOLERANCE && Math.abs(centerY - slotCenterY) <= TOLERANCE;

    }


    private void checkWinCondition() {
        boolean allPlaced = true;
        for (Organ organ : organs) {
            if (!organ.isCorrect()) {
                allPlaced = false;
                break;
            }
        }
        if (allPlaced) {
            gameWon = true;
            JOptionPane.showMessageDialog(null, "Congratulations! You won the game!");
            repaint();
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (selectedOrgan != null) {
            int newX = e.getX() - dragOffset.x;
            int newY = e.getY() - dragOffset.y;
            selectedOrgan.setRectangleLocation(newX, newY);
            repaint();
        }
    }

    private void drawWelcomeScreen(Graphics g) {
        // Clear the background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the welcome message and instructions
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier New", Font.BOLD, 24));
        g.drawString("Welcome to the Organ Placement Game!", 100, 150);
        g.setFont(new Font("Courier New", Font.PLAIN, 18));
        g.drawString("Place each organ in its correct slot to complete the operation.", 100, 200);
        g.drawString("Click 'START OPERATION' to begin.", 100, 250);

        // Draw the "Start Operation" button
        g.drawRect(button.x, button.y, button.width, button.height);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("START OPERATION", button.x + 20, button.y + 30);
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }
}
