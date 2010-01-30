package nu.balp.test;

import java.util.Iterator;

import nu.balp.UserMap;
import junit.framework.TestCase;

public class UserMapTest extends TestCase {
	String key1 = "fe0f8dbc-1b19-4301-bea4-c65cf0cc8f20";
	String name1 = "Balp Allen";
	String clientUrl1 = "http://sim8078.agni.lindenlab.com:12046/cap/bbf0e429-67af-53d1-7345-926ff058c7b7";
	String key2 = "ee0f8dbc-1b19-4301-bea4-c65cf0cc8f20";
	String name2 = "Balpina Allen";
	String clientUrl2 = "http://sim8079.agni.lindenlab.com:12046/cap/bbf0e429-67af-53d1-7345-926ff058c7b7";
	
	private UserMap db;

	public void setUp()
	{
		db = new UserMap();
		db.updateUrl(key1, name1, clientUrl1);
		db.updateUrl(key2, name2, clientUrl2);
	}

	public void testUpdateUrl() {
		assertEquals(clientUrl1, db.getUrl(key1));
		
	}

	public void testUpdate2Urls() {


		assertEquals(clientUrl1, db.getUrl(key1));
		assertEquals(clientUrl2, db.getUrl(key2));
		
	}
	public void testGetNames() {
		Iterator<String> it = db.keyIterator();
		while(it.hasNext()) {
			String key = it.next();
			String name = db.getName(key);
			String url = db.getUrl(key);
		} 
		
	}
	
}
