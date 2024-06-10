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

    public Organ(int x, int y, String imageTitle, Shape shape)
    {
        this.shape = shape;
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
    public static ArrayList<Organ> buildOrgans() {
        Brain brain = new Brain(100, 100);
        Foot foot = new Foot(0, 0);

        ArrayList<Organ> Organs = new ArrayList<>();
        Organs.add(brain);
        Organs.add(foot);
        Organs.add(Hand);
        Organs.add(Heart);
        Organs.add(Kidney);
        Organs.add(LowerIntestine);
        Organs.add(RightArm);
        return Organs;
    }

}
