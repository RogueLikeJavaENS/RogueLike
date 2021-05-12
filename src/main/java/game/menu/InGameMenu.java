package game.menu;

import com.diogonunes.jcolor.Attribute;
import display.RendererUI;
import game.entity.Entity;
import game.entity.living.inventory.OpenInventory;
import game.entity.living.npc.monster.Monster;
import game.entity.living.npc.monster.MonsterFactory;
import game.entity.living.npc.monster.MonsterType;
import game.entity.living.player.Player;
import game.elements.GameState;
import game.entity.living.player.spell.Spell;
import utils.Colors;
import utils.Position;
import utils.ScanPanel;
import utils.State;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;


public class InGameMenu {

    private final String pauseHead =
            colorize("  _____                           _____             _     \n" +
                    " |  __ \\                         / ____|           | |    \n" +
                    " | |__) |___   __ _ _   _  ___  | (___   ___  _   _| |___ \n" +
                    " |  _  // _ \\ / _` | | | |/ _ \\  \\___ \\ / _ \\| | | | / __|\n" +
                    " | | \\ \\ (_) | (_| | |_| |  __/  ____) | (_) | |_| | \\__ \\\n" +
                    " |_|  \\_\\___/ \\__, |\\__,_|\\___| |_____/ \\___/ \\__,_|_|___/\n" +
                    "               __/ |                                      \n" +
                    "              |___/                                       \n\n", Attribute.BOLD(), Colors.RED.textApply())
            + " ============================ PAUSE ============================\n";

    private final String shopHead =
            colorize("  .        _________________________    .\n" +
                    "           UUUUUUUUUUUUUUUUUUUUUUUUU         ()\n" +
                    "     .     UUUUUUUUUUUUUUUUUUUUUUUUU ()  () (()\n" +
                    "  _________UUUUUUUUUUUUUUUUUUUUUUUUU()())()))()\n" +
                    "  UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU))\n" +
                    "()UUUUUUUUUU|| ___ || ___ || ___ ||UUUUUUUUUU()\n" +
                    "(()|__\\$/__||| ~|~ || ~|~ || ~|~ ||___\\$/__|()(\n" +
                    "))(|-------||| \"\"\" || \"\"\" || \"\"\" ||  _____ |()(\n" +
                    "(()|-------||| ___ ||/_º_\\|| ___ ||  ~|~|~ |(()\n" +
                    ")))|-------||| ~|~ |||\"\"\"||| ~|~ ||  \"\"\"\"\" |)()\n" +
                    "@@@@¯¯¯¯¯¯¯@||@@@@@|||'  |||@@@@@||@@@@@@@@@@@@\n" +
                    "____  '`.  ___________| |______________________\n\n", Attribute.BOLD())
            + " ============================ SHOP  ============================\n";

    private List<InGameAction> actions;
    private InGameAction selectedAction;
    private String headMenu;
    private boolean endMenu = false;
    private final ScanPanel sp;


    public InGameMenu(GameState gameState) {
        actions = new ArrayList<>();
        this.sp = gameState.getScanPanel();
        initMenu(gameState.getState());
        try {
            loopMenu(gameState);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void loopMenu(GameState gameState) throws InterruptedException {
        boolean display = true;
        while (!endMenu) {
            if (display) {
                RendererUI.clearConsole();
                displayMenu();
            }
            sp.reset();
            int keyCode = 0;
            while(keyCode == 0) {
                keyCode = sp.getKeyPressed();
                Thread.sleep(1);  // Without that, Java deletes the loop
            }
            display = true;
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
                    selectedAction.doAction(gameState);
                    if (gameState.getState() != State.PAUSE_MENU) {
                        endMenu = true;
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    gameState.setState(State.NORMAL);
                    gameState.isThereMonstersInventory();
                    endMenu = true;
                    break;
                default:
                    display = false;
                    break;
            }
        }
    }

    public void initMenu(State stateMenu) {
        if (stateMenu.equals(State.SHOP_MENU)) {
            headMenu = shopHead;
            actions.add(new InGameAction("Buy", state -> {
                new OpenInventory(state, state.getMerchant().getMerchantInventory(), true);
            }));
            actions.add(new InGameAction("Sell", state -> {
                new OpenInventory(state, state.getMerchant().getMerchantInventory(), false);
            }));
            actions.add(new InGameAction("Attack the Merchant", state -> {
                Position pos = state.getMerchant().getPosition();
                state.getGridMap().update((Entity) state.getMerchant(), false);
                MonsterFactory mf = new MonsterFactory(state.getDungeon().getFloor());
                Monster angryMerchant = mf.getMonster(MonsterType.MERCHANT.ordinal(), pos);
                state.getGridMap().update(angryMerchant, true);
                state.setState(State.NORMAL);
                Player player = state.getPlayer();
                Spell spell = player.getPlayerStats().getSpells().get(0);
                if (angryMerchant.getMonsterStats().hasAvoided()){
                    state.getDescriptor().updateDescriptor(String.format("%s dodged %s's attack!", player.getName(), angryMerchant.getName()));
                } else {
                    int damage = spell.getDamage();
                    double mult = spell.getDamageMult();
                    int damages = (int) (damage + mult * state.getPlayer().getPlayerStats().getDamageTotal());
                    angryMerchant.getMonsterStats().sufferDamage(damages);

                    state.getDescriptor().updateDescriptor(String.format("%s used %s for %s mana and inflicted %s damages to the %s !",
                            player.getName(),
                            spell,
                            colorize(Integer.toString(spell.getManaCost()), Colors.BLUE.textApply()),
                            colorize(Integer.toString(damages), Colors.ORANGE.textApply()),
                            angryMerchant.getName()));
                    state.isMonsterAlive(angryMerchant);
                }
                state.isThereMonsters();
                state.getDescriptor().updateDescriptor(
                        "Merchant : "+colorize(String.format("It's treason then, you will die %s !", state.getPlayer().getName()), Attribute.BOLD(), Colors.RED.textApply()));
            }));
            actions.add(new InGameAction("Exit Shop", state -> {
                state.setState(State.NORMAL);
                state.isThereMonsters();
            }));
        } else if (stateMenu.equals(State.PAUSE_MENU)) {  // Menu Pause
            headMenu = pauseHead;
            actions.add(new InGameAction("Resume game", state -> {
                state.setState(State.NORMAL);
                state.isThereMonstersInventory();
            }));
            actions.add(new InGameAction("Controls", state -> {
                RendererUI.clearConsole();
                System.out.println(RendererUI.getControls());
                sp.reset();
                int keyCode = 0;
                while(keyCode == 0) {
                    keyCode = sp.getKeyPressed();
                    try {
                        Thread.sleep(1);  // Without that, Java deletes the loop
                    }catch(Exception e){}
                }
                sp.reset();
                state.setState(State.PAUSE_MENU);
            }));
            actions.add(new InGameAction("Exit game", state -> state.setState(State.END)));

        }
        selectedAction = actions.get(0);
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
        for (InGameAction action : actions) {
            sb.append("|");
            if (action.equals(selectedAction)) {
                sb.append(colorize(" -> ", Attribute.BOLD(), Colors.RED.textApply()));
            }
            else {
                sb.append("    ");
            }
            sb.append(action.getName()).append(" ".repeat(59-action.getName().length())).append("|\n");
        }
        sb.append(" =============================================================== \n");
        System.out.println(sb);
    }
}
