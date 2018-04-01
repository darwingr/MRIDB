package game.gui;

import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import engine.GameContainer;
import engine.Renderer;
import models.MRIScanModel;
import models.PatientModel;
import models.VisitModel;

public class AttributeSearch {

	private int x, y, width, height;
	private CommandLine input;
	private Attribute[] uAttribs;

	public AttributeSearch(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		input = new CommandLine("Search Measurement", x, y, width, height);

	}

	public void update(GameContainer gc, FilterManager filter, float dt) {
		input.update(gc, dt);
		if(input.getWord().length() > 0 && gc.getInput().isKeyDown(KeyEvent.VK_ENTER)) {
			search(input.getWord(),filter);
		}
	}

	public void render(GameContainer gc, Renderer r) {
		input.render(gc, r);
	}

	public float[] search(String key, FilterManager filter) {
		MRIScanModel mri;
		ArrayList<Float> vals = new ArrayList<Float>();
		ArrayList<Float> ages = new ArrayList<Float>();
		try {
			int index = 1;
			for (mri = MRIScanModel.findByID(index); MRIScanModel.findByID(index + 1) != null; mri = MRIScanModel
					.findByID(index)) {
				ArrayList<Double> v = mri.getMeasurementSubset(new int[] { Integer.parseInt(key) });
				VisitModel visit = VisitModel.findByID(mri.getVisitID());
				PatientModel p = PatientModel.findByID(visit.getPatientID());
				if (filter.genderFilter() != FilterManager.ALL_GENDERS
						&& filter.disorderFilter() != FilterManager.ALL_DISORDERS) {
					switch (filter.genderFilter()) {
					case FilterManager.MALE_ONLY:
						if (visit.getGender() == 0) {
							for (Double d : v) {
								vals.add(Float.parseFloat(Double.toString(d)));
							}
							ages.add(visit.getAge());
						}
						break;
					case FilterManager.FEMALE_ONLY:
						if (visit.getGender() == 1) {
							for (Double d : v) {
								vals.add(Float.parseFloat(Double.toString(d)));
							}
							ages.add(visit.getAge());
						}
						break;
					case FilterManager.ALL_GENDERS:
						for (Double d : v) {
							vals.add(Float.parseFloat(Double.toString(d)));
						}
						ages.add(visit.getAge());
						break;
					}
					;
					switch (filter.disorderFilter()) {
					case FilterManager.ADHD_ONLY:
						break;
					case FilterManager.AUTISM_ONLY:
						break;
					case FilterManager.ALL_DISORDERS:
						break;
					}
					;
				} else {

				}
				index++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		float[] data = new float[ages.size()+vals.size()];
		for(int i = 0 ; i < data.length;i++) {
			if(i%2==0) {
				data[i]=ages.get(i);
			} else {
				data[i]=vals.get(i);
			}
		}
		return data;
	}

}
