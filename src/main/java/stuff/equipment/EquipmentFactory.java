package stuff.equipment;

public class EquipmentFactory {

    public Equipment getEquipment(EquipmentType equipmentType,String name){
        if (equipmentType == EquipmentType.SWORD){
            return new Sword(name);
        }
        return null;
    }

}
