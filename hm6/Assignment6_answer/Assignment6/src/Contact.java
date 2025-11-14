public class Contact {
    public String name;
    public String street;
    public String city;
    public String state;
    public String phone;
    public String email;

    public Contact(String name, String street, String city, String state, String phone, String email) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.phone = phone;
        this.email = email;
    }

    public Object[] toRow() {
        return new Object[]{name, street, city, state, phone, email};
    }
}
