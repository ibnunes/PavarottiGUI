/*
 * Copyright (C) 2019 Igor Nunes, Beatriz Costa
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pavarottigui;

import org.pavarottigui.types.PerformanceTableItem;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import javafx.collections.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.control.TextInputDialog;

import org.pavarottigui.pentagon.Pentagon;
import org.pavarottigui.types.StaffTableItem;

/**
 *
 * @author Igor Nunes, Beatriz Costa
 */
public class FXMLPavarottiController implements Initializable {
    // --- APPLICATION CORE ----------------------------------------------------
    private Pentagon pentagon;  // Shortcut to the main classe's pentagon object
    
    
    // --- FXML COMPONENTS AND RELATED -----------------------------------------
    // Main window
    @FXML private Label labelStatus;
    @FXML private Button buttonSave;
    @FXML private Button buttonSaveExit;
    @FXML private TabPane tabpaneMain;
    
    // Tab: company information
    @FXML private Tab tabInfoMgr;
    @FXML private Label labelCompanyName;
    @FXML private Label labelCompanyDirector;
    @FXML private Label labelCompanyCity;
    @FXML private Label labelCompanyCountry;
    @FXML private TextField txtCompanyName;
    @FXML private TextField txtCompanyCity;
    @FXML private ComboBox comboCompanyCountry;
    @FXML private TextField txtCompanyDirectorID;
    @FXML private Button buttonCompanySave;
    
    // Tab: staff management
    @FXML private Tab tabStaffMgr;
    @FXML private ComboBox comboStaffType;
    @FXML private ComboBox comboStaffGender;
    @FXML private TableView tableStaff;
    @FXML private TableColumn tblcolStaffID;
    @FXML private TableColumn tblcolStaffName;
    @FXML private TableColumn tblcolStaffGender;
    @FXML private TableColumn tblcolStaffAdmission;
    @FXML private TableColumn tblcolStaffPosition;
    @FXML private TextField txtStaffID;
    @FXML private TextField txtStaffName;
    @FXML private TextField txtStaffPosition;
    @FXML private Button buttonStaffSaveChanges;
    @FXML private Button buttonStaffDelete;
    @FXML private Button buttonStaffNew;
    
    // Tab: performances management
    @FXML private Tab tabPerfMgr;
    @FXML private TableView tablePerformances;
    @FXML private TableColumn tblcolPerfID;
    @FXML private TableColumn tblcolPerfName;
    @FXML private TableColumn tblcolPerfPrice;
    @FXML private TableColumn tblcolPerfDays;
    @FXML private TableColumn tblcolPerfOperaDir;
    @FXML private TableColumn tblcolPerfCastingDir;
    @FXML private TableColumn tblcolPerfSingers;
    @FXML private TableColumn tblcolPerfDancers;
    @FXML private TableColumn tblcolPerfTickets;
    @FXML private TextArea txtPerfWhens;
    @FXML private TextArea txtPerfSingers;
    @FXML private TextArea txtPerfDancers;
    @FXML private TextField txtPerfID;
    @FXML private TextField txtPerfName;
    @FXML private TextField txtPerfPrice;
    @FXML private TextField txtPerfOpera;
    @FXML private TextField txtPerfCasting;
    @FXML private Button buttonPerfSaveChanges;
    @FXML private Button buttonPerfDelete;
    @FXML private Button buttonPerfNew;
    
    // Tab: tickets
    @FXML private GridPane gridHall;
    @FXML private TextField txtTicketPerfID;
    @FXML private Button buttonTicketID2Dates;
    @FXML private ComboBox comboTicketPerfDates;
    @FXML private Button buttonTicketOpenHall;
    private ObservableList<Button> hallButtons;
    
    // Tab: statistics
    @FXML private Tab tabStatMgr;
    // @FXML private TextArea txtStat;
    
    
    // --- PUBLIC METHODS ------------------------------------------------------
    public void setStatus(String status) {
        labelStatus.setText(status);
    }
    
    public void resetStatus() {
        labelStatus.setText("");
    }
    
    
    // --- CONTROLLER ----------------------------------------------------------
    // Main window
    @FXML
    private void handleButtonSaveExitAction(ActionEvent event) {
        System.out.println("User has ordered the application to be shutdown.");
        setStatus("Saving and closing...");
        pentagon.stage().close();
    }
    
