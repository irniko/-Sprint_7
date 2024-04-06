import io.qameta.allure.Allure;
import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;


@DisplayName("Удаление курьера (DELETE /api/v1/courier/:id)")
public class DeleteCourierTest extends TestDataAndConstants {
    String id;
    CourierMethods courierMethods;

    @Before
    public void setUp() {
        courierMethods = new CourierMethods();
    }

    @Test
    @DisplayName("Удаление курьера")
    public void checkСourierDelete() {
        courierMethods.create(courier);
        id = courierMethods.login(CourierLoginDetails.fromCourier(courier)).path("id").toString();  // id для последующего удаления курьера
        Response deleteResponse = courierMethods.delete(id);
        deleteResponse.then().statusCode(200).and().assertThat().body("ok", equalTo(true));
        Allure.step("Курьер успешно удален из системы");
    }

    @Test
    @DisplayName("Удаление курьера без параметра id")
    @Issue("Код ответа не соответствует документации: ожидаем 400, приходит 404")
    public void checkСourierDeleteWithoutId() {
        id = "";
        Response deleteResponse = courierMethods.delete(id);

        String expectedMessage = DELETE_400_BAD_REQUEST;
        deleteResponse.then().statusCode(400).and().assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Удаление курьера с несуществующим id")
    @Issue("Сообщение об ошибке не соответствует документации: ожидаем сообщение без точки")
    public void checkСourierDeleteWithIncorrectId() {
        id = String.valueOf(0);
        Response deleteResponse = courierMethods.delete(id);

        String expectedMessage = DELETE_404_NOT_FOUND;
        deleteResponse.then().statusCode(404).and().assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

}
