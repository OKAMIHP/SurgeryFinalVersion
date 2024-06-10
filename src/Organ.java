import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Rectangle;
public class Organ {
    private BufferedImage image;
    private Rectangle organHitBox;
    private String imageFileName;
    private boolean highlight;
    private boolean correct;

    public Organ(int x, int y, String imageTitle)
    {
        this.imageFileName = imageTitle;
        this.image = readImage();
        this.organHitBox = new Rectangle(-100, -100, image.getWidth(), image.getHeight());
        this.highlight = false;
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
        ArrayList<Organ> Organs = new ArrayList<>();
        Organs.add(Brain);
        Organs.add(Foot);
        Organs.add(Hand);
        Organs.add(Heart);
        Organs.add(Kidney);
        Organs.add(LowerIntestine);
        Organs.add(RightArm);
        return Organs;
    }

}
