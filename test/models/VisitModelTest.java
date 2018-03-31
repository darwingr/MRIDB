package models;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VisitModelTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFindByID() throws SQLException {
	    VisitModel visit = VisitModel.findByID(2);
	    assertEquals(2, visit.getID());
	}
	
	@Test
	void testCreate() throws SQLException {
		Date date1 = new Date(1979-1900, 06-1, 30);
		VisitModel visit = new VisitModel(1, date1);
		assertTrue(visit.create());
		
	}

}
