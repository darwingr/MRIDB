package models;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MRIScanModelTest {

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
	    MRIScanModel scan = MRIScanModel.findByID(2);
	    assertEquals(2, scan.getID());
	}

	@Test
	void testScansForAgeRange() throws SQLException {
		ArrayList<MRIScanModel> scans = MRIScanModel.scansForAgeRange(11, 15);
		// TODO should be 73, Patient.delete() is deleting a visit without recreating it
		assertEquals(73, scans.size());
	}

	@Test
	void testScansForAgeRangeForMales() throws SQLException {
		int male = 1;
		ArrayList<MRIScanModel> scans = MRIScanModel.scansForAgeRange(11, 15, male);
		// TODO 20 might be wrong, Patient.delete() is deleting a visit without recreating it
		assertEquals(20, scans.size());
	}

	@Test
	void testGetMeasurementSubset() throws Exception {
		MRIScanModel scan = MRIScanModel.findByID(2);
		int subset_index[] = { 0, 3, 4787 };
		ArrayList<Double> subset = scan.getMeasurementSubset(subset_index);

		assertEquals(1217852, subset.get(0), 0.001);
		assertEquals(263278.1461, subset.get(1), 0.001);
		assertEquals(150, subset.get(2), 0.001);
	}
}
