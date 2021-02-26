import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * Stores the Tiles it finds in an xml using DOM Parser in an ArrayList.
 *
 * @see Tile
 *
 * @author Raphael
 *
 */
public class TileFactory {
    private ArrayList<Tile> tileList;

    /**
     *Creates the factory and stores the Tiles it finds in the XML given as an argument.
     *
     * @param path Path of the XML to look into.
     */
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
        } catch (NullPointerException wrongPath) {
            wrongPath.printStackTrace();
            System.out.println("XML not existing.\n");
        } catch (NumberFormatException wrongID) {
            wrongID.printStackTrace();
            System.out.println("An ID is wrong in the XML.\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Couldn't parse the document properly.\n");
        }
    }

    public ArrayList<Tile> getTileList() {
        return tileList;
    }

    /**
     *Checks in the tileList for a Tile corresponding to the type given in parameter.
     *
     * @param type Integer corresponding to the type of Tile you want to be returned
     * @return A Tile corresponding to the type given in parameter. Returns a "bugged"
     * non-accessible Tile with a typeID of 0 if none matches the given type.
     *
     */
    public Tile getTile(int type) {
        for (Tile tile : tileList) {
            if (tile.getTypeID() == type) {
                return tile;
            }
        }
        //bugged Tile
        return (new Tile(0, "□□\n□□", false));
    }
}
