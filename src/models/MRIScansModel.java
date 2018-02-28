/**
 * 
 */
package models;


/**
 * @author Michael
 *
 */
public class MRIScansModel extends ActiveRecordModel {
	private int id;
	private String TechnicianNotes;
	private int TechnicianID;
	private double measurements[];
	private int visitID;
	private int deviceID;
	
	public MRIScansModel() {
		// TODO Auto-generated constructor stub
	}
}