    @FXML
    private void handleButtonSaveAction(ActionEvent event) {
        System.out.println("User has ordered the application to save changes.");
        setStatus("Saving...");
        boolean success = true;
        try {
            pentagon.core().refresh();
        } catch (Exception e) {
            success = false;
            setStatus("Error while saving! (" + e.getMessage() + ")");
        }
        if (success)
            setStatus("Saved.");
    }
    
    
    // Company Information
    
    @FXML
    private void handleTabInfoMgrOnFocus(Event event) {
        if (tabInfoMgr.isSelected())
            refreshCompanyInfo();
    }
    
    @FXML
    private void handleButtonCompanySaveAction(ActionEvent event) {
        final String NAME    = txtCompanyName.getText();
        final String CITY    = txtCompanyCity.getText();
        final String COUNTRY = (String) comboCompanyCountry.getSelectionModel().getSelectedItem();
        Integer DIRECTOR     = pentagon.getCompanyDirectorID();
        try {
            DIRECTOR = Integer.valueOf(txtCompanyDirectorID.getText());
        } catch (NumberFormatException e) {
            pentagon.showError("The provided ID is not a number!");
        }
        pentagon.setCompanyInfo(NAME, CITY, COUNTRY, DIRECTOR);
        refreshCompanyInfo();
        setStatus("Changes to Company information submitted.");
    }
    
    private void refreshCompanyInfo() {
        if (pentagon != null) {
            System.out.println("Refreshing company information...");

            pentagon.refreshCompanyInfo();

            labelCompanyName.setText(pentagon.getCompanyName());
            labelCompanyCity.setText(pentagon.getCompanyCity());
            labelCompanyCountry.setText(pentagon.getCompanyCountry());
            labelCompanyDirector.setText(pentagon.getCompanyDirectorName());

            txtCompanyName.setText(pentagon.getCompanyName());
            txtCompanyCity.setText(pentagon.getCompanyCity());
            comboCompanyCountry.getSelectionModel().select(pentagon.getCompanyCountry());
            txtCompanyDirectorID.setText(pentagon.getCompanyDirectorID().toString());
        }
    }
    
    
    // Staff management
    @FXML
    private void handleTabStaffMgrOnFocus(Event event) {
        if (tabStaffMgr.isSelected())
            refreshStaff();
    }
    
    @FXML
    private void handleComboStaffTypeAction(ActionEvent event) {
        int INDEX = comboStaffType.getSelectionModel().getSelectedIndex();
        System.out.printf("Got new staff management type: index %d.\n", INDEX);
        switch (INDEX) {
            case 0:  pentagon.setStaffMgrType(Pentagon.StaffType.DIRECTOR); break;
            case 1:  pentagon.setStaffMgrType(Pentagon.StaffType.SINGER);   break;
            case 2:  pentagon.setStaffMgrType(Pentagon.StaffType.DANCER);   break;
            default: break;
        }
        refreshStaff();
        setStatus(
                String.format(
                        "Selected staff member class %s.",
                        (String) comboStaffType.getSelectionModel().getSelectedItem()
                )
        );
    }
    
