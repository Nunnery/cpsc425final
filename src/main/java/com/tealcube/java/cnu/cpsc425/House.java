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
