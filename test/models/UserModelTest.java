package models;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapters.DBAdapter;

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
	
	@Test
	void testDeleteThenCreate() throws SQLException {
		UserModel user = UserModel.findByID(3);
		UserModel clone = UserModel.findByID(3);

		assertTrue(user.delete());
		
		// Cleanup: tests create()
		assertTrue(clone.create());

		// NASTY cleanup, so we can keep testing on 1
		DBAdapter db = new DBAdapter();
		String sql =
				"UPDATE users \n" +
				"SET id = 3 \n" +
				"WHERE id = " + clone.getID();
		try (ResultSet rs = db.executeQuery(sql)) {
		} finally {
			db.close();
		}
	}

	@Test
	void testChangePassword() throws SQLException {
		UserModel user = UserModel.findByID(2);
		String old_pword = user.getPassword();

		String pword = "password";
		assertTrue(user.changePassword(pword));
		
		// cleanup
		assertTrue(user.changePassword(old_pword));
	}

	/*
	@Test
	void testFetchAttributes() throws SQLException {
		assertTrue()
	}*/

}
