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
 *
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
 * 
 * This will work for both static and instance methods.
 * For an example see how User.findByID() is implemented.
 * Note that you must close the db connection when you're done with it.
 *
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
 * 
 * The above is an example called from the GameManager and shows a user's name in a
 * button on screen.
 * Notice how we don't use any SQL and we don't need to close the connection,
 * that's all handled internally in the model's implementation.
 *
 */
<<<<<<< HEAD

@SuppressWarnings("unused")

public class ActiveRecord {
	private static final String TABLE_NAME = "records";
=======
>>>>>>> branch 'master' of https://github.com/ThreeFourSeven/Database-Gui.git

<<<<<<< HEAD
=======
@SuppressWarnings("unused")

abstract public class ActiveRecord {

>>>>>>> branch 'master' of https://github.com/ThreeFourSeven/Database-Gui.git
	public ActiveRecord() {
		//constructor
	}

	public String table() { return "records"; }

	public boolean delete() throws SQLException {
		DBAdapter db = new DBAdapter();
		String sql = "DELETE FROM "+ table() +" WHERE id = '" + getID() + "'";
		boolean success = false;
		try (ResultSet rs = db.executeQuery(sql)) {
			success = rs.next();
		} catch (Exception e) {
            System.err.println("Exception occurred while deleting record.");
		} finally {
			db.close();
		}
		return success;
	}

	abstract public int getID();
}
