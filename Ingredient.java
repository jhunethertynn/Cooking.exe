public class Ingredient extends FoodItem {
    public Ingredient(String name) {
        super(name.trim().toLowerCase()); // Call parent constructor
    }
    
    @Override
    public void displayInfo() {
        System.out.println("🥬 Ingredient: " + name);
    }
}