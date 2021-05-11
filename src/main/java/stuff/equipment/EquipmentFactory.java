package stuff.equipment;

import classeSystem.InGameClasses;
import gameElement.GameRule;
import gameElement.GameState;
import stuff.equipment.equipments.*;
import java.util.ArrayList;
import java.util.List;


public class EquipmentFactory {
    private final InGameClasses classe;

    public EquipmentFactory(InGameClasses playerClasse) {
        this.classe = playerClasse;
    }

    public Equipment getCopyOfEquipment(Equipment equipment, int level) {
        return getEquipment(level, equipment.getType(), equipment.getRarity());
    }

    public Equipment getEquipment(int level, EquipmentType type, EquipmentRarity rarity) {

        Equipment equipment;

        switch (type) {
            case WEAPON:
                equipment = new Weapon(level, rarity, type);
                break;
            case SHIELD:
                equipment = new Shield(level, rarity, type);
                break;
            case ARMOR:
                equipment = new Armor(level, rarity, type);
                break;
            case BOOT:
                equipment = new Boot(level, rarity, type);
                break;
            case GLOVE:
                equipment = new Glove(level, rarity, type);
                break;
            case HELMET:
                equipment = new Helmet(level, rarity, type);
                break;
            case PANT:
                equipment = new Pants(level, rarity, type);
                break;
            default:
                equipment = null;
        }
        GameRule gm = new GameRule();
        List<String> namePlusDesctription = buildDescriptionAndName(type, rarity);
        equipment.setDescription(namePlusDesctription.get(1));
        equipment.setName(namePlusDesctription.get(0));
        gm.SetBonusEquipement(equipment, classe);
        equipment.setPrice(gm.getEquipmentPrice(level, rarity));
        return equipment;
    }

    private List<String> buildDescriptionAndName(EquipmentType type, EquipmentRarity rarity) {
        List<String> namePlusDesctription = new ArrayList<>(2);
        String description;
        String name;

        String rarityAttribute = getAttributeByRarityAndClasses(rarity, classe);
        String typeClasse = getEquipmentByTypeAndClasses(type, classe);

        name = rarityAttribute + " " + typeClasse;
        description = getDescriptionByType(name, rarity, type, classe);
        namePlusDesctription.add(name);
        namePlusDesctription.add(description);
        return namePlusDesctription;
    }

    private String getAttributeByRarityAndClasses(EquipmentRarity rarity, InGameClasses classe) {
        String attribute = "";
        switch (rarity) {
            case E:
                switch (classe) {
                    case RANGER:
                        attribute = "Pierced";
                        break;
                    case WARRIOR:
                        attribute = "Wooden";
                        break;
                    case MAGE:
                        attribute = "Old";
                        break;
                }
                break;
            case D:
                switch (classe) {
                    case RANGER:
                        attribute = "Leather";
                        break;
                    case WARRIOR:
                        attribute = "Copper";
                        break;
                    case MAGE:
                        attribute = "Novice";
                        break;
                }
                break;
            case C:
                switch (classe) {
                    case RANGER:
                        attribute = "Hunter";
                        break;
                    case WARRIOR:
                        attribute = "Iron";
                        break;
                    case MAGE:
                        attribute = "Medium";
                        break;
                }
                break;
            case B:
                switch (classe) {
                    case RANGER:
                        attribute = "Rogue";
                        break;
                    case WARRIOR:
                        attribute = "Silver";
                        break;
                    case MAGE:
                        attribute = "Advanced";
                        break;
                }
                break;
            case A:
                switch (classe) {
                    case RANGER:
                        attribute = "Assassin";
                        break;
                    case WARRIOR:
                        attribute = "Platinum";
                        break;
                    case MAGE:
                        attribute = "Confirmed";
                        break;
                }
                break;
            case S:
                switch (classe) {
                    case RANGER:
                        attribute = "Shadow";
                        break;
                    case WARRIOR:
                        attribute = "Diamond";
                        break;
                    case MAGE:
                        attribute = "Master";
                        break;
                }
                break;
            case L: // specific
                switch (classe) {
                    case RANGER:
                        attribute = "Invisible";
                        break;
                    case WARRIOR:
                        attribute = "Vibranium";
                        break;
                    case MAGE:
                        attribute = "Divine";
                        break;
                }
                break;
        }
        return attribute;
    }

