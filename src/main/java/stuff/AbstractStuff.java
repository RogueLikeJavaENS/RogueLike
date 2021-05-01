package stuff;

import java.util.Objects;

public abstract class AbstractStuff implements Stuff {
    String name;
    String description;
    boolean isUsable;
    boolean isEquipable;
    int price;

    public AbstractStuff(String name, boolean isUsable, boolean isEquipable){
        this.isUsable = isUsable;
        this.isEquipable = isEquipable;
        this.name = name;
        price = 5;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public boolean isEquipable() {
        return isEquipable;
    }

    @Override
    public boolean isUsable() {
        return isUsable;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Coucou ceci est un stuff";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractStuff that = (AbstractStuff) o;
        return isUsable == that.isUsable && isEquipable == that.isEquipable && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, isUsable, isEquipable);
    }

    public int getSellingPrice() {
        return price;
    }

    public int getBuyingPrice() {
        return (int) (price*0.60);
    }
}
