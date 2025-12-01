import java.util.*;
import java.util.stream.Collectors;

public class Cookbook {
    private final List<Recipe> allRecipes;

    public Cookbook() {
        this.allRecipes = new ArrayList<>();
        initializeRecipes();
        addSpecializedRecipes();
    }

    private void initializeRecipes() {
        // Meat and Poultry
        allRecipes.add(new Recipe(
            "Adobo (Chicken/Pork)",
            Arrays.asList("chicken or pork", "soy sauce", "vinegar", "garlic", "peppercorns", "bay leaf"),
            "1. Marinate meat in soy sauce, vinegar, garlic, peppercorns, and bay leaf for 30 mins. 2. Boil until meat is tender and sauce reduces. 3. Serve with rice.",
            "Meat and Poultry"
        ));

        allRecipes.add(new Recipe(
            "Sinigang na Baboy",
            Arrays.asList("pork belly", "sinigang mix", "water", "radish", "taro (gabi)", "kangkong (water spinach)", "long green beans"),
            "1. Boil pork until tender. 2. Add taro and radish. 3. Add sinigang mix. 4. Add beans and kangkong, cook briefly. 5. Serve hot.",
            "Meat and Poultry"
        ));

        // Fish and Seafood
        allRecipes.add(new Recipe(
            "Ginataang Tilapia",
            Arrays.asList("tilapia", "coconut milk", "ginger", "garlic", "onion", "chili (optional)", "malunggay leaves"),
            "1. Sauté ginger, garlic, and onion. 2. Add coconut milk and simmer. 3. Place fish in the sauce and cook until done. 4. Add malunggay leaves.",
            "Fish and Seafood"
        ));

        // Vegetables
        allRecipes.add(new Recipe(
            "Pinakbet",
            Arrays.asList("squash (kalabasa)", "okra", "eggplant", "long beans", "shrimp paste (bagoong)"),
            "1. Sauté shrimp paste. 2. Add squash and cook until tender. 3. Add other vegetables and cook until just done. 4. Serve immediately.",
            "Vegetables"
        ));
    }

    public Map<Recipe, Integer> findPossibleRecipes(List<String> userIngredientsList) {
    Map<Recipe, Integer> results = new HashMap<>();
    
    // Normalize user ingredients
    Set<String> userIngredients = userIngredientsList.stream()
                                      .map(ing -> ing.trim().toLowerCase())
                                      .collect(Collectors.toSet());

    for (Recipe recipe : allRecipes) {
        int missingCount = 0;
        
        for (String required : recipe.getRequiredIngredients()) {
            // Check if any user ingredient contains the required ingredient
            boolean found = userIngredients.stream()
                .anyMatch(userIng -> containsIngredient(userIng, required.toLowerCase()));
            
            if (!found) {
                missingCount++;
            }
        }
        
        results.put(recipe, missingCount);
    }
    return results;
}

// Helper method for better ingredient matching
private boolean containsIngredient(String userIngredient, String requiredIngredient) {
    // Handle compound ingredients like "chicken or pork"
    if (requiredIngredient.contains(" or ")) {
        String[] options = requiredIngredient.split(" or ");
        for (String option : options) {
            if (userIngredient.contains(option.trim()) || option.trim().contains(userIngredient)) {
                return true;
            }
        }
        return false;
    }
    
    // Basic contains check
    return userIngredient.contains(requiredIngredient) || requiredIngredient.contains(userIngredient);
}

    public Map<String, List<Recipe>> getAllRecipesByCategory() {
        return allRecipes.stream()
            .collect(Collectors.groupingBy(Recipe::getCategory));
    }
    
    public Recipe getRecipeByName(String name) {
        return allRecipes.stream()
            .filter(r -> r.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }

    public Recipe[] getAllRecipesArray() {
        return allRecipes.toArray(new Recipe[0]);
    }
    
    public Recipe getRecipeByNameWithException(String name) throws RecipeNotFoundException {
        Recipe recipe = getRecipeByName(name);
        if (recipe == null) {
            throw new RecipeNotFoundException("Recipe '" + name + "' not found");
        }
        return recipe;
    }
    
    private void addSpecializedRecipes() {
        allRecipes.add(new TraditionalRecipe(
            "Kare-Kare",
            Arrays.asList("oxtail", "peanut butter", "eggplant", "string beans", "banana blossom", "shrimp paste"),
            "1. Boil oxtail until tender. 2. Add vegetables and peanut sauce. 3. Simmer until thick. 4. Serve with shrimp paste.",
            "Meat and Poultry",
            "Central Luzon"
        ));
        
        allRecipes.add(new QuickRecipe(
            "Tortang Talong", 
            Arrays.asList("eggplant", "eggs", "onion", "garlic", "salt", "pepper"),
            "1. Grill eggplant until soft. 2. Peel and flatten. 3. Dip in beaten eggs. 4. Pan-fry until golden brown.",
            "Vegetables",
            15
        ));
    }
}