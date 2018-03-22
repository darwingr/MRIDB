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
            System.err.println("Exception occurred while processing Building ResultSet.");
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
		boolean user_exists;
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

	public int getID() {
		return id;
	}
	
}