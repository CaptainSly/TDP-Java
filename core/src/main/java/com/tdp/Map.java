package com.tdp;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.tdp.bsp.Node;
import com.tdp.wad.Player;
import com.tdp.wad.data.Linedef;
import com.tdp.wad.data.Thing;
import com.tdp.wad.data.Vertex;

@SuppressWarnings("unused")
public class Map {

	private String mapName;

	private int lumpIndex = -1;

	private int xMin = Integer.MAX_VALUE, xMax = Integer.MAX_VALUE;
	private int yMin = Integer.MAX_VALUE, yMax = Integer.MAX_VALUE;

	private int autoMapScaleFactor = 15;

	private int renderXSize = 1280 - 1, renderYSize = 720 - 1;

	private List<Node> nodes; // BSP
	private List<Vertex> vertexes;
	private List<Linedef> linedefs;
	private List<Thing> things;

	private Player player;

	public Map(String mapName, Player player) {
		this.mapName = mapName;
		this.player = player;

		vertexes = new ArrayList<>();
		linedefs = new ArrayList<>();
		things = new ArrayList<>();
		nodes = new ArrayList<>();
	}

	public void addVertex(Vertex v) {
		vertexes.add(v);

		if (xMin > v.getxPos())
			xMin = v.getxPos();
		else if (xMax < v.getxPos())
			xMax = v.getxPos();

		if (yMin > v.getyPos())
			yMin = v.getyPos();
		else if (yMax < v.getyPos())
			yMax = v.getyPos();

	}

	public void addLinedefs(Linedef d) {
		linedefs.add(d);
	}

	public void addNode(Node n) {
		nodes.add(n);
	}

	public void addThing(Thing t) {

		if (t.getType() == player.getPlayerId()) {
			player.setxPos(t.getxPos());
			player.setyPos(t.getyPos());
			player.setAngle(t.getAngle());
		}

		things.add(t);
	}

	public void renderAutoMapPlayer(SpriteBatch batch, ShapeRenderer shape, int xShift, int yShift) {
		shape.setColor(Color.LIME);
		shape.setAutoShapeType(true);
		shape.begin(ShapeType.Filled);
		shape.circle((player.getxPos() + xShift) / autoMapScaleFactor, (player.getyPos() + yShift) / autoMapScaleFactor,
				2.5f);
		shape.end();
	}

	public void renderAutoMap(SpriteBatch batch, ShapeRenderer shape) {
		int xShift = -xMin;
		int yShift = -yMin;

		renderAutoMapWalls(batch, shape, xShift, yShift);
		renderAutoMapPlayer(batch, shape, xShift, yShift);
		renderAutoMapNode(batch, shape);
	}

	public void renderAutoMapNode(SpriteBatch batch, ShapeRenderer shape) {
		// Get last node
		Node node = nodes.get(nodes.size() - 1);

		int rBL = 0, rBT = 0, rBR = 0, rBB = 0;
		rBL = remapXToScreen(node.getRightBoxLeft());
		rBT = remapYToScreen(node.getRightBoxTop());
		rBR = remapXToScreen(node.getRightBoxRight() - remapXToScreen(node.getRightBoxLeft()) + 1);
		rBB = remapYToScreen(node.getRightBoxBottom() - remapYToScreen(node.getRightBoxTop()) + 1);
		Rectangle rightRect = new Rectangle(rBL, rBT, rBR, rBB);

		int lBL = 0, lBT = 0, lBR = 0, lBB = 0;
		lBL = remapXToScreen(node.getLeftBoxLeft());
		lBT = remapYToScreen(node.getLeftBoxTop());
		lBR = remapXToScreen(node.getLeftBoxRight() - remapXToScreen(node.getLeftBoxLeft()) + 1);
		lBB = remapYToScreen(node.getLeftBoxBottom() - remapYToScreen(node.getLeftBoxTop()) + 1);
		Rectangle leftRect = new Rectangle(lBL, lBT, lBR, lBB);

		shape.begin();
		shape.setColor(new Color(0, 255, 0, 1));
		shape.rect(rightRect.x, rightRect.y, rightRect.width, rightRect.height);
		shape.end();

		shape.begin();
		shape.setColor(new Color(255, 0, 0, 1));
		shape.rect(leftRect.x, leftRect.y, leftRect.width, leftRect.height);
		shape.end();

		shape.begin();
		shape.setColor(new Color(0, 0, 255, 1));
		shape.line(remapXToScreen(node.getxPartition()), remapYToScreen(node.getyPartition()),
				remapXToScreen(node.getxPartition() + node.getChangeXPartition()),
				remapYToScreen(node.getyPartition() + node.getChangeYPartition()));
		shape.end();
	}

	public void renderAutoMapWalls(SpriteBatch batch, ShapeRenderer shape, int xShift, int yShift) {

		shape.begin(ShapeType.Line);
		shape.setColor(Color.WHITE);

		for (Linedef linedef : linedefs) {
			Vertex vStart = vertexes.get(linedef.getStartVertex());
			Vertex vEnd = vertexes.get(linedef.getEndVertex());

			// Draw a Line
			shape.line((vStart.getxPos() + xShift) / autoMapScaleFactor,
					(vStart.getyPos() + yShift) / autoMapScaleFactor, (vEnd.getxPos() + xShift) / autoMapScaleFactor,
					(vEnd.getyPos() + yShift) / autoMapScaleFactor);

		}

		shape.end();
	}

	public int remapXToScreen(int xMapPosition) {
		return (xMapPosition + (-xMin)) / autoMapScaleFactor;
	}

	public int remapYToScreen(int yMapPosition) {
		return renderYSize - (yMapPosition + (-yMin)) / autoMapScaleFactor;
	}

	public void setLumpIndex(int index) {
		this.lumpIndex = index;
	}

	public String getMapName() {
		return mapName;
	}

	public List<Vertex> getVertexes() {
		return vertexes;
	}

	public List<Linedef> getLinedefs() {
		return linedefs;
	}

	public int getLumpIndex() {
		return lumpIndex;
	}

}
