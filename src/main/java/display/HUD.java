package display;

import entity.living.Player;
import spells.Spell;
import utils.Colors;

import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * HUD is used to create a string of a player's info such as name, level, HP and MP, as well as its spells during combat.
 *
 * @author Raphael
 *
 * @see Player
 *
 */

public class HUD {
    private final Player player;
    private String spellBar;

    public HUD(Player player) {
        this.player = player;
        spellListString();
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
                .append("   "); //"Lvl: xx  "
        sb.append(colorize("HP: ", Colors.RED.textApply()))
                .append(colorize(String.valueOf(player.getCurrentHP()), Colors.RED.textApply()))
                .append(colorize("/", Colors.RED.textApply()))
                .append(colorize(String.valueOf(player.getMaxHP()), Colors.RED.textApply()))
                .append("   "); //"HP: xx/yy "
        sb.append(colorize("MP: ", Colors.BLUE.textApply()))
                .append(colorize(String.valueOf(player.getCurrentMP()), Colors.BLUE.textApply()))
                .append(colorize("/", Colors.BLUE.textApply()))
                .append(colorize(String.valueOf(player.getMaxMP()), Colors.BLUE.textApply())) //"MP: xx/yy"
                .append("   ");
        sb.append(colorize("BTC: ",Colors.YELLOW.textApply()))
                .append(colorize(String.valueOf(player.getMoneyCount()), Colors.YELLOW.textApply())); //"BTC: xxx"
        sb.append("\n");
        String finalString = sb.toString();
        listStrHud.add(finalString);
        return listStrHud; //"Name:    Lvl: 42    HP: 69/420    MP: 42/56" (example)
    }

    public void spellListString() {
        int IMPOSSIBLE_SPELL_POSITION = -1;
        spellSelectionString(IMPOSSIBLE_SPELL_POSITION);
    }

    /**
     * Creates the string containing the player's list of spells and highlights the one corresponding to the parameter, if it exists.
     *
     * @param spellPosition position of the spell + 1 (because the list starts at 0 and the player can only start at 1, since 0 is technically 10)
     * @author Raphael
     */
    public void spellSelectionString(int spellPosition) {
        StringBuilder spellsString = new StringBuilder();
        StringBuilder currentSpell = new StringBuilder();
        List<Spell> playerSpells = player.getSpells();

        for (int i = 0; i < playerSpells.size(); i++) {
            spellsString.append("| ");
            currentSpell.append(i + 1).append(" : ")
                    .append(playerSpells.get(i).toString()); //Example : "| 1 : Fire Ball | 2 : Thanos' Snap | 3 : Tactical Nuke
            if (i + 1 == spellPosition) {
                spellsString.append(colorize(currentSpell.toString(),Colors.BLACK.textApply(), Colors.WHITE.bgApply()));
            } else {
                spellsString.append(currentSpell);
            }
            spellsString.append(' ');
            currentSpell.setLength(0); //resets the StringBuilder
        }

        spellBar = spellsString + "\n";
    }

    @Override
    public String toString() {
        return strByLine().get(0);
    }

    public String getSpellBar() {
        return spellBar;
    }

    // Pour l'instant le HUD est petit, quand il s'aggrandira, Juliette le modifira afin qu'il s'affiche sur plusieurs
    // lignes pour ne pas empieter sur la minimap
}
