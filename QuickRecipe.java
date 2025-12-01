import java.util.List;

public class QuickRecipe extends Recipe {
    private int prepTime;
    
    public QuickRecipe(String name, List<String> requiredIngredients, String instructions, String category, int prepTime) {
        super(name, requiredIngredients, instructions, category);
        this.prepTime = prepTime;
    }
    
    public int getPrepTime() {
        return prepTime;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("** QUICK MEAL INFO **");
        System.out.println("Preparation Time: " + prepTime + " minutes");
    }
    
    @Override
    public void displayRecipe() {
        displayInfo();
    }
}