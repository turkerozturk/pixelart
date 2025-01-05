import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TmxParser {
    public static void main(String[] args) {
        try {
            // TMX dosyasını yükle
            File file = new File("C:\\tmp\\untitled.tmx");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            // Layer'ı seç
            NodeList layerList = doc.getElementsByTagName("layer");
            for (int i = 0; i < layerList.getLength(); i++) {
                Element layer = (Element) layerList.item(i);
                String layerName = layer.getAttribute("name");
                System.out.println("Layer: " + layerName);

                // Layer içindeki data
                NodeList dataList = layer.getElementsByTagName("data");
                if (dataList.getLength() > 0) {
                    Element data = (Element) dataList.item(0);
                    String encoding = data.getAttribute("encoding");

                    if ("csv".equals(encoding)) {
                        String csvData = data.getTextContent().trim();
                        String[] tiles = csvData.split(",");

                        // Tile sayısını hesapla
                        Map<String, Integer> tileCount = new HashMap<>();
                        for (String tile : tiles) {
                            tile = tile.trim();
                            tileCount.put(tile, tileCount.getOrDefault(tile, 0) + 1);
                        }

                        // Sonuçları yazdır
                        for (Map.Entry<String, Integer> entry : tileCount.entrySet()) {
                            System.out.println("Tile ID: " + entry.getKey() + " Count: " + entry.getValue());
                        }
                    } else {
                        System.out.println("Unsupported encoding: " + encoding);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

