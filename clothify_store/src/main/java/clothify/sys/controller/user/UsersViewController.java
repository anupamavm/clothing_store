package clothify.sys.controller.user;

import clothify.sys.db.DBConnection;
import clothify.sys.model.User;
import clothify.sys.model.tm.UserTableTM;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

public class UsersViewController implements Initializable {

    public JFXTextField txtUserId;
    public JFXTextField txtUserEmail;
    public JFXTextField txtUserName;
    public JFXCheckBox checkActive;
    public JFXCheckBox checkAdmin;
    public TableView tblUser;
    public TableColumn colUserId;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colIsAdmin;
    public TableColumn colIsActive;


    private List<User> userList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colIsAdmin.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
        colIsActive.setCellValueFactory(new PropertyValueFactory<>("isActive"));

        loadUsers();
        loadUserTable();

    }

    private void loadUserTable() {
        ObservableList<UserTableTM> userTableData = FXCollections.observableArrayList();

        List<User> userList = loadUsers();

        userList.forEach(user -> {
            UserTableTM userTableTM = new UserTableTM(
                    user.getId(),
                    user.getEmail(),
                    user.getName(),
                    user.getIsAdmin(),
                    user.getIsActive()
            );

            userTableData.add(userTableTM);
        });

        tblUser.setItems(userTableData);
    }

    private List<User> loadUsers() {
        ArrayList<User> allUsers;
        allUsers = UserController.getInstance().getAllUsers();
        return allUsers;
    }


    public void btnAddUserOnAction(ActionEvent actionEvent) {
        String userId = txtUserId.getText().trim();
        String userEmail = txtUserEmail.getText().trim();
        String userName = txtUserName.getText().trim();

        if (userId.isEmpty() || userEmail.isEmpty() || userName.isEmpty()) {
            showAlert("Error", "All fields must be filled out.");
            return;
        }

        // Validate email format
        if (!isValidEmail(userEmail)) {
            showAlert("Error", "Invalid email format.");
            return;
        }

        try {
            Connection connection = DBConnection.getInstance().getConnection();

            // Check if the email already exists
            String checkEmailSql = "SELECT id FROM user WHERE email = ?";
            try (PreparedStatement checkEmailStatement = connection.prepareStatement(checkEmailSql)) {
                checkEmailStatement.setString(1, userEmail);
                ResultSet emailResultSet = checkEmailStatement.executeQuery();
                if (emailResultSet.next()) {
                    showAlert("Error", "Email address already exists. Please use a different email.");
                    txtUserEmail.clear();
                    return;
                }
            }

            // Check if the user ID already exists
            String checkUserIdSql = "SELECT id FROM user WHERE id = ?";
            try (PreparedStatement checkUserIdStatement = connection.prepareStatement(checkUserIdSql)) {
                checkUserIdStatement.setString(1, userId);
                ResultSet userIdResultSet = checkUserIdStatement.executeQuery();
                if (userIdResultSet.next()) {
                    showAlert("Error", "User ID already exists. Please use a different ID.");
                    txtUserId.clear();
                    return;
                }
            }

            // If the user ID does not exist, proceed with the insertion
            String insertSql = "INSERT INTO user (id, email, name, isAdmin, isActive) VALUES (?,?,?,?,?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                insertStatement.setString(1, userId);
                insertStatement.setString(2, userEmail);
                insertStatement.setString(3, userName);
                insertStatement.setBoolean(4, checkAdmin.isSelected());
                insertStatement.setBoolean(5, checkActive.isSelected());

                insertStatement.executeUpdate();

                showAlert("Success", "User added successfully.");
                loadUsers();
                loadUserTable();
                clearText();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //show an alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // validate email format

    public void btnUpdateUserOnAction(ActionEvent actionEvent) {
        String userId = txtUserId.getText().trim();
        String userEmail = txtUserEmail.getText().trim();
        String userName = txtUserName.getText().trim();

        // Check if any of the fields are empty
        if (userId.isEmpty() || userEmail.isEmpty() || userName.isEmpty()) {
            showAlert("Error", "All fields must be filled out.");
            return;
        }

        // Validate email format
        if (!isValidEmail(userEmail)) {
            showAlert("Error", "Invalid email format.");
            return;
        }

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "UPDATE user SET email = ?, name = ?, isAdmin = ?, isActive = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, userEmail);
                preparedStatement.setString(2, userName);
                preparedStatement.setBoolean(3, checkAdmin.isSelected());
                preparedStatement.setBoolean(4, checkActive.isSelected());
                preparedStatement.setString(5, userId);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    showAlert("Success", "User updated successfully.");
                    loadUsers();
                    loadUserTable();
                    clearText();
                } else {
                    showAlert("Error", "No user found with ID: " + userId);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    public void btnDeleteUserOnAction(ActionEvent actionEvent) {
        String userId = txtUserId.getText();

        if (userId.isEmpty()) {
            showAlert("Error", "User ID must be filled out.");
            return;
        }

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM user WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, userId);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    showAlert("Success", "User deleted successfully.");
                    loadUsers();
                    loadUserTable();
                } else {
                    showAlert("Error", "No user found with ID: " + userId);
                }
            }
            clearText();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearText() {
        txtUserId.clear();
        txtUserEmail.clear();
        txtUserName.clear();
        checkAdmin.setSelected(false);
        checkActive.setSelected(false);
    }

    public void btnSearchUserOnAction(ActionEvent actionEvent) {
        String userId = txtUserId.getText();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM user WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getBoolean(4),
                        resultSet.getBoolean(5)
                );
                System.out.println(user);

                txtUserId.setText(user.getId());
                txtUserName.setText(user.getName());
                txtUserEmail.setText(user.getEmail());
                checkActive.setSelected(user.getIsActive());
                checkAdmin.setSelected(user.getIsAdmin());


            } else {
                System.out.println("No user found with ID: " + userId);
                showAlert("Error", "No such user");
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



}
