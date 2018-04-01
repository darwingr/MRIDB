package game.gui;

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

	public void update(GameContainer gc, float dt) {
		input.update(gc, dt);
	}

	public void render(GameContainer gc, Renderer r) {
		input.render(gc, r);
	}

	public float[] addMeasurementToGraph(String key, FilterManager filter) {
		MRIScanModel mri;
		ArrayList<Double> v;
		try {
			int index = 1;
			for (mri = MRIScanModel.findByID(index); MRIScanModel.findByID(index + 1) != null; mri = MRIScanModel
					.findByID(index)) {
				v = mri.getMeasurementSubset(new int[] { Integer.parseInt(key) });
				VisitModel visit = VisitModel.findByID(mri.getVisitID());
				PatientModel p = PatientModel.findByID(visit.getPatientID());
				if (filter.genderFilter() != FilterManager.ALL_GENDERS
						&& filter.disorderFilter() != FilterManager.ALL_DISORDERS) {
					if (visit.getGender() == filter.genderFilter()) {
						
					}
				} else {
					
				}
				index++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
