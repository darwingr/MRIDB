package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import adapters.DBAdapter;
import adapters.OracleDBAdapter;

public class UserModel extends ActiveRecord {
	private static final String TABLE_NAME = "users";

	public class LoginRequest {
		private boolean loggedIn;
		private int     userID;

		public LoginRequest() {
			loggedIn = false;
			userID = -1;
		}

		public boolean isLoggedIn() {
			return loggedIn;
		}

		public int getUserID() {
			return userID;
		}

		public void setLoggedIn(boolean success) {
			loggedIn = success;
		}

		public void setID(int id) {
			userID = id;
		}
	}

	private int     id;
	private String  username;
	private String  password;
	private String  first_name;
	private String  last_name;
	private String  email;
	private boolean hipaa_authorized;

	public static UserModel findByID(int user_id) throws SQLException {
		UserModel user = new UserModel();
		DBAdapter db = new OracleDBAdapter();
		try (ResultSet rs = db.executeQuery("select * from " + user.table() + " where id = " + user_id)) {
			if (rs.next()) {
				user.id = rs.getInt("id");
				user.username = rs.getString("username");
				user.password = rs.getString("password");
				user.first_name = rs.getString("first_name");
				user.last_name = rs.getString("last_name");
				user.email = rs.getString("email");
				user.hipaa_authorized = rs.getBoolean("hipaa_authorized");
			}
		} catch (SQLException sqle) {
            System.err.println("Exception occurred while processing Building ResultSet after findByID.");
		} finally {
			db.close();
		}
		return user;
	}

	public UserModel() {
		
	}

	public UserModel(String fname, String lname, String email_addr, String passwrd, boolean authorized) {
		first_name = fname;
		last_name = lname;
		username = fname.toLowerCase().charAt(0) + lname.toLowerCase();
		password = passwrd;
		email = email_addr;
		hipaa_authorized = authorized;
	}

    @Override
	public String table() { return "users"; }

	public String fullName() {
		return first_name + " " + last_name;
	}

	public LoginRequest authenticate(String user_username, String user_password) throws SQLException {
		DBAdapter db = new OracleDBAdapter();
		String sql = "select * from users where username = '" + user_username
				+ "' AND password = '" + user_password + "'";
		boolean user_exists = false;
		try (ResultSet rs = db.executeQuery(sql)) {
			user_exists = rs.next();
		} finally {
			db.close();
		}
		LoginRequest lr = new LoginRequest();
		lr.setLoggedIn(user_exists);
		UserModel user = new UserModel();
		try (ResultSet rs = db.executeQuery("select id from " + user.table() + " where username = " + "'" + user_username + "'" )) {
			if (rs.next()) {
				user.id = rs.getInt("id");
			}
		} catch (SQLException sqle) {
            System.err.println("Exception occurred while processing Building ResultSet after findByID.");
		} finally {
			db.close();
		}
		lr.setID(user.id);
		return lr;
	}

	public boolean isAuthorized() {
		return hipaa_authorized;
	}


	public boolean changePassword(String new_pword) throws SQLException {
		setPassword(new_pword);
		return save();
	}

	/*
	 * Will generate an id automatically
	 */
	public boolean create() throws SQLException {
		boolean success = false;
		DBAdapter db = new OracleDBAdapter();
		String plsql = 
				"DECLARE \n" +
				"  rec_id NUMBER; \n" +
				"BEGIN \n" +
				"  INSERT INTO "+ table() +" \n" +
				"    (first_name, last_name, username, email, password, hipaa_authorized) \n" +
				"    VALUES \n" +
				"    ('" + first_name + "', '" + last_name + "', '" + username + "', '"
					 	 + email + "', '" + password + "', " + (hipaa_authorized ? 1 : 0) + ")\n" +
				"    RETURNING id INTO rec_id; \n" +
				"  ? := rec_id; \n" +
				"END; \n";

		try {
			int rec_id = db.executeCall(plsql);
			if (rec_id != -1) {
				success = true;
				id = rec_id;
			}
		} catch (Exception sqle) {
            System.err.println("Exception occurred while processing returning the new record id.");
		}  finally {
			db.close();
		}
		return success;
	}


	// Because we don't want to be publicly getting and setting attributes, which breaks encapsulation,
	// we as a result don't need save() to be a public method. If you think you need to make this public
	// then try instead to implement your code as a method on this class and call save() from there.
	// Note that we don't save or change ID, ever.
	private boolean save() throws SQLException {
		DBAdapter db = new OracleDBAdapter();
		String sql = "UPDATE "+TABLE_NAME+"\n" +
					 "SET username = '" + username + "', \n" +
					 "    password = '" + password + "', \n" +
					 "    first_name = '" + first_name + "', \n" +
					 "    last_name = '" + last_name + "', \n" +
					 "    email = '" + email + "', \n" +
					 "    hipaa_authorized = " + (hipaa_authorized ? 1 : 0) + " \n" +
					 "WHERE id = " + id;
		boolean success = false;
		try (ResultSet rs = db.executeQuery(sql)) {
			success = rs.next();
		} finally {
			db.close();
		}
		return success;
	}

    @Override
	public int getID() { return id; }

    public String getUsername() {
    		return username;
    }

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	// Use the change password method
	private boolean setPassword(String pword) {
		password = pword;
		return true;
	}
}
