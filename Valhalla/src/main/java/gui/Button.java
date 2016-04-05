package gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Button {
    String name;
    Image img;
    int y;
    int x;
    int width;
    int height;

    public Button(String name, int x, int y, int width, int height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        String s = File.separator;
        String relativePath = "src" + s + "resources" + s + name + ".png";
        try {
            this.img = ImageIO.read(new File(relativePath));
        } catch (IOException e) {
            System.out.println("Unable to preload image " + relativePath);
            /* Proceed execution anyway */
        }
    }

    public void draw(Graphics2D g2d) {
        if (img == null) {
            /* If preloading image failed earlier, don't crash the program */
            return;
        }
        g2d.drawImage(img, x, y, width, height, null);
    }
}
