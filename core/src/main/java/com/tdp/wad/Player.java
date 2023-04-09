package com.tdp.wad;

public class Player {

	public static final int BYTES = Integer.BYTES * 4;
	public static final int SIZE = Integer.SIZE * 4;

	private int playerId;
	private int xPos, yPos;
	private int angle;

	public Player(int playerId) {
		this.playerId = playerId;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public int getPlayerId() {
		return playerId;
	}

	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public int getAngle() {
		return angle;
	}

}
