import java.awt.Rectangle;
import java.util.ArrayList;

public class OrganSlot {
    private Rectangle rectangle;
    private boolean occupied;

    public OrganSlot(int x, int y, int width, int height) {
        this.rectangle = new Rectangle(x, y, width, height);
        this.occupied = false;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
    public int getX()
    {
        return rectangle.x;
    }
    public int getY()
    {
        return rectangle.y;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

// designateOrgan designates an organ (organ parameter) through
// reoccuring conditionals that if organ's coordinates are near
// an organSlot's coordinates, then
}