/**
 * 
 */
package models;


/**
 *
 */
public class MRIScanModel extends ActiveRecord {
	private static final String TABLE_NAME = "mri_scans";

	private int 		id;
	private String 	technician_notes;
	private int 		technician_id;
	private int 		visit_id;
	private int 		device_id;
	private double 	vals[];

	public MRIScanModel() {
		// TODO Auto-generated constructor stub
	}
}
