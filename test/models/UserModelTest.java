package models;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserModelTest {

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
		UserModel user = UserModel.findByID(2);
		
		assertEquals(2, user.getID());
	}

	@Test
	void testFullName() throws SQLException {
		UserModel user = UserModel.findByID(2);
		String name = user.fullName();
		assertEquals("Darwin Groskleg", name);
	}

	@Test
	void testAuthenticatePasses() throws SQLException {
		UserModel user = new UserModel();
		assertTrue(user.authenticate("dgroskleg", "csci275"));
	}

	@Test
	void testAuthenticateFails() throws SQLException {
		UserModel user = new UserModel();
		assertTrue(!user.authenticate("dgroskleg", "XXX"));
	}
	
	/*
	@Test
	void testFetchAttributes() throws SQLException {
		assertTrue()
	}*/

}
