package osaki.mylang.model.exceptions;

public class MyExceptionDict extends MyException {
    String msg;
    public MyExceptionDict(String msg) {
        super(msg);
        this.msg = msg;
    }
}
