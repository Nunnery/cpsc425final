package com.tealcube.java.cnu.cpsc425;

import com.javafx.experiments.jfx3dviewer.Xform;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainApp.class);
    private Stage primaryStage;
    private final Group root3D = new Group();
    private final Group axisGroup = new Group();
    private final Xform world = new Xform();
    private final Xform carGroup = new Xform();
    private final PerspectiveCamera camera = new PerspectiveCamera(true);
    private final Xform cameraXform = new Xform();
    private final Xform cameraXform2 = new Xform();
    private final Xform cameraXform3 = new Xform();
    private final double cameraDistance = 600;

    public static Logger getLogger() {
        return LOGGER;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Richard Harrah - CPSC 425 - Scene Editor");

        getLogger().debug("Application starting");

        buildScene();
        buildCamera();
        buildAxes();
        buildCars();

        Scene scene = new Scene(root3D, 800, 600, true);
        scene.setFill(Color.GREY);

        this.primaryStage.setScene(scene);
        this.primaryStage.show();
        scene.setCamera(camera);

        getLogger().debug("Application started");
    }

    private void buildCars() {
        getLogger().debug("Building cars");
        world.getChildren().add(new Car(50, 0, 0));
        getLogger().debug("Cars built");
    }

    private void buildScene() {
        getLogger().debug("Building scene");
        root3D.getChildren().add(world);
        getLogger().debug("Scene built");
    }

    private void buildCamera() {
        getLogger().debug("Building camera");
        root3D.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);

        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-cameraDistance);
        cameraXform.ry.setAngle(320.0);
        cameraXform.rx.setAngle(40);
        getLogger().debug("Camera built");
    }

    private void buildAxes() {
        getLogger().debug("Building axes");
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(240.0, 1, 1);
        final Box yAxis = new Box(1, 240.0, 1);
        final Box zAxis = new Box(1, 1, 240.0);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        world.getChildren().addAll(axisGroup);
        getLogger().debug("Axes built");
    }

    public static void main(String[] args) {
        launch(args);
    }

}
