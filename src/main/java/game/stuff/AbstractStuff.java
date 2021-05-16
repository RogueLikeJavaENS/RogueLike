package game.stuff;

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
        this.description = "";
        price = 5;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(int price) { this.price = price; }

    public boolean isEquipable() {
        return isEquipable;
    }
    public boolean isUsable() {
        return isUsable;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getSellingPrice() {
        return price;
    }
    public int getBuyingPrice() {
        return (int) (price*0.30);
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
}
