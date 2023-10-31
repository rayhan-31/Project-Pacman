import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public class Pacman extends JFrame {
      Pacman(){
          add(new Model());
      }
    public static void main(String[] args) {
        Pacman pac = new Pacman();

        pac.setVisible(true);
        pac.setTitle("Pacman");
        pac.setSize(625,650);
        pac.setDefaultCloseOperation(EXIT_ON_CLOSE);

        pac.setLocationRelativeTo(null);

    }

}
