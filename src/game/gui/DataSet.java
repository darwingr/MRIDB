package game.gui;

public class DataSet {
	private Attribute[] attribs;
	private Float[] ages;

	public DataSet(Attribute[] attribs, Float[] ages) {
		this.attribs = attribs;
		this.ages = ages;

	}

	public Attribute[] getAttribs() {
		return attribs;
	}

	public Float[] getAges() {
		return ages;
	}

}