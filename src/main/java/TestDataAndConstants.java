
public class TestDataAndConstants {

    public static String BASE_URL = "http://qa-scooter.praktikum-services.ru";
    public static String LOGIN_400_BAD_REQUEST = "Недостаточно данных для входа";
    public static String LOGIN_404_NOT_FOUND = "Учетная запись не найдена";
    public static String CREATE_400_BAD_REQUEST = "Недостаточно данных для создания учетной записи";
    public static String CREATE_409_CONFLICT = "Этот логин уже используется";
    public static String DELETE_400_BAD_REQUEST = "Недостаточно данных для удаления курьера";
    public static String DELETE_404_NOT_FOUND = "Курьера с таким id нет";
    public static String CANCEL_400_BAD_REQUEST = "Недостаточно данных для поиска";
    public static String CANCEL_404_NOT_FOUND = "Заказ не найден";

    Courier courier = new Courier("Semka1k", "1234", "Semka1k");
    Courier courierSameLoginDiffPasswd = new Courier("Semka1k", "123456", "Semka2k");
    Courier courierDiffLoginSamePasswd = new Courier("Semka2k", "1234", "Semka2k");

    Order order = new Order("Ким", "Чи", "ул. Якорная, 2", 26, "+79882345677", 1, "2024-04-23", "везите", new String[]{"GREY"});

    protected static final String NON_EXISTENT_TRACK = "995738290";

    public static Object[][] getColorData() {
        return new Object[][] {
                {new String[]{"GREY"}, "Серый"},
                {new String[]{"BLACK"}, "Черный"},
                {new String[]{"BLACK", "GREY"}, "Черный, Серый"},
                {null, "не указан"},
        };
    }

}
