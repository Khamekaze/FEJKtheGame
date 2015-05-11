/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.arena.maps.practice03;

import com.fejkathegame.game.arena.Level;
import com.fejkathegame.game.arena.tiles.AirTile;
import com.fejkathegame.game.arena.tiles.SolidTile;
import com.fejkathegame.game.arena.tiles.TargetTile;
import com.fejkathegame.game.arena.tiles.Tile;
import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.entities.PracticeTarget;
import com.fejkathegame.game.timer.PracticeTimer;
import org.lwjgl.util.Timer;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author Swartt
 */
public class BigBlue02 extends Level {

    private TiledMap map;

    private Tile[][] tiles;

    private ArrayList<LevelObject> players;
    private ArrayList<PracticeTarget> targets;

    private PracticeTarget movableTarget;
    float movableTargetStartingPos;
    private float targetVelY = 1.0f;

    PracticeTimer timer;

    Font font;
    TrueTypeFont ttf;
    String score;

    /**
     * Constructor for Arena, creates the playingfield and adds all players to
     * the field
     *
     * @param levelObject
     * @throws org.newdawn.slick.SlickException
     */
    public BigBlue02(String name, LevelObject levelObject) throws SlickException {
        map = new TiledMap("src/main/resources/data/levels/" + name + ".tmx", "src/main/resources/data/img");
        players = new ArrayList<>();
        targets = new ArrayList<>();

        addPlayer(levelObject);

        loadTileMap();
        font = new Font("Verdana", Font.BOLD, 20);
        ttf = new TrueTypeFont(font, true);

        score = "Targets Left: " + String.valueOf(targets.size());

        timer = new PracticeTimer();
        timer.startTimer();


        /*movableTarget = targets.get(15);*/
        /*movableTargetStartingPos = movableTarget.getY();*/
    }

    /**
     * populates the arena with tiles
     *
     * @throws org.newdawn.slick.SlickException
     */
    public void loadTileMap() {
        tiles = new Tile[map.getWidth() + 1][map.getHeight() + 1];

        int collisionLayer = map.getLayerIndex("CollisionLayer");
        int noLayer = -1;

        if (collisionLayer == noLayer) {
            System.err.println("Map does not contain CollisionLayer");
            System.exit(0);
        }


        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {


                try {
                    int tileID = map.getTileId(x, y, collisionLayer);
                    
                    Tile tile = null;
                    PracticeTarget target = null;
                    
                    switch (map.getTileProperty(tileID, "tileType", "solid")) {
                        case "air":
                            tile = new AirTile(x, y);
                            break;
                            
                        case "target":
                            tile = new TargetTile(x, y);
                            target = new PracticeTarget(x * 25, y * 25);
                            targets.add(target);
                            break;
                            
                        default:
                            tile = new SolidTile(x, y);
                            break;
                    }
                    tiles[x][y] = tile;
                } catch (SlickException ex) {
                    Logger.getLogger(BigBlue02.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BigBlue02.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }

            }
        }
    }

    /**
     * adds a player to the player array
     *
     * @param p
     */
    public void addPlayer(LevelObject p) {
        players.add(p);
    }

    /**
     * Updates the scoreboard
     */
    public void updateScore() {
        if (targets.size() > 0) {
            score = "Targets Left: " + String.valueOf(targets.size());
        } else {
            score = "You are a winrar!";
            timer.stopTimer();
        }
    }

    public void moveTarget() {
        movableTarget.setY(movableTarget.getY() + targetVelY);

        if (movableTarget.getY() > (movableTargetStartingPos + 100) && targetVelY > 0) {
            targetVelY = -1.0f;
        } else if (movableTarget.getY() < movableTargetStartingPos && targetVelY < 0) {
            targetVelY = 1.0f;
        }

    }

    /**
     * @return the player array which includes all player objects
     */
    public ArrayList<LevelObject> getPlayers() {
        return players;
    }

    /**
     * @return the player array which includes all {@code targetPractice} objects
     */
    public ArrayList<PracticeTarget> getTargets() {
        return targets;
    }


    /**
     * @return a multidimensional array of tiles
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    public TiledMap getMap () {
        return map;
    }

    /**
     * Renders the arena
     *
     * @throws org.newdawn.slick.SlickException
     */
    public void render() throws SlickException {
        map.render(0, 0, 0, 0, 150, 50);

        for (LevelObject p : players) {
            p.render();
        }

        for (LevelObject t : targets) {
            t.render();
        }

        //for debugging
        /*for (int i = 0; i < targets.size(); i++) {
            ttf.drawString(targets.get(i).getX(), targets.get(i).getY(), String.valueOf(i));
        }*/

    }

    public void updateText(float x, float y) {
        ttf.drawString(x, y, score, Color.green);
        ttf.drawString(x + 200, y, "Time: " + String.valueOf(timer.getElapsedTimeSecs()), Color.red);
    }
}
