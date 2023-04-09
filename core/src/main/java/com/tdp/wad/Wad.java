package com.tdp.wad;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.tdp.Map;
import com.tdp.bsp.Node;
import com.tdp.wad.data.Directory;
import com.tdp.wad.data.Linedef;
import com.tdp.wad.data.Thing;
import com.tdp.wad.data.Vertex;

// Where's All my Data! <- Heh, thought that was funny
public class Wad {

	private enum MAP_LUMPS_INDEX {
		THINGS, LINEDEFS, SIDEDDEFS, VERTEXES, SEAGS, SSECTORS, NODES, SECTORS, REJECT, BLOCKMAP, COUNT
	}

	@SuppressWarnings("unused")
	private enum LINEDEF_FLAGS {
		BLOCKING(0), BLOCKMONSTERS(1), TWOSIDED(2), DONTPEGTOP(4), DONTPEGBOTTOM(8), SECRET(16), SOUNDBLOCK(32),
		DONTDRAW(64), DRAW(128);

		int flag;

		LINEDEF_FLAGS(int flag) {
			this.flag = flag;
		}

		public int getFlag() {
			return flag;
		}
	}

	private String wadType;
	private int directoryCount;
	private int directoryOffset;
	private List<Directory> wadDirectories;

	private byte[] wadData;

	public Wad(byte[] wadData) {
		this.wadData = wadData;
		wadDirectories = new ArrayList<>();

		readHeaderData();
		readDirectoryData();
	}

	public void readHeaderData() {

		// 0x00 to 0x03
		wadType = new String(wadData, 0, 4, StandardCharsets.US_ASCII).trim();

		// 0x04 to 0x07
		directoryCount = read4Bytes(4);

		// 0x08 to 0x0b
		directoryOffset = read4Bytes(8);
	}

	public void readDirectoryData() {
		int lumpOffset = 0;
		int lumpSize = 0;

		Directory directory = null;
		for (int i = 0; i < directoryCount; i++) {
			int offset = directoryOffset + i * 16;

			// 0x00 to 0x03
			lumpOffset = read4Bytes(offset);

			// 0x04 to 0x07
			lumpSize = read4Bytes(offset + 4);

			String lumpName = new String(wadData, offset + 8, 8, StandardCharsets.US_ASCII);

			directory = new Directory(lumpOffset, lumpSize, lumpName.trim());

			wadDirectories.add(directory);
		}
	}

	private Vertex readVertexData(int offset) {
		Vertex v = new Vertex(read2Bytes(offset), read2Bytes(offset + 2));
		return v;
	}

	private Linedef readLinedefData(int offset) {
		Linedef l = new Linedef(read2Bytes(offset), read2Bytes(offset + 2), read2Bytes(offset + 4),
				read2Bytes(offset + 6), read2Bytes(offset + 10), read2Bytes(offset + 8), read2Bytes(offset + 12));

		return l;
	}

	private Thing readThingData(int offset) {
		Thing t = new Thing(read2Bytes(offset), read2Bytes(offset + 2), read2Bytes(offset + 4), read2Bytes(offset + 6),
				read2Bytes(offset + 8));

		return t;
	}

	private Node readNodesData(int offset) {
		Node n = new Node(
				read2Bytes(offset),
				read2Bytes(offset + 2),
				read2Bytes(offset + 4),
				read2Bytes(offset + 6),
				read2Bytes(offset + 8),
				read2Bytes(offset + 10),
				read2Bytes(offset + 12),
				read2Bytes(offset + 14),
				read2Bytes(offset + 16),
				read2Bytes(offset + 18),
				read2Bytes(offset + 20),
				read2Bytes(offset + 22),
				read2Bytes(offset + 24),
				read2Bytes(offset + 26)
				
		);
		
		return n;
	}
	
	private int findMapIndex(Map map) {

		if (map.getLumpIndex() > -1) {
			return map.getLumpIndex();
		}
		
		for (int i = 0; i < wadDirectories.size(); i++) {
			Directory dir = wadDirectories.get(i);
			if (dir.getLumpName().equalsIgnoreCase(map.getMapName())) {
				map.setLumpIndex(i);
				return i;
			}
		}

		return -1;
	}

