package game.menu;

import game.entity.living.player.classeSystem.InGameClasses;
import com.diogonunes.jcolor.Attribute;
import display.RendererUI;
import utils.Colors;
import utils.ScanPanel;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Used to display the StartMenu as the Selection of the class, the startMenu...
 */
public class StartMenu {
    private final String title =
           colorize("             ,-=.\n" +
                    "       __,__//__,_\n" +
                    "       7~7~7|F~F~F`\n" +
                    "      7^7^7||F^F^F\n" +
                    "       7   |/   F\n" +
                    "           /             \\\n" +
                    "        -'`    v__     \\\\  /|\n" +
                    "             __|==)_   \\  / )\n" +
                    "            |_\\`+'_/)   _/|/              __\n" +
                    "   __        \\ )!/( | _/~           _.-'t/_(\n" +
                    "   )_\\j`-._  |\\\\._//_/~  choppe!    \"~7 |7/_(\n" +
                    "  )_\\F| F~\"  (#`(_)7~               ,/) 7/_(\n" +
                    "   )_\\F  )   \\#|  \\#|  _... =Oo     (_7 | )\n" +
                    "<-._(_|_F_)_,|#b  (>=.'  {@{  -}   ,_/_7(7__.->\n" +
                    "\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"`\"--= `\"\"`\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\n", Attribute.BOLD()) +
            colorize(
                    "  _____                           _____             _     \n" +
                        " |  __ \\                         / ____|           | |    \n" +
                        " | |__) |___   __ _ _   _  ___  | (___   ___  _   _| |___ \n" +
                        " |  _  // _ \\ / _` | | | |/ _ \\  \\___ \\ / _ \\| | | | / __|\n" +
                        " | | \\ \\ (_) | (_| | |_| |  __/  ____) | (_) | |_| | \\__ \\\n" +
                        " |_|  \\_\\___/ \\__, |\\__,_|\\___| |_____/ \\___/ \\__,_|_|___/\n" +
                        "               __/ |                                      \n" +
                        "              |___/                                       \n\n", Attribute.BOLD(), Colors.RED.textApply() );

    private final String classeArt =
                    "                    .)                     / \\\n" +
                            "                   .   )                   | |\n" +
                            "                  .      )                 |.|\n" +
                            "                 .        )                |.|\n" +
                            "          ~~~   .          )               |:|      __\n" +
                            "        ~~~ o) .            )            ,_|:|_,   /  )                           /^\\     .\n" +
                            "   ____~~~  =) .             )             (Oo    / _I_                      /\\   \"V\"\n" +
                            "((______ |00%(============>>>>>            +\\ \\  || __|                    /__\\   I      O  o\n" +
                            "        |      | .           )                \\ \\||___|                   //..\\\\  I     .\n" +
                            "        |______|  .         )                   \\ /.:.\\-\\                 \\].`[/  I\n" +
                            "        |___¤__|   .       )                     |.:. /-----\\             /l\\/j\\  (]    .  O\n" +
                            "        |      |    .     )                      |___|::oOo::|           /. ~~ ,\\/I          .\n" +
                            "        |       |    .   )                       /   |:<_T_>:|           \\\\L__j^\\/I       o\n" +
                            "        |_______|     . )                       |_____\\ ::: /             \\/--v}  I     o   .\n" +
                            "        | |   | |                                | |  \\ \\:/               |    |  I   _________\n" +
                            "        | |   | |                                | |   | |                |    |  I c(`       ')o\n" +
                            "        | |    | |                               \\ /   | \\___             |    l  I   \\.     ,/\n" +
                            "        | |__  | |__                             / |   \\_____\\          _/j  L l\\_!  _//^---^\\\\_\n" +
                            "                                                 `-'\n";

    private final String chooseRanger =colorize("    ######################\n",Colors.RED.textApply());
    private final String chooseWarrior = colorize("                                        ########################\n",Colors.RED.textApply());
    private final String chooseMage =colorize("                                                                        ########################\n",Colors.RED.textApply());

    private final String chooseClass ="Choose your class Hero :\n";


    private InGameClasses classe;
    private final List<StartMenuAction> actions;
    private String name = "";
    private final ScanPanel sp;
    private boolean classSelected;
    private StartMenuAction selectedAction;
    private String headMenu;


