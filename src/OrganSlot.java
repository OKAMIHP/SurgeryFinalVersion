import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class OrganSlot {
    private Rectangle rectangle;
    private boolean occupied;
    private BufferedImage slotSprite;
    private String imageTitle;
    private static final int SLOT_WIDTH = 50;

    public OrganSlot(int x, int y, int width, int height, String imageTitle) {
        this.imageTitle = imageTitle;
        this.slotSprite = readImage(); // Load the image
        if (this.slotSprite != null) { // Check if the image was loaded successfully
            this.slotSprite = scaleImage(slotSprite, SLOT_WIDTH);
        }
        this.rectangle = new Rectangle(x, y, width, height);
        this.occupied = false;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    private BufferedImage readImage() {
        try {
            return ImageIO.read(new File(imageTitle));
        } catch (IOException e) {
            e.printStackTrace(); // Print the error message
            return null; // Return null if image loading fails
        }
    }

    private BufferedImage scaleImage(BufferedImage originalImage, int targetWidth) {
        if (originalImage == null) {
            return null; // Return null if the original image is null
        }
        double scaleFactor = (double) targetWidth / originalImage.getWidth();
        int targetHeight = (int) (originalImage.getHeight() * scaleFactor);
        Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(scaledImage, 0, 0, null);
        g.dispose();
        return resizedImage;
    }

    public int getX() {
        return rectangle.x;
    }

    public int getY() {
        return rectangle.y;
    }

    public int getWidth() {
        return rectangle.width;
    }

    public int getHeight() {
        return rectangle.height;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void setCoordinates(int x, int y) {
        rectangle.setLocation(x, y);
    }

    public Image getSlotSprite() {
        return slotSprite;
    }
}
