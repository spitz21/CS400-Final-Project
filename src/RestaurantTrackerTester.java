
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestaurantTrackerTester {
	RestaurantTracker test;
	
	@BeforeEach
	public void populateData() {
		test = new RestaurantTracker();
		test.populate();
	}
	
	
	@Test
	public void testInsert() {
		Restaurant testR = new Restaurant("testR");
		assertEquals(testR.toString().contains("testR"), true);
				
	}
	
	@Test
	public void testDuplicate() {
		assertEquals(test.createNewRestaurant("Arbys"), false);
	}
	
	@Test
	public void testSearch() {
		assertEquals(test.search("arbys"), true);
	}
	
	@Test
	public void testRemove() {
		Restaurant remove = test.search("Arbys");
		test.remove(remove);
		assertEquals(test.search("arbys"), true);
	}
	
	@Test
	public void testExpand() {
		test.expandData();
		assertEquals(test.data.length, 202);
	}
	
	@Test
	public void testDisplay() {
		Restaurant testR = new Restaurant("testR");
		testR.rating = 5.0;
		testR.location = "here";
		
		String expected = "testR\nPersonal rating: 5.0 / 10.0\nLocated: here\n";
		assertEquals(testR.display().equals(expected), true);
		assertEquals(test.toString().contains(expected), true);
	}
	
	@Test
	public void testSortResults() {
		test.data = test.foodSort();
	}
	

}
