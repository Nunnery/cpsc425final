package com.tealcube.java.cnu.cpsc425;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class House extends Group {

    private static final Random RANDOM = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(House.class);

    public House(double x, double y, double z) {
        getLogger().debug("Creating new House");

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.rgb(RANDOM.nextInt(255), RANDOM.nextInt(255), RANDOM.nextInt(255)));
        material.setSpecularColor(Color.rgb(RANDOM.nextInt(255), RANDOM.nextInt(255), RANDOM.nextInt(255)));

        Box base = new Box(20, 20, 20);
        base.setMaterial(material);
        base.setTranslateX(x);
        base.setTranslateY(y);
        base.setTranslateZ(z);

        Box roof = new Box(25, 20, 25);
        roof.setMaterial(material);
        roof.setTranslateX(x);
        roof.setTranslateY(y - 20);
        roof.setTranslateZ(z);

        getChildren().addAll(base, roof);
    }

    public static Logger getLogger() {
        return LOGGER;
    }

}
