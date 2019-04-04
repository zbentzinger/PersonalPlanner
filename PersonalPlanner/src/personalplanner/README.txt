README - C195 Software II - Task GZP1



Student: Zach Bentzinger
StudentID: 000748862
Student Email: zbentz2@wgu.edu
Mentor: Saqib Khalil
Mentor Email: saqib.khalil@wgu.edu

## REVISION 1:
I have added a validateForm() method to the AddCustomerViewController.java class and EditCustomerViewController.java class as well as an error label on both forms. This method will show the error label if either the Postal Code or Phone fields contain non-numeric values.

## General
This is a JavaFX application that acts as a personal planner/calendar. It is a fairly simple CRUD application that has a backing MySQL database. The codebase has inline comments referencing which part of the rubric the block of code pertains to. The database that was provided did not match the ERD, so I altered the schema. From what I could tell from browsing the course page, this is allowed. I can definitely revert and make the accommodating changes in the app. One last general note, in order to make things easier with associating objects with each other, I overrode the hashCode(), equals() and toString() methods in a few of the Model classes to equate their database id attribute as unique enough. This way I can reuse objects in ChoiceBoxes.

Here are the schema changes I made:
    ALTER TABLE `address` ADD FOREIGN KEY (`cityId`) REFERENCES `city`(`cityId`);
    ALTER TABLE `address` MODIFY COLUMN `addressid` INT auto_increment;
    ALTER TABLE `appointment` ADD COLUMN `type` TEXT NOT NULL AFTER `contact`;
    ALTER TABLE `appointment` ADD COLUMN `userId` INT NOT NULL AFTER `customerId`, ADD FOREIGN KEY (`userId`) REFERENCES `user`(`userId`);
    ALTER TABLE `appointment` ADD FOREIGN KEY (`customerId`) REFERENCES `customer`(`customerId`);
    ALTER TABLE `appointment` MODIFY COLUMN `appointmentid` INT auto_increment;
    ALTER TABLE `city` ADD FOREIGN KEY (`countryId`) REFERENCES `country`(`countryId`);
    ALTER TABLE `city` MODIFY COLUMN `cityid` INT auto_increment;
    ALTER TABLE `country` MODIFY COLUMN `countryid` INT auto_increment;
    ALTER TABLE `customer` ADD FOREIGN KEY (`addressId`) REFERENCES `address`(`addressId`);
    ALTER TABLE `customer` MODIFY COLUMN `customerid` INT auto_increment;
    ALTER TABLE `reminder` MODIFY COLUMN `reminderid` INT auto_increment;
    ALTER TABLE `user` MODIFY COLUMN `userid` INT auto_increment;

In addition to the schema changes, I also added some test data for addressing:
    INSERT INTO USER (`userName`, `password`, `active`) VALUES ('test', 'test', TRUE);
    INSERT INTO `country` (`country`, `createDate`, `createdBy`, `lastUpdateBy`) VALUES ('United States', NOW(), 'test', 'test'), ('England', NOW(), 'test', 'test');
    INSERT INTO `city` (`city`, `countryId`, `createDate`, `createdBy`, `lastUpdateBy`) VALUES ('Phoenix', 1, NOW(), 'test', 'test'), ('New York', 1, NOW(), 'test', 'test'), ('london', 2, NOW(), 'test', 'test');

### Log in creds for the app (there is only one user):
    Username: test
    Password: test

### Database:
    Username: U04Szi
    Password: 53688332123
    Database: U04Szi
    Host: 52.206.157.109
    Port: 3306



