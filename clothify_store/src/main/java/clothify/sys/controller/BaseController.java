package clothify.sys.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class BaseController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private JFXButton homeButton;

    @FXML
    private JFXButton ordersButton;

    @FXML
    private JFXButton productsButton;

    @FXML
    private JFXButton suppliersButton;

    @FXML
    private JFXButton employeesButton;

    @FXML
    private JFXButton usersButton;

    @FXML
    private JFXButton reportsButton;

    @FXML
    public void initialize() {
        // Load the home view by default
        loadView("/view/home_view.fxml");

        // Add action listeners to the buttons
        homeButton.setOnAction(event -> loadView("/view/home_view.fxml"));
        ordersButton.setOnAction(event -> loadView("/view/orders_view.fxml"));
        productsButton.setOnAction(event -> loadView("/view/products_view.fxml"));
        suppliersButton.setOnAction(event -> loadView("/view/suppliers_view.fxml"));
        employeesButton.setOnAction(event -> loadView("/view/employees_view.fxml"));
        usersButton.setOnAction(event -> loadView("/view/users_view.fxml"));
        reportsButton.setOnAction(event -> loadView("/view/reports_view.fxml"));
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane newLoadedPane = loader.load();
            mainPane.setCenter(newLoadedPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
