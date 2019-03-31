package personalplanner.DAO;

import java.util.ResourceBundle;
import personalplanner.Utils.Utils;

// Rubric F: Throw an exception that the login form can handle.
// Rubric A: Login form handles exception.
public class InvalidUserException extends Throwable {

    ResourceBundle labels = ResourceBundle.getBundle(Utils.RESOURCE_BUNDLE_PATH);

    public InvalidUserException(String error) {

        super(error);

    }

    @Override public String getLocalizedMessage() {

        return labels.getString(getMessage());

    }

}