	private boolean readMapThing(Map map) {
		int mapIndex = findMapIndex(map);

		if (mapIndex == -1)
			return false;

		mapIndex += MAP_LUMPS_INDEX.THINGS.ordinal() + 1;

		Directory dir = wadDirectories.get(mapIndex);

		if (!(dir.getLumpName().equalsIgnoreCase("THINGS")))
			return false;

		int thingsSizeInBytes = Thing.BYTES;
		int thingsCount = dir.getLumpSize() / thingsSizeInBytes;

		Thing thing = null;
		for (int i = 0; i < thingsCount; i++) {
			thing = readThingData(dir.getLumpOffset() + i * thingsSizeInBytes);
			map.addThing(thing);
		}

		return true;
	}

	private boolean readMapVertex(Map map) {
		int mapIndex = findMapIndex(map);

		if (mapIndex == -1)
			return false;

		mapIndex += MAP_LUMPS_INDEX.VERTEXES.ordinal() + 1; // Increment by one since enum indexes start at 0

		Directory dir = wadDirectories.get(mapIndex);

		if (!(dir.getLumpName().equalsIgnoreCase("VERTEXES")))
			return false;

		int vertexSizeInBytes = Vertex.BYTES;
		int vertexesCount = dir.getLumpSize() / vertexSizeInBytes;

		Vertex vertex = null;
		for (int i = 0; i < vertexesCount; i++) {
			vertex = readVertexData(dir.getLumpOffset() + i * vertexSizeInBytes);
			map.addVertex(vertex);
		}

		return true;
	}

	private boolean readMapLinedef(Map map) {
		int mapIndex = findMapIndex(map);

		if (mapIndex == -1)
			return false;

		mapIndex += MAP_LUMPS_INDEX.LINEDEFS.ordinal() + 1;
		Directory dir = wadDirectories.get(mapIndex);

		if (!(dir.getLumpName().equalsIgnoreCase("LINEDEFS")))
			return false;

		int linedefSizeInBytes = Linedef.BYTES;
		int linedefCount = dir.getLumpSize() / linedefSizeInBytes;

		Linedef linedef = null;
		for (int i = 0; i < linedefCount; i++) {
			linedef = readLinedefData(dir.getLumpOffset() + i * linedefSizeInBytes);
			map.addLinedefs(linedef);
		}

		return true;
	}
	
	public boolean readMapNode(Map map) {
		int mapIndex = findMapIndex(map);
		
		if (mapIndex == 1) return false;
		
		mapIndex += MAP_LUMPS_INDEX.NODES.ordinal() + 1;
		
		Directory dir = wadDirectories.get(mapIndex);
		if (!(dir.getLumpName().equalsIgnoreCase("NODES"))) return false;
		
		int nodeSizeInBytes = Node.BYTES;
		int nodesCount = dir.getLumpOffset() / nodeSizeInBytes;
		
		Node node = null;
		for (int i = 0; i < nodesCount; i++) {
			node = readNodesData(dir.getLumpOffset() + i * nodeSizeInBytes);
			map.addNode(node);
		}
		
		return true;
	}

	public boolean loadMapData(Map map) throws Exception {
		if (!readMapVertex(map))
			throw new Exception("Failed to load map vertex data. Map: " + map.getMapName());

		if (!readMapLinedef(map))
			throw new Exception("Failed to load map linedef data. Map: " + map.getMapName());

		if (!readMapThing(map))
			throw new Exception("Failed to load map thing data. Map: " + map.getMapName());

		if (!readMapNode(map))
			throw new Exception("Failed to load map node data. Map: " + map.getMapName());
		
		return true;
	}

	private short read2Bytes(int offset) {
		ByteBuffer buffer = ByteBuffer.wrap(wadData, offset, 2);
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		return buffer.getShort();
	}

	private int read4Bytes(int offset) {
		ByteBuffer buffer = ByteBuffer.wrap(wadData, offset, 4);
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		return buffer.getInt();
	}

	public String getWadType() {
		return wadType;
	}

	public List<Directory> getDirectoryList() {
		return wadDirectories;
	}

	public int getDirectoryCount() {
		return directoryCount;
	}

	public int getDirectoryOffset() {
		return directoryOffset;
	}

	public byte[] getWadData() {
		return wadData;
	}

}
