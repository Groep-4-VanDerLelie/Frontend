package com.vanderlelie.frontend.views;

import com.vanderlelie.frontend.enums.ViewRoute;
import com.vanderlelie.frontend.observers.AuthObserver;
import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AdminSideMenuView implements AuthObserver {

    @FXML
    private VBox adminContextMenu;

    @FXML
    private Text clientDetails;
    @FXML
    private Text defaultPackaging;
    @FXML
    private Text employees;
    @FXML
    private Text archive;
    @FXML
    private Text stock;
    @FXML
    private TabPane adminTab;


    public void initialize(){
    }

    @FXML
    public void onClick(){
        clientDetails.setUnderline(true);

        ViewNavigator.getInstance().navigateTo(ViewRoute.ADMIN);

        SingleSelectionModel<Tab> selectionModel = adminTab.getSelectionModel();

        selectionModel.select(1);
    }

    @Override
    public boolean update(boolean authorized) {
        return false;
    }
}
