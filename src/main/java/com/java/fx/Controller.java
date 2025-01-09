package com.java.fx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class Controller implements Initializable {

    @Autowired
    private Repository repo;

    @FXML
    private Button btnCreate, btnSave, btnUpdate, btnDelete, btnAdd;
    @FXML
    private Label lblTotal, lblWarning;

    @FXML
    private TextField txtId, txtName, txtPhone;

    @FXML
    private ListView<Model> list;

    @FXML
    private AnchorPane homeForm, createForm, searchForm;

    @FXML
    private BarChart<String, Number> homeChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @Autowired
    private DBService chartService;

    public void list() {
        list.setItems(FXCollections.observableArrayList(repo.findAll()));
        lblTotal.setText(String.valueOf(list.getItems().size()));
    }

    @FXML
    public void create() {
        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();

        if (!name.isEmpty() && !phone.isEmpty()) {
            if (phone.matches("\\d+") && phone.length() == 8) {
                    Model mod = new Model();
                    mod.setName(name);
                    mod.setPhone(phone);
                    repo.save(mod);
                    list();
                    clear();
                    lblWarning.setVisible(false);
            } else {
                lblWarning.setText("\"Phone number must contain only digits and be exactly 8 characters long.");
                lblWarning.setVisible(true);
            }
        } else {
            lblWarning.setText("Both Name and Phone fields are required.");
            lblWarning.setVisible(true);
        }
    }

    @FXML
    public void clear() {
        txtId.clear();
        txtName.clear();
        txtPhone.clear();
    }

    @FXML
    public void populateBarChart() {
        homeChart.getData().clear();

        List<Object[]> results = chartService.getNewEmployeesByDate();
        results.sort(Comparator.comparing(o -> LocalDate.parse(o[0].toString())));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("New Employees");

        for (Object[] row : results) {
            String date = row[0].toString();
            int count = ((Number) row[1]).intValue();
            XYChart.Data<String, Number> data = new XYChart.Data<>(date, count);
            series.getData().add(data);

            Tooltip.install(data.getNode(), new Tooltip("Date: " + date + "\nCount: " + count));
        }

        homeChart.getData().add(series);

        CategoryAxis xAxis = (CategoryAxis) homeChart.getXAxis();
        xAxis.setTickLabelRotation(45);
        xAxis.setTickLabelGap(10);

        homeChart.setTitle("New Employees Over Time");
        homeChart.getXAxis().setLabel("Date");
        homeChart.getYAxis().setLabel("Number of Employees");
    }

    @FXML
    public void switchForm(ActionEvent event) {
        if (event.getSource() == btnCreate) {
            homeForm.setVisible(true);
            createForm.setVisible(false);
            searchForm.setVisible(false);

            btnCreate.getStyleClass().remove("active-btn");
            btnUpdate.getStyleClass().remove("active-btn");
            btnSave.getStyleClass().remove("active-btn");
            btnCreate.getStyleClass().add("active-btn");

            populateBarChart();
        } else if (event.getSource() == btnUpdate) {
            homeForm.setVisible(false);
            createForm.setVisible(true);
            searchForm.setVisible(false);

            btnCreate.getStyleClass().remove("active-btn");
            btnUpdate.getStyleClass().remove("active-btn");
            btnSave.getStyleClass().remove("active-btn");
            btnUpdate.getStyleClass().add("active-btn");

            list();
        } else if (event.getSource() == btnSave) {
            homeForm.setVisible(false);
            createForm.setVisible(false);
            searchForm.setVisible(true);

            btnCreate.getStyleClass().remove("active-btn");
            btnUpdate.getStyleClass().remove("active-btn");
            btnSave.getStyleClass().remove("active-btn");
            btnSave.getStyleClass().add("active-btn");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list();
        populateBarChart();
    }

}
