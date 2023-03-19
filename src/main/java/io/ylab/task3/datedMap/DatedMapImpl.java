package io.ylab.task3.datedMap;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class DatedMapImpl implements DatedMap{
    private HashMap<String, String> map;
    private HashMap<String, Date> dateMap;

    public DatedMapImpl() {
        this.map = new HashMap<>();
        this.dateMap = new HashMap<>();
    }

    @Override
    public void put(String key, String value) {
        map.put(key, value);
        dateMap.put(key, new Date());
    }

    @Override
    public String get(String key) {
        return map.get(key);
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
        dateMap.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        if (map.containsKey(key)) {
            return dateMap.get(key);
        } else {
            return null;
        }
    }
}
