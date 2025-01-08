package osaki.mylang.model.exceptions;

public class MyException extends Exception {
    String msg;
    public MyException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
