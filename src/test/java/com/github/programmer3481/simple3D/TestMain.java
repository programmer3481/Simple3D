package com.github.programmer3481.simple3D;

import com.github.programmer3481.easyBullet.EasyBullet;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class TestMain {
    public static void main(String[] args) {
        EasyBullet bullet = new EasyBullet(-9.8f);
        bullet.addBoxRB(new Vector3f(10.0f, 1.0f, 10.0f),
                new Vector3f(0.0f, -0.5f, 0.0f),
                (new Quaternionf()).rotationXYZ(0.0f, 0.0f, 0.0f),
                0.0f,
                "floor");
        bullet.addSphereRB(0.5f,
                new Vector3f(0.0f, 10.0f, 0.0f),
                (new Quaternionf()).rotationXYZ(0.0f, 0.0f, 0.0f),
                1.0f,
                "ball");
    }
}
