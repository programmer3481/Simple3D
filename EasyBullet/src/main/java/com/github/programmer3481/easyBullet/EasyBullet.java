package com.github.programmer3481.easyBullet;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.broadphase.Dispatcher;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class EasyBullet {
    public DynamicsWorld world;
    public Dispatcher dispatcher;
    public CollisionConfiguration collisionConfig;
    public BroadphaseInterface broadphase;
    public ConstraintSolver solver;
    public Map<String, RigidBody> bodies = new HashMap<>()
    public ArrayList<RigidBody> bodies = new ArrayList<>();

    public EasyBullet(float gravity) {
        collisionConfig = new DefaultCollisionConfiguration();
        dispatcher = new CollisionDispatcher(collisionConfig);
        broadphase = new DbvtBroadphase();
        solver = new SequentialImpulseConstraintSolver();
        world = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfig);
        world.setGravity(new Vector3f(0.0f, gravity, 0.0f));
    }

    public EasyBullet() {
        this(-10.0f);
    }

    public void step(float deltaTime) {
        world.stepSimulation(deltaTime);
    }

    public void addBoxRB(org.joml.Vector3f dimensions, org.joml.Vector3f position,
                         org.joml.Quaternionf rotation, float mass) {
        Transform t = new Transform();
        t.setIdentity();
        t.set(new Matrix4f(JToV(rotation), JToV(position), 1.0f));
        BoxShape box = new BoxShape(JToV(dimensions.mul(2.0f)));
        Vector3f inertia = new Vector3f(0.0f, 0.0f, 0.0f);
        if (mass != 0.0f) {
            box.calculateLocalInertia(mass, inertia);
        }
        MotionState motion = new DefaultMotionState(t);
        RigidBodyConstructionInfo info = new RigidBodyConstructionInfo(mass, motion, box, inertia);
        RigidBody body = new RigidBody(info);
        world.addRigidBody(body);
        bodies.add(body);
    }

    public void addSphereRB(float rad, org.joml.Vector3f position,
                            org.joml.Quaternionf rotation, float mass) {
        Transform t = new Transform();
        t.setIdentity();
        t.set(new Matrix4f(JToV(rotation), JToV(position), 1.0f));
        SphereShape sphere = new SphereShape(rad);
        Vector3f inertia = new Vector3f(0.0f, 0.0f, 0.0f);
        if (mass != 0.0f) {
            sphere.calculateLocalInertia(mass, inertia);
        }
        MotionState motion = new DefaultMotionState(t);
        RigidBodyConstructionInfo info = new RigidBodyConstructionInfo(mass, motion, sphere, inertia);
        RigidBody body = new RigidBody(info);
        world.addRigidBody(body);
        bodies.add(body);
    }

    private Vector3f JToV(org.joml.Vector3f in) {
        return new Vector3f(in.x, in.y, in.z);
    }

    private Quat4f JToV(org.joml.Quaternionf in) {
        return new Quat4f(in.x, in.y, in.z, in.w);
    }

}
