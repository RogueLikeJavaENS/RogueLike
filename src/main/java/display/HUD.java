package display;

import game.entity.living.player.Player;
import game.entity.living.player.spell.Spell;
import game.stuff.item.ItemType;
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
        String finalString = player.getName() +
                ": " + //"Name:    "
                "Lvl: " +
                player.getStats().getLevel() +
                "  " + //"Lvl: xx  "
                colorize("XP: ", Colors.GREEN.textApply()) +
                colorize(String.valueOf(player.getPlayerStats().getXp()), Colors.GREEN.textApply()) +
                colorize("/", Colors.GREEN.textApply()) +
                colorize(String.valueOf(player.getPlayerStats().getXpRequired()), Colors.GREEN.textApply()) +
                "  " +
                colorize("HP: ", Colors.RED.textApply()) +
                colorize(String.valueOf(player.getStats().getLifePointActual()), Colors.RED.textApply()) +
                colorize("/", Colors.RED.textApply()) +
                colorize(String.valueOf(player.getStats().getLifePointTotal()), Colors.RED.textApply()) +
                "  " + //"HP: xx/yy "
                colorize("MP: ", Colors.BLUE.textApply()) +
                colorize(String.valueOf(player.getStats().getManaPointActual()), Colors.BLUE.textApply()) +
                colorize("/", Colors.BLUE.textApply()) +
                colorize(String.valueOf(player.getStats().getManaPointTotal()), Colors.BLUE.textApply()) + //"MP: xx/yy"
                "  " +
                colorize("BTC: ", Colors.YELLOW.textApply()) +
                colorize(String.valueOf(player.getPlayerStats().getMoneyCount()), Colors.YELLOW.textApply()) + //"BTC: xxx"
                "\n" +
                "Health Potion: " + colorize(String.valueOf(player.getInventory().getItemNumber(ItemType.HEALTH_POTION)), Colors.RED.textApply()) +
                " Elixir: " + colorize(String.valueOf(player.getInventory().getItemNumber(ItemType.ELIXIR)), Colors.BLUE.textApply()) +
                " Xp Bottle: " + colorize(String.valueOf(player.getInventory().getItemNumber(ItemType.XP_BOTTLE)), Colors.GREEN.textApply()) +
                "\n" +
                "A : " +
                player.getPlayerStats().getSelectedSpell().toString() +
                "\n";
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
     * @param spellPosition position of the game.entity.living.player.spell starting from 1 to 10 (0 on the keyboard).
     *
     * @author Raphael
     */
    public void spellSelectionString(int spellPosition) {
        StringBuilder spellsString = new StringBuilder();
        StringBuilder currentSpellString = new StringBuilder();
        List<Spell> playerSpells = player.getPlayerStats().getSpells();
        Spell currentSpell;
        for (int i = 0; i < playerSpells.size(); i++) {
            currentSpell = playerSpells.get(i);
            spellsString.append("| ");
            currentSpellString.append(i + 1).append(" : ")
                    .append(currentSpell.toString()); //Example : "| 1 : Fire Ball | 2 : Thanos' Snap | 3 : Tactical Nuke
            if (i == spellPosition) { //if it's the game.entity.living.player.spell we want to select
                spellsString.append(colorize(currentSpellString.toString(),Colors.BLACK.textApply(), Colors.WHITE.bgApply()));
            } else {
                spellsString.append(currentSpellString);
            }
            spellsString.append(' ');
            currentSpellString.setLength(0); //resets the StringBuilder
        }
        spellBar = spellsString + " |\n";
    }

    @Override
    public String toString() {
        return strByLine().get(0);
    }

    public String getSpellBar() {
        return spellBar;
    }


}
