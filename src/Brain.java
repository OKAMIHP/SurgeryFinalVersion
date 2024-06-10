import java.awt.Image;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Brain extends Organ {
    private BufferedImage image;
    private Rectangle organHitBox;
    private String imageFileName;
    private boolean highlight;
    private boolean correct;
    public Brain(int x, int y) {
        super();
        this.imageFileName = "Resources/Brain.png";
        this.image = readImage();
        this.organHitBox = new Rectangle(-200, -200, image.getWidth(), image.getHeight());
        this.highlight = false;
    }
}