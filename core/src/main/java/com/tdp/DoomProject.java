package com.tdp;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tdp.wad.Player;
import com.tdp.wad.Wad;
import com.tdp.wad.WadLoader;

public class DoomProject extends Game {
    private SpriteBatch batch;
    private ShapeRenderer shape;
    private WadLoader wadLoader;
    private Map map;
    private Player player;
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        
        player = new Player(1);
        
        try {
        	
			wadLoader = new WadLoader(Gdx.files.internal("doom1.wad"));
			Wad wad = wadLoader.getWadData();
		
			map = new Map("E1M1", player);
			wad.loadMapData(map);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        map.renderAutoMap(batch, shape);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
