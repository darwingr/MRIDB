package adapters;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.jdbc.pool.OracleDataSource;
import oracle.jdbc.OracleConnection;
import java.sql.DatabaseMetaData;


/**
 *
 *
 */
public class DBAdapter {
	final static String DB_SERVICE_NAME = System.getenv("DB_SERVICE_NAME");
	final static String DB_HOST = System.getenv("DB_HOST");
	final static String DB_PORT = System.getenv("DB_PORT");
	final static String DB_URL = "jdbc:oracle:thin:@(DESCRIPTION = "
			+ "(ADDRESS = (HOST = " + DB_HOST + ")(PORT = " + DB_PORT + ")(PROTOCOL = TCP))"
			+ "(CONNECT_DATA = (SERVICE_NAME = " + DB_SERVICE_NAME + "))"
			+ ")";
	final static String DB_USERNAME = System.getenv("DB_USERNAME");
	final static String DB_PASSWORD = System.getenv("DB_PASSWORD");
	
	//private OracleConnection connection;
	private OracleDataSource ods;
	
	private OracleConnection connection;
	private Statement statement;
	private ResultSet result;

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
	
	public void close() {
        try {
        		result.close();
            statement.close();
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

	/*
	 * Should return an exception if certain variables aren't defined
	 */
	public void showEnvironment() {
		System.out.println(DB_HOST);
		System.out.println(DB_PORT);
		System.out.println(DB_URL);
		System.out.println(DB_USERNAME);
		System.out.println(DB_PASSWORD);
	}
}
