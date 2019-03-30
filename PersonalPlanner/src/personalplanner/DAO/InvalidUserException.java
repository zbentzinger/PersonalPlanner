package personalplanner.DAO;

import java.util.ResourceBundle;

// Rubric A and F
public class InvalidUserException extends Throwable {

    ResourceBundle labels = ResourceBundle.getBundle("personalplanner.Utils.LoginView");

    public InvalidUserException(String error) {

        super(error);

    }

    @Override public String getLocalizedMessage() {

        return labels.getString(getMessage());

    }

}