    public StartMenu(ScanPanel sp) {
        this.sp = sp;
        this.classSelected = false;
        actions = new ArrayList<>();
        RendererUI.clearConsole();
        System.out.println(title);
        System.out.println("\nSo, whats you're name hero ? (max 14 characters)\n");
        chooseName();
        initMenu(true);
        try {
            loopMenu();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the menu according to the first boolean.
     * @param first if it's the first menu or the second one.
     */
    private void initMenu(boolean first) {
        actions.clear();
        if (first) {
            headMenu = title;
            actions.add(new StartMenuAction("New Game", () -> {
                initMenu(false); // init Class Selection Menu.
            }));
            actions.add(new StartMenuAction("Controls", () -> {
                RendererUI.clearConsole();
                System.out.println(RendererUI.getControls());
                sp.reset();
                int keyCode = 0;
                while(keyCode == 0) {
                    keyCode = sp.getKeyPressed();
                    try {
                        Thread.sleep(1);  // Without that, Java deletes the loop
                    }catch(Exception ignored){}
                }
                sp.reset();
            }));
            actions.add(new StartMenuAction("Exit Game", () -> System.exit(0)));
        }
        else {
            headMenu = classeArt;
            actions.add(new StartMenuAction("Ranger", () -> {
                this.classe = InGameClasses.RANGER;
                classSelected = true;
            }));
            actions.add(new StartMenuAction("Warrior", () -> {
                this.classe = InGameClasses.WARRIOR;
                classSelected = true;
            }));
            actions.add(new StartMenuAction("Mage", () -> {
                this.classe = InGameClasses.MAGE;
                classSelected = true;
            }));
        }
        selectedAction = actions.get(0);
    }

    /**
     * Launch the loopMenu. The loop continue while the ending menu boolean isn't set at false;
     * @throws InterruptedException if the scanPanel is closed during the loop.
     */
    private void loopMenu() throws InterruptedException {
        while (!classSelected) {
            RendererUI.clearConsole();
            displayMenu();

            sp.reset();
            int keyCode = 0;
            while(keyCode == 0) {
                keyCode = sp.getKeyPressed();
                Thread.sleep(1);  // Without that, Java deletes the loop
            }

            switch (keyCode) {
                case KeyEvent.VK_Z:
                case KeyEvent.VK_UP:
                    previousSelection();
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    nextSelection();
                    break;
                case KeyEvent.VK_E:
                case KeyEvent.VK_ENTER:
                    selectedAction.doAction();
                    break;
                default:
                    break;
            }
        }
    }

    public void nextSelection() {
        int index = actions.indexOf(selectedAction);
        int size = actions.size();
        selectedAction = actions.get((index+1)% size);
    }

    public void previousSelection() {
        int index = actions.indexOf(selectedAction);
        int size = actions.size();
        selectedAction = actions.get((index + size -1)% size);
    }

    public void displayMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append(headMenu);

        if (selectedAction.getName().equals("Warrior")){
            sb.append(chooseWarrior);
            sb.append(chooseClass);
        }
        else if (selectedAction.getName().equals("Ranger")){
            sb.append(chooseRanger);
            sb.append(chooseClass);
        }
        else if (selectedAction.getName().equals("Mage")){
            sb.append(chooseMage);
            sb.append(chooseClass);
        }


        sb.append(" ===============================================================\n");
        for (StartMenuAction action : actions) {
            sb.append("|");
            if (action.equals(selectedAction)) {
                sb.append(colorize(" -> ", Attribute.BOLD(), Colors.RED.textApply()));
            }
            else {
                sb.append("    ");
            }
            sb.append(action.getName()).append(" ".repeat(59-action.getName().length())).append("|\n");
        }
        sb.append(" ===============================================================\n");
        System.out.println(sb);
    }

    private void chooseName(){
        String name = "";
        try {
            name = getNameWithScan();
            while (name.length()==0 || name.length()>14){
                System.out.println("We're sadly limited by the technology of our times, enter a name \n that must between 1 and 14 characters long.");
                name = getNameWithScan();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.name = name;
    }

    private String getNameWithScan() throws InterruptedException {
        boolean choosedName = false;
        StringBuilder sb = new StringBuilder();
        int size = 0;
        while (!choosedName) {
            sp.reset();
            int keyCode = 0;
            while(keyCode == 0) {
                keyCode = sp.getKeyPressed();
                Thread.sleep(1);  // Without that, Java deletes the loop
            }

            switch (keyCode) {
                case KeyEvent.VK_ENTER:
                    choosedName = true;
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    if (size == 0) {
                        sb.delete(size, size);
                    } else {
                        sb.delete(size-1, size);
                        size--;
                    }
                    RendererUI.clearConsole();
                    System.out.println(title);
                    System.out.println("\nSo, whats you're name hero ? (max 14 characters)\n");
                    System.out.print(sb);
                    break;
                default:
                    char c = (char) keyCode;
                    if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'){
                        size++;
                        if (size > 1) {
                            sb.append(Character.toLowerCase(c));
                            System.out.print(Character.toLowerCase(c));
                        } else{
                            sb.append(c);
                            System.out.print(c);
                        }

                    }
                    break;
            }
        }
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public InGameClasses getClasse(){
        return classe;
    }
}
