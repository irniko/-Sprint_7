import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
@DisplayName("Создание заказа (POST /api/v1/orders)")
public class CreateOrderTest extends TestDataAndConstants {

    OrderMethods orderMethods;
    String track;
    private String[] color;
    private String colorRu;

    public CreateOrderTest(String[] color, String colorRu) {
        this.color = color;
        this.colorRu = colorRu;
    }

    @Before
    public void setUp() {
        orderMethods = new OrderMethods();
    }

    @After
    public void cleanUp() {
        Allure.step("Возвращение системы в исходное состояние (удаление заказа)");
        orderMethods.cancel(track);
    }

    @Parameterized.Parameters(name = "Цвет: {1}")
    public static Object[][] getTestData() {
        return TestDataAndConstants.getColorData();
    }

    @Test
    @DisplayName("Успешное создание заказа с указанным цветом")
    public void createSuccessOrder() {
        order.setColor(color);
        Allure.parameter("Цвет самоката", order.getColor());
        Response response = orderMethods.create(order);

        response.then().statusCode(201).and().assertThat().body("track", notNullValue());
        Allure.step("Заказа успешно создан");
        track = response.then().extract().jsonPath().getString("track");   // track для последующего удаления заказа
    }

}
