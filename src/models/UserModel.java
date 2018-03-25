package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import adapters.DBAdapter;

public class UserModel extends ActiveRecord {
	private static final String TABLE_NAME = "users";

	private int    id;
	private String username;
	private String password;
	private String first_name;
	private String last_name;
	private String email;
	private boolean hipaa_authorized;
	
	public static UserModel findByID(int user_id) throws SQLException {
		UserModel user = new UserModel();
		DBAdapter db = new DBAdapter();
		try (ResultSet rs = db.executeQuery("select * from " + TABLE_NAME + " where id = " + user_id)) {
			rs.next();
			user.id = rs.getInt("id");
			user.username = rs.getString("username");
			user.password = rs.getString("password");
			user.first_name = rs.getString("first_name");
			user.last_name = rs.getString("last_name");
			user.email = rs.getString("email");
			user.hipaa_authorized = rs.getBoolean("hipaa_authorized");
		} catch (SQLException sqle) {
            System.err.println("Exception occurred while processing Building ResultSet after findByID.");
		} finally {
			db.close();
		}
		return user;
	}

	public UserModel() {
	}

	public boolean authenticate(String user_username, String user_password) throws SQLException {
		DBAdapter db = new DBAdapter();
		String sql = "select * from users where username = '" + user_username
				+ "' AND password = '" + user_password + "'";
		boolean user_exists = false;
		try (ResultSet rs = db.executeQuery(sql)) {
			user_exists = rs.next();
		} finally {
			db.close();
		}
		return user_exists;
	}
	
	public String fullName() {
		return first_name + " " + last_name;
	}

	// Required to test findByID
	public int getID() {
        return id;
    }

	public boolean delete() throws SQLException {
		DBAdapter db = new DBAdapter();
		String sql = "DELETE FROM users WHERE id = '" + id + "'";
		boolean success = false;
		try (ResultSet rs = db.executeQuery(sql)) {
			success = rs.next();
		} finally {
			db.close();
		}
		return success;
	}

	public boolean changePassword(String new_pword) throws SQLException {
		setPassword(new_pword);
		return save();
	}

	public boolean create() throws SQLException {
		DBAdapter db = new DBAdapter();
		String sql = "INSERT INTO users\n" +
					 "(id, first_name, last_name, username, email, password, hipaa_authorized)\n" +
					 "VALUES\n" +
					 "(" + id + ", '" + first_name + "', '" + last_name + "', '" + username + "', '"
					 	 + email + "', '" + password + "', " + (hipaa_authorized ? 1 : 0) + ")";
		boolean success = false;
		try (ResultSet rs = db.executeQuery(sql)) {
			success = rs.next();
		} finally {
			db.close();
		}
		return success;
	}

	// Because we don't want to be publicly getting and setting attributes, which breaks encapsulation,
	// we as a result don't need save() to be a public method. If you think you need to make this public
	// then try instead to implement your code as a method on this class and call save() from there.
	// Note that we don't save or change ID, ever.
	private boolean save() throws SQLException {
		DBAdapter db = new DBAdapter();
		String sql = "UPDATE users " +
					 "SET username = '" + username + "', " +
					 "    password = '" + password + "', " +
					 "    first_name = '" + first_name + "', " +
					 "    last_name = '" + last_name + "', " +
					 "    email = '" + email + "', " +
					 "    hipaa_authorized = " + (hipaa_authorized ? 1 : 0) + " " +
					 "WHERE id = " + id;
		boolean success = false;
		try (ResultSet rs = db.executeQuery(sql)) {
			success = rs.next();
		} finally {
			db.close();
		}
		return success;
	}

	public String getPassword() {
		return password;
	}

	private boolean setPassword(String pword) {
		password = pword;
		return true;
	}
}