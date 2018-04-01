/*
 * 
 * Author: Cynthia F.
 * Based off of D. Groskleg's Patient Model.
 * 
 * 
 */

package models;

public class TechnicianModel extends ActiveRecord {
	
	private static final String TABLE_NAME = "technicians";

	private int id;
    private String first_name;
    private String last_name;
    
	public TechnicianModel(String fname, String lname) {
		fname = first_name;
		lname = last_name;
	}
	
	public String fullname() {
		return first_name + last_name;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}


	
	

}
