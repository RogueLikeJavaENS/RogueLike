import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StartMenu {

    private String[] Title;

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
        Title[10]=colorize("\\/_/    \\_\\/\\/_________/ \\/___________/ \\/_________/ \\/__________/ \\_____\\/   \\/_________/ \\/_________/ \\_______\\/     \\_____\\/        ", Colors.SOFT_GREY.textApply());
    }

    public String[] getTitle() {
        return Title;
    }
    public String begin() {
        for (int i = 0; i < 11; i++) {
            System.out.println(Title[i]);
        }
        System.out.println("So, whats you're name hero ?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
