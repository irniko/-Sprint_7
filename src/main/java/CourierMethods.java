import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierMethods extends TestDataAndConstants {

    @Step("Создание курьера с заданными параметрами")
    public Response create(Courier courier) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
    }

    @Step("Ввод логина/пароля курьера")
    public Response login(CourierLoginDetails courierLoginDetails) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(courierLoginDetails)
                .post("/api/v1/courier/login");
    }

    @Step("Удаление курьера по track-номеру")
    public Response delete(String id) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .delete("/api/v1/courier/" + id);
    }

}
