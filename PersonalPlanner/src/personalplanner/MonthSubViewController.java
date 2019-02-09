package personalplanner;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class MonthSubViewController implements Initializable {

    @FXML
    private AnchorPane calendarPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
       createCalendar(); 
        
    }
    
    private void createCalendar() {
        
        GridPane calendar = new GridPane();
        
        // Set gridpane to resize based on anchorpane.
        AnchorPane.setBottomAnchor(calendar, 0.0);
        AnchorPane.setTopAnchor(calendar, 0.0);
        AnchorPane.setLeftAnchor(calendar, 0.0);
        AnchorPane.setRightAnchor(calendar, 0.0);
        
        calendar.setGridLinesVisible(true);
        
        
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(50);
        calendar.getColumnConstraints().add(column);
        
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        calendar.getColumnConstraints().add(column1);
        
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(50);
        calendar.getRowConstraints().add(row);
        
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);
        calendar.getRowConstraints().add(row1);
        
        
        calendarPane.getChildren().add(calendar);
        
    }
    
}
