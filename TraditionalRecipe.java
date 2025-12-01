import java.util.List;

public class TraditionalRecipe extends Recipe {
    private String region;
    
    public TraditionalRecipe(String name, List<String> requiredIngredients, String instructions, String category, String region) {
        super(name, requiredIngredients, instructions, category);
        this.region = region;
    }
    
    public String getRegion() {
        return region;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("** TRADITIONAL INFO **");
        System.out.println("Region: " + region);
    }
    
    @Override
    public void displayRecipe() {
        displayInfo();
    }
}