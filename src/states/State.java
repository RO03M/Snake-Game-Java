package states;

import entities.Player;

import java.awt.Graphics;

public class State {
    
    private Player player;

    public State() {
        player = new Player(10, 10, 1, 1, 0.25);
    }

    public void Render(Graphics g) {
        player.Render(g);
    }

    public void Update() {
        player.Update();
    }

}
