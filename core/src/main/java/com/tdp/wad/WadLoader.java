package com.tdp.wad;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.badlogic.gdx.files.FileHandle;

public class WadLoader {

	private FileHandle wadFileHandle;
	private Wad wad;
	

	public WadLoader(FileHandle wadFileHandle) throws IOException {
		this.wadFileHandle = wadFileHandle;
		
		if (!wadFileHandle.exists()) {
			throw new FileNotFoundException("Could not find: " + wadFileHandle.path());
		}

		wad = new Wad(wadFileHandle.readBytes());
		
	}

	public FileHandle getWadFileHandle() {
		return wadFileHandle;
	}

	public Wad getWadData() {
		return wad;
	}

}
