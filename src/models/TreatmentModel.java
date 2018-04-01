package models;

public class TreatmentModel extends ActiveRecord {
    private static final String TABLE_NAME = "treatments";

    private int    id;
    private String treatment_type;
    private String description;

    public TreatmentModel(String treatment) {
        treatment = treatment_type;
    }

    @Override
    public String table() { return "treatments"; }

    @Override
    public int getID() { return id; }
}
