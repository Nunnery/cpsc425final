package com.tealcube.java.cnu.cpsc425;

import com.javafx.experiments.jfx3dviewer.Xform;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private double ONE_FRAME = 1.0/24.0;
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
        handleKeyboard(scene, world);

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
                cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX*modifierFactor*modifier*2.0);  // +
                cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY*modifierFactor*modifier*2.0);  // -
            }
            else if (me.isSecondaryButtonDown()) {
                double z = camera.getTranslateZ();
                double newZ = z + mouseDeltaX*modifierFactor*modifier;
                camera.setTranslateZ(newZ);
            }
            else if (me.isMiddleButtonDown()) {
                cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX*modifierFactor*modifier*0.3);  // -
                cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY*modifierFactor*modifier*0.3);  // -
            }
        });
    }

    private void handleKeyboard(Scene scene, final Node root) {
        final boolean moveCamera = true;
        scene.setOnKeyPressed(event -> {
            Duration currentTime;
            switch (event.getCode()) {
                case Z:
                    if (event.isShiftDown()) {
                        cameraXform.ry.setAngle(0.0);
                        cameraXform.rx.setAngle(0.0);
                        camera.setTranslateZ(-300.0);
                    }
                    cameraXform2.t.setX(0.0);
                    cameraXform2.t.setY(0.0);
                    break;
                case X:
                    if (event.isControlDown()) {
                        if (axisGroup.isVisible()) {
                            System.out.println("setVisible(false)");
                            axisGroup.setVisible(false);
                        }
                        else {
                            System.out.println("setVisible(true)");
                            axisGroup.setVisible(true);
                        }
                    }
                    break;
                case S:
                    if (event.isControlDown()) {
                        if (houseGroup.isVisible()) {
                            houseGroup.setVisible(false);
                        }
                        else {
                            houseGroup.setVisible(true);
                        }
                        if (carGroup.isVisible()) {
                            carGroup.setVisible(false);
                        }
                        else {
                            carGroup.setVisible(true);
                        }
                    }
                    break;
                case SPACE:
                    if (timelinePlaying) {
                        timeline.pause();
                        timelinePlaying = false;
                    }
                    else {
                        timeline.play();
                        timelinePlaying = true;
                    }
                    break;
                case UP:
                    if (event.isControlDown() && event.isShiftDown()) {
                        cameraXform2.t.setY(cameraXform2.t.getY() - 10.0*CONTROL_MULTIPLIER);
                    }
                    else if (event.isAltDown() && event.isShiftDown()) {
                        cameraXform.rx.setAngle(cameraXform.rx.getAngle() - 10.0*ALT_MULTIPLIER);
                    }
                    else if (event.isControlDown()) {
                        cameraXform2.t.setY(cameraXform2.t.getY() - 1.0*CONTROL_MULTIPLIER);
                    }
                    else if (event.isAltDown()) {
                        cameraXform.rx.setAngle(cameraXform.rx.getAngle() - 2.0*ALT_MULTIPLIER);
                    }
                    else if (event.isShiftDown()) {
                        double z = camera.getTranslateZ();
                        double newZ = z + 5.0*SHIFT_MULTIPLIER;
                        camera.setTranslateZ(newZ);
                    }
                    break;
                case DOWN:
                    if (event.isControlDown() && event.isShiftDown()) {
                        cameraXform2.t.setY(cameraXform2.t.getY() + 10.0*CONTROL_MULTIPLIER);
                    }
                    else if (event.isAltDown() && event.isShiftDown()) {
                        cameraXform.rx.setAngle(cameraXform.rx.getAngle() + 10.0*ALT_MULTIPLIER);
                    }
                    else if (event.isControlDown()) {
                        cameraXform2.t.setY(cameraXform2.t.getY() + 1.0*CONTROL_MULTIPLIER);
                    }
                    else if (event.isAltDown()) {
                        cameraXform.rx.setAngle(cameraXform.rx.getAngle() + 2.0*ALT_MULTIPLIER);
                    }
                    else if (event.isShiftDown()) {
                        double z = camera.getTranslateZ();
                        double newZ = z - 5.0*SHIFT_MULTIPLIER;
                        camera.setTranslateZ(newZ);
                    }
                    break;
                case RIGHT:
                    if (event.isControlDown() && event.isShiftDown()) {
                        cameraXform2.t.setX(cameraXform2.t.getX() + 10.0*CONTROL_MULTIPLIER);
                    }
                    else if (event.isAltDown() && event.isShiftDown()) {
                        cameraXform.ry.setAngle(cameraXform.ry.getAngle() - 10.0*ALT_MULTIPLIER);
                    }
                    else if (event.isControlDown()) {
                        cameraXform2.t.setX(cameraXform2.t.getX() + 1.0*CONTROL_MULTIPLIER);
                    }
                    else if (event.isAltDown()) {
                        cameraXform.ry.setAngle(cameraXform.ry.getAngle() - 2.0*ALT_MULTIPLIER);
                    }
                    break;
                case LEFT:
                    if (event.isControlDown() && event.isShiftDown()) {
                        cameraXform2.t.setX(cameraXform2.t.getX() - 10.0*CONTROL_MULTIPLIER);
                    }
                    else if (event.isAltDown() && event.isShiftDown()) {
                        cameraXform.ry.setAngle(cameraXform.ry.getAngle() + 10.0*ALT_MULTIPLIER);  // -
                    }
                    else if (event.isControlDown()) {
                        cameraXform2.t.setX(cameraXform2.t.getX() - 1.0*CONTROL_MULTIPLIER);
                    }
                    else if (event.isAltDown()) {
                        cameraXform.ry.setAngle(cameraXform.ry.getAngle() + 2.0*ALT_MULTIPLIER);  // -
                    }
                    break;
            }
        });
    }

}
