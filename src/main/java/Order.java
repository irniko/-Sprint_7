import java.util.Arrays;

public class Order {

     private String firstName;
     private String lastName;
     private String address;
     private int metroStation;
     private String phone;
     private int rentTime;
     private String deliveryDate;
     private String comment;
     private String[] color;

    public void setColor(String[] color) {
        this.color = color;
    }
    public String[] getColor() {
        return color;
    }

    public Order(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Order{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", metroStation=" + metroStation +
                ", phone='" + phone + '\'' +
                ", rentTime=" + rentTime +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", comment='" + comment + '\'' +
                ", color=" + Arrays.toString(color) +
                '}';
    }
}
