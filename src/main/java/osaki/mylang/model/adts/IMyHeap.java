package osaki.mylang.model.adts;

import java.util.Map;

public interface IMyHeap<K, V> {
    void set(K k, V v);
    V get(K k);
    boolean has(K k);
    void remove(K k);

    String myString();

    int nextAddress();

    void setContent(Map<K, V> content);
    Map<K, V> getContent();
}
