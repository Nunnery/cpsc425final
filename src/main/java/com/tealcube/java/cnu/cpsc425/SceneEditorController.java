package com.tealcube.java.cnu.cpsc425;

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
    private MainApp mainApp;

    public SceneEditorController() {
        // do nothing here
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public void addCar() {
        int x = RANDOM.nextInt((MAX_X - MIN_X) + 1) + MIN_X;
        int z = RANDOM.nextInt((MAX_Z - MIN_Z) + 1) + MIN_Z;
        getLogger().debug("Adding car at " + x + ", 0, " + z);
        getMainApp().getCarGroup().getChildren().add(new Car(x, 0, z));
    }

    public void removeCar() {
        if (getMainApp().getCarGroup().getChildren().isEmpty()) {
            getLogger().debug("There are no cars to remove");
            return;
        }
        getLogger().debug("Removing oldest car");
        getMainApp().getCarGroup().getChildren().remove(0);
    }

    public void closeApp() {
        getLogger().debug("Closing primary stage");
        getMainApp().getPrimaryStage().close();
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
