import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class TilesetCreator {
    public static void makeTileSet(
            int xCount,
            int yCount,
            int tileXSizePx,
            int tileYSizePx,
            String sourcePngFolder,
            String outputPngFile) {
        try {
            // Tileset boyutunu belirle
            int tilesetWidth = xCount * tileXSizePx;
            int tilesetHeight = yCount * tileYSizePx;

            // Tileset için boş bir BufferedImage oluştur
            BufferedImage tileset = new BufferedImage(tilesetWidth, tilesetHeight, BufferedImage.TYPE_INT_ARGB);

            // Grafik nesnesi
            Graphics2D g2d = tileset.createGraphics();
            g2d.setComposite(AlphaComposite.Clear);
            g2d.fillRect(0, 0, tilesetWidth, tilesetHeight);
            g2d.setComposite(AlphaComposite.SrcOver);

            // Kaynak klasöründen PNG dosyalarını al
            File folder = new File(sourcePngFolder);
            File[] pngFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));

            if (pngFiles == null || pngFiles.length == 0) {
                System.out.println("Klasörde PNG dosyası bulunamadı.");
                return;
            }

            // PNG dosyalarını sıralı işlemek için sıralama
            Arrays.sort(pngFiles);

            int fileIndex = 0;
            for (int y = 0; y < yCount; y++) {
                for (int x = 0; x < xCount; x++) {
                    if (fileIndex >= pngFiles.length) {
                        break;
                    }

                    // Geçerli PNG dosyasını yükle
                    BufferedImage tile = ImageIO.read(pngFiles[fileIndex]);

                    // PNG'yi tileset'e çiz
                    int xPos = x * tileXSizePx;
                    int yPos = y * tileYSizePx;
                    g2d.drawImage(tile, xPos, yPos, tileXSizePx, tileYSizePx, null);

                    fileIndex++;
                }
            }

            // Grafik kaynağını serbest bırak
            g2d.dispose();

            // Tileset'i dışa aktar
            File outputFile = new File(outputPngFile);
            ImageIO.write(tileset, "png", outputFile);

            System.out.println("Tileset başarıyla oluşturuldu: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Kullanım örneği
        int xCount = 6; // Tileset genişlikteki tile sayısı
        int yCount = 6; // Tileset yükseklikteki tile sayısı
        int tileXSizePx = 32; // Tile genişliği
        int tileYSizePx = 32; // Tile yüksekliği
        String sourcePngFolder = "input_tiles"; // Kaynak PNG dosyalarının klasörü
        String outputPngFile = "output_tileset.png"; // Çıktı tileset dosyası

        makeTileSet(xCount, yCount, tileXSizePx, tileYSizePx, sourcePngFolder, outputPngFile);
    }
}
