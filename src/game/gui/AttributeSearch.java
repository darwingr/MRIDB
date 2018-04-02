package game.gui;

import java.sql.SQLException;
import java.util.ArrayList;

import engine.GameContainer;
import engine.Renderer;
import models.MRIScanModel;
import models.MeasurementModel;
import models.VisitModel;

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
		ArrayList<Double> vals = new ArrayList<Double>();
		ArrayList<Float> a = new ArrayList<Float>();
		Attribute[] attribs;
		float[] ages;
		try {
			MeasurementModel m = MeasurementModel.findByID(Integer.parseInt(key));
			measurementName = m.getLabel();
			ArrayList<MRIScanModel> mris = MRIScanModel.scansForAgeRange((int) filter.getAgeRange()[0],
					(int) filter.getAgeRange()[1]);
			for (int i = 0; i < mris.size(); i++) {
				Log.print("# of values "+ vals.size());
				Log.print("$ of ages " + a.size());
				Log.print("# of MRIS "+ i + "/"+mris.size());
				VisitModel v = VisitModel.findByID(mris.get(i).getVisitID());
				switch (filter.genderFilter()) {
				case FilterManager.MALE_ONLY:
					if (v.getGender() == 1) {
						vals.add(mris.get(i).getMeasurementSubset(new int[] { Integer.parseInt(key) }).get(0));
						a.add(v.getAge());
					}
					break;
				case FilterManager.FEMALE_ONLY:
					if (v.getGender() == 2) {
						vals.add(mris.get(i).getMeasurementSubset(new int[] { Integer.parseInt(key) }).get(0));
						a.add(v.getAge());
					}
					break;
				case FilterManager.ALL_GENDERS:
					vals.add(mris.get(i).getMeasurementSubset(new int[] { Integer.parseInt(key) }).get(0));
					a.add(v.getAge());
					break;
				}
			}
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
		attribs = new Attribute[vals.size()];
		ages = new float[a.size()];
		for(int i = 0; i < attribs.length; i++) {
			attribs[i] = new Attribute(measurementName,""+vals.get(i));
			ages[i] = a.get(i);
		}
		return new DataSet(attribs,ages);
	}

	public String getWord() {
		return input.getWord();
	}

}
