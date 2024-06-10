import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
// fix the shape-rectangle dilemma

public class Organ {
    private Shape shape;
    private BufferedImage image;
    private Rectangle organHitBox;
    private String imageFileName;
    private boolean highlight;
    private boolean correct;
    private OrganSlot organSlot;

    public Organ(int x, int y, String imageTitle, int slotX, int slotY)
    {;
        this.organSlot = new OrganSlot(slotX, slotY,image.getWidth(), image.getHeight());
        this.imageFileName = imageTitle;
        this.image = readImage();
        this.organHitBox = new Rectangle(x, y, image.getWidth(), image.getHeight());
        this.highlight = false;

    }

    public Organ() {
    }

    public BufferedImage getImage() {
        return image;
    }
    public boolean getHighlight()
    {
        return highlight;
    }
    public Rectangle getOrganHitBox()
    {
        return organHitBox;
    }
    public boolean isCorrect()
    {
        return correct;
    }

    public void setCorrect(boolean correct)
    {
        this.correct = correct;
    }

    public void setRectangleLocation(int x, int y)
    {
        Rectangle2D bounds = shape.getBounds2D(); // implements boundary around sprite
        double width = bounds.getWidth();
        double height = bounds.getHeight();
        shape = new Rectangle2D.Double(x, y, width, height);
        organHitBox.setLocation(x,y);
    }

    public void flipHighlight() {
        highlight = !highlight;
    }

    public BufferedImage readImage() {
        try {
            BufferedImage image;
            image = ImageIO.read(new File(imageFileName));
            return image;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
    public int getX()
    {
        return organHitBox.x;
    }
    public int getY()
    {
        return organHitBox.y;
    }

    public OrganSlot getSlot()
    {
        return organSlot;
    }
    public static ArrayList<Organ> buildOrgans() {
        Organ brain = new Organ(100, 50, "Resources/Brain.png", -50, 0);
        Organ foot = new Organ(100, 0, "Resources/Foot.png", -100, 50);
        Organ hand = new Organ(100, 150, "Resources/Hand.png", -150, 100);
        Organ heart = new Organ(100, 200, "Resources/Heart.png", -200, 150);
        Organ kidney = new Organ(100, 100, "Resources/Kidney.png", -250, 200);
        Organ lowerIntestine = new Organ(100,250, "Resources/LowerIntestine.png", -300, 250);
        Organ rightArm = new Organ(100, 300, "Resources/RightArm.png", -350, 300);
        ArrayList<Organ> Organs = new ArrayList<>();
        Organs.add(brain);
        Organs.add(foot);
        Organs.add(hand);
        Organs.add(heart);
        Organs.add(kidney);
        Organs.add(lowerIntestine);
        Organs.add(rightArm);
        return Organs;
    }

}
