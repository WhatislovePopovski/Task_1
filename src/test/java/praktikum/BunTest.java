package praktikum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты класса Bun")
class BunTest {

    @ParameterizedTest(name = "Булочка {0} должна стоить {1} руб.")
    @CsvSource({
            "Булочка с кунжутом, 50.0",
            "Булочка с маком, 75.5",
            "Булочка без глютена, 120.0"
    })
    @DisplayName("Конструктор должен сохранять имя и цену")
    void constructorShouldSetNameAndPrice(String name, float price) {
        Bun bun = new Bun(name, price);

        assertEquals(name, bun.getName());
        assertEquals(price, bun.getPrice());
    }

    @Test
    @DisplayName("Метод getName должен возвращать правильное имя")
    void getNameShouldReturnCorrectName() {
        Bun bun = new Bun("Тестовая булочка", 100);
        assertEquals("Тестовая булочка", bun.getName());
    }

    @Test
    @DisplayName("Метод getPrice должен возвращать правильную цену")
    void getPriceShouldReturnCorrectPrice() {
        Bun bun = new Bun("Булочка", 99.9f);
        assertEquals(99.9f, bun.getPrice());
    }

    @Test
    @DisplayName("Цена не должна меняться после создания объекта")
    void priceShouldNotChangeAfterCreation() {
        Bun bun = new Bun("Тестовая булочка", 100);
        float initialPrice = bun.getPrice();

        bun.price = 200;

        assertNotEquals(initialPrice, bun.getPrice(), "Цена изменилась после прямой модификации поля");
    }
}