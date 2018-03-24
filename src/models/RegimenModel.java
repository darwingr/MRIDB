/*
 * 
 * Author: Cynthia F.
 * Based off of D. Groskleg's Patient Model.
 * 
 */

package models;
import java.util.Date;

public class RegimenModel extends ActiveRecord {
	
    private String physician_notes;
    private Date start_date;
    private int treatment_id;

	public RegimenModel() {
		// TODO Auto-generated constructor stub
	}

}
