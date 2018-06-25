import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class InventorySystem extends JPanel implements MouseListener, MouseMotionListener {

    Item[]  items;
    Item dragging;
    int[] xCords;
    int[] yCords;
    int offsetX, offsetY, takenX, takenY;
    int dragCount = 0;
    int spriteSize;
    int rowNum;
    int colNum;
    int widthGap;
    int heightGap;
    int heightDif, widthDif;
    boolean firstPaint = false;

    public InventorySystem(int heightGap, int widthGap, int rowNum, int colNum, int spriteSize) {
        setUp(spriteSize,rowNum, colNum);
        doMathMakeSize(heightGap,widthGap,rowNum,colNum,spriteSize);
        repaint();
    }

    public InventorySystem(int xSize, int ySize, int rowNum, int colNum, int spriteSize, int useless) {
        setUp(spriteSize, rowNum, colNum);
        doMathSetSize(xSize, ySize, rowNum, colNum, spriteSize);
        //initInventory(rowNum, colNum);
        addItem("String", "Resources/ItemImages/Sword80.png");
        addItem("String", "Resources/ItemImages/ManaPotion80.png");
        addItem("String", "Resources/ItemImages/HealthPotion80.png");
        addItem("String", "Resources/ItemImages/Shield80.png");
        setPositions(rowNum, colNum);
        repaint();
    }

    private void setUp(int spriteSize, int rowNum, int colNum) {
        items = new Item[rowNum * colNum];
        this.spriteSize = spriteSize;
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.setLayout(null);
        this.setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);

    }

    private void doMathMakeSize(int heightGap, int widthGap, int rowNum, int colNum, int spriteSize) {
        int xSize, ySize;
        xSize = widthGap * colNum + spriteSize * colNum + widthGap;
        ySize = heightGap * rowNum + spriteSize * rowNum + heightGap;
        widthDif = xSize - colNum*spriteSize + colNum *  (spriteSize + 1);
        heightDif = ySize - rowNum*spriteSize + rowNum *  (spriteSize + 1);
        xCords = new int[colNum];
        yCords = new int[rowNum];
        for (int i = 0; i < colNum; i++) {
            if (i == 0) {
                xCords[i] = widthGap;
            }
            else {
                xCords[i] = xCords[i - 1] + widthGap + spriteSize;
            }
        }
        for (int i = 0; i < rowNum; i++) {
            if (i == 0) {
                yCords[i] = heightGap;
            }
            else {
                yCords[i] = yCords[i - 1] + heightGap + spriteSize;
            }
        }
        setSize(xSize,ySize);
        this.widthGap = widthGap;
        this.heightGap = heightGap;

        //initInventory(rowNum, colNum);
        addItem("String", "Resources/ItemImages/Sword80.png");
        setPositions(rowNum, colNum);
    }

    private void doMathSetSize(int xSize, int ySize, int rowNum, int colNum, int spriteSize) {
        setSize(xSize,ySize);
        int widthGap = (xSize - colNum * spriteSize) / (colNum + 1);
        int heightGap = (ySize - rowNum * spriteSize) / (rowNum + 1);

        xCords = new int[colNum];
        yCords = new int[rowNum];
        for (int i = 0; i < colNum; i++) {
            if (i == 0) {
                xCords[i] = widthGap;
            }
            else {
                xCords[i] = xCords[i - 1] + widthGap + spriteSize;
            }
        }
        for (int i = 0; i < rowNum; i++) {
            if (i == 0) {
                yCords[i] = heightGap;
            }
            else {
                yCords[i] = yCords[i - 1] + heightGap + spriteSize;
            }
        }
        setSize(xSize,ySize);
        this.widthGap = widthGap;
        this.heightGap = heightGap;
        widthDif = xSize - colNum*spriteSize + colNum *  (spriteSize + 1);
        heightDif = ySize - rowNum*spriteSize + rowNum *  (spriteSize + 1);
    }

    private void initInventory(int rowNum, int colNum) {
    }

    private void setPositions(int rowNum, int colNum) {
        int count = 0;
        try {
            for (int i = 0; i < rowNum; i++) {
                for (int x = 0; x < colNum; x++) {
                    items[count].x = xCords[x];
                    items[count].y = yCords[i];
                    count++;
                }
            }
        } catch (IndexOutOfBoundsException e) {
        }
        catch (NullPointerException e) {

        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!firstPaint) {
            first(g);
        }
        drawObjects(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void first(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, widthGap, this.getHeight());
        g.fillRect(0, 0, this.getWidth(), heightGap);
        for (int i = 0; i < colNum; i++) {
            if (i == colNum - 1) {
                g.fillRect((xCords[i] + spriteSize), 0, widthGap + widthDif, this.getHeight());
            }
            else
            g.fillRect((xCords[i] + spriteSize), 0, widthGap, this.getHeight());
        }

        for (int i = 0; i < rowNum; i++) {
            if (i == rowNum - 1) {
                g.fillRect(0, yCords[i] + spriteSize, this.getWidth(), heightGap + heightDif);
            }
            else
            g.fillRect(0, yCords[i] + spriteSize, this.getWidth(), heightGap);
        }
    }

    private void drawObjects(Graphics g) {
        for (Item i : items) {
            if (i != dragging && i != null) {
                g.drawImage(i.getImage(),i.getX(),i.getY(),this);
            }
        }
        if (dragging != null) {
            g.drawImage(dragging.getImage(),dragging.getX(),dragging.getY(),this);
        }
    }

    public void mousePressed(MouseEvent e) {
        for (Item i : items) {
            if (i != null) {
                Rectangle r = new Rectangle(i.getX(), i.getY(), spriteSize, spriteSize);
                if (r.contains(e.getX(), e.getY())) {
                    offsetX = e.getX() - i.getX();
                    offsetY = e.getY() - i.getY();
                    takenX = i.getX();
                    takenY = i.getY();
                    dragging = i;
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {

            boolean swapped = false;
            for (int i = 0; i < rowNum; i++) {
                for (int x = 0; x < colNum; x++) {
                    if (e.getX() < xCords[x] + spriteSize && e.getX() > xCords[x] - spriteSize) {
                        if (e.getY() < yCords[i] + spriteSize && e.getY() > yCords[i] - spriteSize) {
                            for (Item t : items) {
                                // Rectangle p = t.getBounds();
                                if (t != null) {
                                    Rectangle p = new Rectangle(t.getX() - 1, t.getY() - 1, spriteSize, spriteSize);
                                    if (p.contains(xCords[x], yCords[i]) && t != dragging && swapped == false) {
                                        Item dragg = t;
                                        if (dragging != null) {
                                            dragging.x = xCords[x];
                                            dragging.y = yCords[i];
                                            dragg.x = takenX;
                                            dragg.y = takenY;
                                            dragging = null;
                                            dragCount = 0;
                                            repaint();
                                            swapped = true;
                                            return;
                                        }
                                    }
                                }
                            }
                            if (dragging != null && swapped == false) {
                                dragging.x = xCords[x];
                                dragging.y = yCords[i];
                                dragging = null;
                                repaint();
                                return;
                            }
                        }
                    } else {
                        if (swapped == false) {
                            dragging.x = takenX;
                            dragging.y = takenY;
                            repaint();
                        }
                    }
                }
            }
        }



    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        dragging = null;
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

    public void addItem(String itemDescription, String itemImage) {
        Item a = new Item(itemDescription, itemImage);
        for (int i = 0 ; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = a;
                a = null;
            }
        }

        for(Item i : items) {
            System.out.println(i);
        }
    }

    public void removeItem(String itemDescription, int amount) {
        for (int i = 0; i < amount; i++) {
            if (items[i] != null && items[i].itemDescription == itemDescription) {
                items[i] = null;
            }
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


