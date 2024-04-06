import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Создание курьера (POST /api/v1/courier)")
public class CreateCourierTest extends TestDataAndConstants {

    String id;
    CourierMethods courierMethods;

    @Before
    public void setUp() {
        courierMethods = new CourierMethods();
    }

    @After
    public void cleanUp() {
        if (id != null) {
            Allure.step("Возвращение системы в исходное состояние (удаление курьера)");
            courierMethods.delete(id);
        }
    }

    @Test
    @DisplayName("Создание учетной записи курьера")
    public void checkСourierСreation() {
        Response response = courierMethods.create(courier);

        response.then().statusCode(201).and().assertThat().body("ok", equalTo(true));
        id = courierMethods.login(CourierLoginDetails.fromCourier(courier)).path("id").toString();  // id для последующего удаления курьера
        Allure.step("Курьер успешно зарегистрирован в системе");

    }

    @Test
    @DisplayName("Создание двух курьеров с одинаковыми данными (дубликат)")
    @Issue("Сообщение об ошибке не соответствует документации")
    public void checkСourierСreationDouble() {
        courierMethods.create(courier);
        id = courierMethods.login(CourierLoginDetails.fromCourier(courier)).path("id").toString(); // id для последующего удаления курьера
        Response response = courierMethods.create(courier); // создание курьера с теми же данными

        String expectedMessage = CREATE_409_CONFLICT;
        response.then().statusCode(409).and().assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Создание двух курьеров с одинаковым полем 'login'")
    public void checkСourierСreationWithSameLogin() {
        courierMethods.create(courier);
        id = courierMethods.login(CourierLoginDetails.fromCourier(courier)).path("id").toString(); // id для последующего удаления курьера
        Response response = courierMethods.create(courierSameLoginDiffPasswd); // создание курьера с тем же логином

        String expectedMessage = CREATE_409_CONFLICT;
        response.then().statusCode(409).and().assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }


    @Test
    @DisplayName("Создание курьера без поля 'login'")
    public void checkСourierСreationWithoutLogin() {
        courier.setLogin(null);
        Allure.parameter("Login ", courier.getLogin());
        Allure.parameter("Password ", courier.getPassword());
        Allure.parameter("FirstName ", courier.getFirstName());
        Response response = courierMethods.create(courier);

        String expectedMessage = CREATE_400_BAD_REQUEST;
        response.then().statusCode(400).and().assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Создание курьера без поля 'password'")
    public void checkСourierСreationWithoutPassword() {
        courier.setPassword(null);
        Allure.parameter("Login ", courier.getLogin());
        Allure.parameter("Password ", courier.getPassword());
        Allure.parameter("FirstName ", courier.getPassword());
        Response response = courierMethods.create(courier);

        String expectedMessage = CREATE_400_BAD_REQUEST;
        response.then().statusCode(400).and().assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Создание курьера без поля 'firstName'")
    public void checkСourierСreationWithoutFirstName() {
        courier.setFirstName(null);
        Allure.parameter("Login ", courier.getLogin());
        Allure.parameter("Password ", courier.getPassword());
        Allure.parameter("FirstName ", courier.getFirstName());
        Response response = courierMethods.create(courier);
        id = courierMethods.login(CourierLoginDetails.fromCourier(courier)).path("id").toString();

        response.then().statusCode(201).and().assertThat().body("ok", equalTo(true));
        Allure.step("Курьер успешно зарегистрирован в системе");
    }

}
