package com.github.programmer3481.simple3D;

import com.github.programmer3481.simple3D.engine.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.system.CallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements Runnable{
    public Thread game;
    public Window window;
    public Renderer renderer;
    public Shader testShader;
    public Texture testTexture;
    //public Cube[] cubes;
    public Mesh dragonMesh;
    public Object3D dragon;
    public Camera camera;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final String TITLE = "LWJGL";

    public long deltaTimeStart;
    public float deltaTime;

    public boolean leftMouseDownLast = false;
    public boolean mouseDisabled = false;

    public void start() {
        game = new Thread(this, "game");
        game.start();
    }

    public void init() {
        window = new Window(WIDTH, HEIGHT, TITLE, new Vector3f(0.5f, 0.5f, 0.5f));
        testTexture = new Texture("src/main/resources/bad.png");
        testShader = new Shader("src/main/resources/testVS.glsl", "src/main/resources/testFS.glsl", testTexture);
        //cubes = new Cube[10000];
        //for (int i = 0; i < 10000; i++) {
        //        cubes[i] = new Cube(testShader,
        //                //new Vector3f(i*2, 0.0f, j*2),
        //                new Vector3f((float)(Math.random() * 50), (float)(Math.random() * 50), (float)(Math.random() * 50)),
        //                new Vector3f(0.0f, 0.0f, 0.0f),
        //                new Vector3f(1.0f, 1.0f, 1.0f));
        //    }
        //dragonMesh = ModelLoader.loadModel("C:\\Chrome\\gradle-workspace\\Simple3D\\src\\main\\resources\\dragon.obj");
        dragonMesh = new Mesh(new Vertex[] {
                new Vertex(new Vector3f(-1.0f, -1.0f, 0.0f), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(0.0f, 1.0f, 0.0f), new Vector2f(0.5f, 1.0f)),
                new Vertex(new Vector3f(1.0f, -1.0f, 0.0f), new Vector2f(1.0f, 0.0f))
        }, new int[] {
                0, 1, 2
        });
        dragon = new Object3D(testShader, dragonMesh, new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f));
        camera = new Camera(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f),
                70.0f, (float)WIDTH/(float)HEIGHT, 0.1f,1000.0f);
        renderer = new Renderer();

        deltaTimeStart = System.currentTimeMillis();
    }

    public void update() {
        window.update();
        if (mouseDisabled) camera.update(deltaTime);
    }

    public void render() {
        renderer.render(dragon, camera);
        window.swapBuffers();
    }

    public void close() {
        window.destroy();
        renderer.destroy();
        testShader.destroy();
        testTexture.destroy();
    }

    @Override
    public void run() {
        init();
        while (!window.windowShouldClose() && !Input.isKeyDown(GLFW_KEY_ESCAPE)) {
            deltaTimeStart = System.currentTimeMillis();
            update();
            render();
            if (leftMouseDownLast != Input.isButtonDown(GLFW_MOUSE_BUTTON_LEFT) && !leftMouseDownLast) {
                mouseDisabled = !mouseDisabled;
                window.mouseState(mouseDisabled);
            }
            leftMouseDownLast = Input.isButtonDown(GLFW_MOUSE_BUTTON_LEFT);
            deltaTime = (System.currentTimeMillis() - deltaTimeStart) / 1000.0f;
        }
        close();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
