package com.tdp.wad.data;

public class Directory {

	private int lumpOffset;
	private int lumpSize;
	private String lumpName;

	public Directory(int lumpOffset, int lumpSize, String lumpName) {
		this.lumpOffset = lumpOffset;
		this.lumpSize = lumpSize;
		this.lumpName = lumpName;
	}

	public int getLumpOffset() {
		return lumpOffset;
	}

	public int getLumpSize() {
		return lumpSize;
	}

	public String getLumpName() {
		return lumpName;
	}

}
