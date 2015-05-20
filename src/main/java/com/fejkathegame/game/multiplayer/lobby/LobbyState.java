package com.fejkathegame.game.multiplayer.lobby;

import com.fejkathegame.client.ClientProgram;
import com.fejkathegame.client.MPPlayer;
import com.fejkathegame.client.PacketNamePlayer;
import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.State;
import com.fejkathegame.game.arena.maps.multiplayer.versus01.VersusState;
import com.fejkathegame.game.entities.Character;
import com.fejkathegame.menu.HostScreen;
import com.fejkathegame.menu.HostScreenState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Khamekaze
 */
public class LobbyState extends State {
    
    
    private ClientProgram client = new ClientProgram();
    
    private HostScreenState hs;
    
    private Character localPlayer;
    
    private String name, playerName;
    private Lobby lobby;
    private ArrayList<Image> heads;
    private ArrayList<Character> characters;
    
    private int increase = 1;

    @Override
    public int getID() {
        return Main.LOBBYSTATE;
    }
    
    public LobbyState(String name) {
        this.name = name;
    }
    
    public LobbyState(HostScreenState hs) {
        this.hs = hs;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        lobby = new Lobby(name);
        heads = lobby.getImages();
        characters = lobby.getCharacters();
        if(hs != null) {
            client.network(hs.getIp());
            playerName = hs.getPlayerName();
        } else {
            client.network("localhost");
            playerName = "Player";
        }
        
        localPlayer = null;
        try {
            localPlayer = new com.fejkathegame.game.entities.Character(800, 40);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        localPlayer.setName(playerName);
        sendNameToServer();
        
        characters.add(localPlayer);
        sbg.addState(new VersusState("01versus", client, localPlayer, characters));
    }
    
    public void checkIfNewPlayerConnected() {
        for (MPPlayer mpPlayer : client.getPlayers().values()) { //other player render here.
            if (mpPlayer.character == null) {
                try {
                    mpPlayer.character = new Character(300, 40);
                } catch (SlickException | IOException ex) {
                    Logger.getLogger(VersusState.class.getName()).log(Level.SEVERE, null, ex);
                }
                mpPlayer.character.setHealth(5);
                mpPlayer.hp = 5;
                mpPlayer.character.setName(mpPlayer.name);
                characters.add(mpPlayer.character);
            }
            if (mpPlayer.connected == false) {
                characters.remove(mpPlayer.character);
            }
        }
    }
    
    public ArrayList<Character> getCharacters() {
        return characters;
    }
    
    public void sendNameToServer() {
            PacketNamePlayer packet = new PacketNamePlayer();
            packet.name = localPlayer.getName();
            client.getClient().sendUDP(packet);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        lobby.render();
        for(Character c : characters) {
            g.drawString(c.getName(), increase * 128, 132);
            increase += 1;
        }
        
        increase = 1;
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        checkIfNewPlayerConnected();
        if(gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
            sbg.getState(Main.VERSUSSTATE).init(gc, sbg);
            sbg.enterState(Main.VERSUSSTATE);
        }
    }
}
