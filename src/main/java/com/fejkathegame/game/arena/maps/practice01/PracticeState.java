package com.fejkathegame.game.arena.maps.practice01;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.maps.PracticeCamera;
import com.fejkathegame.game.arena.maps.PracticeStateHelper;
import com.fejkathegame.game.arena.physics.Physics;
import com.fejkathegame.game.entities.Character;
import com.fejkathegame.game.entities.logic.MovementSystem;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

/**
 *
 * @author Swartt
 */
public class PracticeState extends BasicGameState {
    private Practice arena;
    private String name;
    private MovementSystem movementSystem;
    private Physics physics;
    private Character obj;
    private PracticeStateHelper helper;
    private PracticeCamera camera;
    
    private float offsetMaxX = 2050;
    private float offsetMaxY = 750;

    private float camX = 0, camY = 0;

    /**
     * Constructor for ArenaState
     * @param name of the stage
     */
    public PracticeState(String name) {
        this.name = name;
    }

    @Override
    public int getID() {
        return Main.FIRSTPRACTICE;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        obj = null;
        try {
            obj = new Character(32, 40);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        arena = new Practice(name, obj);
        
        movementSystem = new MovementSystem(obj);

        physics = new Physics();

        camera = new PracticeCamera(offsetMaxX, offsetMaxY);

        helper = new PracticeStateHelper(arena, obj, camera);


    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setAntiAlias(false);
        g.scale(Main.SCALE, Main.SCALE);
        g.translate(-camera.getCamX(), -camera.getCamY());
        arena.render();
        arena.helper.updateText(camera.getCamX(), camera.getCamY());
        g.translate(-camX, -camY);
        g.resetTransform();
    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        helper.checkCameraOffset();
        if(!arena.timer.isCountdownRunning()) {
            movementSystem.handleInput(gc.getInput(), i);
            physics.handlePhysics(arena, i);
            helper.checkCollisionWithTarget(getID());
            obj.update(i);
            arena.helper.moveTarget();
        }
        arena.timer.calculateSecond(i);
    }
    


}