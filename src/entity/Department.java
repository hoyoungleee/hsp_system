package entity;

public enum Department {
    ORTHOPEDICS(10000, "정형외과"),
    OPHTHALMOLOGY(10000, "안과"),
    INTERNAL_MEDICINE(15000, "내과"),
    PLASTIC_SURGERY(20000, "성형외과"),
    ;

    private final int price;
    private final String name;

    Department (int price, String name) {
        this.price = price;
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
