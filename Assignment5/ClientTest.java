import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ClientTest {
	@Test(expected=IllegalArgumentException.class)
    public void testNullArrayInput() {
        Client client = new Client();
        String[] ids = null;
        client.get(ids);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContainsNullString() {
        Client client = new Client();
        String[] ids = new String[] {"1234", null};
        client.get(ids);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContainsEmptyString() {
        Client client = new Client();
        String[] ids = new String[] {"1234", ""};
        client.get(ids);
    }

    @Test(expected=IllegalStateException.class)
    public void testFailureToConnectToServer() {
        Client client = new Client("localhost", 2000);
        String[] ids = new String[] {"1234", "5678"};
        client.get(ids);
    }

    @Test(expected=IllegalStateException.class)
    public void testProcessingError() {
        // modify the index.js file to return something that is not an array
        // if the index.js file returns something that is not an array then this test passes
        Client client = new Client("localhost", 3000); 
        String[] ids = new String[] {"1234", "5678"};
        client.get(ids);
    }

    @Test 
    public void testEmptyArrayInput() {
        Set<Person> set = new HashSet<>();
        Client client = new Client();
		String[] ids = new String[]{};
        assertEquals(set, client.get(ids));
    }

    @Test 
    // the index.js will return an empty json array if there is no id 
    public void testEmptyJsonArray() {
        Set<Person> set = new HashSet<>();
        Client client = new Client("localhost", 3000); 
        String[] ids = new String[] {"1234", "5678"};
        client.get(ids);
        assertEquals(set, client.get(ids));
    }
}