    @FXML
    private void handleTableStaffMgrOnClick(MouseEvent event) {
        Object selectedItem = tableStaff.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            final String ID = ((StaffTableItem) selectedItem).getId();
            System.out.printf("\tSelected staff ID: %s\n", ID);

            pentagon.setStaffMgrID(ID);

            final String  STAFFID  = pentagon.getIDFromStaff();
            final String  NAME     = pentagon.getNameFromStaff();
            final String  POSITION = pentagon.getPositionFromStaff();
            final int     GENDER   = pentagon.getGenderFromStaff();

            txtStaffID.setText(STAFFID);
            txtStaffName.setText(NAME);
            txtStaffPosition.setText(POSITION);
            comboStaffGender.getSelectionModel().select(GENDER);
        }
    }
    
    @FXML
    private void handleButtonStaffDeleteAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(pentagon.getAppName());
        alert.setHeaderText("Deleting a staff member");
        alert.setContentText("Do you confirm the deletion of the staff member with ID " + pentagon.getIDFromStaff() + "?");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("NO", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result;
        
        result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            pentagon.deleteStaff();
            refreshStaff();
            setStatus("Staff member deleted.");
        }
    }
    
    @FXML
    private void handleButtonStaffNewAction(ActionEvent event) {
        if (!pentagon.createStaff()) {
            pentagon.showError("There was an error creating the new staff member.");
        } else {
            pentagon.showInfo("New staff member created! You can now change it from the table.");
        }
        refreshStaff();
        setStatus("New staff member created.");
    }
    
    @FXML
    private void handleButtonStaffSaveChangesAction(ActionEvent event) {
        final String NAME     = txtStaffName.getText();
        final String POSITION = txtStaffPosition.getText();
        final int    GENDER   = comboStaffGender.getSelectionModel().getSelectedIndex();
        
        txtStaffName.setText(pentagon.setNameFromStaff(NAME));
        txtStaffPosition.setText(pentagon.setPositionFromStaff(POSITION));
        comboStaffGender.getSelectionModel().select(pentagon.setGenderFromStaff(GENDER));
        
        refreshStaff();
        setStatus("Changes to staff submitted.");
    }
    
    public void refreshStaff() {
        System.out.print("Refreshing staff... ");
        ObservableList<StaffTableItem> STAFF = pentagon.getStaffItems();
        System.out.printf("[got %d items]\n", STAFF.size());
        tableStaff.getItems().clear();
        tableStaff.getItems().addAll(STAFF);
    }
    
    
    // Performances management
    @FXML
    private void handleTabPerfMgrOnFocus(Event event) {
        if (tabPerfMgr.isSelected())
            refreshPerformances();
    }
    
    @FXML
    private void handleTablePerfMgrOnClick(MouseEvent event) {
        Object selectedItem = tablePerformances.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            final String ID = ((PerformanceTableItem) selectedItem).getId();
            System.out.printf("\tSelected performance ID: %s\n", ID);

            pentagon.setPerfMgrID(ID);

            final String WHENS   = pentagon.getWhensFromPerformance();
            final String SINGERS = pentagon.getSingersFromPerformance();
            final String DANCERS = pentagon.getDancersFromPerformance();
            final String OPERA   = pentagon.getOperaDirectorFromPerformance();
            final String CASTING = pentagon.getCastingDirectorFromPerformance();
            final String NAME    = pentagon.getNameFromPerformance();
            final String SHOWID  = pentagon.getIDFromPerformance();
            final String PRICE   = pentagon.getPriceFromPerformance();

            txtPerfWhens.setText(WHENS);
            txtPerfSingers.setText(SINGERS);
            txtPerfDancers.setText(DANCERS);
            txtPerfID.setText(SHOWID);
            txtPerfName.setText(NAME);
            txtPerfCasting.setText(CASTING);
            txtPerfOpera.setText(OPERA);
            txtPerfPrice.setText(PRICE);
        }
    }
    
    @FXML
    private void handleButtonPerfSaveChangesAction(ActionEvent event) {
        final String WHENS   = txtPerfWhens.getText();
        final String SINGERS = txtPerfSingers.getText();
        final String DANCERS = txtPerfDancers.getText();
        final String OPERA   = txtPerfOpera.getText();
        final String CASTING = txtPerfCasting.getText();
        final String NAME    = txtPerfName.getText();
        final String SHOWID  = pentagon.getIDFromPerformance(); // ID can't be changed
        final String PRICE   = txtPerfPrice.getText();
        
        txtPerfID.setText(SHOWID);
        txtPerfName.setText(pentagon.setNameFromPerformance(NAME));
        txtPerfPrice.setText(pentagon.setPriceFromPerformance(PRICE));
        txtPerfOpera.setText(pentagon.setOperaDirectorFromPerformance(OPERA));
        txtPerfCasting.setText(pentagon.setCastingDirectorFromPerformance(CASTING));
        txtPerfSingers.setText(pentagon.setSingersFromPerformance(SINGERS));
        txtPerfDancers.setText(pentagon.setDancersFromPerformance(DANCERS));
        txtPerfWhens.setText(pentagon.setWhensFromPerformance(WHENS));
        
        refreshPerformances();
        setStatus("Changes to performance submitted.");
    }
    
    @FXML
    private void handleButtonPerfDeleteAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(pentagon.getAppName());
        alert.setHeaderText("Deleting a performance");
        alert.setContentText("Do you confirm the deletion of the performance with ID " + pentagon.getIDFromPerformance() + "?");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("NO", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result;
        
        result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            pentagon.deletePerformance();
            refreshPerformances();
            setStatus("Performance deleted.");
        }
    }
    
    @FXML
    private void handleButtonPerfNewAction(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("new");
        dialog.setTitle(pentagon.getAppName());
        dialog.setHeaderText("Create a new performance");
        dialog.setContentText("Insert the ID:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String SHOWID = result.get();
            if (SHOWID.isEmpty()) {
                pentagon.showWarning("ID can't be empty.");
            } else {
                System.out.println("Got new ID: " + SHOWID);
                if (pentagon.createPerformance(SHOWID)) {
                    System.out.println("New performance created");
                    refreshPerformances();
                    setStatus("New performance created.");
                }
            }
        }
    }
    
    public void refreshPerformances() {
        System.out.print("Refreshing performances... ");
        
        ObservableList<PerformanceTableItem> PERF = pentagon.getPerformancesItems();
        System.out.printf("[got %d items]\n", PERF.size());
        tablePerformances.getItems().clear();
        tablePerformances.getItems().addAll(PERF);
    }
    
    
    
    
    // Ticket management
    @FXML
    private void handleTxtTicketPerfIDOnChange(KeyEvent event) {
        System.out.println("Triggered handleTxtTicketPerfIDOnChange");
        if (!comboTicketPerfDates.getItems().isEmpty())
            comboTicketPerfDates.getItems().clear();
        if (!hallButtons.isEmpty()) {
            hallButtons.clear();
            gridHall.getChildren().clear();
        }
    }
    
    @FXML
    private void handleComboTicketPerfDatesOnChange(ActionEvent event) {
        System.out.println("Triggered handleComboTicketPerfDatesOnChange");
        if (!hallButtons.isEmpty()) {
            hallButtons.clear();
            gridHall.getChildren().clear();
        }
        
        final String ID = txtTicketPerfID.getText();
        final boolean EXISTS = pentagon.core().performanceIDExists(ID);
        if (!EXISTS) {
            pentagon.showError("The performance with ID \"" + ID + "\" does not exist!");
            comboTicketPerfDates.getItems().clear();
            return;
        }
        final int SELECTED = comboTicketPerfDates.getSelectionModel().getSelectedIndex();
        showHall(ID, (short) SELECTED);
        setStatus("New hall selected. Ready to manage tickets.");
    }
    
    @FXML
    private void handleButtonTicketID2Dates(ActionEvent event) {
        final String ID = txtTicketPerfID.getText();
        final boolean EXISTS = pentagon.core().performanceIDExists(ID);
        if (!EXISTS) {
            pentagon.showError("The performance with ID \"" + ID + "\" does not exist!");
            comboTicketPerfDates.getItems().clear();
        } else {
            ArrayList<LocalDateTime> whens = pentagon.getOnlyWhensByID(ID);
            comboTicketPerfDates.setItems(FXCollections.observableArrayList(whens));
            comboTicketPerfDates.getSelectionModel().selectFirst();
            setStatus("Performance selected at its first date. Ready to manage tickets.");
        }
    }
    
    @FXML
    private void handleButtonHallSeat(MouseEvent event) {
        Button b = (Button) event.getSource();
        final String SEAT = b.getText();
        System.out.println("Got hall seat: " + SEAT);
        pentagon.setTicketPerfSeat(SEAT);
        
        boolean sold = pentagon.isTicketSold();
        Double price = pentagon.ticketPrice();
        System.out.printf("\tIs ticket sold? %b\n\tPrice: EUR %.2f\n", sold, price);
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(pentagon.getAppName());
        alert.setHeaderText("Seat " + SEAT);
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("NO", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result;
        
        if (sold) {
            alert.setContentText("Do you confirm the REFUND of this ticket?");
            result = alert.showAndWait();
            if (result.get() == buttonTypeYes) {
                if (pentagon.refundTicket()) {
                    pentagon.showInfo("The ticket was refunded!");
                    refreshHall();
                    setStatus("Ticket refunded.");
                } else
                    pentagon.showError("The ticket could not be refunded!");
            } else {
                pentagon.showInfo("The ticket was NOT refunded.");
            }
        } else {
            alert.setContentText("Do you confirm the SELL of this ticket?");
            result = alert.showAndWait();
            if (result.get() == buttonTypeYes) {
                if (pentagon.sellTicket()) {
                    pentagon.showInfo("The ticket was sold!");
                    refreshHall();
                    setStatus("Ticket sold.");
                } else
                    pentagon.showError("The ticket could not be sold!");
            } else {
                pentagon.showInfo("The ticket was NOT sold.");
            }
        }
    }
    
    private void refreshHall() {
        showHall(pentagon.getTicketPerfID(), pentagon.getTicketPerfWhenIndex());
    }
    
    private void showHall(final String SHOWID, final short WHENINDEX) {
        final ArrayList<ArrayList<Boolean>> MATRIX = pentagon.core().hallSoldMatrix(SHOWID, WHENINDEX);
        final int ROWS = MATRIX.size();
        final int COLS = MATRIX.get(0).size();
        
        gridHall.getChildren().clear();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Button b = new Button(String.format("%c%02d", (char)((int)'A' + i), j));
                Background p =
                        (MATRIX.get(i).get(j)) ?
                        new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)) :
                        new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY));
                b.backgroundProperty().set(p);
                b.setMinSize(40., 30.);
                b.setOnMouseClicked(event -> handleButtonHallSeat(event));
                hallButtons.add(b);
                gridHall.add(b, j, i);
            }
        }
        
        pentagon.setTicketPerfID(SHOWID);
        pentagon.setTicketPerfWhenIndex(WHENINDEX);
    }
    
    
    // Statistics
    @FXML
    private void handleTabStatMgrOnFocus(Event event) {
        if (tabStatMgr.isSelected()) { }
    }
    
    
    // --- INITIALIZATION ------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initializing the FXML controller...");
        
        this.pentagon = PavarottiGUI.pentagon;
        this.pentagon.bindFXML(this);
        
        hallButtons = FXCollections.observableArrayList();
        gridHall.setHgap(5.);
        gridHall.setVgap(5.);
        gridHall.getChildren().addAll(hallButtons);
        
        tblcolPerfID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblcolPerfName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblcolPerfPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tblcolPerfDays.setCellValueFactory(new PropertyValueFactory<>("days"));
        tblcolPerfOperaDir.setCellValueFactory(new PropertyValueFactory<>("opera"));
        tblcolPerfCastingDir.setCellValueFactory(new PropertyValueFactory<>("casting"));
        tblcolPerfSingers.setCellValueFactory(new PropertyValueFactory<>("singers"));
        tblcolPerfDancers.setCellValueFactory(new PropertyValueFactory<>("dancers"));
        tblcolPerfTickets.setCellValueFactory(new PropertyValueFactory<>("tickets"));
        
        comboStaffType.getItems().removeAll(comboStaffType.getItems());
        comboStaffType.getItems().addAll("Directors", "Singers", "Dancers");
        comboStaffType.getSelectionModel().select("Directors");
        pentagon.setStaffMgrType(Pentagon.StaffType.DIRECTOR);
        
        comboStaffGender.getItems().removeAll(comboStaffGender.getItems());
        comboStaffGender.getItems().addAll("Male", "Female", "Other");
        comboStaffGender.getSelectionModel().select("Other");
        
        tblcolStaffID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblcolStaffName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblcolStaffGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        tblcolStaffAdmission.setCellValueFactory(new PropertyValueFactory<>("admission"));
        tblcolStaffPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        
        ObservableList<String> countries = Stream.of(Locale.getISOCountries())
                .map(locales -> new Locale("", locales))
                .map(Locale::getDisplayCountry)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        comboCompanyCountry.setItems(countries);
    }
    
    public void loadInitialComponents() {
        refreshCompanyInfo();
    }
    
}
