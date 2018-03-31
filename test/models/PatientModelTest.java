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

	@Test
	void testFindByID() throws SQLException {
		PatientModel p = PatientModel.findByID(2);
		assertEquals(2, p.getID());
	}

	@Test
	void testFullName() {
		PatientModel p = new PatientModel("John", "Smith");
		assertEquals("John Smith", p.fullName());
	}
	
	@Test
	void testCreateDelete() throws SQLException {
		PatientModel p = new PatientModel();
		assertTrue(p.create());

		// Cleanup
		int id = p.getID();
		assertTrue(p.delete());

		// Double check
		PatientModel p2 = PatientModel.findByID(id);
		assertNotEquals(id, p2.getID());
	}

	
}
