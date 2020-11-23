package entities;

import java.awt.Color;
import java.awt.Graphics;

import java.util.ArrayList;

import globals.*;

public class Player {
    
    public int WIDTH, HEIGHT;
    public double x, y, speed;

    private int directionX, directionY;
    private ArrayList<PlayerSection> playerSections = new ArrayList<PlayerSection>();
    private int snakeSize = 200;

    public Player(int x, int y, int WIDTH, int HEIGHT, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        for (int i = 0; i < snakeSize; i++) {
            AddSection();
        }
    }

    public void Render(Graphics g)  {
        for (int i = 0; i < playerSections.size(); i++) {
            g.setColor(Color.white);
            g.fillRect((int) playerSections.get(i).x, (int) playerSections.get(i).y, WIDTH, HEIGHT);
        }
    }

    public void Update() {
        if (Input.rawX != 0) {
            directionX = Input.rawX;
            directionY = 0;
        }

        if (Input.rawY != 0) {
            directionX = 0;
            directionY = Input.rawY;
        }
        double lastX = playerSections.get(0).x;
        double lastY = playerSections.get(0).y;
        for (int i = playerSections.size() - 1; i >= 0; i--) {
            if (i == 0) {
                playerSections.get(i).x += directionX * speed;
                playerSections.get(i).y -= directionY * speed;
            } else {
                playerSections.get(i).x = playerSections.get(i - 1).x;
                playerSections.get(i).y = playerSections.get(i - 1).y;
            }

            if (playerSections.get(i).x > Screen.WIDTH) playerSections.get(i).x = 0;
            else if (playerSections.get(i).x < 0) playerSections.get(i).x = Screen.WIDTH;

            if (playerSections.get(i).y > Screen.HEIGHT) playerSections.get(i).y = 0;
            else if (playerSections.get(i).y < 0) playerSections.get(i).y = Screen.HEIGHT;
            lastX = playerSections.get(i).x;
            lastY = playerSections.get(i).y;
        }
    }

    protected void AddSection() {
        if (playerSections.size() == 0) {
            playerSections.add(new PlayerSection(this.x, this.y, this.WIDTH, this.HEIGHT));
            return;
        } else {
            int sectionsSize = playerSections.size();
            double sectionX = playerSections.get(sectionsSize - 1).x;
            double sectionY = playerSections.get(sectionsSize - 1).y;
            playerSections.add(new PlayerSection(sectionX, sectionY, this.WIDTH, this.HEIGHT));
            return;
        }
    }

}
