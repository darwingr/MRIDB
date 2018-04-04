package game.gui;

import java.sql.SQLException;
import java.util.ArrayList;

import engine.GameContainer;
import engine.Renderer;
import models.MRIScanModel;
import models.MeasurementModel;

public class AttributeSearch {

	public class DataSet {
		private Attribute[] attribs;
		private float[] ages;

		public DataSet(Attribute[] attribs, float[] ages) {
			this.attribs = attribs;
			this.ages = ages;

		}

		public Attribute[] getAttribs() {
			return attribs;
		}

		public float[] getAges() {
			return ages;
		}

	}

	private int x, y, width, height;
	private CommandLine input;
	private Attribute[] uAttribs;

	public AttributeSearch(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		input = new CommandLine("Search Measurement: ", x, y, width, height);

	}

	public void update(GameContainer gc, FilterManager filter, float dt) {
		input.update(gc, dt);

	}

	public void render(GameContainer gc, Renderer r) {
		input.render(gc, r);
	}

	public DataSet search(String key, FilterManager filter) {
		String measurementName = "DEFAULT_NAME";
		Attribute[] attribs;
		float[] ages;
		ArrayList<MRIScanModel> mris;

		try {
			measurementName = MeasurementModel.findByID(Integer.parseInt(key)).getLabel();
			mris = MRIScanModel.scansForAgeRange(
					(int) filter.getAgeRange()[0],
					(int) filter.getAgeRange()[1],
					filter.genderFilter());
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			return new DataSet(new Attribute[0], new float[0]);
		}

		attribs = new Attribute[mris.size()];
		ages = new float[mris.size()];
		for (int i=0; i<mris.size(); i++) {
			attribs[i] = new Attribute(
					measurementName,
					"" + mris.get(i).getMeasurementSubset(new int[] { Integer.parseInt(key) }).get(0));
			ages[i] = mris.get(i).getVisitAge();
		}

		return new DataSet(attribs,ages);
	}

	public String getWord() {
		return input.getWord();
	}

}
