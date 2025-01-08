package osaki.mylang.model.adts;

import osaki.mylang.model.exceptions.MyExceptionDict;

import java.util.Map;

public interface IMyDictionary<K, V> {
    V get(K key) throws MyExceptionDict;
    V set(K key, V value);
    void remove(K key);
    boolean has(K key);
    int size();
    boolean isEmpty();
    void clear();
    String toString();
    String myString();
    String myKeyString();

    Map<K, V> getContent();
    IMyDictionary<K, V> deepCopy();
}
