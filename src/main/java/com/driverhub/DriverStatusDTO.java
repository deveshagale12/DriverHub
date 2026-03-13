
/**
 * Data Transfer Object to send nearby driver information 
 * along with calculated distance and ETA to the passenger.
 */
public class DriverStatusDTO {

    private Long driverId;
    private String name;
    private Double distanceKm;
    private Integer etaMinutes;
    private Vehicle vehicle;

    // --- Constructors ---

    // Default Constructor (Required by Jackson for JSON mapping)
    public DriverStatusDTO() {
        super();
    }

    // Parameterized Constructor
    public DriverStatusDTO(Long driverId, String name, Double distanceKm, Integer etaMinutes, Vehicle vehicle) {
        super();
        this.driverId = driverId;
        this.name = name;
        this.distanceKm = distanceKm;
        this.etaMinutes = etaMinutes;
        this.vehicle = vehicle;
    }

    // --- Getters and Setters ---

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public Integer getEtaMinutes() {
        return etaMinutes;
    }

    public void setEtaMinutes(Integer etaMinutes) {
        this.etaMinutes = etaMinutes;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}