package osaki.mylang.model.adts;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<K, V> implements IMyHeap<K, V> {
    int nextClear = 1;
    HashMap<K, V> map = new HashMap<>();

    @Override
    public void set(K k, V v) {
        map.put(k, v);
    }

    @Override
    public V get(K k) {
        return map.get(k);
    }

    @Override
    public boolean has(K k) {
        return map.containsKey(k);
    }

    @Override
    public void remove(K k) {
        map.remove(k);
    }

    @Override
    public String myString() {
        StringBuilder result = new StringBuilder();
        for(K key : this.map.keySet())
        {
            result.append(key.toString()).append(" : ").append(this.map.get(key).toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public int nextAddress() {
        this.nextClear += 1;
        return this.nextClear - 1;
    }

    @Override
    public void setContent(Map<K, V> content) {
        if (content == null) {
            throw new IllegalArgumentException("New content cannot be null");
        }
        this.map.clear();
        this.map.putAll(content);
    }

    @Override
    public Map<K, V> getContent() {
        return new HashMap<>(this.map);
    }
}
