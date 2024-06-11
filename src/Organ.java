import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Organ {
    private BufferedImage image;
    private Rectangle organHitBox;
    private String imageFileName;
    private boolean highlight;
    private boolean correct;
    private OrganSlot organSlot;
    private static final int ORGAN_WIDTH = 50; // Define the width of the scaled organ

    public Organ(int x, int y, String imageTitle, int slotX, int slotY, String slotSprite) {
        this.imageFileName = imageTitle;
        this.image = readImage();
        this.image = scaleImage(image, ORGAN_WIDTH); // Scale the image
        this.organHitBox = new Rectangle(x, y, image.getWidth(), image.getHeight());
        this.highlight = false;
        this.correct = false;
        this.organSlot = new OrganSlot(slotX, slotY, image.getWidth(), image.getHeight(), slotSprite);
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean getHighlight() {
        return highlight;
    }

    public Rectangle getOrganHitBox() {
        return organHitBox;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public void setRectangleLocation(int x, int y) {
        organHitBox.setLocation(x, y);
    }

    public void flipHighlight() {
        highlight = !highlight;
    }

    private BufferedImage readImage() {
        try {
            return ImageIO.read(new File(imageFileName));
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public OrganSlot getSlot() {
        return organSlot;
    }

    // Method to scale the image while preserving its aspect ratio
    private BufferedImage scaleImage(BufferedImage originalImage, int targetWidth) {
        double scaleFactor = (double) targetWidth / originalImage.getWidth();
        int targetHeight = (int) (originalImage.getHeight() * scaleFactor);
        Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(scaledImage, 0, 0, null);
        g.dispose();
        return resizedImage;
    }

    public static ArrayList<Organ> buildOrgans() {
        ArrayList<Organ> organs = new ArrayList<>();
        organs.add(new Organ(100, 100, "Resources/Brain.png", 1420, 50, "Resources/BrainSlot.png"));
        organs.add(new Organ(50, 100, "Resources/Foot.png", 1500, 950,"Resources/FootSlot.png"));
        organs.add(new Organ(50, 150, "Resources/Hand.png", 1730, 500,"Resources/ArmSlot.png"));
        organs.add(new Organ(50, 200, "Resources/Heart.png", 1500, 350,"Resources/HeartSlot.png"));
        organs.add(new Organ(50, 250, "Resources/Kidney.png", 1350, 430,"Resources/KidneySlot.png"));
        organs.add(new Organ(50, 300, "Resources/LowerIntestine.png", 1420, 600,"Resources/LowerIntestineSlot.png"));
        organs.add(new Organ(50, 350, "Resources/RightArm.png", 1200, 390,"Resources/RightArmSlot.png"));
        return organs;
    }


    public int getX() {
        return organHitBox.x;
    }
    public int getY()
    {
        return organHitBox.y;
    }
}
