package osaki.mylang.model.adts;

import osaki.mylang.model.exceptions.MyExceptionDict;
import osaki.mylang.model.values.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements IMyDictionary<K, V> {
    HashMap<K, V> dict = new HashMap<K, V>();

    public MyDictionary() {}

    public MyDictionary(HashMap<K, V> dict) {
        this.dict = dict;
    }

    @Override
    public V get(K key) throws MyExceptionDict {
        if (dict.containsKey(key)) {
            return dict.get(key);
        }
        throw new MyExceptionDict("Key not in Dictionary");
    }

    @Override
    public V set(K key, V value) {
        return dict.put(key, value);
    }

    @Override
    public void remove(K key) {
        dict.remove(key);
    }

    @Override
    public boolean has(K key) {
        if (key instanceof IValue) {
            for (K k : this.dict.keySet()) {
                IValue v = (IValue) k;
                if (v.equals((IValue) key)) return true;
            }
            return false;
        }
        return dict.containsKey(key);

    }

    @Override
    public int size() {
        return dict.size();
    }

    @Override
    public boolean isEmpty() {
        return dict.isEmpty();
    }

    @Override
    public void clear() {
        dict.clear();
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append("{");
        for(K key : this.dict.keySet())
        {
            result.append(key.toString()).append(" : ").append(this.dict.get(key).toString()).append(", ");
        }
        result.append("}");
        return result.toString();
    }

    @Override
    public String myString() {
        StringBuilder result = new StringBuilder();
        for(K key : this.dict.keySet())
        {
            result.append(key.toString()).append(" --> ").append(this.dict.get(key).toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public String myKeyString() {
        StringBuilder result = new StringBuilder();
        for(K key : this.dict.keySet())
        {
            result.append(key.toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public Map<K, V> getContent() {
        return new HashMap<>(this.dict);
    }

    @Override
    public IMyDictionary<K, V> deepCopy() {
        MyDictionary<K, V> newDictionary = new MyDictionary<>();

        for(K key : this.dict.keySet())
            newDictionary.set(key, this.dict.get(key));
        // TODO: MAYBE ADD DEEPCOPY?

        return newDictionary;
    }
}
