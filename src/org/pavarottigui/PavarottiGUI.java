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

import org.pavarottigui.pentagon.Pentagon;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.pavarotti.Pavarotti;
import org.pavarotti.ui.controller.Version;

/**
 *
 * @author Igor Nunes, Beatriz Costa
 */
public class PavarottiGUI extends Application {
    private static final Version VERSION = new Version(1, 1, 0, Version.Stage.Final);
    public static Pentagon pentagon;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLPavarotti.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        
        pentagon.bindStage(stage);
        pentagon.setLogo("res/logo.png");
        
        Pavarotti.launchFromExternal(pentagon, "GUI", VERSION);
    }
    
    @Override
    public void stop() {
        pentagon.stop();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        pentagon = new Pentagon();
        launch(args);
    }
}
