import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class HexagonGridImage {

    static boolean colColorShifter = false;
    static boolean rowColorShifter = false;

    public static void main(String[] args) {
        // Parametreler: Satır ve sütun sayısı
        int rows = 10; // Kaç satır
        int cols = 10; // Kaç sütun

        // Tek bir altıgenin boyutları
        int hexSize = 32; // Altıgenin kapsadığı kare boyutu
        int hexRadius = hexSize / 2; // Altıgenin yarıçapı (merkezden köşelere)

        // Resmin genişlik ve yüksekliği
        int imageWidth = cols * hexSize;
        int imageHeight = rows * hexSize;

        // Transparent bir BufferedImage oluştur
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Arka planı şeffaf yap
        g2d.setColor(new Color(0, 0, 0, 0)); // RGBA (alpha = 0 -> şeffaf)
        g2d.fillRect(0, 0, imageWidth, imageHeight);

        // Altıgenleri çiz
       // g2d.setColor(getColor()); // Altıgen dolgu rengi
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // 32x32 karelerin merkez koordinatları
                int centerX = col * hexSize + hexRadius;
                int centerY = row * hexSize + hexRadius;

                // Altıgenin köşe koordinatlarını hesapla
                int enlargeXDistortionPx = 3;

                int[] xPoints = {
                        centerX,
                        centerX + (int) (hexRadius * Math.sqrt(3) / 2) + enlargeXDistortionPx,
                        centerX + (int) (hexRadius * Math.sqrt(3) / 2) + enlargeXDistortionPx,
                        centerX,
                        centerX - (int) (hexRadius * Math.sqrt(3) / 2) - enlargeXDistortionPx,
                        centerX - (int) (hexRadius * Math.sqrt(3) / 2) - enlargeXDistortionPx
                };
                int[] yPoints = {
                        centerY - hexRadius,  // Üst merkez
                        centerY - hexRadius / 2, // Sağ üst
                        centerY + hexRadius / 2, // Sağ alt
                        centerY + hexRadius,  // Alt merkez
                        centerY + hexRadius / 2, // Sol alt
                        centerY - hexRadius / 2  // Sol üst
                };

                int colorIndex = (row * rows + col) ;
                g2d.setColor(getColor()); // Altıgen dolgu rengi


                // Altıgeni çiz ve doldur
                Polygon hexagon = new Polygon(xPoints, yPoints, 6);
                g2d.fillPolygon(hexagon);
            }

            if(rowColorShifter) {
                rowColorShifter = false;
            } else {
                rowColorShifter = true;
            }

        }

        g2d.dispose();

        // PNG dosyasını kaydet
        try {
            File outputFile = new File("hexagon_grid.png");
            ImageIO.write(image, "PNG", outputFile);
            System.out.println("Altıgen ızgara PNG dosyası oluşturuldu: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Color getColor() {
        if(colColorShifter) {
            colColorShifter = false;
            return rowColorShifter ? Color.ORANGE : Color.YELLOW;
        } else {
            colColorShifter = true;
            return rowColorShifter ? Color.DARK_GRAY : Color.LIGHT_GRAY;
        }

    }

    public static Color getColor(int colorIndex) {

        switch (colorIndex) {
            case 64:
                return Color.BLACK;
            case 65:
                return Color.white;
            case 66:
                return Color.GRAY;
            case 67:
                return Color.DARK_GRAY;
            case 68:
                return Color.LIGHT_GRAY;
            case 69:
                return Color.ORANGE;
            case 70:
                return Color.yellow;
            case 71:
                return Color.MAGENTA;
            case 72:
                return Color.CYAN;
            default:
                if (colorIndex < 0 || colorIndex > 63) {
                   // throw new IllegalArgumentException("colorIndex must be between 0 and 63");
                    return Color.GRAY;
                } else {

                    // Toplam renk sayısı ve renk aralıkları
                    int steps = 64;
                    int r = (colorIndex * 255 / (steps - 1)) % 256; // Kırmızı kanalı
                    int g = ((colorIndex * 255 / (steps - 1) + 85) % 256); // Yeşil kanalı
                    int b = ((colorIndex * 255 / (steps - 1) + 170) % 256); // Mavi kanalı

                    return new Color(r, g, b);
                }

        }

    }

}
