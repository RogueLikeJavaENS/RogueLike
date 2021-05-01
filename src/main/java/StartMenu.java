import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

import java.util.Locale;
import java.util.Scanner;

public class StartMenu {

    private final String[] Title;

    public StartMenu() {
        Title = new String[11];
        Title[0]=colorize("         _           _            _        _                  _           _            _      _                  _            _       ", Colors.RED.textApply());
        Title[1]=colorize("        /\\ \\        /\\ \\         /\\ \\     /\\_\\               /\\ \\        / /\\         /\\ \\   /\\_\\               _\\ \\         / /\\      ", Colors.GREY.textApply());
        Title[2]=colorize("       /  \\ \\      /  \\ \\       /  \\ \\   / / /         _    /  \\ \\      / /  \\       /  \\ \\ / / /         _    /\\__ \\       / /  \\     ", Colors.BLUE.textApply());
        Title[3]=colorize("      / /\\ \\ \\    / /\\ \\ \\     / /\\ \\_\\  \\ \\ \\__      /\\_\\ / /\\ \\ \\    / / /\\ \\__   / /\\ \\ \\\\ \\ \\__      /\\_\\ / /_ \\_\\     / / /\\ \\__  ", Colors.GREEN.textApply());
        Title[4]=colorize("     / / /\\ \\_\\  / / /\\ \\ \\   / / /\\/_/   \\ \\___\\    / / // / /\\ \\_\\  / / /\\ \\___\\ / / /\\ \\ \\\\ \\___\\    / / // / /\\/_/    / / /\\ \\___\\ ", Colors.BLACK.textApply());
        Title[5]=colorize("    / / /_/ / / / / /  \\ \\_\\ / / / ______  \\__  /   / / // /_/_ \\/_/  \\ \\ \\ \\/___// / /  \\ \\_\\\\__  /   / / // / /         \\ \\ \\ \\/___/ ", Colors.BROWN.textApply());
        Title[6]=colorize("   / / /__\\/ / / / /   / / // / / /\\_____\\ / / /   / / // /____/\\      \\ \\ \\     / / /   / / // / /   / / // / /           \\ \\ \\       ", Colors.CYAN.textApply());
        Title[7]=colorize("  / / /_____/ / / /   / / // / /  \\/____ // / /   / / // /\\____\\/  _    \\ \\ \\   / / /   / / // / /   / / // / / ____   _    \\ \\ \\      ", Colors.DARK_GREY.textApply());
        Title[8]=colorize(" / / /\\ \\ \\  / / /___/ / // / /_____/ / // / /___/ / // / /______ /_/\\__/ / /  / / /___/ / // / /___/ / // /_/_/ ___/\\/_/\\__/ / /      ", Colors.MAGENTA.textApply());
        Title[9]=colorize("/ / /  \\ \\ \\/ / /____\\/ // / /______\\/ // / /____\\/ // / /_______\\\\ \\/___/ /  / / /____\\/ // / /____\\/ //_______/\\__\\/\\ \\/___/ /       ", Colors.ORANGE.textApply());
        Title[10]=colorize("\\/_/    \\_\\/\\/_________/ \\/___________/ \\/_________/ \\/__________/ \\_____\\/   \\/_________/ \\/_________/ \\_______\\/     \\_____\\/        ", Colors.DEEP_GREY.textApply());
    }

    public String[] getTitle() {
        return Title;
    }

    public void begin() {
        for (int i = 0; i < 11; i++) {
            System.out.println(Title[i]);
        }
    }

    public String getName(){
        System.out.println("So, whats you're name hero ? (max 14 characters)");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        while (name.length()==0 || name.length()>14){
            System.out.println("We're sadly limited by the technology of our times, enter a name \n that must between 1 and 14 characters long.");
            name = scanner.nextLine();
        }
        return name;
    }

    public Classe getClasse(){
        System.out.println("what do you want to be Hero ?\n An elvish ranger roaming the dungeon ? (1.Ranger)\n A mighty warrior brawling your way to the upper floor ? (2.Warrior)\n A mysterious Mage, exploring the arcane of this dark dungeon ? (3.Mage)\n Decide Hero, your fate awaits you. ");
        Scanner scanner = new Scanner(System.in);
        Classe pick = null;
        while (pick==null) {
            switch (scanner.nextLine().toLowerCase(Locale.ROOT)) {
                case ("ranger"):
                    case ("1"):
                        pick=Classe.RANGER;
                        break;
                case ("warrior"):
                    case ("2"):
                        pick=Classe.WARRIOR;
                        break;
                case ("mage"):
                    case ("3"):
                        pick=Classe.MAGE;
                        break;
                default:
                    System.out.println("this is not a valid class, please try again hero");
            }
        }
        return pick;
    }
}
