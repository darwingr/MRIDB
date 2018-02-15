/**
 * 
 */
package models;

/**
 * @author darwingroskleg
 * This is the intended superclass of all other model objects.
 *
 */
//public abstract class ActiveRecordModel {
public class ActiveRecordModel {
	
	public static ActiveRecordModel findById(int id) {
		ActiveRecordModel model = new ActiveRecordModel();
		return model;
	}
	
	public static ActiveRecordModel findBy(String attribute, int id) {
		ActiveRecordModel model = new ActiveRecordModel();
		return model;
	}
	
	private String table_name;

	public ActiveRecordModel() {
		//constructor
	}

}
