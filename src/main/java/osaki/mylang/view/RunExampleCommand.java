package osaki.mylang.view;

import osaki.mylang.controller.Controller;
import osaki.mylang.model.exceptions.MyException;

public class RunExampleCommand extends Command {
    private Controller ctr;

    public RunExampleCommand(String key, String desc, Controller ctr) {
        super(key, desc);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try {
            ctr.typeCheck();
            ctr.allStep();
        } catch (MyException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
