package praktikum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты перечисления IngredientType")
class IngredientTypeTest {

    @Test
    @DisplayName("Перечисление должно содержать два значения")
    void shouldHaveTwoValues() {
        IngredientType[] values = IngredientType.values();
        assertEquals(2, values.length);
    }

    @ParameterizedTest(name = "Значение {0} должно существовать")
    @EnumSource(value = IngredientType.class, names = {"SAUCE", "FILLING"})
    @DisplayName("Перечисление должно содержать SAUCE и FILLING")
    void shouldContainSauceAndFilling(IngredientType type) {
        assertNotNull(type);
    }

    @Test
    @DisplayName("SAUCE должен соответствовать соусу")
    void sauceShouldBeSauce() {
        assertEquals("SAUCE", IngredientType.SAUCE.name());
    }

    @Test
    @DisplayName("FILLING должен соответствовать начинке")
    void fillingShouldBeFilling() {
        assertEquals("FILLING", IngredientType.FILLING.name());
    }
}