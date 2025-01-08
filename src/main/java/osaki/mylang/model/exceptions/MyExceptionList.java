package osaki.mylang.model.exceptions;

public class MyExceptionList extends MyException {
    String msg;
    public MyExceptionList(String msg) {
        super(msg);
        this.msg = msg;
    }
}
