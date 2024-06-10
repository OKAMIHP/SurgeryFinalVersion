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

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    public static ArrayList<OrganSlot> buildOrganSlots() {
        ArrayList<OrganSlot> slots = new ArrayList<>();
        // Placeholder implementation:
        slots.add(new OrganSlot(300, 10, 50, 50));
        slots.add(new OrganSlot(300, 70, 50, 50));
        slots.add(new OrganSlot(300, 130, 50, 50));
        // Add more slots as needed
        return slots;
    }
}