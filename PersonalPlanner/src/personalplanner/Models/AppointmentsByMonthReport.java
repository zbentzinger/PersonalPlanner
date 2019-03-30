package personalplanner.Models;

public class AppointmentsByMonthReport {

    private String appointmentType;
    private Integer numberOfAppointments;
    private String appointmentMonth;
    private Integer appointmentYear;

    public AppointmentsByMonthReport() {
    }

    public String getAppointmentType() {

        return appointmentType;

    }

    public void setAppointmentType(String appointmentType) {

        this.appointmentType = appointmentType;

    }

    public Integer getNumberOfAppointments() {

        return numberOfAppointments;

    }

    public void setNumberOfAppointments(Integer numberOfAppointments) {

        this.numberOfAppointments = numberOfAppointments;

    }

    public String getAppointmentMonth() {

        return appointmentMonth;

    }

    public void setAppointmentMonth(String appointmentMonth) {

        this.appointmentMonth = appointmentMonth;

    }

    public Integer getAppointmentYear() {

        return appointmentYear;

    }

    public void setAppointmentYear(Integer appointmentYear) {

        this.appointmentYear = appointmentYear;

    }

}
