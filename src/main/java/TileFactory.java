import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class TileFactory {
    private ArrayList<Tile> tileList;

    public TileFactory(String path) {
        tileList = new ArrayList<>();
        try {
            File file = new File(path);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            Document doc = docBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList xmlTiles = doc.getDocumentElement().getElementsByTagName("tile");

            for (int i = 0; i < xmlTiles.getLength(); ++i) {
                Node currentTile = xmlTiles.item(i);
                if (currentTile.getNodeType() == Node.ELEMENT_NODE) {
                    Element elt = (Element)currentTile;
                    int typeID = Integer.parseInt(elt.getElementsByTagName("id").item(0).getTextContent());
                    String sprite = elt.getElementsByTagName("sprite").item(0).getTextContent();
                    boolean isAccessible = Boolean.parseBoolean(elt.getElementsByTagName("isAccessible")
                            .item(0).
                            getTextContent());
                    Tile actualTile = new Tile(typeID, sprite, isAccessible);
                    tileList.add(actualTile);
                }
            }
        } catch (NumberFormatException wrongID) {
            Tile buggedTile = new Tile(0, "X", false);
            tileList.add(buggedTile);
        } catch (Exception e) {
            System.out.println("I couldn't parse the document properly\n");
        }
    }

    public ArrayList<Tile> getTileList() {
        return tileList;
    }

    public Tile getTile(int type) {
        for (Tile tile : tileList) {
            if (tile.getTypeID() == type) {
                return tile;
            }
        }
        return (new Tile(0, "□", false));
    }
}
