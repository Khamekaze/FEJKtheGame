/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.arena.tiles;

import com.fejkathegame.game.arena.collision.AABoundingRect;

/**
 *
 * @author Swartt
 */
public class PitTile extends Tile {
   
   public PitTile (int x, int y) {
       super(x, y);
       boundingShape = new AABoundingRect(x * 25, y * 25, 25, 25);
   }
   
}
