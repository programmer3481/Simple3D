package com.github.programmer3481.easyBullet;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.*;
import com.badlogic.gdx.physics.bullet.linearmath.btDefaultMotionState;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.physics.bullet.linearmath.btTransform;

import java.util.HashMap;

public class EasyBullet {
    public btDynamicsWorld world;
    public btDispatcher dispatcher;
    public btCollisionConfiguration collisionConfig;
    public btBroadphaseInterface broadphase;
    public btConstraintSolver solver;
    public HashMap<String, btRigidBody> bodies = new HashMap<>();

    public EasyBullet(float gravity) {
        Bullet.init();

        collisionConfig = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfig);
        broadphase = new btDbvtBroadphase();
        solver = new btSequentialImpulseConstraintSolver();
        world = new btDiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfig);
        world.setGravity(new Vector3(0.0f, gravity, 0.0f));
    }

    public EasyBullet() {
        this(-10.0f);
    }

    public void step(float deltaTime) {
        world.stepSimulation(deltaTime);
    }

    public void addBoxRB(org.joml.Vector3f dimensions, org.joml.Vector3f position,
                         org.joml.Quaternionf rotation, float mass, String name) {
        btTransform t = new btTransform();
        t.setIdentity();
        t.setOrigin(JToV(position));
        t.setRotation(JToV(rotation));
        btBoxShape box = new btBoxShape(JToV(dimensions.mul(2.0f)));
        Vector3 inertia = new Vector3(0.0f, 0.0f, 0.0f);
        if (mass != 0.0f) {
            box.calculateLocalInertia(mass, inertia);
        }
        float[] floatArray = new float[16];
        t.getOpenGLMatrix(floatArray);
        btMotionState motion = new btDefaultMotionState(new Matrix4(floatArray));
        btRigidBody.btRigidBodyConstructionInfo info =
                new btRigidBody.btRigidBodyConstructionInfo(mass, motion, box, inertia);
        btRigidBody body = new btRigidBody(info);
        world.addRigidBody(body);
        bodies.put(name, body);
    }

    public void addSphereRB(float rad, org.joml.Vector3f position,
                            org.joml.Quaternionf rotation, float mass, String name) {
        btTransform t = new btTransform();
        t.setIdentity();
        t.setOrigin(JToV(position));
        t.setRotation(JToV(rotation));
        btSphereShape sphere = new btSphereShape(rad);
        Vector3 inertia = new Vector3(0.0f, 0.0f, 0.0f);
        if (mass != 0.0f) {
            sphere.calculateLocalInertia(mass, inertia);
        }
        float[] floatArray = new float[16];
        t.getOpenGLMatrix(floatArray);
        btMotionState motion = new btDefaultMotionState(new Matrix4(floatArray));
        btRigidBody.btRigidBodyConstructionInfo info =
                new btRigidBody.btRigidBodyConstructionInfo(mass, motion, sphere, inertia);
        btRigidBody body = new btRigidBody(info);
        world.addRigidBody(body);
        bodies.put(name, body);
    }

    public void addCapsuleRB(float rad, float height, org.joml.Vector3f position,
                            org.joml.Quaternionf rotation, float mass, String name) {
        btTransform t = new btTransform();
        t.setIdentity();
        t.setOrigin(JToV(position));
        t.setRotation(JToV(rotation));
        btCapsuleShape capsule = new btCapsuleShape(rad, height);
        Vector3 inertia = new Vector3(0.0f, 0.0f, 0.0f);
        if (mass != 0.0f) {
            capsule.calculateLocalInertia(mass, inertia);
        }
        float[] floatArray = new float[16];
        t.getOpenGLMatrix(floatArray);
        btMotionState motion = new btDefaultMotionState(new Matrix4(floatArray));
        btRigidBody.btRigidBodyConstructionInfo info =
                new btRigidBody.btRigidBodyConstructionInfo(mass, motion, capsule, inertia);
        btRigidBody body = new btRigidBody(info);
        world.addRigidBody(body);
        bodies.put(name, body);
    }

    public void removeRB(String name) {
        world.removeRigidBody(bodies.get(name));
        bodies.remove(name);
    }

    public void lockMovement(boolean xr, boolean yr, boolean zr,
                             boolean x, boolean y, boolean z, String name) {
        bodies.get(name).setAngularFactor(new Vector3(BToF(xr) , BToF(yr), BToF(zr)));
        bodies.get(name).setLinearFactor(new Vector3(BToF(x), BToF(y), BToF(z)));
    }

    public void applyForce(String name, Vector3 force) {
        bodies.get(name).applyCentralForce(force);
    }

    private Vector3 JToV(org.joml.Vector3f in) {
        return new Vector3(in.x, in.y, in.z);
    }

    private Quaternion JToV(org.joml.Quaternionf in) {
        return new Quaternion(in.x, in.y, in.z, in.w);
    }

    private float BToF(boolean in) {
        return in ? 1.0f : 0.0f;
    }
}
