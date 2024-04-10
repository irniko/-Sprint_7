import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderMethods extends TestDataAndConstants {

    @Step("Создание заказа")
    public Response create(Order order) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(order)
                .post("/api/v1/orders");
    }

    @Step("Отмена заказа по track-номеру")
    public Response cancel(String track) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .queryParam("track", track)
                .put("/api/v1/orders/cancel");
    }

    @Step("Получение списка заказов")
    public Response getOrders() {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .get("/api/v1/orders");
    }
}
