package stuff;

public interface Stuff {
    String toString();
    String getDescription();
    String getName();
    void setName(String name);
    void setDescription(String description);
    void setPrice(int price);
    boolean isEquipable();
    boolean isUsable();
    int getSellingPrice();
    int getBuyingPrice();

}
