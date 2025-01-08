package osaki.mylang.model.adts;

import osaki.mylang.model.exceptions.MyExceptionList;

import java.util.Iterator;

public interface IMyList<T> extends Iterable<T> {
    boolean add(T elem);
    T get(int index) throws MyExceptionList;
    Iterator<T> iterator();

    String toString();
    String myString();
}