    private String getEquipmentByTypeAndClasses(EquipmentType type, InGameClasses classe) {
        String attribute = "";
        switch(type) {
            case WEAPON:
                switch (classe) {
                    case RANGER:
                        attribute = "Bow";
                        break;
                    case WARRIOR:
                        attribute = "Sword";
                        break;
                    case MAGE:
                        attribute = "Staff";
                        break;
                }
                break;
            case SHIELD:
                switch (classe) {
                    case RANGER:
                    case MAGE:
                        attribute = "Cloak";
                        break;
                    case WARRIOR:
                        attribute = "Shield";
                        break;

                }
                break;
            case ARMOR:
                switch (classe) {
                    case WARRIOR:
                    case RANGER:
                        attribute = "Armor";
                        break;
                    case MAGE:
                        attribute = "Dress";
                        break;
                }
                break;
            case BOOT:
                switch (classe) {
                    case RANGER:
                    case WARRIOR:
                        attribute = "Boot";
                        break;
                    case MAGE:
                        attribute = "Shoe";
                        break;
                }
                break;
            case GLOVE:
                switch (classe) {
                    case RANGER:
                        attribute = "Mittens";
                        break;
                    case WARRIOR:
                    case MAGE:
                        attribute = "Glove";
                        break;
                }
                break;
            case HELMET:
                switch (classe) {
                    case RANGER:
                    case MAGE:
                        attribute = "Hood";
                        break;
                    case WARRIOR:
                        attribute = "Helmet";
                        break;
                }
                break;
            case PANT:
                switch (classe) {
                    case RANGER:
                        attribute = "Tights";
                        break;
                    case WARRIOR:
                        attribute = "Pants";
                        break;
                    case MAGE:
                        attribute = "Sock";
                        break;
                }
                break;
        }
        return attribute;
    }

    private String getDescriptionByType(String name, EquipmentRarity rarity, EquipmentType type, InGameClasses classe) {
        String attribute = String.format("A %s %s ", rarity.getRarity(), name);
        switch(type) {
            case WEAPON:
                switch (classe) {
                    case RANGER:
                        attribute += "to throw hardened arrows.";
                        break;
                    case WARRIOR:
                        attribute += "to slash your enemies in pieces.";
                        break;
                    case MAGE:
                        attribute += "to set in fire everything.";
                        break;
                }
                break;
            case SHIELD:
                switch (classe) {
                    case RANGER:
                        attribute += "that protects your back.";
                        break;
                    case MAGE:
                        attribute += "that makes you smarter.";
                        break;
                    case WARRIOR:
                        attribute += "to protect yourself.";
                        break;

                }
                break;
            case ARMOR:
                switch (classe) {
                    case WARRIOR:
                        attribute += "protecting you very well.";
                        break;
                    case RANGER:
                        attribute += "with free movement.";
                        break;
                    case MAGE:
                        attribute += "infused by magic power.";
                        break;
                }
                break;
            case BOOT:
                switch (classe) {
                    case RANGER:
                        attribute += "that makes you faster.";
                        break;
                    case WARRIOR:
                        attribute += "protecting your foot from the spikes.";
                        break;
                    case MAGE:
                        attribute += "that are really fancy...";
                        break;
                }
                break;
            case GLOVE:
                switch (classe) {
                    case RANGER:
                        attribute += "that lets your fingers free.";
                        break;
                    case WARRIOR:
                        attribute += "protecting your entire arms.";
                        break;
                    case MAGE:
                        attribute += "that improves your skills.";
                        break;
                }
                break;
            case HELMET:
                switch (classe) {
                    case RANGER:
                        attribute += "that makes you more stealthy.";
                        break;
                    case MAGE:
                        attribute += "that makes you more clever.";
                        break;
                    case WARRIOR:
                        attribute += "that protects well your heads.";
                        break;
                }
                break;
            case PANT:
                switch (classe) {
                    case RANGER:
                        attribute += "that makes you faster";
                        break;
                    case WARRIOR:
                        attribute += "protecting you, but it's heavy.";
                        break;
                    case MAGE:
                        attribute += "making you more comfortable.";
                        break;
                }
                break;
        }
        return attribute;
    }
}
