import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private Scanner scanner;
    private Cookbook cookbook;
    private List<Ingredient> inventory;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.cookbook = new Cookbook();
        this.inventory = new ArrayList<>();
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    public void run() {
        displayWelcome();
        mainMenu();
    }

    private void displayWelcome() {
        System.out.println("==============================================");
        System.out.println("           FILIPINO COOKING ASSISTANT");
        System.out.println("==============================================");
    }

    private void mainMenu() {
        String choice = "";
        while (!choice.equalsIgnoreCase("exit")) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Start (tara luto tayo!)");
            System.out.println("2. Check my inventory");
            System.out.println("3. View full recipe catalog");
            System.out.println("4. Help/About");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1":
                    startCooking();
                    break;
                case "2":
                    checkInventory();
                    break;
                case "3":
                    viewRecipeCatalog();
                    break;
                case "4":
                    displayHelp();
                    break;
                case "5":
                    System.out.println("Thank you for using Filipino Cooking Assistant! ;)\nSalamat!");
                    choice = "exit";
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1-5.");
            }
        }
    }

    private void startCooking() {
        inventory.clear();
        System.out.println("--- START COOKING ---");
        System.out.println("Enter ingredients separated by commas:");
        System.out.println("Example: chicken, soy sauce, garlic, onion");
        System.out.print("Your ingredients: ");
        
        String input = scanner.nextLine().trim();
        
        if (input.isEmpty()) {
            System.out.println("No ingredients entered. Returning to menu.");
            return;
        }

        // Process ingredients
        Set<String> uniqueIngredients = new HashSet<>();
        String[] parts = input.split(",");
        for (String part : parts) {
            String ingredient = part.trim().toLowerCase();
            if (!ingredient.isEmpty() && uniqueIngredients.add(ingredient)) {
                inventory.add(new Ingredient(ingredient));
            }
        }

        if (inventory.isEmpty()) {
            System.out.println("No valid ingredients found.");
            return;
        }

        System.out.println("\nProcessing ingredients...");
        processIngredients();
    }

    private void processIngredients() {
        List<String> userIngredientsNames = inventory.stream()
            .map(Ingredient::getName)
            .collect(Collectors.toList());

        Map<Recipe, Integer> results = cookbook.findPossibleRecipes(userIngredientsNames);

        System.out.println("\n--- WHAT YOU CAN COOK ---");
        
        // Filter and sort results
        List<Map.Entry<Recipe, Integer>> filteredResults = results.entrySet().stream()
            .filter(entry -> entry.getValue() < entry.getKey().getRequiredIngredients().size())
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toList());

        if (filteredResults.isEmpty()) {
            System.out.println("No recipes found with your ingredients.");
            return;
        }

        // Display recipes
        int recipeCount = 1;
        List<String> recipeChoices = new ArrayList<>();
        
        for (Map.Entry<Recipe, Integer> entry : filteredResults) {
            Recipe recipe = entry.getKey();
            int missing = entry.getValue();
            int total = recipe.getRequiredIngredients().size();
            recipeChoices.add(recipe.getName());
            
            String status;
            if (missing == 0) {
                status = "COMPLETE - Ready to cook!";
            } else if (missing == 1) {
                status = "ALMOST - Missing 1 ingredient";
            } else if (missing == 2) {
                status = "CLOSE - Missing 2 ingredients";
            } else if (missing <= 4) {
                status = "NEEDS SOME - Missing " + missing + " of " + total;
            } else {
                status = "NEEDS MANY - Missing " + missing + " of " + total;
            }
            
            System.out.printf("%d. %s\n     %s\n", recipeCount++, recipe.getName(), status);
        }

        System.out.print("\nSelect recipe number for details, or 'M' for menu: ");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("M")) {
            return;
        }

        try {
            int index = Integer.parseInt(choice) - 1;
            if (index >= 0 && index < recipeChoices.size()) {
                String selectedRecipeName = recipeChoices.get(index);
                Recipe selectedRecipe = cookbook.getRecipeByName(selectedRecipeName);
                if (selectedRecipe != null) {
                    selectedRecipe.displayRecipe();
                }
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private void checkInventory() {
        System.out.println("--- MY INVENTORY ---");
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
            System.out.println("Go to 'Start Cooking' to add ingredients.");
            return;
        }

        System.out.println("\nYour ingredients:");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.printf("%d. %s\n", (i + 1), inventory.get(i).getName());
        }

        System.out.print("\nFind recipes with these? (Y/N): ");
        String choice = scanner.nextLine();
        
        if (choice.equalsIgnoreCase("Y")) {
            processIngredients();
        }
    }
    
    private void viewRecipeCatalog() {
        System.out.println("--- RECIPE CATALOG ---");
        Map<String, List<Recipe>> categorizedRecipes = cookbook.getAllRecipesByCategory();
        
        List<String> categories = new ArrayList<>(categorizedRecipes.keySet());

        System.out.println("\nCategories:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%d. %s\n", (i + 1), categories.get(i));
        }

        System.out.print("\nSelect category number, or 'M' for menu: ");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("M")) {
            return;
        }
        
        try {
            int categoryIndex = Integer.parseInt(choice) - 1;
            if (categoryIndex >= 0 && categoryIndex < categories.size()) {
                String selectedCategory = categories.get(categoryIndex);
                List<Recipe> recipes = categorizedRecipes.get(selectedCategory);
                
                System.out.println("\n--- " + selectedCategory.toUpperCase() + " ---");
                for (int i = 0; i < recipes.size(); i++) {
                    System.out.printf("%d. %s\n", (i + 1), recipes.get(i).getName());
                }

                System.out.print("\nSelect recipe number for details, or 'M' for menu: ");
                String recipeChoice = scanner.nextLine();
                
                if (recipeChoice.equalsIgnoreCase("M")) {
                    return;
                }
                
                int recipeNum = Integer.parseInt(recipeChoice);
                if (recipeNum > 0 && recipeNum <= recipes.size()) {
                    recipes.get(recipeNum - 1).displayRecipe();
                } else {
                    System.out.println("Invalid recipe number.");
                }
            } else {
                System.out.println("Invalid category number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private void displayHelp() {
        System.out.println("--- HELP/ABOUT ---");
        System.out.println("How to use:");
        System.out.println("1. Start Cooking - Enter ingredients to find recipes");
        System.out.println("2. Check Inventory - View your saved ingredients");
        System.out.println("3. View Recipes - Browse all available recipes");
        System.out.println("4. Help - Show this information");
        System.out.println("5. Exit - Close the program");
        System.out.println("\nEnjoy cooking! ;)");
    }
}