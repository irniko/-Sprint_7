import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.hamcrest.Matchers.*;

@DisplayName("Получение списка заказаов (GET /api/v1/orders)")
public class GetOrdersListTest {

    OrderMethods orderMethods;

    @Before
    public void setUp() {
        orderMethods = new OrderMethods();
    }

    @Test
    @DisplayName("Получение списка всех заказов с системе")
    public void getAvailableOrders() {
        Response response = orderMethods.getOrders();

        response.then().statusCode(200).and().body("orders", notNullValue());
        response.then().assertThat().body("orders", Matchers.instanceOf(List.class));
        Allure.step("Тело ответа возвращает список всех заказов");
    }
 }
