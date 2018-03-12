package models;

import adapters.DBAdapter;

import java.sql.ResultSet;

/**
 * This is the intended superclass of all other model objects.
 * Used as an abstract class, there is no instance of a active record.
 * Note that all subclasses must have TABLE_NAME initialized.
 */
public class ActiveRecord {
	private static final String TABLE_NAME = "records";

	/*
	 * Usage in a subclass: user = UserModel.findByID(5);
	 */
	public static ActiveRecord findByID(int id) {
		DBAdapter db = new DBAdapter();
		ResultSet result = db.executeQuery("select * from " + TABLE_NAME + " where id = " + id);
		
		// TODO
		// Something here to create the object from the given result set.
		// Just passing the result set to the object leaves a lot of duplicate work to be done in each
		// subclass.
		
		ActiveRecord record = new ActiveRecord();
		return record;
	}
	
	public static ActiveRecord findBy(String attribute, int id) {
		DBAdapter db = new DBAdapter();
		ResultSet result = db.executeQuery("select * from " + TABLE_NAME + " where " + attribute + "=" + id);
		
		// TODO
		// Same as in findByID above.

		ActiveRecord record = new ActiveRecord();
		return record;
	}
	
	public ActiveRecord() {
		//constructor
	}

}
