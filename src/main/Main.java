package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import states.State;
import globals.*;

public class Main extends Canvas implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;

    protected boolean isRunning;

    private float fpsDelay = 1;
    private float fpsRate = 60;
    private JFrame frame;
    private final String TITLE = "Snake Game";
    private Thread thread;
    private State state;

    public Main() {
        addKeyListener(this);
        frame = new JFrame();
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Screen.WIDTH, Screen.HEIGHT);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.add(this);
        state = new State();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.Start();
    }

    public synchronized void Start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void Stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void Update() {
        state.Update();
    }

    public void Render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Screen.WIDTH, Screen.HEIGHT);
        state.Render(g);
        g.dispose();
        bs.show();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double fpsStack = 1000000000 / fpsRate;
        int frames = 0;
        while (isRunning) {
            long currentTime = System.nanoTime();
            if ((currentTime - lastTime) >= fpsStack) {
                Update();
                Render();
                frames++;
                lastTime = currentTime;
            }

            if (System.currentTimeMillis() - timer >= fpsDelay * 1000) {
                timer = System.currentTimeMillis();
                double FPS = frames / fpsDelay;
                frame.setTitle(TITLE + " " + FPS + "FPS");
                frames = 0;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        String keyChar = Character.toString(e.getKeyChar());
        switch (keyChar) {
            case "w":
                Input.rawY = 1;
                break;
            case "s":
                Input.rawY = -1;
                break;
            case "d":
                Input.rawX = 1;
                break;
            case "a":
                Input.rawX = -1;
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Input.rawX = 0;
        Input.rawY = 0;
    }
}