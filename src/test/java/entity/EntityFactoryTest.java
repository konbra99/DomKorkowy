package entity;

import entity.entities_tree.Entity;
import entity.entities_tree.TestRect;
import entity.entities_utils.EntityFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EntityFactoryTest {

	@Test
	public void ExistingEntityFactoryTest() {
		Entity obj = EntityFactory.fromName("TestRect");
		assertEquals(obj.getClass(), TestRect.class);
	}

	@Test
	public void NotExistingEntityFactoryTest() {
		Entity obj = EntityFactory.fromName("NieistniejacyElement");
		assertEquals(obj, null);
	}
}
