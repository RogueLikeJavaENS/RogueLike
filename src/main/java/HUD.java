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
    public String toString() {
        StringBuilder finalString = new StringBuilder();
        finalString.append(player.getName())
                .append(":\t"); //"Name:    "
        finalString.append("Lvl: ")
                .append(player.getLevel())
                .append("\t\t"); //"Lvl: xx  "
        finalString.append("HP: ")
                .append(player.getCurrentHP())
                .append("/")
                .append(player.getMaxHP())
                .append("\t\t"); //"HP: xx/yy"
        finalString.append("MP: ")
                .append(player.getCurrentMP())
                .append("/")
                .append(player.getMaxMP()); //"MP: xx/yy"
        return finalString.toString(); //"Name:    Lvl: 42    HP: 69/420    MP: 42/56" (example)
    }
}
