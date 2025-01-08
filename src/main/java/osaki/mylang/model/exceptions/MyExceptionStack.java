package osaki.mylang.model.exceptions;

public class MyExceptionStack extends MyException {
    String msg;
    public MyExceptionStack(String msg) {
        super(msg);
        this.msg = msg;
    }
}
