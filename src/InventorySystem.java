import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class InventorySystem extends JPanel implements MouseListener, MouseMotionListener {

    ArrayList<Item> items;
    Item dragging;
    boolean drag = false;
    int[] xCords = {20, 75, 130, 185};
    int[] yCords = {20, 75, 130};
    int offsetX, offsetY, takenX, takenY;
    int dragCount = 0;

    public InventorySystem() {
        this.setLayout(null);
        this.setFocusable(true);
        setSize(225,170);
        initInventory();
        setPositions();
        repaint();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private void initInventory() {
        items = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            String s = "Resources/image" + (i+1) + ".png";

            items.add(new Item(0, 0,s));
        }
    }

    private void setPositions() {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 4; x++) {
                items.get(count).x = xCords[x];
                items.get(count).y = yCords[i];
                count++;
            }
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawObjects(g);
        Toolkit.getDefaultToolkit().sync();

    }

    private void drawObjects(Graphics g) {
        for (Item i : items) {
            g.drawImage(i.getImage(), i.getX(), i.getY(), this);
        }
    }

    public void mousePressed(MouseEvent e) {
        for (Item i : items) {
            Rectangle r = i.getBounds();
            if (r.contains(e.getX(),e.getY())) {
                offsetX = e.getX() - i.getX();
                offsetY = e.getY() - i.getY();
                takenX = i.getX();
                takenY = i.getY();
                dragging = i;
                System.out.println(dragging.ID);
            }
        }
    }

    public void mouseReleased(MouseEvent e) {

        try {
            for (int i = 0; i < 3; i++) {
                for (int x = 0; x < 4; x++) {
                    if (e.getX() < xCords[x] + 30 && e.getX() > xCords[x] - 30) {
                        if (e.getY() < yCords[i] + 30 && e.getY() > yCords[i] - 30) {
                            for (Item t : items) {
                                Rectangle p = t.getBounds();
                                if (p.contains(xCords[x], yCords[i]) && t != dragging) {
                                    Item dragg = t;
                                    System.out.println(t.ID);
                                    dragging.x = xCords[x];
                                    dragging.y = yCords[i];
                                    dragg.x = takenX;
                                    dragg.y = takenY;
                                    dragging = null;
                                    dragCount = 0;
                                    return;
                                }
                            }
                        }
                    } else {
                        dragging.x = takenX;
                        dragging.y = takenY;
                    }
                }
            }
        } catch (NullPointerException i) {

        }
    }


    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            System.out.println("Left Click");
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            System.out.println("Middle Click");
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            System.out.println("Right Click");
        }
    }

    public void mouseDragged(MouseEvent e) {
        try {
            if (dragging != null) {
                dragging.x = e.getX() - offsetX;
                dragging.y = e.getY() - offsetY;
                dragCount++;
                repaint();
            }
        } catch (NullPointerException i) {
        }
    }
}


