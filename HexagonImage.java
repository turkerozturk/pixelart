import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class HexagonImage {
    public static void main(String[] args) {
        int width = 32;
        int height = 32;

        // Transparent bir BufferedImage oluştur
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Arka planı şeffaf yap
        g2d.setColor(new Color(0, 0, 0, 0)); // RGBA (alpha = 0 -> şeffaf)
        g2d.fillRect(0, 0, width, height);

        // Altıgeni çizmek için koordinatlar
        int[] xPoints = {
                width / 2,         // Üst ortası
                width - 1,         // Sağ üst
                width - 1,         // Sağ alt
                width / 2,         // Alt ortası
                0,                 // Sol alt
                0                  // Sol üst
        };
        int[] yPoints = {
                0,                 // Üst
                height / 4,        // Sağ üst
                3 * height / 4,    // Sağ alt
                height - 1,        // Alt
                3 * height / 4,    // Sol alt
                height / 4         // Sol üst
        };

        Polygon hexagon = new Polygon(xPoints, yPoints, 6);

        // Altıgenin rengini belirle ve doldur
        g2d.setColor(Color.BLUE); // Mavi dolgu
        g2d.fillPolygon(hexagon);

        g2d.dispose();

        // PNG dosyasını kaydet
        try {
            File outputFile = new File("hexagon.png");
            ImageIO.write(image, "PNG", outputFile);
            System.out.println("Altıgen PNG dosyası oluşturuldu: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
