package gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Button has a (pre-loaded) image and an identifier (name).
 */
public class Button {
    /** Name acts as an identifier. */
    String name;

    /** Pre-loaded image file. */
    Image img;

    /** Coordinates on screen. */
    int y;
    int x;

    /** Size of the button. */
    int width;
    int height;

    /** Constructor initializes button.
     *
     * @param name name
     * @param x x
     * @param y y
     * @param width width
     * @param height height this is stupid
     */
    public Button(String name, int x, int y, int width, int height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        boolean oneUp = true; /* Image path varies depending if we run from JAR or IDE. */
        boolean success = tryLoadingImagePath(oneUp);
        if (!success) tryLoadingImagePath(!oneUp);
        /* If we still fail, continue without images. */
    }

    private boolean tryLoadingImagePath(boolean oneUp) {
        String s = File.separator; /* To work on linux and windows etc. */
        String relativePath = "Valhalla" + s + "src" + s + "resources" + s + name + ".png";
        if (oneUp) relativePath = ".." + s + relativePath;
        try {
            this.img = ImageIO.read(new File(relativePath));
        } catch (IOException e) {
            System.out.println("Unable to preload image " + relativePath);
            return false;
        }
        return true;
    }

    /** Draws button. */
    public void draw(Graphics2D g2d) {
        if (img == null) {
            /* If preloading image failed earlier, don't crash the program */
            return;
        }
        g2d.drawImage(img, x, y, width, height, null);
    }
}
