package adapters;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBAdapter {
	public ResultSet executeQuery(String sql_query) throws SQLException;
	public int executeCall(String plsql) throws SQLException;
	public void close();
}
