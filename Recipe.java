import java.util.List;

public class Recipe extends FoodItem {
    private List<String> requiredIngredients;
    private String instructions;
    private String category;

    public Recipe(String name, List<String> requiredIngredients, String instructions, String category) {
        super(name);
        this.requiredIngredients = requiredIngredients;
        this.instructions = instructions;
        this.category = category;
    }

    public List<String> getRequiredIngredients() {
        return requiredIngredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getCategory() {
        return category;
    }

    public void displayRecipe() {
        displayInfo();
    }

    @Override
    public void displayInfo() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("RECIPE: " + name.toUpperCase());
        System.out.println("=".repeat(40));
        System.out.println("Category: " + category);

        System.out.println("\nINGREDIENTS:");
        for (String ingredient : requiredIngredients) {
            System.out.println("- " + ingredient);
        }

        System.out.println("\nINSTRUCTIONS:");
        // Format instructions with proper numbering
        String[] steps = instructions.split("\\d+\\.");
        for (int i = 1; i < steps.length; i++) {
            System.out.println(i + "." + steps[i].trim());
        }
        System.out.println("=".repeat(40));
        System.out.println();
    }
}