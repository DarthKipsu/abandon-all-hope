
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.logic.ResourceEvents;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LootDistributorTest {
	
	private LootDistributor distributor;
	
	@Before
	public void setUp() {
		distributor = new LootDistributor(new ResourceEvents());
		distributor.setInventory(new Inventory());
	}
	
	@Test
	public void doesntReturnEmptyLoot() {
		assertNotNull(distributor.getLoot());
	}
	
}
