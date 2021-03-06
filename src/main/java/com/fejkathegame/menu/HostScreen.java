package com.fejkathegame.menu;

import com.fejkathegame.Fonts.FontFactory;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;

/**
 *
 * @author Filip
 */
public class HostScreen {
    
    private TextField playerNameTextField;
    private Image host;
    private Font font;
    private String ip, playerName;
    private FontFactory playerNameText;

    public HostScreen(String name, GameContainer gc) throws SlickException {
        
        font = gc.getDefaultFont();
        playerNameTextField = new TextField(gc, font, gc.getWidth() / 2 - 150, gc.getHeight() / 2 - 80, 300, 40);
        playerNameTextField.setBackgroundColor(Color.white);
        playerNameTextField.setTextColor(Color.black);
        host = new Image("src/main/resources/data/img/buttons/hostButton.png");
        
        playerNameTextField.setMaxLength(10);

        playerNameText = new FontFactory("Veranda", 20, "Player Name:");
    }

    public void render(GameContainer gc, Graphics g) {
        g.setColor(Color.white);
        playerNameTextField.render(gc, g);
        host.draw(gc.getWidth() / 2 - 75, gc.getHeight() / 2 + 80);
        playerNameTextField.setFocus(true);
        playerNameText.renderText(300, 145);
    }

    public TextField getPlayerNameTextField() {
        return playerNameTextField;
    }
    
}
