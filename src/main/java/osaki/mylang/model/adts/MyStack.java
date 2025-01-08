package osaki.mylang.model.adts;

import osaki.mylang.model.exceptions.MyExceptionStack;

import java.util.Stack;

public class MyStack<T> implements IMyStack<T> {
    Stack<T> stack = new Stack<T>();

    public MyStack() {

    }

    @Override
    public T pop() throws MyExceptionStack {
        if (stack.empty()) throw new MyExceptionStack("Stack is empty!");
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for(T element : this.stack) {
            result.append(element.toString()).append(", ");
        }
        result.append("]");
        return result.toString();
    }

    @Override
    public String myString() {
        StringBuilder result = new StringBuilder();
        /* for(T element : this.stack) {
            result.append(element.toString()).append("\n");
        } */
        for (int i = this.stack.size() - 1; i >= 0; i--) {
            T element = this.stack.get(i);
            result.append(element.toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public Stack<T> getContent() {
        return stack;
    }
}
