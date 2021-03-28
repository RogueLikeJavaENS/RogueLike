package items;

public abstract class AbstractItem implements Item {
    int weight;
    String name;
    String description;

    public AbstractItem(int weight,String name, String description){
        this.weight = weight;
        this.description = description;
        this.name = name;
    }
    @Override
    public String getName() { return name; }
    @Override
    public int getWeight() { return weight; }
    @Override
    public String getDescription() { return description; }
}
