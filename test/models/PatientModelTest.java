/**
 * 
 */
package models;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;

import adapters.DBAdapter;

/**
 * @author darwingroskleg
 *
 */
class PatientModelTest {

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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	// Broken example of how to test a unit
	@Test
	void fullNameTest() {
		fail("Not yet implemented");
		PatientModel p = new PatientModel("John", "Smith");
		assertEquals(p.fullName(), "John Smith");
	}
	
	@Test
	void testcreate() throws SQLException {
		DBAdapter db = new DBAdapter();
		boolean userExists = false;
		String sql = 
				"SELECT count(first_name), count(last_name)\n" + 
				"FROM users " + 
				"WHERE first_name = '" + first_name + "' " + 
				"AND last_name = '" + last_name + "';";
		try (ResultSet rs = db.executeQuery(sql)) {
			userExists = rs.next();
		}
		boolean success = false;
		if(!userExists) {
			String sql1 = "INSERT INTO patients " + 
						"VALUES (" + first_name + ", " + last_name + ");";
			//display message on gender that lets user know 1=male, 2=female, 3=N/A
			System.out.println("1=male, 2=female, 3=N/A");
			String sql2 = "INSERT INTO visits " + 
						"VALUES (" + gender + ", " + dob + ", " + check_in + ");";
			try (ResultSet rs = db.executeQuery(sql1)) {
				success = rs.next();
			}
			try (ResultSet rs = db.executeQuery(sql2)) {
				success = rs.next();
			}finally {
				db.close();
			}
		}
		assertTrue(success);
	}

}
