package young.hospital.model;


public enum DoctorRole {
    THERAPIST(12000),
    SURGEON(11500),
    OPHTHALMOLOGIST(14000),
    UROLOGIST(18000),
    OBSTETRICIAN_GYNECOLOGIST(10000),
    GENERAL_PRACTITIONER(17000),
    OTORHINOLARYNGOLOGIST(12200);

    private final int price;

    DoctorRole(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
