package items;

public abstract class AbstractItem implements Item { ;
    String name;

    public AbstractItem(String name){
        this.name = name;
    }
    @Override
    public String getName() { return name; }

}
