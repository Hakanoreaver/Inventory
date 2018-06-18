import javax.swing.*;
import java.awt.*;

public class Item extends Sprite {
    int ID;
    public Item(int x, int y, String imageString){
        super(x,y);
        ID++;
        this.ID = ID;
        loadImage(imageString);
        getImageDimensions();
    }

}
