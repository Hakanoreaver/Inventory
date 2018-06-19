import javax.swing.*;
import java.awt.*;

public class Item extends Sprite {
    int ID;
    Color color;

    public Item(int x, int y, String imageString){
        super(x,y);
        loadImage(imageString);
        getImageDimensions();
        int colour1 = (int) Math.round(Math.random() * 255);
        int colour2 = (int) Math.round(Math.random() * 255);
        int colour3 = (int) Math.round(Math.random() * 255);
        color = new Color(colour1, colour2, colour3);
    }

    public Color getColor() {
        return color;
    }
}
