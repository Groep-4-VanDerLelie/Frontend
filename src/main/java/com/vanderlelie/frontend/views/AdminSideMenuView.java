package com.vanderlelie.frontend.views;

import com.vanderlelie.frontend.enums.ViewRoute;
import com.vanderlelie.frontend.observers.AuthObserver;
import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AdminSideMenuView {

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

    private final int STOCK_TAB = 0;
    private final int CLIENT_TAB = 1;

    public void initialize(){
    }

    @FXML
    public void onClick(){
        clientDetails.setUnderline(true);
    }

    public void switchToStockTab() {
        selectTabByIndex(STOCK_TAB);
    }

    public void switchToClientTab() {
        selectTabByIndex(CLIENT_TAB);
    }

    private void selectTabByIndex(int index) {
        if (adminTab == null) {
            adminTab = (TabPane) ViewNavigator.root.lookup("#adminTab");
        }

        SingleSelectionModel<Tab> selectionModel = adminTab.getSelectionModel();
        selectionModel.select(index);
    }
}
