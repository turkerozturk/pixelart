import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class PngSlicer {
    public static void slicePNGtoTiles(int tileWidth, int tileHeight, File inputFile, String outputFolder) {
        try {
            // Girdi dosyasını yükle
            BufferedImage sourceImage = ImageIO.read(inputFile);

            int imageWidth = sourceImage.getWidth();
            int imageHeight = sourceImage.getHeight();

            // Çıktı klasörü oluştur
            File outputDir = new File(outputFolder);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            // Resmi parçalara ayır ve kaydet
            int tileIndex = 0;
            for (int y = 0; y < imageHeight; y += tileHeight) {
                for (int x = 0; x < imageWidth; x += tileWidth) {
                    // Parçayı al
                    int w = Math.min(tileWidth, imageWidth - x); // Son kenar kontrolü
                    int h = Math.min(tileHeight, imageHeight - y); // Son kenar kontrolü
                    BufferedImage tile = sourceImage.getSubimage(x, y, w, h);

                    // Çıktı dosyasını kaydet
                    File outputTile = new File(outputFolder, "tile_" + tileIndex + ".png");
                    ImageIO.write(tile, "png", outputTile);

                    tileIndex++;
                }
            }

            System.out.println("Parçalama işlemi tamamlandı. Çıkış klasörü: " + outputFolder);
        } catch (Exception e) {
            System.err.println("Hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Kullanım örneği
        File inputFile = new File("C:\\tmp\\emoticons.png"); // Girdi dosyası
        String outputFolder = "output_tiles_emoticon"; // Çıktı klasörü
        int tileWidth = 32; // Dilim genişliği
        int tileHeight = 32; // Dilim yüksekliği

        slicePNGtoTiles(tileWidth, tileHeight, inputFile, outputFolder);
    }
}

