package com.tdp.wad.data;

public class Vertex {

	public static final int SIZE = Short.SIZE + Short.SIZE;
	public static final int BYTES = Short.BYTES + Short.BYTES;

	private short xPos, yPos;

	public Vertex(short xPos, short yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public short getxPos() {
		return xPos;
	}

	public short getyPos() {
		return yPos;
	}

}
