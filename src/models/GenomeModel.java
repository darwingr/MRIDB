package models;
import java.util.Date;

public class GenomeModel {
	private static final String TABLE_NAME = "genomes";

	private int    id;
	private String sequence;
	private Date date_taken;
	private int    visit_id;

	public GenomeModel() {
		// TODO Auto-generated constructor stub
	}

    @Override
    public String table() { return "genomes";}

    @Override
    public int getID() { return id; }
}
