package com.fejkathegame.menu;

import com.fejkathegame.game.Main;
import com.fejkathegame.menu.camera.MenuCamera;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Khamekaze
 */
public class LevelSelectState extends BasicGameState {

    private LevelSelect levelSelect;
    private String name;
    private Input input;
    private MenuCamera cam;

    @Override
    public int getID() {
        return Main.LEVELSELECTSTATE;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        levelSelect = new LevelSelect();
        input = gc.getInput();
        cam = new MenuCamera(0, 0);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        //g.scale(900, 900);
        g.translate(-cam.getX(), -cam.getY());
        scrollPage(input, cam, g);
        
        levelSelect.render(g);
        g.resetTransform();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();
        selectLevel(mouseX, mouseY, input, sbg);
        
    }
    
    public void scrollPage(Input i, MenuCamera camera, Graphics g){
        
        if(i.isKeyPressed(Input.KEY_UP)){
            camera.addToY(50);
        }else if(i.isKeyPressed(Input.KEY_DOWN)){
            camera.addToY(-50);
        }
        
    }

    public void selectLevel(int x, int y, Input i, StateBasedGame sbg) {
        
            if(levelSelect.getLevelButtons().get(0).onHover(x, y)){
                if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                    sbg.enterState(Main.VERSUSSTATE);
                }
            }
            
            if(levelSelect.getLevelButtons().get(1).onHover(x, y)){
                if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                    sbg.enterState(Main.BIGBLUESTATE);
                }
            }
            
            if(levelSelect.getLevelButtons().get(2).onHover(x, y)){
                if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                    sbg.enterState(Main.FIRSTPRACTICE);
                }
            }
            
            if(levelSelect.getLevelButtons().get(3).onHover(x, y)){
                if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                    sbg.enterState(Main.TOWER1STATE);
                }
            }
            
            if(levelSelect.getLevelButtons().get(4).onHover(x, y)){
                if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                    sbg.enterState(Main.TUTORIAL);
                }
            }
            
    }
}
