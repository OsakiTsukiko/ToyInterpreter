package osaki.mylang.model.adts;

import osaki.mylang.model.exceptions.MyExceptionList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyList<T> implements IMyList<T> {
    List<T> list = new ArrayList<T>();


    @Override
    public boolean add(T elem) {
        return list.add(elem);
    }

    @Override
    public T get(int index) throws MyExceptionList {
        if (index >= list.size()) throw new MyExceptionList("Index out of bounds!!1");
        return this.list.get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for(T element : this.list)
            result.append(element.toString()).append(", ");
        result.append("]");
        return result.toString();
    }

    @Override
    public String myString() {
        StringBuilder result = new StringBuilder();
        for(T element : this.list)
            result.append(element.toString()).append("\n");
        return result.toString();
    }
}
