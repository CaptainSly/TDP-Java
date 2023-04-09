package com.tdp.wad.data;

public class Thing {

	public static final int BYTES = Short.BYTES * 5;
	public static final int SIZE = Short.SIZE * 5;

	private short xPos, yPos;
	private short angle;
	private short type;
	private short flags;

	public Thing(short xPos, short yPos, short angle, short type, short flags) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.angle = angle;
		this.type = type;
		this.flags = flags;
	}

	public short getxPos() {
		return xPos;
	}

	public short getyPos() {
		return yPos;
	}

	public short getAngle() {
		return angle;
	}

	public short getType() {
		return type;
	}

	public short getFlags() {
		return flags;
	}

}
