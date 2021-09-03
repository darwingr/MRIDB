package adapters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.CallableStatement;
import java.sql.ResultSet;

/**
 * Tests the OracleDBAdapter object, mostly by printing data for visual inspection
 * than by assertions.
 */
class OracleDBAdapterTest {
	OracleDBAdapter db;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		db = new OracleDBAdapter();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testShowEnvironment() {
		db.showEnvironment();
	}

	@Test
	void testExecuteQuery() throws Exception {
		int result_id;
        String sql = "SELECT * FROM diagnoses WHERE id = " + 2;
		try (ResultSet rs = db.executeQuery(sql)) {
			rs.next();	// Requires next() be called at least once
			result_id = rs.getInt("id");

		} finally {
			db.close();
		}
		assertEquals(2, result_id);
	}

	@Test
	void testExecuteCall() throws Exception {
		String plsql =
				"DECLARE \n" +
				"  a NUMBER := 5; \n" +
				"  PROCEDURE squared(" +
				"    x NUMBER \n" +
				") IS \n" +
				"BEGIN \n" +
				"  ? := x * x; " +
				"END;\n" +
				"BEGIN \n" +
				"  squared(a);\n" +
				"END;";
		int num;
		try {
			num = db.executeCall(plsql);
		} finally {
			db.close();
		}
		assertEquals(25, num);
	}
	
	@Test
	void testDescribeConnection() throws Exception {
		db.describeConnection();
	}
}
