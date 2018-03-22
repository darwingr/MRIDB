/**
 * 
 */
package adapters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

/**
 * Tests the DBAdapter object, mostly by printing data for visual inspection than by assertions.
 */
class DBAdapterTest {
	DBAdapter db;

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
		db = new DBAdapter();
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
		try (ResultSet rs = db.executeQuery("SELECT * FROM diagnoses WHERE id = " + 2)) {
			rs.next();	// Requires next() be called at least once
			result_id = rs.getInt("id");

		} finally {
			db.close();
		}
		assertEquals(2, result_id);
	}
	
	@Test
	void testDescribeConnection() throws Exception {
		db.describeConnection();
	}
}
