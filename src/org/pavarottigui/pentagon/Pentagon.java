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
package org.pavarottigui.pentagon;

import java.time.LocalDate;
import org.pavarottigui.types.PerformanceTableItem;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;

import javafx.stage.Stage;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;

import org.pavarottigui.*;

import org.pavarotti.core.components.Performance;
import org.pavarotti.core.intf.Person;
import org.pavarotti.core.intf.StaffMember;
import org.pavarotti.ui.intf.*;
import org.pavarotti.core.throwable.*;
import org.pavarottigui.types.StaffTableItem;
import org.strutil.ArrayUtility;

/**
 * PAVAROTTI Viewer implementation
 * @author Igor Nunes, Beatriz Costa
 */
public class Pentagon implements Viewer {
    public static enum StaffType {
        DIRECTOR, DANCER, SINGER;
    }
    
    private FXMLPavarottiController vControl;
    private Controller vCore;
    
    private Stage vStage;
    private Scene vScene;
    
    private String appname;
    private Image applogo;
    
    /**
     * The constructor of the class
     */
    public Pentagon() {
        super();
        appname = "";
    }
    
    /**
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return
                String.format(
                        "%s {\n\tcontrol = %s\n\t   core = %s\n}",
                        getClass(), vControl, vCore
                );
    }
    
    /**
     * Returns the application name and version
     * @return the string
     */
    public String getAppName() {
        return appname;
    }
    
    public Image getLogo() {
        return applogo;
    }
    
    public void setLogo(String path) {
        System.out.print("Setting logo... ");
        try {
            this.applogo = new Image( /*"file:" +*/ path);
            vStage.getIcons().add(getLogo());
            System.out.println("[DONE]");
        } catch (Exception e) {
            System.out.println("[ERROR: " + e.getMessage() + "]");
        }
    }
    
    public void setStyle(String path) {
        System.out.print("Setting stylesheet... ");
        try {
            vScene.getStylesheets().add(path);
            System.out.println("[DONE]");
        } catch (Exception e) {
            System.out.println("[ERROR: " + e.getMessage() + "]");
        }
    }
    
    /**
     * Binds the Pentagon with the Stage where action is taking place.
     * @param stage the stage
     */
    public void bindStage(Stage stage) {
        this.vStage = stage;
    }
    
    public void bindScene(Scene scene) {
        this.vScene = scene;
    }
    
    public void closeStage() {
        this.vStage.close();
    }
    
    /**
     * Binds the Pentagon with the FXML Controller of the main window
     * @param control the FXML Controller
     */
    public void bindFXML(FXMLPavarottiController control) {
        this.vControl = control;
    }
    
    /**
     * Loads the Viewer
     */
    @Override
    public void loadViewer() {
        System.out.println("Loading GUI");
    }
    
    /**
     * Launches the Viewer
     * @param args the command line arguments
     * @throws Exception 
     */
    @Override
    public void launchViewer(String[] args) throws Exception {
        System.out.println("Launching GUI");
        vStage.setTitle(appname);
        vControl.loadInitialComponents();
        vStage.show();
    }
    
    /**
     * Binds the Pentagon with the program controller
     * @param controller the program controller
     */
    @Override
    public void bindController(Controller controller) {
        System.out.println("Binding application controller");
        this.vCore = controller;
        appname = "Pavarotti " + vCore.getVersion().toString();
    }
    
    public void refresh() throws Exception {
        vCore.refresh();
    }
    
    public void stop() {
        System.out.println("The core is being stopped");
        try {
            vCore.stop();
            System.out.println("Stopped application");
        } catch (Exception e) {
            System.out.println("EXCEPTION while shutting down the core: " + e.getMessage());
        }
    }
    
    @Override
    public void emergencyStop() {
        Platform.exit();
    }
    
    /**
     * Shows Help window or dialog
     */
    @Override
    public void showHelp() {
        // TODO
    }
    
    /**
     * Shows About window or dialog
     */
    @Override
    public void showAbout() {
        // TODO
    }
    
