public class CourierLoginDetails {
    private String login;
    private String password;

    public CourierLoginDetails(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierLoginDetails fromCourier(Courier courier) {
        return  new CourierLoginDetails(courier.getLogin(), courier.getPassword());
    }

    @Override
    public String toString() {
        return "CourierLoginDetails{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
