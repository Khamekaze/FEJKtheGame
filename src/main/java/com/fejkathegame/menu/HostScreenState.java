package com.fejkathegame.menu;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.multiplayer.lobby.LobbyState;
import com.fejkathegame.game.properties.HSPropertiesAdapter;
import com.fejkathegame.server.ServerProgram;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The HostScreen for multiplayer
 * 
 * @author Filip
 */
public class HostScreenState extends BasicGameState {

    ServerProgram server;
    private String name;
    private HostScreen hostScreen;
    private Input input;

    private String playerName, ip;

    private HSPropertiesAdapter prop = new HSPropertiesAdapter();

    @Override
    public int getID() {
        return Main.HOSTSTATE;
    }
    
    public HostScreenState(String name) {
        this.name = name;
    }
    
    /**
     * initilizes the text fields.
     */
    private void initTextFields() {
        String prevIp = prop.load("ip");
        String prevName = prop.load("playername");
        hostScreen.getPlayerNameTextField().setText(prevName);
    }
    
    /**
     * Checks if the host button area is clicked.
     * 
     * @param x mouse location 
     * @param y mouse location
     * @param i the Input
     * @param gc
     * @param sbg
     * @throws SlickException 
     */
    private void checkIfHostIsClicked(int x, int y, Input i, GameContainer gc, StateBasedGame sbg) throws SlickException {
        if ((x > 375 && x < 525) && (y > 330 && y < 405)) {
            if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                playerName = hostScreen.getPlayerNameTextField().getText();
                ip = "localhost";
                server.startServer();
                if (server.isServerReady()) {
                    prop.save("ip", ip);
                    prop.save("playername", playerName);
                    sbg.addState(new LobbyState(this, server));
                    sbg.getState(Main.LOBBYSTATE).init(gc, sbg);
                    sbg.enterState(Main.LOBBYSTATE);
                }
            }
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getIp() {
        return ip;
    }

    /**
     * The initialization of HostScreen.
     * 
     * @param gc
     * @param sbg
     * @throws SlickException 
     */
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        input = gc.getInput();
        hostScreen = new HostScreen(name, gc);
        server = new ServerProgram();
        initTextFields();
    }
    
    /**
     * HostScreen render on every frame.
     * 
     * @param gc
     * @param sbg
     * @param grphcs
     * @throws SlickException 
     */
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        grphcs.setBackground(Color.decode("#655d5d"));
        
        hostScreen.render(gc, grphcs);
    }
    
    /**
     * the HostSreen update on every frame.
     * 
     * @param gc
     * @param sbg
     * @param i
     * @throws SlickException 
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        int x = input.getMouseX();
        int y = input.getMouseY();
        checkIfHostIsClicked(x, y, input, gc, sbg);
    }

}
