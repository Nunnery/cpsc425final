/*
 * This file is part of Facecore, licensed under the ISC License.
 *
 * Copyright (c) 2014 Richard Harrah
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted,
 * provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF
 * THIS SOFTWARE.
 */
package com.tealcube.java.cnu.cpsc425;

import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class SceneEditorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SceneEditorController.class);
    private static final Random RANDOM = new Random();
    private static final int MIN_X = -100;
    private static final int MAX_X = 100;
    private static final int MIN_Z = -100;
    private static final int MAX_Z = 100;
    private Main main;

    public SceneEditorController() {
        // do nothing here
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public void addCar() {
        int x = RANDOM.nextInt((MAX_X - MIN_X) + 1) + MIN_X;
        int y = 15;
        int z = RANDOM.nextInt((MAX_Z - MIN_Z) + 1) + MIN_Z;
        getLogger().debug("Adding car at " + x + ", " + y + ", " + z);
        getMain().getCarGroup().getChildren().add(new Car(x, y, z));
    }

    public void addHouse() {
        int x = RANDOM.nextInt((MAX_X - MIN_X) + 1) + MIN_X;
        int y = 40;
        int z = RANDOM.nextInt((MAX_Z - MIN_Z) + 1) + MIN_Z;
        getLogger().debug("Adding house at " + x + ", " + y + ", " + z);
        getMain().getHouseGroup().getChildren().add(new House(x, y, z));
    }

    public void removeCar() {
        if (getMain().getCarGroup().getChildren().isEmpty()) {
            getLogger().debug("There are no cars to remove");
            return;
        }
        getLogger().debug("Removing oldest car");
        getMain().getCarGroup().getChildren().remove(0);
    }

    public void removeHouse() {
        if (getMain().getHouseGroup().getChildren().isEmpty()) {
            getLogger().debug("There are no houses to remove");
            return;
        }
        getLogger().debug("Removing oldest house");
        getMain().getHouseGroup().getChildren().remove(0);
    }

    public void closeApp() {
        getLogger().debug("Closing primary stage");
        getMain().getPrimaryStage().close();
    }

    public void displayHelp() {
        getLogger().debug("Displaying help text");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Instructions");
        alert.setContentText("In order to rotate the editor, hold down the left mouse button and move your mouse.\n" +
                "In order to translate the editor, hold down the right mouse button and move your mouse.");
        alert.showAndWait();
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

}
