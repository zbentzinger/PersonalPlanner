package personalplanner.Models;

public class AppointmentsByCustomerReport {

    private String customerName;
    private Integer numberOfAppointments;

    public AppointmentsByCustomerReport() {
    }

    public String getCustomerName() {

        return customerName;

    }

    public void setCustomerName(String customerName) {

        this.customerName = customerName;

    }

    public Integer getNumberOfAppointments() {

        return numberOfAppointments;

    }

    public void setNumberOfAppointments(Integer numberOfAppointments) {

        this.numberOfAppointments = numberOfAppointments;

    }

}