    /**
     * Shows an Information dialog
     * @param msg the message
     */
    @Override
    public void showInfo(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(appname);
        alert.setHeaderText("Information");
        alert.setContentText(msg);
        vControl.setStatus("Info: " + msg);
        alert.showAndWait();
        vControl.resetStatus();
    }
    
    /**
     * Shows a Message dialog
     * @param msg the message
     */
    @Override
    public void showMessage(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(appname);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        vControl.setStatus("Message: " + msg);
        alert.showAndWait();
        vControl.resetStatus();
    }
    
    /**
     * Shows a Warning dialog
     * @param msg the message
     */
    @Override
    public void showWarning(String msg) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(appname);
        alert.setHeaderText("Warning");
        alert.setContentText(msg);
        vControl.setStatus("Warning: " + msg);
        alert.showAndWait();
        vControl.resetStatus();
    }
    
    /**
     * Shows an Error dialog
     * @param msg the message
     */
    @Override
    public void showError(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(appname);
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        vControl.setStatus("Error: " + msg);
        alert.showAndWait();
        vControl.resetStatus();
    }
    
    /**
     * Shows a Yes/No dialog
     * @param prompt the prompt
     * @return true if OK, false if Cancel
     */
    public boolean showYesNo(String prompt) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(appname);
        alert.setHeaderText("Confirmation");
        alert.setContentText(prompt);
        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);
    }
    
    /**
     * Launches the first setup of the program, namely the Company information
     */
    @Override
    public void firstSetup() {
        String INFORMATION =
                "The company information is not set or complete.\n" +
                "You can change the name, city and country at your will.\n" +
                "Please note that you need to first set at least one director " +
                "to be able to select it as the Company Director.";
        
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(appname);
        alert.setHeaderText("Company information needs setup!");
        alert.setContentText(INFORMATION);

        alert.showAndWait();
        
        vCore.setupCompanyInfo("Company Name", "City", "Portugal", 0);
    }
    
    
    // -------------------------------------------------------------------------
    // --- COMPANY INFORMATION -------------------------------------------------
    
    private String  companyName;
    private String  companyCountry;
    private String  companyCity;
    private String  companyDirectorName;
    private Integer companyDirectorID;

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public String getCompanyDirectorName() {
        return companyDirectorName;
    }
    
    public Integer getCompanyDirectorID() {
        return companyDirectorID;
    }
    
    public void refreshCompanyInfo() {
        companyName    = vCore.getCompanyName();
        companyCity    = vCore.getCompanyCity();
        companyCountry = vCore.getCompanyCountry();
        try {
            companyDirectorName = vCore.getCompanyDirectorName();
            companyDirectorID   = vCore.getCompanyDirector();
        } catch (Exception e) {
            companyDirectorName = "";
            companyDirectorID   = 0;
        }
    }

    public void setCompanyInfo(String companyName, String city, String country, Integer director) {
        if (vCore.searchDirector(director) != null) {
            vCore.setupCompanyInfo(companyName, city, country, director);
        } else {
            showWarning(String.format("The provided director ID (%d) does not exist.", director));
            vCore.setupCompanyInfo(companyName, city, country, companyDirectorID);
        }
    }
    
    
    // -------------------------------------------------------------------------
    // --- STAFF ---------------------------------------------------------------
    
    private StaffType staffMgrType;
    private Integer   staffMgrID;
    private String    staffMgrName;
    private String    staffMgrPosition;
    private Integer   staffMgrGender;
    private String    staffMgrAdmission;
    
    public void setStaffMgrType(StaffType staffMgrType) {
        this.staffMgrType = staffMgrType;
    }
    
    public StaffType getStaffMgrType() {
        return staffMgrType;
    }
    
    public void setStaffMgrID(String staffMgrID) {
        this.staffMgrID = Integer.valueOf(staffMgrID);
        initStaffMgrInfo();
    }
    
    public Integer getStaffMgrID() {
        return staffMgrID;
    }
    
    public String getIDFromStaff() {
        return getStaffMgrID().toString();
    }

    public String getNameFromStaff() {
        return staffMgrName;
    }

    public <T extends Person & StaffMember> String setNameFromStaff(String name) {
        T t;
        switch (staffMgrType) {
            case DIRECTOR:
                t = vCore.searchDirector(staffMgrID);
                t.setName(name);
                vCore.modifyDirector(staffMgrID, t);
                break;
                
            case SINGER:
                t = vCore.searchSinger(staffMgrID);
                t.setName(name);
                vCore.modifySinger(staffMgrID, t);
                break;
                
            case DANCER:
                t = vCore.searchDancer(staffMgrID);
                t.setName(name);
                vCore.modifyDancer(staffMgrID, t);
                break;
        }
        return name;
    }

    public String getPositionFromStaff() {
        return staffMgrPosition;
    }

    public <T extends Person & StaffMember> String setPositionFromStaff(String position) {
        T t;
        switch (staffMgrType) {
            case DIRECTOR:
                t = vCore.searchDirector(staffMgrID);
                t.setPosition(position);
                vCore.modifyDirector(staffMgrID, t);
                break;
                
            case SINGER:
                t = vCore.searchSinger(staffMgrID);
                t.setPosition(position);
                vCore.modifySinger(staffMgrID, t);
                break;
                
            case DANCER:
                t = vCore.searchDancer(staffMgrID);
                t.setPosition(position);
                vCore.modifyDancer(staffMgrID, t);
                break;
        }
        return position;
    }

    public Integer getGenderFromStaff() {
        return staffMgrGender;
    }

    public <T extends Person & StaffMember> int setGenderFromStaff(int genderindex) {
        T t;
        Person.Gender gender =
                genderindex == 0 ? Person.Gender.Male :
                (genderindex == 1 ? Person.Gender.Female :
                Person.Gender.Other);
        
        switch (staffMgrType) {
            case DIRECTOR:
                t = vCore.searchDirector(staffMgrID);
                t.setGender(gender);
                vCore.modifyDirector(staffMgrID, t);
                break;
                
            case SINGER:
                t = vCore.searchSinger(staffMgrID);
                t.setGender(gender);
                vCore.modifySinger(staffMgrID, t);
                break;
                
            case DANCER:
                t = vCore.searchDancer(staffMgrID);
                t.setGender(gender);
                vCore.modifyDancer(staffMgrID, t);
                break;
        }
        return genderindex;
    }
    
    private Integer convertGenderToInteger(Person.Gender gender) {
        switch (gender) {
            case Male:   return 0;
            case Female: return 1;
            default:     return 2;
        }
    }

    public String getAdmissionFromStaff() {
        return staffMgrAdmission;
    }
    
    public <T extends Person & StaffMember> boolean createStaff() {
        boolean success = false;

        final String    NAME     = "";
        final LocalDate BIRTHDAY = defaultBirthday();
        final String    POSITION = "";
        final char      GENDER   = 'O';

        T t;
        switch (staffMgrType) {
            case DIRECTOR: success = vCore.newDirector(NAME, GENDER, POSITION, BIRTHDAY); break;
            case SINGER:   success = vCore.newSinger(NAME, GENDER, POSITION, BIRTHDAY);   break;
            case DANCER:   success = vCore.newDancer(NAME, GENDER, POSITION, BIRTHDAY);   break;
            default:       return false;
        }
        
        return success;
    }
    
    public void deleteStaff() {
        boolean success = false;
        switch (staffMgrType) {
            case DIRECTOR: success = vCore.deleteDirector(staffMgrID); break;
            case SINGER:   success = vCore.deleteSinger(staffMgrID);   break;
            case DANCER:   success = vCore.deleteDancer(staffMgrID);   break;
        }
        if (!success) {
            showError("It was not possible to delete the staff member.");
        }
    }

    @Deprecated
    public <T extends Person & StaffMember> String setAdmissionFromStaff(String admission) {
        // read-only, does nothing
        return staffMgrAdmission;
    }
    
    private <T extends Person & StaffMember> void initStaffMgrInfo() {
        final int ID = Integer.valueOf(staffMgrID);
        T t;
        switch (staffMgrType) {
            case DIRECTOR: t = vCore.searchDirector(ID); break;
            case SINGER:   t = vCore.searchSinger(ID);   break;
            case DANCER:   t = vCore.searchDancer(ID);   break;
            default:       return;
        }
        
        staffMgrName      = t.getName();
        staffMgrPosition  = t.getPosition();
        staffMgrGender    = convertGenderToInteger(t.getGender());
        staffMgrAdmission = t.getAdmission().toString();
    }
    
    
    // --- PERFORMANCES --------------------------------------------------------
    
    private String perfMgrID;
    private Performance perfMgrObject;
    private String perfMgrWhens;
    private String perfMgrSingers;
    private String perfMgrDancers;

    public String getPerfMgrID() {
        return perfMgrID;
    }

    public void setPerfMgrID(String perfMgrID) {
        this.perfMgrID = perfMgrID;
        initPerfMgrObject();
    }
    
    private void initPerfMgrObject() {
        perfMgrObject = vCore.searchPerformance(perfMgrID);
    }
    
    public String getIDFromPerformance() {
        return getPerfMgrID();
    }
    
    public boolean performanceIDExists(String ID) {
        return vCore.performanceIDExists(ID);
    }
    
    public String getNameFromPerformance() {
        return String.valueOf(perfMgrObject.getName());
    }
    
    public String setNameFromPerformance(String name) {
        if (vCore.modifyPerformanceName(perfMgrID, name)) {
            return name;
        } else {
            showError("Performance name could not be changed.");
            return perfMgrObject.getName();
        }
    }
    
    public String getPriceFromPerformance() {
        return String.valueOf(perfMgrObject.getBasePrice());
    }
    
    public String setPriceFromPerformance(String theprice) {
        try {
            double price = Double.valueOf(theprice);
            if (vCore.modifyPerformancePrice(perfMgrID, price)) {
                return theprice;
            } else {
                showError("Performance name could not be changed.");
                return String.valueOf(perfMgrObject.getHall(0).getBasePrice());
            }
        } catch (NumberFormatException e) {
            showError("The given price is not a valid number.");
            return String.valueOf(perfMgrObject.getHall(0).getBasePrice());
        }
    }
    
    public String getSingersFromPerformance() {
        ArrayList<String> result = new ArrayList<>();
        if (perfMgrObject != null)
            perfMgrObject.getSingers().forEach( singer -> result.add(singer.toString()) );
        perfMgrSingers = ArrayUtility.convertToString(result);
        return perfMgrSingers;
    }
    
    public String setSingersFromPerformance(String singers) {
        if (singers.isEmpty()) return perfMgrSingers;
        try {
            ArrayList<Integer> result = new ArrayList<>();
            for (String s : singers.split("\\r?\\n")) {
                int id = Integer.valueOf(s);
                if (vCore.searchSinger(id) != null) {
                    result.add(id);
                } else {
                    showWarning(String.format("The singer ID %d does not exist!", id));
                    return singers;
                }
            }
            if (!vCore.modifyPerformanceSingers(perfMgrID, result)) {
                showError("There was an error modifying the singers.");
                return perfMgrSingers;
            }
            return singers;
        } catch (NumberFormatException e) {
            showError("There is at least one ID that is not a number.");
        }
        return perfMgrSingers;
    }
    
    public String getDancersFromPerformance() {
        ArrayList<String> result = new ArrayList<>();
        if (perfMgrObject != null)
            perfMgrObject.getDancers().forEach( dancer -> result.add(dancer.toString()) );
        perfMgrDancers = ArrayUtility.convertToString(result);
        return perfMgrDancers;
    }
    
    public String setDancersFromPerformance(String dancers) {
        if (dancers.isEmpty()) return perfMgrDancers;
        try {
            ArrayList<Integer> result = new ArrayList<>();
            for (String s : dancers.split("\\r?\\n")) {
                int id = Integer.valueOf(s);
                if (vCore.searchDancer(id) != null) {
                    result.add(id);
                } else {
                    showWarning(String.format("The dancer ID %d does not exist!", id));
                    return dancers;
                }
            }
            if (!vCore.modifyPerformanceDancers(perfMgrID, result)) {
                showError("There was an error modifying the dancers.");
                return perfMgrDancers;
            }
            return dancers;
        } catch (NumberFormatException e) {
            showError("There is at least one ID that is not a number.");
        }
        return perfMgrDancers;
    }
    
    public String getWhensFromPerformance() {
        ArrayList<String> result = new ArrayList<>();
        if (perfMgrObject != null)
            perfMgrObject.getHall().forEach( hall -> result.add(hall.getWhen().toString()) );
        perfMgrWhens = ArrayUtility.convertToString(result);
        return perfMgrWhens;
    }
    
    public String setWhensFromPerformance(String whens) {
        if (whens.isEmpty()) return perfMgrWhens;
        try {
            ArrayList<LocalDateTime> result = new ArrayList<>();
            for (String s : whens.split("\\r?\\n"))
                result.add(LocalDateTime.parse(s));
            if (!vCore.modifyPerformanceWhen(perfMgrID, result)) {
                showError("There was an error modifying the dates!");
                return perfMgrWhens;
            }
            return whens;
        } catch (DateTimeParseException e) {
            showError("There is at least one date that is not in the format \"aaaa-mm-ddThh:mm\".");
        }
        return perfMgrWhens;
    }
    
    public String getOperaDirectorFromPerformance() {
        if (perfMgrObject != null)
            return perfMgrObject.getOperaDirector().toString();
        else
            return "";
    }
    
    public String setOperaDirectorFromPerformance(String operadir) {
        try {
            int opera = Integer.valueOf(operadir);
            if (vCore.searchDirector(opera) != null) {
                vCore.modifyPerformanceDirectors(perfMgrID, opera, perfMgrObject.getCastingDirector());
                return operadir;
            } else {
                showError(String.format("The director with ID \"%s\" does not exist.", opera));
            }
        } catch (NumberFormatException e) {
            showError("The opera director ID is not a number.");
        }
        return String.valueOf(perfMgrObject.getOperaDirector());
    }
    
    public String getCastingDirectorFromPerformance() {
        if (perfMgrObject != null)
            return perfMgrObject.getCastingDirector().toString();
        else
            return "";
    }
    
    public String setCastingDirectorFromPerformance(String castingdir) {
        try {
            int casting = Integer.valueOf(castingdir);
            if (vCore.searchDirector(casting) != null) {
                vCore.modifyPerformanceDirectors(perfMgrID, perfMgrObject.getOperaDirector(), casting);
                return castingdir;
            } else {
                showError(String.format("The director with ID \"%s\" does not exist.", castingdir));
            }
        } catch (NumberFormatException e) {
            showError("The casting director ID is not a number.");
        }
        return String.valueOf(perfMgrObject.getCastingDirector());
    }
    
    public void deletePerformance() {
        if (!vCore.deletePerformance(perfMgrID)) {
            showError("Error deleting the performance with ID " + perfMgrID + ".");
        }
    }
    
    public boolean createPerformance(String SHOWID) {
        if (vCore.performanceIDExists(SHOWID)) {
            showWarning("This ID already exists!");
            return false;
        }
        final String NAME                   = "";
        final ArrayList<LocalDateTime> WHEN = new ArrayList<>();
        WHEN.add(LocalDateTime.now());
        final Double PRICE                  = 0.;
        final Integer OPERA                 = 0;
        final Integer CASTING               = 0;
        final ArrayList<Integer> SINGERS    = new ArrayList<>();
        final ArrayList<Integer> DANCERS    = new ArrayList<>();
        
        if (!vCore.newPerformance(SHOWID, NAME, WHEN, PRICE, OPERA, CASTING, SINGERS, DANCERS)) {
            showError("Error while creating the new performance.");
            return false;
        }
        return true;
    }
    
    
    // --- TICKETS -------------------------------------------------------------
    
    private String ticketPerfID;
    private short  ticketPerfWhenIndex;
    private String ticketPerfSeat;

    public String getTicketPerfSeat() {
        return ticketPerfSeat;
    }

    public void setTicketPerfSeat(String ticketPerfSeat) {
        System.out.printf("Set ticket performance seat: %s\n", ticketPerfSeat);
        this.ticketPerfSeat = ticketPerfSeat;
    }

    public String getTicketPerfID() {
        return ticketPerfID;
    }

    public void setTicketPerfID(String ticketPerfID) {
        System.out.printf("Set ticket performance ID: %s\n", ticketPerfID);
        this.ticketPerfID = ticketPerfID;
    }

    public short getTicketPerfWhenIndex() {
        return ticketPerfWhenIndex;
    }

    public void setTicketPerfWhenIndex(short ticketPerfWhenIndex) {
        System.out.printf("Set ticket performance when index: %h\n", ticketPerfWhenIndex);
        this.ticketPerfWhenIndex = ticketPerfWhenIndex;
    }
    
    public boolean isTicketSold() {
        return vCore.isTicketSold(ticketPerfID, ticketPerfSeat, ticketPerfWhenIndex);
    }
    
    public Double ticketPrice() {
        return vCore.getTicketPrice(ticketPerfID, ticketPerfSeat);
    }
    
    public Double ticketBasePrice(String SHOWID) {
        return vCore.getPerformancePrice(SHOWID);
    }
    
    public boolean sellTicket() {
        try {
            return vCore.sellTicket(ticketPerfID, ticketPerfSeat, ticketPerfWhenIndex);
        } catch (TicketSoldException e) {
            return false;
        }
    }
    
    public boolean refundTicket() {
        try {
            return vCore.refundTicket(ticketPerfID, ticketPerfSeat, ticketPerfWhenIndex);
        } catch (TicketNotSoldException e) {
            return false;
        }
    }
    
    public ArrayList<ArrayList<Boolean>> hallSoldMatrix(final String SHOWID, final short WHENINDEX) {
        return vCore.hallSoldMatrix(SHOWID, WHENINDEX);
    }
    
    // --- GENERIC -------------------------------------------------------------
    
    public <T extends Person & StaffMember> ObservableList<StaffTableItem> getStaffItems() {
        ObservableList<StaffTableItem> result = FXCollections.observableArrayList();
        ArrayList<T> STAFF = new ArrayList<>();
        switch (staffMgrType) {
            case DIRECTOR: STAFF = vCore.getAllDirectors(); break;
            case SINGER:   STAFF = vCore.getAllSingers();   break;
            case DANCER:   STAFF = vCore.getAllDancers();   break;
        }
        System.out.printf("getAll<StaffMember>.size() = %d\n", STAFF.size());
        for (T t : STAFF) {
            result.add(new StaffTableItem(t));
        }
        return result;
    }
    
    public ObservableList<PerformanceTableItem> getPerformancesItems() {
        ObservableList<PerformanceTableItem> result = FXCollections.observableArrayList();
        final ArrayList<Performance> PERF = vCore.getAllPerformances();
        System.out.printf("getAllPerformances.size() = %d\n", PERF.size());
        for (Performance p : PERF) {
            result.add(new PerformanceTableItem(p));
        }
        return result;
    }
    
    /**
     * Gets the dates and times of a performance by ID
     * @return the dates and times
     * @param ID 
     */
    public ArrayList<LocalDateTime> getOnlyWhensByID(String ID) {
        ArrayList<LocalDateTime> result = new ArrayList<>();
        Performance p = vCore.searchPerformance(ID);
        if (p == null) {
            showWarning("ID não existe.\n");
            return result;
        }
        for (int i = 0; i < p.getHall().size(); i++) {
            result.add(p.getHall(i).getWhen());
        }
        return result;
    }
    
    @Deprecated
    public LocalDate defaultBirthday() {
        return LocalDate.of(2001, Month.JANUARY, 1);
    }
}
