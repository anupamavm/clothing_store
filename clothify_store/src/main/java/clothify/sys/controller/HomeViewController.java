package clothify.sys.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;

import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {
    public ComboBox cmbProductCode;
    public Label lblTime;
    public Label lblDate;
    public Label lblOrderId;
    public Label lblProductName;
    public Label lblProductSize;
    public Label lblUnitPrice;
    public Label lblQty;
    public TableView tblCart;
    public TableColumn colProductId;
    public TableColumn colProductName;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public Label lblNetTotal;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerTelephone;
    public TextField txtQtyForCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        loadProductCode();
    }

    private void loadProductCode() {

    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        //Time
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO,e->{
            LocalTime time = LocalTime.now();
            lblTime.setText(
                    time.getHour()+" : "+time.getMinute()+" : "+time.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
    }

    public void txtAddtoCartOnAction(ActionEvent actionEvent) {
    }


}
