public abstract class FoodItem {
    protected String name;
    
    public FoodItem(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public abstract void displayInfo();
}