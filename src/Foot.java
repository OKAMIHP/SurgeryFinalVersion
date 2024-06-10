import java.awt.Image;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Foot extends Organ {
    private BufferedImage image;
    private Rectangle organHitBox;
    private String imageFileName;
    private boolean highlight;
    private boolean correct;
    public Foot(int x, int y) {
        this.imageFileName = "Resources/Foot.png";
        this.image = readImage();
        this.organHitBox = new Rectangle(-200, -200, image.getWidth(), image.getHeight());
        this.highlight = false;
    }
}