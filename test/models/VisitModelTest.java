package models;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
<<<<<<< HEAD
=======
import java.util.ArrayList;
>>>>>>> branch 'master' of https://github.com/ThreeFourSeven/Database-Gui.git
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
<<<<<<< HEAD
	
	@Test
	void testCreate() throws SQLException {
		Date date1 = new Date(1979-1900, 06-1, 30);
		VisitModel visit = new VisitModel(1, date1);
		assertTrue(visit.create());
		
=======
 
	@Test
	void testFindByPatientID() throws SQLException {
		// For whatever reason, patient_id and visit_id just happened to be
		// matched on the same number.
		ArrayList<VisitModel> visits = VisitModel.findByPatientID(2);

		assertTrue(!visits.isEmpty());

		visits.forEach(v->{
			assertEquals(2, v.getPatientID());
		});
	}

	@Test
	void testCreateDelete() throws SQLException {
		Date date = new Date(1979-1900, 06-1, 30);
		VisitModel visit = new VisitModel(1, date);
		assertTrue(visit.create(2));
		System.out.println(visit.getID());

		int id = visit.getID();
		assertTrue(visit.delete());

		VisitModel v2 = VisitModel.findByID(id);
		assertNotEquals(id, v2.getID());
>>>>>>> branch 'master' of https://github.com/ThreeFourSeven/Database-Gui.git
	}

}
