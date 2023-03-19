package io.ylab.task3.datedMap;

import java.util.Date;
import java.util.Set;

public class DatedMapTest {
    public static void main(String[] args) {
       DatedMapImpl map = new DatedMapImpl();

        //put
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        //get
        String value1 = map.get("key1"); // return "value1"
        String value2 = map.get("key2"); // return "value2"
        String value4 = map.get("key4"); // return null because "key4" is not in map

        System.out.println("value1 = " + value1);
        System.out.println("value2 = " + value2);
        System.out.println("value4 = " + value4);
        System.out.println();

        //containsKey
        boolean containsKey1 = map.containsKey("key1"); // return true
        boolean containsKey4 = map.containsKey("key4"); // return false

        System.out.println("containsKey1 = " + containsKey1);
        System.out.println("containsKey4 = " + containsKey4);
        System.out.println();

        //remove
        map.remove("key1");
        boolean containsKey1AfterRemove = map.containsKey("key1"); // return false
        boolean containsKey2AfterRemove = map.containsKey("key2"); // return true

        System.out.println("containsKey1AfterRemove = " + containsKey1AfterRemove);
        System.out.println("containsKey2AfterRemove = " + containsKey2AfterRemove);
        System.out.println();

        //keySet
        Set<String> keySet = map.keySet(); // return {"key2", "key3"} because "key1" was removed

        System.out.println("keySet = " + keySet);
        System.out.println();

        //getKeyLastInsertionDate
        Date date1 = map.getKeyLastInsertionDate("key1"); // return null because "key1" was removed
        Date date2 = map.getKeyLastInsertionDate("key2"); // date of insertion of "key2"
        Date date4 = map.getKeyLastInsertionDate("key4"); // return null because "key4" is not in map

        System.out.println("date1 = " + date1);
        System.out.println("date2 = " + date2);
        System.out.println("date4 = " + date4);
        System.out.println();
    }
}

