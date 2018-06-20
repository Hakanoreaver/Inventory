import javax.swing.*;
import java.awt.*;

public class Item extends Sprite {
    String itemDescription;

    public Item(String itemDescription, String image){
        super(0,0);
        loadImage(image);
    }

}
