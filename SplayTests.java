import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Vector;

public class SplayTests {

    @Test
    public void testSimpleInsert() {
        SplaySymbolTable<Integer, Integer> tree = new SplaySymbolTable<>();
        String[] soln = new String[]{"6:black", "5:black", "4:black", null,  null, null, null};
        Vector<String> solnVector = new Vector<String>(Arrays.asList(soln));

        tree.insert(5, 5);
        tree.insert(4, 4);
        tree.insert(6, 6);

        Vector<String> st = tree.serialize();
        assertEquals(solnVector, st);
    }

    @Test
    public void test12349876(){
        SplaySymbolTable<Integer, Integer> tree = new SplaySymbolTable<>();
        String[] soln = new String[]{"6:black", "4:black", "3:black", "2:black",  "1:black",
                null, null, null, null, null,
                "7:black", null, "8:black", null, "9:black", null, null};
        Vector<String> solnVector = new Vector<String>(Arrays.asList(soln));
        Integer[] input = new Integer[]{1,2,3,4,9,8,7,6};

        for(Integer i: input) {
            tree.insert(i, i);
        }

        Vector<String> st = tree.serialize();
        assertEquals(solnVector, st);
    }

    @Test
    public void test12349876withSearch(){
        SplaySymbolTable<Integer, Integer> tree = new SplaySymbolTable<>();
        String[] soln = new String[]{"7:black", "3:black", "2:black", "1:black",
                null, null, null,
                "6:black", "4:black",
                null, null, null,
                "9:black", "8:black", null, null, null};
        Vector<String> solnVector = new Vector<String>(Arrays.asList(soln));
        Integer[] input = new Integer[]{1,2,3,4,9,8,7,6};

        for(Integer i: input) {
            tree.insert(i, i);
        }

        tree.search(3);
        tree.search(9);
        tree.search(7);

        Vector<String> st = tree.serialize();
        assertEquals(solnVector, st);
    }


    @Test
    public void testBigTree() {
        SplaySymbolTable<Integer, Integer> tree = new SplaySymbolTable<Integer, Integer>();

        for (int i = 0; i < 100; i++) {
            tree.insert(i, i);
        }

        Vector<String> st = tree.serialize();

        String[] root = st.get(0).split(":");
        assertEquals(root[0], "99"); // was inserted last, should be root

        int counter = 0;
        for(String node : st) {
            if (node == null) break;
            counter++;
        }
        assertEquals(counter, 100); // there should be a run of 100 nodes

    }


    @Test
    /* Assuming single search works, this should only fail if you're losing nodes */
    public void testManyPresentSearches(){
        SplaySymbolTable<Integer, Integer> tree = new SplaySymbolTable<Integer, Integer>();
        for (int i = 0; i < 100; i++) {
            tree.insert(i, i);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals((long) tree.search(i), i);
        }

        Vector<String> st = tree.serialize();

        String[] root = st.get(0).split(":");
        assertEquals(root[0], "99"); // was searched last, should be root

        int counter = 0;
        for(String node : st) {
            if (node == null) break;
            counter++;
        }
        assertEquals(counter, 100); // there should be a run of 100 nodes

        /*
         * it turns out this case produces a left-leaning from 99 to 0 after the
         * inserts, which is disrupted and the reconstructed by the searches
         */
    }
}
