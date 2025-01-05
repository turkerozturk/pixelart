import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class CharacterPngGenerator {

    public static void main(String[] args) {
        String characters = "abcçdefgğhıijklmnoöprsştuüvyz1234567890qx";
        characters += "ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZQX";
        characters += "/*-+,.:;~-_@<>|\\?=)(&%^'!\"é£#$½{[]}`";

        int imageSize = 32; // Boyut: 32x32 px
        String outputDir = "output"; // Çıktı dosyalarının kaydedileceği klasör

        // Çıkış klasörünü oluştur
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Her karakter için bir PNG oluştur
        for (char c : characters.toCharArray()) {
            createPngWithCharacter(c, imageSize, outputDir);
        }

        System.out.println("PNG dosyaları oluşturuldu: " + outputDir);
    }

    static int counter = 1;


    private static void createPngWithCharacter(char character, int imageSize, String outputDir) {
        try {
            // Şeffaf bir resim oluştur
            BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();

            // Şeffaf arkaplan
            g2d.setComposite(AlphaComposite.Clear);
            g2d.fillRect(0, 0, imageSize, imageSize);
            g2d.setComposite(AlphaComposite.Src);

            // Siyah karakter ayarları
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 20)); // Yazı tipi ve boyutu

            // Karakterin boyutlarını ölç
            FontMetrics metrics = g2d.getFontMetrics();
            int charWidth = metrics.stringWidth(String.valueOf(character));
            int charHeight = metrics.getHeight();
            int charAscent = metrics.getAscent();

            // Ortalamak için koordinatları hesapla
            int x = (imageSize - charWidth) / 2;
            int y = (imageSize - charHeight) / 2 + charAscent;

            // Karakteri çiz
            g2d.drawString(String.valueOf(character), x, y);

            // Kaynakları serbest bırak
            g2d.dispose();

            // Dosyayı kaydet
            File outputFile;
            PrintStream originalErr = System.err; // Orijinal System.err saklanır
            try {
                // System.err devre dışı bırakılır
                System.setErr(new PrintStream(new OutputStream() {
                    public void write(int b) {
                        // Hiçbir şey yapma
                    }
                }));

                // niye boyle yaptik cunku imageio filenotfoundexceptionunu kendi icinden veriyor ve onu gostermek
                // istemiyoruz.

                try {
                    outputFile = new File(outputDir, character + ".png");
                    if(!outputFile.exists()) {
                        ImageIO.write(image, "PNG", outputFile);
                    } else {
                        ImageIO.write(image, "PNG", new File(outputDir, character + "(1).png"));
                    }
                } catch (Exception ee) {
                    File ooutputFile = new File(outputDir, "special" + counter + ".png");
                    if(!ooutputFile.exists()) {
                        ImageIO.write(image, "PNG", ooutputFile);
                    } else {
                        ImageIO.write(image, "PNG", new File(outputDir, "special" + counter + "(1).png"));
                    }
                    System.out.println("Su karakter dosya sisteminde isim olarak kullanilamadigindan " + "special" + counter + ".png" + " adiyla yazdirildi: " + character);

                    counter++;
                }

            } finally {
                // System.err eski haline döndürülür
                System.setErr(originalErr);
            }


        } catch (Exception e) {
            // e.printStackTrace();
        }
    }
}

