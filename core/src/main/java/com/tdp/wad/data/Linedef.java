package com.tdp.wad.data;

public class Linedef {

	private short startVertex, endVertex, flags, lineType, sectorTag, rightSidedef, leftSidedef;

	public static final int SIZE = Short.SIZE * 7;
	public static final int BYTES = Short.BYTES * 7;

	public Linedef(short startVertex, short endVertex, short flags, short lineType, short sectorTag, short rightSidedef,
			short leftSidedef) {
		this.startVertex = startVertex;
		this.endVertex = endVertex;
		this.flags = flags;
		this.lineType = lineType;
		this.sectorTag = sectorTag;
		this.rightSidedef = rightSidedef;
		this.leftSidedef = leftSidedef;
	}

	public short getStartVertex() {
		return startVertex;
	}

	public short getEndVertex() {
		return endVertex;
	}

	public short getFlags() {
		return flags;
	}

	public short getLineType() {
		return lineType;
	}

	public short getSectorTag() {
		return sectorTag;
	}

	public short getRightSidedef() {
		return rightSidedef;
	}

	public short getLeftSidedef() {
		return leftSidedef;
	}

}
