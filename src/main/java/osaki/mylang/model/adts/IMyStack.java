package osaki.mylang.model.adts;

import osaki.mylang.model.exceptions.MyExceptionStack;

import java.util.Stack;

public interface IMyStack<T> {
    T pop() throws MyExceptionStack;
    void push(T v);
    boolean isEmpty();
    String toString();
    String myString();
    Stack<T> getContent();
}
