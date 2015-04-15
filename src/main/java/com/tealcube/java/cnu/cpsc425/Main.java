package com.tealcube.java.cnu.cpsc425;

import com.javafx.experiments.jfx3dviewer.Xform;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private Stage primaryStage;
    private BorderPane base;
    private SubScene subScene;
    private Timeline timeline;
    private boolean timelinePlaying = false;
    private double ONE_FRAME = 1.0 / 24.0;
    private double DELTA_MULTIPLIER = 200.0;
    private double CONTROL_MULTIPLIER = 0.1;
    private double SHIFT_MULTIPLIER = 0.1;
    private double ALT_MULTIPLIER = 0.5;
    private double mousePosX, mousePosY, mouseOldX, mouseOldY, mouseDeltaX, mouseDeltaY;
    private final Group root3D = new Group();
    private final Group axisGroup = new Group();
    private final Xform world = new Xform();
    private final Xform carGroup = new Xform();
    private final Xform houseGroup = new Xform();
    private final PerspectiveCamera camera = new PerspectiveCamera(true);
    private final Xform cameraXform = new Xform();
    private final Xform cameraXform2 = new Xform();
    private final Xform cameraXform3 = new Xform();
    private final double cameraDistance = 600;

    public static Logger getLogger() {
        return LOGGER;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Richard Harrah - CPSC 425 - Scene Editor");

        getLogger().debug("Application starting");

        FXMLLoader loader = new FXMLLoader();
        base = loader.load(Main.class.getResourceAsStream("SceneEditor.fxml"));
        SceneEditorController controller = loader.getController();
        controller.setMain(this);

        subScene = new SubScene(world, 690, 600, true, SceneAntialiasing.BALANCED);
        base.setCenter(subScene);

        buildScene();
        buildCamera();
        buildAxes();
        buildCars();
        buildHouses();

        Scene scene = new Scene(base, 800, 600, true);
        scene.setFill(Color.ALICEBLUE);
        handleMouse(scene, world);

        this.primaryStage.setScene(scene);
        this.primaryStage.show();
        subScene.setCamera(camera);

        getLogger().debug("Application started");
    }

    private void buildCars() {
        getLogger().debug("Building cars");
        world.getChildren().add(carGroup);
        getLogger().debug("Cars built");
    }

    private void buildHouses() {
        getLogger().debug("Building houses");
        world.getChildren().add(houseGroup);
        getLogger().debug("Houses built");
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

    public Xform getCarGroup() {
        return carGroup;
    }

    public Xform getHouseGroup() {
        return houseGroup;
    }

    private void handleMouse(Scene scene, final Node root) {
        scene.setOnMousePressed(me -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged(me -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);

            double modifier = 1.0;
            double modifierFactor = 0.1;

            if (me.isControlDown()) {
                modifier = 0.1;
            }
            if (me.isShiftDown()) {
                modifier = 10.0;
            }
            if (me.isPrimaryButtonDown()) {
                cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX * modifierFactor * modifier * 2.0);
                cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY * modifierFactor * modifier * 2.0);
            } else if (me.isSecondaryButtonDown()) {
                cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * modifierFactor * modifier * 2.0);
                cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * modifierFactor * modifier * 2.0);
            }
        });
    }

}