## Project Structure
Here are the packages and classes in the project:
    - src/personalplanner
        - PersonalPlanner.java: Main interface, launches the JavaFX app, instantiates the logger.
    - src/personalplanner/Logs: Will contain log.txt - See Rubric Section J. I have purposely not included a log.txt to demonstrate the app will generate the file if not found.
    - src/personalplanner/Views: Contains the FXML.xml files used for the UI, as well as a Theme.css for styling.
    - src/personalplanner/Controllers: Contains the associative controller classes for the FXML files.
    - src/personalplanner/Utils
        - mysql-connector-java-5.1.21.java: MySQL JDBC for connecting to the provided database. I used version 5 instead of 8 due to a bug regarding timestamps.
        - Database.java: Static class for establishing and closing connections to the database.
        - Utils.java: Static class that provides some generic methods and variables used by the entire application - mainly strings and timestamp handling.
        - LoginView_en_US.properties: English translation for internationalization - See Rubric Section A.
        - LoginView_fr_FR.properties: French translation for internationalization - See Rubric Section A.
    - src/personalplanner/Models: Contains POJOs for all database tables and two reports (third report didn't require a special class).
    - src/personalplanner/DAO
        - MainDAO.java: I chose to create just one central DAO for the project. This is the interface class for that DAO.
        - MainDAOImpl.java: This is the implementation class for the MainDAO interface class.
        - InvalidUserException.java: Custom Exception class that will be raised if sent user credentials are invalid. See Rubric section A and F.



## Rubric implementation
As stated earlier, I have included inline comments next to code that reflect a portion of the rubric, but I will also describe those implementations here.

### Section A:
The functionality for this section can be found in LoginViewController.java and LoginView.fxml.

The internationalization ResourceBundles can be found in src/personalplanner/Utils. I chose the United States/English and France/French for the two languages to implement.

### Section B:
The functionality for this section can be found in CustomersViewController.java (displays a tableview of all customers) and CustomersView.fxml. 

For adding new customers and their addresses, that functionality is found in AddCustomerViewController.java and AddCustomerView.fxml.

For editing existing customers, once a customer has been selected on the CustomersView, the edit button will become enabled. This functionality is found in EditCustomerViewController.java and EditCustomerView.fxml

For delete existing customers, once a customer has been selected on the CustomersView, the delete button will become enabled, clicking that will delete the customer, its address and all associated appointments from the database.

See REVISION 1. Added validation for phone and postal fields per request.

### Section C:
The functionality for adding new appointments is found in AddAppointmentViewController.java and AddAppointmentView.fxml.

For editing existing appointments, once an appointment has been selected on the Calendar, the edit button will become enabled. This functionality is found in EditAppointmentViewController.java and EditAppointmentView.fxml

To delete an appointment, either delete its customer, or select an appointment from the calendar and the delete button will become enabled. Clicking that will delete the appointment from the database.

### Section D:
I opted for a TableView for displaying the calendar. Additionally there are two buttons that will populate said table - weekly/monthly.

This functionality can be found in CalendarViewController.java and CalendarView.fxml.

### Section E:
This part was tricky as I found there to be a bug with version 8 of the JDBC for MySQL, so I down graded to 5.

The functionality for this is predominently found in the MainDAOImpl.java class. I essentially convert the LocalDataTime object for the appointment to UTC (with offset) before saving it to the database. And when I retrieve the row from the database I convert the UTC to the local machine's timezone before displaying it.

### Section F:

#### Scheduling an appointment outside business hours and scheduling overlapping appointments (F1 and F2):
Inside the AddAppointmentViewController and EditAppointmentViewController, there is a method called: validateAppointment(). This method will show an alert box if the following is true regarding the prospective appointment:
    - its end date is before its start date.
    - its end date is equal to its start date.
    - its start date is before 8AM.
    - its start date is after 5PM.
    - its end date is before 8AM.
    - its end date is after 5PM.
    - its start date falls on a Saturday or Sunday.
    - its end date falls on a Saturday or Sunday.
    - MainDAO.appointmentConflicts() returns true. This method issues a database call that queries if there are any appointments occurring between the prospective appointment's start/end date, and returns a boolean. NOTE: the query filters out the prospective appointment if editing an appointment.

#### Entering nonexistent or invalid customer data (F3):
Inside the AddCustomerViewController and EditCustomerViewController there is a method that binds all fields on the form to the Save button (disabledProperty()) - bindButtonsToForm(). This prevents a user from sending invalid as the button will not be enabled until they enter valid information.

Additionally, when the associated stages are loaded, the Country and City drop downs will be pre-filled with Country and City objects with a default one being selected.

See REVISION 1. Added validation for phone and postal fields per request.

#### Entering an incorrect username and password (F4):
This is where the InvalidUserException.java comes in to play. When the user clicks the Login button, the form will send the prospective credentials to the MainDAOImpl.getUser() method. This method throws said exception if the user is not found in the database. If the form receives that exception, it will show a label (with internationalization) under the form fields and will not log the user in.

This functionality can be found in LoginViewController.

### Section G:
I have many more than two lambdas in the source code. They are well documented with inline comments. 

The first example I used a lambda for can be found in LoginViewController.initialize(), where I map the LoginButton to the login() method. Note: I map all button actions using lambdas across the entire application.

The second example of a lambda I used can be found in CalendarViewController.setupCalendar() method. I format dates on the calendar using a lambda and SimpleStringProperty so the dates are easier to parse visually. Note: I map all TableView columns across the app using lambdas as well.

A third example of a lambda I used can be found in AddCustomerViewController.initialize(), where I map a listener on the countryDropDown to repopulate the cityDropDown so that cities match up with their appropriate country in the ChoiceBoxes.

### Section H:
After a user has Authenticated, the LoginViewController will call MainDAOImpl.isAppointmentSoon(User user) with the user's object. This will return a boolean and if true, an alert box will be shown. That DAO method queries the appointment table checking if an appointment is within 15 minutes of the current time and if the userid is the sent user's.

Please note that sometimes the AlertBox will display behind the main application window, though you should get a alert sound when it pops up.

### Section I:
For the custom report I chose to report on the number of appointments by customer. The other two reports are on the number of appointments by month per type and each consultant's (user) schedule.

Most of this functionality can be found in ReportsViewController.java, ReportsView.fxml, MainDAOImpl, AppointmentsByCustomerReport.java model and AppointmentsByMonthReport.java model.

The reports are displayed, each in their own TableView, in a TabPane inside ReportsView.

### Section J:
I utilized most of Java's default logger here. I just added a file handler since the default log formatter already adds timestamps to the log entry. The functionality for this can be found in PersonalPlanner.main. I instantiate a static logger in Utils, that each controller can use to log to the file.

The file, will be created if it is not found via the createNewFile() File API. This file can be found in src/personalplanner/Logs/log.txt

Additionally I added some basic logging when the user navigates around the app. I believe this section only asked for a log when the user logs in, attempts to log in and logs out.

### Section K:
This document fulfills this section.