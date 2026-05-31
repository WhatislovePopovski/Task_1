package praktikum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты класса Ingredient")
class IngredientTest {

    @ParameterizedTest(name = "Ингредиент {1} типа {0} должен стоить {2} руб.")
    @CsvSource({
            "SAUCE, Томатный соус, 50.0",
            "FILLING, Куриная котлета, 150.0",
            "SAUCE, Сырный соус, 75.5",
            "FILLING, Говяжья котлета, 200.0"
    })
    @DisplayName("Конструктор должен правильно сохранять тип, имя и цену")
    void constructorShouldSetTypeNameAndPrice(IngredientType type, String name, float price) {
        Ingredient ingredient = new Ingredient(type, name, price);

        assertEquals(type, ingredient.getType());
        assertEquals(name, ingredient.getName());
        assertEquals(price, ingredient.getPrice());
    }

    @ParameterizedTest
    @EnumSource(IngredientType.class)
    @DisplayName("Ингредиент может быть любого типа из перечисления")
    void ingredientCanHaveAnyType(IngredientType type) {
        Ingredient ingredient = new Ingredient(type, "Тестовый ингредиент", 100);
        assertEquals(type, ingredient.getType());
    }

    @Test
    @DisplayName("Метод getPrice должен возвращать правильную цену")
    void getPriceShouldReturnCorrectPrice() {
        Ingredient ingredient = new Ingredient(IngredientType.SAUCE, "Соус", 45.5f);
        assertEquals(45.5f, ingredient.getPrice());
    }

    @Test
    @DisplayName("Метод getName должен возвращать правильное имя")
    void getNameShouldReturnCorrectName() {
        Ingredient ingredient = new Ingredient(IngredientType.FILLING, "Бекон", 120);
        assertEquals("Бекон", ingredient.getName());
    }

    @Test
    @DisplayName("Метод getType должен возвращать правильный тип")
    void getTypeShouldReturnCorrectType() {
        Ingredient ingredient = new Ingredient(IngredientType.SAUCE, "Соус", 30);
        assertEquals(IngredientType.SAUCE, ingredient.getType());
    }

    @Test
    @DisplayName("Цена ингредиента не должна меняться после создания")
    void priceShouldNotChangeAfterCreation() {
        Ingredient ingredient = new Ingredient(IngredientType.FILLING, "Котлета", 150);
        float initialPrice = ingredient.getPrice();

        ingredient.price = 300;

        assertNotEquals(initialPrice, ingredient.getPrice(), "Цена изменилась после прямой модификации поля");
    }
}