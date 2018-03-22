package models;

import adapters.DBAdapter;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.lang.reflect.Field;

/**
 * This is the intended superclass of all other model objects.
 * Used as an abstract class, there is no instance of a active record.
 * Note that all subclasses must have TABLE_NAME initialized.
 *
 * The pattern you need to use when defining methods on any subclass where an
 * interaction with the database occurs is as follows:
 * ```
 * DBAdapter db = new DBAdapter();
 *    try (ResultSet rs = db.executeQuery("sql query here")) {
 *    rs.next();
 *    // do stuff with the result set, see oracle docs on ResultSet
 * } catch (SQLException sqle) {
 *    System.err.println("Exception occurred while processing Building ResultSet.");
 * } finally {
 *    db.close(); // ALWAYS DO THIS
 * }
 * ```
 * This will work for both static and instance methods.
 * For an example see how User.findByID() is implemented.
 * Note that you must close the db connection when you're done with it.
 *
 *
 * The pattern for initializing and using an instance of a subclass of an
 * active record is as follows:
 * ```
 * String button_text = "Filters: ";
 * try {
 *    UserModel user = UserModel.findByID(1);
 *    button_text = user.fullName();
 * } catch (Exception e) { e.printStackTrace(); System.out.println("MYERROR!"); }
 * leftSide.addButtonBranch(button_text, leftSide, 128, 256, 0, 64, 0, false, new String[] {"ADHD","DIABETES"});
 * ```
 * This is an example called from the GameManager and shows a user's name in a
 * button on screen.
 * Notice how we don't use any SQL and we don't need to close the connection,
 * that's all handled internally in the model's implementation.
 *
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
