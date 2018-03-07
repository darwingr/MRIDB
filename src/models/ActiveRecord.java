/**
 * 
 */
package models;

/**
 * @author darwingroskleg
 * This is the intended superclass of all other model objects.
 *
 */
//public abstract class ActiveRecord {
public class ActiveRecord {
	
	public static ActiveRecord findById(int id) {
		ActiveRecord record = new ActiveRecord();
		return record;
	}
	
	public static ActiveRecord findBy(String attribute, int id) {
		ActiveRecord record = new ActiveRecord();
		return record;
	}
	
	private String table_name;

	public ActiveRecord() {
		//constructor
	}

}
