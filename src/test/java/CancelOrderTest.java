import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Отмена заказа (PUT /api/v1/orders/cancel)")
public class CancelOrderTest extends TestDataAndConstants {

    OrderMethods orderMethods;
    String track;

    @Before
    public void setUp() {
        orderMethods = new OrderMethods();
    }

    @Test
    @DisplayName("Отмена существующего заказа")
    public void cancelOrderWithCorrectTrack() {
        track = orderMethods.create(order).path("track").toString();
        orderMethods.cancel(track).then().statusCode(200).and().assertThat().body("ok", equalTo(true));
        Allure.step("Заказ упешно отменен");
    }

    @Test
    @DisplayName("Отмена заказа без передачи значения track")
    public void cancelOrderWithoutTrack() {
        String expectedMessage = CANCEL_400_BAD_REQUEST;
        orderMethods.cancel(track).then().statusCode(400).and().assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Отмена заказа c передачей несуществующего значения track")
    public void cancelOrderWithNonExistentTrack() {
        track = NON_EXISTENT_TRACK;

        String expectedMessage = CANCEL_404_NOT_FOUND;
        orderMethods.cancel(track).then().statusCode(404).and().assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

}

