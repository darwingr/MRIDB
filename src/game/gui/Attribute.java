package game.gui;

import java.util.Random;

public class Attribute {

	private int attribID;
	private String name, data, units;

	public Attribute(String name, String data) {
		this.name = name;
		this.data = data;
		units = "mm";
	}

	public String getDisplayText() {
		return name + "" + data;
	}

	public String getDataAsString() {
		return data;
	}

	public long getDataAsLong() {
		return Long.parseLong(data);
	}

	public float getDataAsFloat() {
		return Float.parseFloat(data);
	}

	public double getDataAsDouble() {
		return Double.parseDouble(data);
	}

	public int getDataAsInt() {
		return Integer.parseInt(data);
	}

	public String getName() {
		return name;
	}

	public int getAttribID() {
		return attribID;
	}
	
	public String getUnits() {
		return units;
	}

	public static Attribute[] genFakeAttributes() {
		Random r = new Random();
		double data = r.nextDouble()*(r.nextInt(99)+1);
		Attribute[] attribs = new Attribute[100];
		for (int i = 0; i < attribs.length; i++)
			attribs[i] = new Attribute("name"+i,""+data);
		return attribs;
	}

}
