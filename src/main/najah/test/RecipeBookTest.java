package main.najah.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;

class RecipeBookTest {
    private RecipeBook recipeBook;

    @BeforeEach
    void setup() {
        recipeBook = new RecipeBook();
    }

    @Test
    @DisplayName("Test adding a valid recipe")
    void testAddRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Latte");
        assertTrue(recipeBook.addRecipe(recipe), "Recipe should be added successfully");
    }

    @Test
    @DisplayName("Test adding a duplicate recipe")
    void testAddDuplicateRecipe() {
        Recipe recipe1 = new Recipe();
        recipe1.setName("Espresso");
        Recipe recipe2 = new Recipe();
        recipe2.setName("Espresso");

        assertTrue(recipeBook.addRecipe(recipe1), "First recipe should be added");
        assertFalse(recipeBook.addRecipe(recipe2), "Duplicate recipe should not be added");
    }

    @Test
    @DisplayName("Test deleting a recipe by valid index")
    void testDeleteRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Mocha");
        recipeBook.addRecipe(recipe);
        assertEquals("Mocha", recipeBook.deleteRecipe(0), "Deleted recipe should be Mocha");
    }

    @Test
    @DisplayName("Test deleting a non-existing recipe")
    void testDeleteNonExistingRecipe() {
        assertNull(recipeBook.deleteRecipe(0), "Should return null when deleting a non-existing recipe");
    }

    @ParameterizedTest
    @DisplayName("Test adding multiple recipes with different names")
    @CsvSource({"Cappuccino", "Americano", "Macchiato", "Flat White"})
    void testAddingMultipleRecipes(String recipeName) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeName);
        assertTrue(recipeBook.addRecipe(recipe), "Recipe should be added: " + recipeName);
    }

    @Test
    @DisplayName("Test method execution within time limit")
    void testExecutionTime() {
        assertTimeout(Duration.ofMillis(100), () -> {
            Recipe recipe = new Recipe();
            recipe.setName("Test");
            recipeBook.addRecipe(recipe);
        }, "Method should execute within 100ms");
    }

    @Test
    @DisplayName("Test multiple assertions on a recipe")
    void testMultipleAssertions() {
        Recipe recipe = new Recipe();
        recipe.setName("Flat White");
        assertAll("Recipe properties",
                () -> assertEquals("Flat White", recipe.getName(), "Recipe name should match"),
                () -> assertEquals(0, recipe.getPrice(), "Price should be default 0"),
                () -> assertEquals(0, recipe.getAmtCoffee(), "Coffee amount should be default 0"));
    }
    @Test
    @DisplayName("Test editing an existing recipe")
    void testEditRecipe() {
        Recipe oldRecipe = new Recipe();
        oldRecipe.setName("Cappuccino");
        recipeBook.addRecipe(oldRecipe);

        Recipe newRecipe = new Recipe();
        newRecipe.setName("Espresso");

        assertEquals("Cappuccino", recipeBook.editRecipe(0, newRecipe), "Should return the old recipe name");
        assertEquals("", recipeBook.getRecipes()[0].getName(), "New recipe should have an empty name due to editRecipe behavior");
    }


    @Test
    @DisplayName("Test editing a non-existing recipe")
    void testEditNonExistingRecipe() {
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Latte");

        assertNull(recipeBook.editRecipe(0, newRecipe), "Should return null when editing a non-existing recipe");
    }

    @Test
    @Disabled("Bug: deleteRecipe replaces with an empty recipe instead of null")
    @DisplayName("Intentionally failing test: Deleting a recipe should set null")
    void testDeleteRecipeBug() {
        Recipe recipe = new Recipe();
        recipe.setName("Hot Chocolate");
        recipeBook.addRecipe(recipe);
        recipeBook.deleteRecipe(0);
        assertNull(recipeBook.getRecipes()[0], "After deletion, the slot should be null");
    }
}