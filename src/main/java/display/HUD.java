package display;

import entity.living.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * HUD is used to create a string of a player's info such as name, level, HP and MP.
 *
 * @author Raphael
 *
 * @see Player
 *
 */

public class HUD {
    private final Player player;

    public HUD(Player player) {
        this.player = player;
    }

    /**
     * Creates the String using a StringBuilder to display the player's info. Calling it over an already
     * existing String should update it as it rewrites the whole String.
     *
     * @return the string containing the player's info
     */
    public List<String> strByLine() {
        List<String> listStrHud = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append(player.getName())
                .append(": "); //"Name:    "
        sb.append("Lvl: ")
                .append(player.getLevel())
                .append("\t"); //"Lvl: xx  "
        sb.append("HP: ")
                .append(player.getCurrentHP())
                .append("/")
                .append(player.getMaxHP())
                .append("\t"); //"HP: xx/yy "
        sb.append("MP: ")
                .append(player.getCurrentMP())
                .append("/")
                .append(player.getMaxMP()) //"MP: xx/yy"
                .append("\t");
        sb.append("BTC: ")
                .append(player.getMoneyCount()); //"BTC: xxx"
        sb.append("\n");
        String finalString = sb.toString();
        //String emptyString = " ".repeat(finalString.length());
        //listStrHud.add(emptyString);
        listStrHud.add(finalString);
        return listStrHud; //"Name:    Lvl: 42    HP: 69/420    MP: 42/56" (example)
    }

    @Override
    public String toString() {
        return strByLine().get(0);
    }

    // Pour l'instant le HUD est petit, quand il s'aggrandira, Juliette le modifira afin qu'il s'affiche sur plusieurs
    // lignes pour ne pas empieter sur la minimap
}
