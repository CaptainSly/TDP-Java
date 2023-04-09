package com.tdp.bsp;

public class Node {

	public static final int SIZE = Short.SIZE * 14;
	public static final int BYTES = Short.BYTES * 14;

	private short xPartition, yPartition;
	private short changeXPartition, changeYPartition;

	private short rightBoxTop, rightBoxBottom;
	private short rightBoxLeft, rightBoxRight;

	private short leftBoxTop, leftBoxBottom;
	private short leftBoxLeft, leftBoxRight;

	private short rightChildId, leftChildId;

	public Node(short xPartition, short yPartition, short changeXPartition, short changeYPartition, short rightBoxTop,
			short rightBoxBottom, short rightBoxLeft, short rightBoxRight, short leftBoxTop, short leftBoxBottom,
			short leftBoxLeft, short leftBoxRight, short rightChildId, short leftChildId) {
		this.xPartition = xPartition;
		this.yPartition = yPartition;
		this.changeXPartition = changeXPartition;
		this.changeYPartition = changeYPartition;
		this.rightBoxTop = rightBoxTop;
		this.rightBoxBottom = rightBoxBottom;
		this.rightBoxLeft = rightBoxLeft;
		this.rightBoxRight = rightBoxRight;
		this.leftBoxTop = leftBoxTop;
		this.leftBoxBottom = leftBoxBottom;
		this.leftBoxLeft = leftBoxLeft;
		this.leftBoxRight = leftBoxRight;
		this.rightChildId = rightChildId;
		this.leftChildId = leftChildId;
	}

	public short getxPartition() {
		return xPartition;
	}

	public short getyPartition() {
		return yPartition;
	}

	public short getChangeXPartition() {
		return changeXPartition;
	}

	public short getChangeYPartition() {
		return changeYPartition;
	}

	public short getRightBoxTop() {
		return rightBoxTop;
	}

	public short getRightBoxBottom() {
		return rightBoxBottom;
	}

	public short getRightBoxLeft() {
		return rightBoxLeft;
	}

	public short getRightBoxRight() {
		return rightBoxRight;
	}

	public short getLeftBoxTop() {
		return leftBoxTop;
	}

	public short getLeftBoxBottom() {
		return leftBoxBottom;
	}

	public short getLeftBoxLeft() {
		return leftBoxLeft;
	}

	public short getLeftBoxRight() {
		return leftBoxRight;
	}

	public short getRightChildId() {
		return rightChildId;
	}

	public short getLeftChildId() {
		return leftChildId;
	}

}
