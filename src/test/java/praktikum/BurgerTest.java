package praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты класса Burger")
class BurgerTest {

    private Burger burger;

    @BeforeEach
    void setUp() {
        burger = new Burger();
        // Устанавливаем локаль для предсказуемого форматирования
        Locale.setDefault(Locale.US);
    }

    @Test
    @DisplayName("setBuns должен устанавливать булочку")
    void setBunsShouldSetBun() {
        Bun bun = new Bun("Тестовая булочка", 100);
        burger.setBuns(bun);
        assertEquals(bun, burger.bun);
    }

    @Test
    @DisplayName("addIngredient должен добавлять ингредиент в список")
    void addIngredientShouldAddToIngredientsList() {
        Ingredient ingredient = new Ingredient(IngredientType.SAUCE, "Соус", 50);
        burger.addIngredient(ingredient);

        assertEquals(1, burger.ingredients.size());
        assertTrue(burger.ingredients.contains(ingredient));
    }

    @Test
    @DisplayName("addIngredient должен добавлять несколько ингредиентов")
    void addIngredientShouldAddMultipleIngredients() {
        Ingredient ing1 = new Ingredient(IngredientType.SAUCE, "Соус1", 50);
        Ingredient ing2 = new Ingredient(IngredientType.FILLING, "Начинка1", 100);

        burger.addIngredient(ing1);
        burger.addIngredient(ing2);

        assertEquals(2, burger.ingredients.size());
        assertTrue(burger.ingredients.contains(ing1));
        assertTrue(burger.ingredients.contains(ing2));
    }

    @Test
    @DisplayName("removeIngredient должен удалять ингредиент по индексу")
    void removeIngredientShouldRemoveByIndex() {
        Ingredient ing1 = new Ingredient(IngredientType.SAUCE, "Соус1", 50);
        Ingredient ing2 = new Ingredient(IngredientType.FILLING, "Начинка1", 100);
        Ingredient ing3 = new Ingredient(IngredientType.SAUCE, "Соус2", 75);

        burger.addIngredient(ing1);
        burger.addIngredient(ing2);
        burger.addIngredient(ing3);

        burger.removeIngredient(1);

        assertEquals(2, burger.ingredients.size());
        assertTrue(burger.ingredients.contains(ing1));
        assertTrue(burger.ingredients.contains(ing3));
        assertFalse(burger.ingredients.contains(ing2));
    }

    @Test
    @DisplayName("removeIngredient должен выбрасывать исключение при неверном индексе")
    void removeIngredientShouldThrowExceptionForInvalidIndex() {
        Ingredient ing1 = new Ingredient(IngredientType.SAUCE, "Соус1", 50);
        burger.addIngredient(ing1);

        assertThrows(IndexOutOfBoundsException.class, () -> burger.removeIngredient(5));
    }

    @Test
    @DisplayName("moveIngredient должен перемещать ингредиент на новую позицию")
    void moveIngredientShouldMoveToNewPosition() {
        Ingredient ing1 = new Ingredient(IngredientType.SAUCE, "Соус1", 50);
        Ingredient ing2 = new Ingredient(IngredientType.FILLING, "Начинка1", 100);
        Ingredient ing3 = new Ingredient(IngredientType.SAUCE, "Соус2", 75);

        burger.addIngredient(ing1);
        burger.addIngredient(ing2);
        burger.addIngredient(ing3);

        burger.moveIngredient(2, 0);

        assertEquals(ing3, burger.ingredients.get(0));
        assertEquals(ing1, burger.ingredients.get(1));
        assertEquals(ing2, burger.ingredients.get(2));
    }

    @Test
    @DisplayName("moveIngredient с одинаковыми индексами не должен менять список")
    void moveIngredientWithSameIndexShouldNotChangeList() {
        Ingredient ing1 = new Ingredient(IngredientType.SAUCE, "Соус1", 50);
        Ingredient ing2 = new Ingredient(IngredientType.FILLING, "Начинка1", 100);

        burger.addIngredient(ing1);
        burger.addIngredient(ing2);

        List<Ingredient> beforeMove = List.copyOf(burger.ingredients);
        burger.moveIngredient(0, 0);

        assertEquals(beforeMove, burger.ingredients);
    }

