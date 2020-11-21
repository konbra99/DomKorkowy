package entity;

import logic.Entity;
import logic.TestRect;
import logic.EntityFactory;
import map.exceptions.NoexistentType;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntityFactoryTest {

	//@Test
	public void ExistingEntityFactoryTest() {
		try {
			Entity obj = EntityFactory.fromName("TestRect");
			assertEquals(obj.getClass(), TestRect.class);
		} catch (Exception e) {
			fail();
		}

	}

	//@Test
	public void NotExistingEntityFactoryTest() {
		try {
			Entity obj = EntityFactory.fromName("NieistniejacyElement");
			fail();

		} catch (NoexistentType e) {
			assertTrue(true);
		}

	}
}
