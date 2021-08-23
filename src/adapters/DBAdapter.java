package adapters;

import java.util.Properties;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;

import oracle.jdbc.pool.OracleDataSource;
import oracle.jdbc.OracleConnection;

public class DBAdapter {
	static String DB_USERNAME = System.getProperty("DB_USERNAME");
	static String DB_PASSWORD = System.getProperty("DB_PASSWORD");
	static String DB_SERVICE_NAME = System.getProperty("DB_SERVICE_NAME");
	static String DB_HOST = System.getProperty("DB_HOST");
	static String DB_PORT = System.getProperty("DB_PORT");

	static String DB_URL;


	private OracleDataSource ods;
	private OracleConnection connection;
	private Statement statement;
	private ResultSet result;
	private CallableStatement callable_statement;

	/*
	 * Purpose:
	 * To test if all the environment variables were declared and initialized
	 * when the program first starts to run.
	 * It first checks if the variable is defined as a JVM system properties and then checks if it was
	 * defined as an environment variable if the property was blank.
	 * Thus environment variables override system properties.
	 * Returns a boolean so you can decide to exit the program prematurely.
	 * 
	 * SIDE EFFECTS:
	 *   all env vars are set, final statics on DBAdapter class
	 *   DB_URL is set
	 */
	public static boolean checkEnvironment() {
	    boolean success = true;
	    String var_names[] = {
	        "DB_USERNAME",
	        "DB_PASSWORD",
	        "DB_SERVICE_NAME",
	        "DB_HOST",
	        "DB_PORT"
	    };
	
	    for (String name : var_names){
	        String error_msg = "CONFIGURATION ERROR: the environment variable "
	            + name + " was unset! Setup either environment variables or java system properties before"
	            + " running the program again.";

	        try {
	            Class<DBAdapter> aClass = DBAdapter.class;
	            Field env_var = aClass.getDeclaredField(name);
	            // overwrite property
	            if (System.getenv(name) != null)
	            	env_var.set(aClass, System.getenv(name));
	            if (env_var.get(null) == null || "".equals(env_var.get(null))) {
	                System.err.println(error_msg);
	                success = false;
	            }
	        } catch (Exception e) {
	        		System.err.println(e);
	        		String de = "Missing declaration error: expected " + name + " to be declared on DBAdapter.";
	        		System.err.println(de); 
	            success = false;
	        }
	    }
		DB_URL = "jdbc:oracle:thin:@(DESCRIPTION = "
				+ "(ADDRESS = (HOST = " + DB_HOST + ")(PORT = " + DB_PORT + ")(PROTOCOL = TCP))"
				+ "(CONNECT_DATA = (SERVICE_NAME = " + DB_SERVICE_NAME + "))"
				+ ")";
	    return success;
	}

    /**
	 *
	 *
	 */
	public DBAdapter() throws SQLException {
		Properties info = new Properties();
		info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USERNAME);
		info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);
		info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");
		info.put(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CHECKSUM_TYPES,
		  "(MD5,SHA1,SHA256,SHA384,SHA512)");
		info.put(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CHECKSUM_LEVEL, "REQUIRED");

		ods = new OracleDataSource();
		ods.setURL(DB_URL);
		ods.setConnectionProperties(info);
	}

	/*
	 * Possible relevant outputs:
	 * - array of strings...but may not all be same type
	 * - 
	 */
	public ResultSet executeQuery(String sql_query) throws SQLException {
		// With AutoCloseable, the connection is closed automatically.
		// Statement and ResultSet are AutoCloseable and closed automatically. 
		try {
			connection = (OracleConnection) ods.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(sql_query);
		} catch (Exception e) { e.printStackTrace(); }
		return result;
	}

	/*
	 * Returns an integer returned by a PL/SQL statement.
	 * Useful for fetching IDs generated by the database immediately after a
	 * record gets created.
	 */
	public int executeCall(String plsql) throws SQLException {
		int pid = -1;
		try {
			connection = (OracleConnection) ods.getConnection();
			callable_statement = connection.prepareCall(plsql);
			callable_statement.registerOutParameter(1, java.sql.Types.INTEGER);
			callable_statement.execute();
			pid = (int) callable_statement.getObject(1);
		} catch (Exception e) { e.printStackTrace(); }
		return pid;
	}

	
	public void close() {
        try {
            if (callable_statement != null) callable_statement.close();
        		if (result != null) result.close();
            if (statement != null) statement.close();
            connection.close();
        } catch (Exception e) { e.printStackTrace(); }
	}
	
	public void describeConnection() throws SQLException {
		// With AutoCloseable, the connection is closed automatically.
		OracleConnection connection = (OracleConnection) ods.getConnection();
				
		// Get the JDBC driver name and version 
	    DatabaseMetaData dbmd = connection.getMetaData();       
	    System.out.println("Driver Name: " + dbmd.getDriverName());
	    System.out.println("Driver Version: " + dbmd.getDriverVersion());
	    // Print some connection properties
	    System.out.println("Default Row Prefetch Value is: " + 
	       connection.getDefaultRowPrefetch());
	    System.out.println("Database Username is: " + connection.getUserName());
	    System.out.println();
	}

	public void showEnvironment() {
		System.out.println(DB_USERNAME);
		System.out.println(DB_PASSWORD);
		System.out.println(DB_SERVICE_NAME);
		System.out.println(DB_HOST);
		System.out.println(DB_PORT);

		// Not an environment variable but important to know
		System.out.println(DB_URL);
	}
}