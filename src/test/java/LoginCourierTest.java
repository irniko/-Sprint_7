import io.qameta.allure.Allure;
import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Before;
import org.junit.Test;

@DisplayName("Логин курьера (POST /api/v1/courier/login)")
public class LoginCourierTest extends TestDataAndConstants {
    String id;
    CourierMethods courierMethods;

    @Before
    public void setUp() {
        courierMethods = new CourierMethods();
        courierMethods.create(courier);
    }

    @After
    public void cleanUp() {
        if (id != null) {
            Allure.step("Возвращение системы в исходное состояние (удаление курьера)");
            courierMethods.delete(id);
        }
    }

    @Test
    @DisplayName("Логин курьера в системе с существующими данными")
    public void checkCourierLogin() {
        Response loginResponse = courierMethods.login(CourierLoginDetails.fromCourier(courier));

        loginResponse.then().statusCode(200).and().assertThat().body("id", notNullValue());
        Allure.step("Курьер успешно залогинен");
        id = loginResponse.path("id").toString();   // id для последующего удаления курьера
    }

    @Test
    @DisplayName("Логин курьера в системе c неверным паролем")
    public void checkCourierLoginWithIncorrectPassword() {
        id = courierMethods.login(CourierLoginDetails.fromCourier(courier)).path("id").toString(); // id для последующего удаления курьера
        Response loginResponse = courierMethods.login(CourierLoginDetails.fromCourier(courierSameLoginDiffPasswd));

        String expectedMessage = LOGIN_404_NOT_FOUND;
        loginResponse.then().statusCode(404).and().assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Логин курьера в системе c неверным логином")
    public void checkCourierLoginWithIncorrectLogin() {
        id = courierMethods.login(CourierLoginDetails.fromCourier(courier)).path("id").toString(); // id для последующего удаления курьера
        Response loginResponse = courierMethods.login(CourierLoginDetails.fromCourier(courierDiffLoginSamePasswd));

        String expectedMessage = LOGIN_404_NOT_FOUND;
        loginResponse.then().statusCode(404).and().assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Логин курьера в системе без поля 'login'")
    public void checkCourierLoginWithoutLogin() {
        id = courierMethods.login(CourierLoginDetails.fromCourier(courier)).path("id").toString(); // id для последующего удаления курьера
        courier.setLogin(null);
        Allure.parameter("Login ", courier.getLogin());
        Allure.parameter("Password ", courier.getPassword());
        Response loginResponse = courierMethods.login(CourierLoginDetails.fromCourier(courier));

        String expectedMessage = LOGIN_400_BAD_REQUEST;
        loginResponse.then().statusCode(400).and().assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Логин курьера в системе без поля 'password'")
    @Issue("Код ответа не соответствует документации: ожидаем 400, приходит 504")
    public void checkCourierLoginWithoutPassword() {
        id = courierMethods.login(CourierLoginDetails.fromCourier(courier)).path("id").toString();
        courier.setPassword(null);
        Allure.parameter("Login ", courier.getLogin());
        Allure.parameter("Password ", courier.getPassword());
        Response loginResponse = courierMethods.login(CourierLoginDetails.fromCourier(courier));

        String expectedMessage = LOGIN_400_BAD_REQUEST;
        loginResponse.then().statusCode(400).and().assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемогo результата: '" + expectedMessage + "'");
    }

}