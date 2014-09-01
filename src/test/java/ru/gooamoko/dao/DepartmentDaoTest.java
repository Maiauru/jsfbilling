package ru.gooamoko.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.gooamoko.model.Department;

public class DepartmentDaoTest {
	private static DepartmentDao dao;

	@BeforeClass
	public static void initDao() {
		dao = new DepartmentDao();
	}

	@AfterClass
	public static void freeDao() {
		dao = null;
	}

	@Test
	public void testFetchAll() {
		System.out.println("======> testFetchAll()");
		try {
			List<Department> list = dao.fetchAll();
			assertNotNull(list);
			// assertFalse(list.isEmpty());
			for (Department g : list) {
				assertNotNull(g);
			}
		} catch (Exception e) {
			fail("Exception throws instead of List<Host>: " + e.getMessage());
		}
	}

	@Test
	public void testDeleteWrong() {
		System.out.println("======> testDeleteWrong()");
		dao.delete(new Department());
	}

	@Test(expected = DaoException.class)
	public void testGetWrong() throws DaoException {
		System.out.println("testGetWrong()");
		Department g = dao.get(0);
		assertNull(g);
		fail("DaoException expected but not thrown");
	}
	
	@Test
	public void testCreateSaveDelete() {
		try {
			System.out.println("======> testCreateSaveDelete()");
			DepartmentDao gd = new DepartmentDao();
			Department grp = new Department();
			grp.setName("Test group");
			gd.save(grp);
			System.out.println("======> Group with id " + grp.getId() + " saved!");
			gd.delete(grp);
			System.out.println("======> Group with id " + grp.getId() + " deleted!");
		} catch (Exception e) {
			fail("Exception " + e.getClass().getName() + " is thrown with message " + e.getMessage());
		}
	}

}
