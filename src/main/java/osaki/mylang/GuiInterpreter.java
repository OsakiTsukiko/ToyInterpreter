package osaki.mylang;

import javafx.application.Application;
import javafx.stage.Stage;
import osaki.mylang.gui.ProgramList;
import osaki.mylang.model.exp.*;
import osaki.mylang.model.stmt.*;
import osaki.mylang.model.types.BoolType;
import osaki.mylang.model.types.IntType;
import osaki.mylang.model.types.RefType;
import osaki.mylang.model.types.StringType;
import osaki.mylang.model.values.BoolValue;
import osaki.mylang.model.values.IntValue;
import osaki.mylang.model.values.StringValue;

import java.util.ArrayList;
import java.util.List;

public class GuiInterpreter extends Application {

    final ArrayList<IStmt> program_list = new ArrayList<IStmt>(List.of(
            new CompStmt(
                    new CompStmt(
                            new CompStmt(
                                    new CompStmt(
                                            new VarDeclStmt("fn1", new StringType()),
                                            new VarDeclStmt("fn2", new StringType())
                                    ),
                                    new CompStmt(
                                            new AssignStmt("fn1", new ValueExp(new StringValue("test_file_1.in"))),
                                            new AssignStmt("fn2", new ValueExp(new StringValue("test_file_2.in")))
                                    )
                            ),
                            new CompStmt(
                                    new VarDeclStmt("i1", new IntType()),
                                    new VarDeclStmt("i2", new IntType())
                            )
                    ),
                    new CompStmt(
                            new CompStmt(
                                    new OpenRFileStmt(new VarExp("fn1")),
                                    new OpenRFileStmt(new VarExp("fn2"))
                            ),
                            new CompStmt(
                                    new CompStmt(
                                            new ReadFileStmt(new VarExp("fn1"), "i1"),
                                            new ReadFileStmt(new VarExp("fn2"), "i2")
                                    ),
                                    new CompStmt(
                                            new CompStmt(
                                                    new CloseRFileStmt(new VarExp("fn1")),
                                                    new CloseRFileStmt(new VarExp("fn2"))
                                            ),
                                            new CompStmt(
                                                    new PrintStmt(new VarExp("i1")),
                                                    new PrintStmt(new VarExp("i2"))
                                            )
                                    )
                            )
                    )
            ),

            new CompStmt(
                    new VarDeclStmt("myVar", new RefType(new IntType())),
                    new CompStmt(
                            new newStmt("myVar", new ValueExp(new IntValue(20))),
                            new CompStmt(
                                    new VarDeclStmt("my2ndVar", new RefType(new RefType(new IntType()))),
                                    new newStmt("my2ndVar", new VarExp("myVar"))
                            )
                    )
            ),

            new CompStmt(
                    new VarDeclStmt("myVar", new RefType(new IntType())),
                    new CompStmt(
                            new newStmt("myVar", new ValueExp(new IntValue(20))),
                            new PrintStmt(new ReadHeapExp(new VarExp("myVar")))
                    )
            ),

            new CompStmt(
                    new VarDeclStmt("myVar", new RefType(new IntType())),
                    new CompStmt(
                            new newStmt("myVar", new ValueExp(new IntValue(20))),
                            new CompStmt(
                                    new PrintStmt(new ReadHeapExp(new VarExp("myVar"))),
                                    new CompStmt(
                                            new heapWriteStmt("myVar", new ValueExp(new IntValue(25))),
                                            new PrintStmt(new ReadHeapExp(new VarExp("myVar")))
                                    )
                            )
                    )
            ),

            new CompStmt(
                    new VarDeclStmt("myVar", new RefType(new IntType())),
                    new CompStmt(
                            new newStmt("myVar", new ValueExp(new IntValue(20))),
                            new CompStmt(
                                    new PrintStmt(new ReadHeapExp(new VarExp("myVar"))),
                                    new CompStmt(
                                            new heapWriteStmt("myVar", new ValueExp(new IntValue(25))),
                                            new CompStmt(
                                                    new PrintStmt(new ReadHeapExp(new VarExp("myVar"))),
                                                    new CompStmt(
                                                            new newStmt("myVar", new ValueExp(new IntValue(50))),
                                                            new CompStmt(
                                                                    new PrintStmt(new ReadHeapExp(new VarExp("myVar"))),
                                                                    new CompStmt(
                                                                            new NopStmt(),
                                                                            new NopStmt()
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            ),

            new CompStmt(
                    new VarDeclStmt("myVar", new RefType(new IntType())),
                    new CompStmt(
                            new newStmt("myVar", new ValueExp(new IntValue(20))),
                            new CompStmt(
                                    new VarDeclStmt("my2ndVar", new RefType(new RefType(new IntType()))),
                                    new CompStmt(
                                            new newStmt("my2ndVar", new VarExp("myVar")),
                                            new CompStmt(
                                                    new newStmt("myVar", new ValueExp(new IntValue(30))),
                                                    new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("my2ndVar"))))
                                            )
                                    )
                            )
                    )
            ),

            new CompStmt(
                    new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                            new PrintStmt(new VarExp("v")))
            ),

            new CompStmt(
                    new VarDeclStmt("a", new IntType()),
                    new CompStmt(
                            new VarDeclStmt("b", new IntType()),
                            new CompStmt(
                                    new AssignStmt("a", new ArithExp(
                                            new ValueExp(new IntValue(2)),
                                            new ArithExp(
                                                    new ValueExp(new IntValue(3)),
                                                    new ValueExp(new IntValue(5)),
                                                    "*"
                                            ),
                                            "+"
                                    )),
                                    new CompStmt(
                                            new AssignStmt("b", new ArithExp(
                                                    new VarExp("a"),
                                                    new ValueExp(new IntValue(1)),
                                                    "+"
                                            )),
                                            new PrintStmt(new VarExp("b"))
                                    )
                            )
                    )
            ),

            new CompStmt(
                    new VarDeclStmt("a",new BoolType()),
                    new CompStmt(
                            new VarDeclStmt("v", new IntType()),
                            new CompStmt(
                                    new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                    new CompStmt(
                                            new IfStmt(
                                                    new VarExp("a"),
                                                    new AssignStmt("v",new ValueExp(new IntValue(2))),
                                                    new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                            new PrintStmt(new VarExp("v"))
                                    )
                            )
                    )
            ),

            new CompStmt(
                    new VarDeclStmt("varf", new StringType()),
                    new CompStmt(
                            new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                            new CompStmt(
                                    new OpenRFileStmt(new VarExp("varf")),
                                    new CompStmt(
                                            new VarDeclStmt("varc", new IntType()),
                                            new CompStmt(
                                                    new ReadFileStmt(new VarExp("varf"), "varc"),
                                                    new CompStmt(
                                                            new PrintStmt(new VarExp("varc")),
                                                            new CompStmt(
                                                                    new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                    new CompStmt(
                                                                            new PrintStmt(new VarExp("varc")),
                                                                            new CloseRFileStmt(new VarExp("varf"))
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            ),

            new CompStmt(
                    new VarDeclStmt("v", new IntType()),
                    new CompStmt(
                            new AssignStmt("v", new ValueExp(new IntValue(4))),
                            new WhileStmt(new RelExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"), new CompStmt(
                                    new PrintStmt(new VarExp("v")),
                                    new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), "-"))
                            ))
                    )
            ),

            new CompStmt(
                    new VarDeclStmt("v", new IntType()),
                    new CompStmt(
                            new VarDeclStmt("a", new RefType(new IntType())),
                            new CompStmt(
                                    new AssignStmt("a", new ValueExp(new IntValue(10))),
                                    new CompStmt(
                                            new newStmt("a", new ValueExp(new IntValue(22))),
                                            new CompStmt(
                                                    new ForkStmt(
                                                            new CompStmt(
                                                                    new heapWriteStmt("a", new ValueExp(new IntValue(30))),
                                                                    new CompStmt(
                                                                            new AssignStmt("a", new ValueExp(new IntValue(32))),
                                                                            new CompStmt(
                                                                                    new PrintStmt(new VarExp("v")),
                                                                                    new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                                            )
                                                                    )
                                                            )
                                                    ),
                                                    new CompStmt(
                                                            new PrintStmt(new VarExp("v")),
                                                            new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                    )
                                            )
                                    )
                            )
                    )
            ),

            new CompStmt(
                    new VarDeclStmt("v", new IntType()),
                    new CompStmt(
                            new VarDeclStmt("a", new RefType(new IntType())),
                            new CompStmt(
                                    new AssignStmt("v", new ValueExp(new IntValue(10))),
                                    new CompStmt(
                                            new newStmt("a", new ValueExp(new IntValue(22))),
                                            new CompStmt(
                                                    new ForkStmt(new CompStmt(
                                                            new heapWriteStmt("a", new ValueExp(new IntValue(30))),
                                                            new CompStmt(
                                                                    new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                    new CompStmt(
                                                                            new PrintStmt(new VarExp("v")),
                                                                            new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                                    )
                                                            )
                                                    )),
                                                    new CompStmt(
                                                            new PrintStmt(new VarExp("v")),
                                                            new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                    )
                                            )
                                    )
                            )
                    )
            ),

            new CompStmt(
                    new ForkStmt(
                            new CompStmt(
                                    new VarDeclStmt("a", new IntType()),
                                    new AssignStmt("a", new ValueExp(new IntValue(5)))
                            )
                    ),
                    new CompStmt(
                            new VarDeclStmt("b", new StringType()),
                            new AssignStmt("b", new ValueExp(new StringValue("HELLO")))
                    )
            )
    ));


    @Override
    public void start(Stage stage) throws Exception {
        ProgramList.show(stage, this.program_list);
    }

    public static void main(String[] args) {
        launch();
    }
}
