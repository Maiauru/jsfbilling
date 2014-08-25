package ru.gooamoko.dao;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.gooamoko.model.Host;

public class HostDaoTest {

	private static HostDao hostDao;

	@BeforeClass
	public static void initDao() {
		hostDao = new HostDao();
	}

	@AfterClass
	public static void freeDao() {
		hostDao = null;
	}

	@Test
	public void testFetchAll() {
		try {
			List<Host> list = hostDao.fetchAll();
			assertNotNull(list);
			// assertFalse(list.isEmpty());
			for (Host h : list) {
				assertNotNull(h);
			}
		} catch (Exception e) {
			fail("Exception throws instead of List<Host>: " + e.getMessage());
		}
	}

	@Test
	public void testDeleteWrong() {
		hostDao.delete(new Host());
	}

	@Test(expected = DaoException.class)
	public void testGetWrong() throws DaoException {
		Host h = hostDao.get(0);
		assertNull(h);
		fail("DaoException expected but not thrown");
	}
}
