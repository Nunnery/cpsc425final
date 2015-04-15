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

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Car extends Group {

    private static final Random RANDOM = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(Car.class);

    public Car(double x, double y, double z) {
        getLogger().debug("Creating new Car");

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.rgb(RANDOM.nextInt(255), RANDOM.nextInt(255), RANDOM.nextInt(255)));
        material.setSpecularColor(Color.rgb(RANDOM.nextInt(255), RANDOM.nextInt(255), RANDOM.nextInt(255)));

        Cylinder body = new Cylinder(5, 30);
        body.setMaterial(material);
        body.setTranslateX(x);
        body.setTranslateY(y);
        body.setTranslateZ(z);
        body.setRotationAxis(Rotate.Z_AXIS);
        body.setRotate(90);

        Cylinder front = new Cylinder(3, 15);
        front.setMaterial(material);
        front.setTranslateX(x + 10);
        front.setTranslateY(y - 3);
        front.setTranslateZ(z);
        front.setRotationAxis(Rotate.X_AXIS);
        front.setRotate(90);

        Cylinder back = new Cylinder(3, 15);
        back.setMaterial(material);
        back.setTranslateX(x - 10);
        back.setTranslateY(y - 3);
        back.setTranslateZ(z);
        back.setRotationAxis(Rotate.X_AXIS);
        back.setRotate(90);

        getChildren().addAll(body, front, back);
    }

    public static Logger getLogger() {
        return LOGGER;
    }

}
