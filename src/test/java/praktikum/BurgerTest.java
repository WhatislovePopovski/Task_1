package praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты класса Burger с использованием моков")
class BurgerTest {

    private Burger burger;

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient mockIngredient1;

    @Mock
    private Ingredient mockIngredient2;

    @Mock
    private Ingredient mockIngredient3;

    @BeforeEach
    void setUp() {
        burger = new Burger();
    }

    @Test
    @DisplayName("setBuns должен устанавливать булочку")
    void setBunsShouldSetBun() {
        burger.setBuns(mockBun);
        assertEquals(mockBun, burger.bun);
    }

    @Test
    @DisplayName("addIngredient должен добавлять ингредиент в список")
    void addIngredientShouldAddToIngredientsList() {
        burger.addIngredient(mockIngredient1);

        assertEquals(1, burger.ingredients.size());
        assertTrue(burger.ingredients.contains(mockIngredient1));
        verify(mockIngredient1, never()).getPrice();
        verify(mockIngredient1, never()).getName();
        verify(mockIngredient1, never()).getType();
    }

    @Test
    @DisplayName("addIngredient должен добавлять несколько ингредиентов")
    void addIngredientShouldAddMultipleIngredients() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        assertEquals(2, burger.ingredients.size());
        assertTrue(burger.ingredients.contains(mockIngredient1));
        assertTrue(burger.ingredients.contains(mockIngredient2));
    }

    @Test
    @DisplayName("removeIngredient должен удалять ингредиент по индексу")
    void removeIngredientShouldRemoveByIndex() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        burger.removeIngredient(1);

        assertEquals(2, burger.ingredients.size());
        assertTrue(burger.ingredients.contains(mockIngredient1));
        assertTrue(burger.ingredients.contains(mockIngredient3));
        assertFalse(burger.ingredients.contains(mockIngredient2));
    }

    @Test
    @DisplayName("removeIngredient должен выбрасывать исключение при неверном индексе")
    void removeIngredientShouldThrowExceptionForInvalidIndex() {
        burger.addIngredient(mockIngredient1);

        assertThrows(IndexOutOfBoundsException.class, () -> burger.removeIngredient(5));
    }

    @Test
    @DisplayName("moveIngredient должен перемещать ингредиент на новую позицию")
    void moveIngredientShouldMoveToNewPosition() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        burger.moveIngredient(2, 0);

        assertEquals(mockIngredient3, burger.ingredients.get(0));
        assertEquals(mockIngredient1, burger.ingredients.get(1));
        assertEquals(mockIngredient2, burger.ingredients.get(2));
    }

    @Test
    @DisplayName("moveIngredient с одинаковыми индексами не должен менять список")
    void moveIngredientWithSameIndexShouldNotChangeList() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        List<Ingredient> beforeMove = List.copyOf(burger.ingredients);
        burger.moveIngredient(0, 0);

        assertEquals(beforeMove, burger.ingredients);
    }

    @Test
    @DisplayName("getPrice должен корректно считать стоимость бургера")
    void getPriceShouldCalculateCorrectTotal() {
        when(mockBun.getPrice()).thenReturn(100f);
        when(mockIngredient1.getPrice()).thenReturn(50f);
        when(mockIngredient2.getPrice()).thenReturn(30f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        float expectedPrice = 100 * 2 + 50 + 30;
        assertEquals(expectedPrice, burger.getPrice());

        verify(mockBun, atLeastOnce()).getPrice();
        verify(mockIngredient1, atLeastOnce()).getPrice();
        verify(mockIngredient2, atLeastOnce()).getPrice();
    }

    @Test
    @DisplayName("getPrice должен возвращать только цену булочек, если нет ингредиентов")
    void getPriceShouldReturnOnlyBunsPriceWhenNoIngredients() {
        when(mockBun.getPrice()).thenReturn(75f);
        burger.setBuns(mockBun);

        float expectedPrice = 75 * 2;
        assertEquals(expectedPrice, burger.getPrice());

        verify(mockBun, atLeastOnce()).getPrice();
        verify(mockIngredient1, never()).getPrice();
    }

    @ParameterizedTest
    @CsvSource({
            "100, 50, 30, 280",
            "200, 100, 150, 650",
            "50, 25, 25, 150"
    })
    @DisplayName("getPrice должен правильно суммировать цены с разными значениями")
    void getPriceShouldSumVariousPrices(float bunPrice, float ingPrice1, float ingPrice2, float expected) {
        when(mockBun.getPrice()).thenReturn(bunPrice);
        when(mockIngredient1.getPrice()).thenReturn(ingPrice1);
        when(mockIngredient2.getPrice()).thenReturn(ingPrice2);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        assertEquals(expected, burger.getPrice());
    }

    @Test
    @DisplayName("getReceipt должен генерировать правильный чек")
    void getReceiptShouldGenerateCorrectReceipt() {
        when(mockBun.getName()).thenReturn("Белая булочка");
        when(mockBun.getPrice()).thenReturn(100f);

        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient1.getName()).thenReturn("Томатный соус");
        when(mockIngredient1.getPrice()).thenReturn(50f);

        when(mockIngredient2.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient2.getName()).thenReturn("Котлета");
        when(mockIngredient2.getPrice()).thenReturn(150f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== Белая булочка ====)"));
        assertTrue(receipt.contains("= sauce Томатный соус ="));
        assertTrue(receipt.contains("= filling Котлета ="));

        verify(mockBun, atLeast(2)).getName();
        verify(mockIngredient1).getType();
        verify(mockIngredient1).getName();
        verify(mockIngredient2).getType();
        verify(mockIngredient2).getName();
    }

    @Test
    @DisplayName("getReceipt должен корректно отображать чек без ингредиентов")
    void getReceiptShouldWorkWithNoIngredients() {
        when(mockBun.getName()).thenReturn("Черная булочка");
        when(mockBun.getPrice()).thenReturn(120f);

        burger.setBuns(mockBun);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== Черная булочка ====)"));
        assertFalse(receipt.contains("= sauce"));
        assertFalse(receipt.contains("= filling"));

        verify(mockBun, atLeast(2)).getName();
        verify(mockIngredient1, never()).getType();
        verify(mockIngredient1, never()).getName();
    }

    @Test
    @DisplayName("Несколько ингредиентов одного типа должны корректно отображаться")
    void multipleSameTypeIngredientsShouldDisplayCorrectly() {
        when(mockBun.getName()).thenReturn("Булочка");
        when(mockBun.getPrice()).thenReturn(100f);

        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient1.getName()).thenReturn("Соус 1");
        when(mockIngredient1.getPrice()).thenReturn(50f);

        when(mockIngredient2.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient2.getName()).thenReturn("Соус 2");
        when(mockIngredient2.getPrice()).thenReturn(75f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("sauce Соус 1"));
        assertTrue(receipt.contains("sauce Соус 2"));

        verify(mockIngredient1).getType();
        verify(mockIngredient1).getName();
        verify(mockIngredient2).getType();
        verify(mockIngredient2).getName();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    @DisplayName("removeIngredient должен корректно удалять по разным индексам")
    void removeIngredientShouldWorkWithDifferentIndices(int index) {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        int initialSize = burger.ingredients.size();
        burger.removeIngredient(index);

        assertEquals(initialSize - 1, burger.ingredients.size());
    }
}