    @Test
    @DisplayName("getPrice должен корректно считать стоимость бургера")
    void getPriceShouldCalculateCorrectTotal() {
        Bun bun = new Bun("Булочка", 100);
        Ingredient ing1 = new Ingredient(IngredientType.SAUCE, "Соус", 50);
        Ingredient ing2 = new Ingredient(IngredientType.FILLING, "Начинка", 30);

        burger.setBuns(bun);
        burger.addIngredient(ing1);
        burger.addIngredient(ing2);

        float expectedPrice = 100 * 2 + 50 + 30;
        assertEquals(expectedPrice, burger.getPrice());
    }

    @Test
    @DisplayName("getPrice должен возвращать только цену булочек, если нет ингредиентов")
    void getPriceShouldReturnOnlyBunsPriceWhenNoIngredients() {
        Bun bun = new Bun("Булочка", 75);
        burger.setBuns(bun);

        float expectedPrice = 75 * 2;
        assertEquals(expectedPrice, burger.getPrice());
    }

    @ParameterizedTest
    @CsvSource({
            "100, 50, 30, 280",
            "200, 100, 150, 650",
            "50, 25, 25, 150"
    })
    @DisplayName("getPrice должен правильно суммировать цены с разными значениями")
    void getPriceShouldSumVariousPrices(float bunPrice, float ingPrice1, float ingPrice2, float expected) {
        Bun bun = new Bun("Булочка", bunPrice);
        Ingredient ing1 = new Ingredient(IngredientType.SAUCE, "Соус", ingPrice1);
        Ingredient ing2 = new Ingredient(IngredientType.FILLING, "Начинка", ingPrice2);

        burger.setBuns(bun);
        burger.addIngredient(ing1);
        burger.addIngredient(ing2);

        assertEquals(expected, burger.getPrice());
    }

    @Test
    @DisplayName("getReceipt должен генерировать правильный чек")
    void getReceiptShouldGenerateCorrectReceipt() {
        Bun bun = new Bun("Белая булочка", 100);
        Ingredient ing1 = new Ingredient(IngredientType.SAUCE, "Томатный соус", 50);
        Ingredient ing2 = new Ingredient(IngredientType.FILLING, "Котлета", 150);

        burger.setBuns(bun);
        burger.addIngredient(ing1);
        burger.addIngredient(ing2);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== Белая булочка ====)"));
        assertTrue(receipt.contains("= sauce Томатный соус ="));
        assertTrue(receipt.contains("= filling Котлета ="));
        assertTrue(receipt.contains("Price: 400"));
        assertTrue(receipt.contains("400.000000") || receipt.contains("400,000000"));
    }

    @Test
    @DisplayName("getReceipt должен корректно отображать чек без ингредиентов")
    void getReceiptShouldWorkWithNoIngredients() {
        Bun bun = new Bun("Черная булочка", 120);
        burger.setBuns(bun);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== Черная булочка ====)"));
        assertTrue(receipt.contains("Price: 240"));
        assertFalse(receipt.contains("= sauce"));
        assertFalse(receipt.contains("= filling"));
        assertTrue(receipt.contains("240.000000") || receipt.contains("240,000000"));
    }

    @Test
    @DisplayName("Несколько ингредиентов одного типа должны корректно отображаться")
    void multipleSameTypeIngredientsShouldDisplayCorrectly() {
        Bun bun = new Bun("Булочка", 100);
        Ingredient ing1 = new Ingredient(IngredientType.SAUCE, "Соус 1", 50);
        Ingredient ing2 = new Ingredient(IngredientType.SAUCE, "Соус 2", 75);

        burger.setBuns(bun);
        burger.addIngredient(ing1);
        burger.addIngredient(ing2);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("sauce Соус 1"));
        assertTrue(receipt.contains("sauce Соус 2"));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    @DisplayName("removeIngredient должен корректно удалять по разным индексам")
    void removeIngredientShouldWorkWithDifferentIndices(int index) {
        Ingredient ing1 = new Ingredient(IngredientType.SAUCE, "Соус1", 50);
        Ingredient ing2 = new Ingredient(IngredientType.FILLING, "Начинка1", 100);
        Ingredient ing3 = new Ingredient(IngredientType.SAUCE, "Соус2", 75);

        burger.addIngredient(ing1);
        burger.addIngredient(ing2);
        burger.addIngredient(ing3);

        int initialSize = burger.ingredients.size();
        burger.removeIngredient(index);

        assertEquals(initialSize - 1, burger.ingredients.size());
    }
}