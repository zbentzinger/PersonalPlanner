package personalplanner.Utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import personalplanner.DAO.MainDAO;
import personalplanner.DAO.MainDAOImpl;

public class Utils {

    public static final MainDAO DATABASE = new MainDAOImpl();

    public static final Logger LOGGER = Logger.getLogger("PersonalPlanner");

    public static String LOG_FILE_PATH = "src/personalplanner/Logs/log.txt";
    public static final String VIEWS_PATH = "/personalplanner/Views";
    public static final String ADD_APPOINTMENT_VIEW_PATH = VIEWS_PATH + "/AddAppointmentView.fxml";
    public static final String ADD_CUSTOMER_VIEW_PATH = VIEWS_PATH + "/AddCustomerView.fxml";
    public static final String CALENDAR_VIEW_PATH = VIEWS_PATH + "/CalendarView.fxml";
    public static final String CUSTOMERS_VIEW_PATH = VIEWS_PATH + "/CustomersView.fxml";
    public static final String EDIT_APPOINTMENT_VIEW_PATH = VIEWS_PATH + "/EditAppointmentView.fxml";
    public static final String EDIT_CUSTOMER_VIEW_PATH = VIEWS_PATH + "/EditCustomerView.fxml";
    public static final String HOME_VIEW_PATH = VIEWS_PATH + "/HomeView.fxml";
    public static final String LOGIN_VIEW_PATH = VIEWS_PATH + "/LoginView.fxml";
    public static final String REPORTS_VIEW_PATH = VIEWS_PATH + "/ReportsView.fxml";
    public static final String RESOURCE_BUNDLE_PATH = "personalplanner/Utils/LoginView";

    public static LocalTime getLocalTimeFromString(String timeStr) {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h[:mm]a");
         String sanitized = timeStr.replaceAll("\\s+","").toUpperCase();

         // instantiate with invalid time for error handling further on.
         LocalTime time = LocalTime.of(1, 0);
         try {

             time = LocalTime.parse(sanitized, formatter);

         } catch (Exception e) {} // do nothing

         return time;

    }

    // Rubric E: Convert UTC appointment times from database to user's local time.
    public static LocalDateTime toLocalTimeZone(LocalDateTime dateTime) {

        ZonedDateTime utcZoned = dateTime.atZone(ZoneId.of("UTC"));
        LocalDateTime localZoned = utcZoned.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();

        return localZoned;

    }

    // Rubric E: Save appointments in database as UTC.
    public static LocalDateTime toUTC(LocalDateTime dateTime) {

        ZonedDateTime localZoned = dateTime.atZone(ZoneId.systemDefault());
        LocalDateTime utcZoned = localZoned.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

        return utcZoned;

    }

}
