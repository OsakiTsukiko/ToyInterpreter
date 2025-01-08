package osaki.mylang;

import osaki.mylang.controller.Controller;
import osaki.mylang.model.PrgState;
import osaki.mylang.model.adts.MyDictionary;
import osaki.mylang.model.adts.MyHeap;
import osaki.mylang.model.adts.MyList;
import osaki.mylang.model.adts.MyStack;
import osaki.mylang.model.exp.*;
import osaki.mylang.model.stmt.*;
import osaki.mylang.model.types.BoolType;
import osaki.mylang.model.types.IntType;
import osaki.mylang.model.types.RefType;
import osaki.mylang.model.types.StringType;
import osaki.mylang.model.values.BoolValue;
import osaki.mylang.model.values.IntValue;
import osaki.mylang.model.values.StringValue;
import osaki.mylang.repository.Repo;
import osaki.mylang.view.ExitCommand;
import osaki.mylang.view.RunExampleCommand;
import osaki.mylang.view.TextMenu;

import java.util.Scanner;


class Interpreter {
    public static void main(String[] args) {
        IStmt t1 = new CompStmt(
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
        );

        IStmt t2 = new CompStmt(
                new VarDeclStmt("myVar", new RefType(new IntType())),
                new CompStmt(
                        new newStmt("myVar", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("my2ndVar", new RefType(new RefType(new IntType()))),
                                new newStmt("my2ndVar", new VarExp("myVar"))
                        )
                )
        );

        IStmt t3 = new CompStmt(
                new VarDeclStmt("myVar", new RefType(new IntType())),
                new CompStmt(
                        new newStmt("myVar", new ValueExp(new IntValue(20))),
                        new PrintStmt(new ReadHeapExp(new VarExp("myVar")))
                )
        );

        IStmt t4 = new CompStmt(
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
        );

        IStmt t5 = new CompStmt(
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
        );

        IStmt t6 = new CompStmt(
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
        );

        IStmt ex1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v")))
        );

        IStmt ex2 = new CompStmt(
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
        );

        IStmt ex3 = new CompStmt(
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
        );

        /*
            string varf;
            varf="test.in";
            openRFile(varf);
            int varc;
            readFile(varf,varc);print(varc);
            readFile(varf,varc);print(varc)
            closeRFile(varf)
        */

        IStmt ex4 = new CompStmt(
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
        );

        IStmt ex5 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new WhileStmt(new RelExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"), new CompStmt(
                                new PrintStmt(new VarExp("v")),
                                new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), "-"))
                        ))
                )
        );

        IStmt ex6 = new CompStmt(
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
        );

        IStmt ex6_2 = new CompStmt(
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
        );

        Scanner scn = new Scanner(System.in);

        System.out.print("Log filename: ");
        String log_filename = scn.next();

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        menu.addCommand(new RunExampleCommand(
                "1",
                ex1.toString(),
                new Controller(
                        new Repo(
                                new PrgState(
                                        new MyStack<>(),
                                        new MyDictionary<>(),
                                        new MyList<>(),
                                        new MyDictionary<>(),
                                        new MyHeap<>(),
                                        ex1
                                ),
                                log_filename
                        )
                )
        ));

        menu.addCommand(new RunExampleCommand(
                "2",
                ex2.toString(),
                new Controller(
                        new Repo(
                                new PrgState(
                                        new MyStack<>(),
                                        new MyDictionary<>(),
                                        new MyList<>(),
                                        new MyDictionary<>(),
                                        new MyHeap<>(),
                                        ex2
                                ),
                                log_filename
                        )
                )
        ));

        menu.addCommand(new RunExampleCommand(
                "3",
                ex3.toString(),
                new Controller(
                        new Repo(
                                new PrgState(
                                        new MyStack<>(),
                                        new MyDictionary<>(),
                                        new MyList<>(),
                                        new MyDictionary<>(),
                                        new MyHeap<>(),
                                        ex3
                                ),
                                log_filename
                        )
                )
        ));

        menu.addCommand(new RunExampleCommand(
                "4",
                ex4.toString(),
                new Controller(
                        new Repo(
                                new PrgState(
                                        new MyStack<>(),
                                        new MyDictionary<>(),
                                        new MyList<>(),
                                        new MyDictionary<>(),
                                        new MyHeap<>(),
                                        ex4
                                ),
                                log_filename
                        )
                )
        ));

        menu.addCommand(new RunExampleCommand(
                "5",
                ex5.toString(),
                new Controller(
                        new Repo(
                                new PrgState(
                                        new MyStack<>(),
                                        new MyDictionary<>(),
                                        new MyList<>(),
                                        new MyDictionary<>(),
                                        new MyHeap<>(),
                                        ex5
                                ),
                                log_filename
                        )
                )
        ));

        menu.addCommand(new RunExampleCommand(
                "6",
                ex6_2.toString(),
                new Controller(
                        new Repo(
                                new PrgState(
                                        new MyStack<>(),
                                        new MyDictionary<>(),
                                        new MyList<>(),
                                        new MyDictionary<>(),
                                        new MyHeap<>(),
                                        ex6_2
                                ),
                                log_filename
                        )
                )
        ));

        IStmt ex7 = new PrintStmt(new RelExp(new ValueExp(new BoolValue(true)), new ValueExp(new StringValue("HELLO")), ">"));
        menu.addCommand(new RunExampleCommand(
                "7",
                ex7.toString(),
                new Controller(
                        new Repo(
                                new PrgState(
                                        new MyStack<>(),
                                        new MyDictionary<>(),
                                        new MyList<>(),
                                        new MyDictionary<>(),
                                        new MyHeap<>(),
                                        ex7
                                ),
                                log_filename
                        )
                )
        ));

        menu.addCommand(new RunExampleCommand(
                "91",
                t1.toString(),
                new Controller(
                        new Repo(
                                new PrgState(
                                        new MyStack<>(),
                                        new MyDictionary<>(),
                                        new MyList<>(),
                                        new MyDictionary<>(),
                                        new MyHeap<>(),
                                        t1
                                ),
                                log_filename
                        )
                )
        ));

        menu.addCommand(new RunExampleCommand(
                "92",
                t2.toString(),
                new Controller(
                        new Repo(
                                new PrgState(
                                        new MyStack<>(),
                                        new MyDictionary<>(),
                                        new MyList<>(),
                                        new MyDictionary<>(),
                                        new MyHeap<>(),
                                        t2
                                ),
                                log_filename
                        )
                )
        ));

        menu.addCommand(new RunExampleCommand(
                "93",
                t3.toString(),
                new Controller(
                        new Repo(
                                new PrgState(
                                        new MyStack<>(),
                                        new MyDictionary<>(),
                                        new MyList<>(),
                                        new MyDictionary<>(),
                                        new MyHeap<>(),
                                        t3
                                ),
                                log_filename
                        )
                )
        ));

        menu.addCommand(new RunExampleCommand(
                "94",
                t4.toString(),
                new Controller(
                        new Repo(
                                new PrgState(
                                        new MyStack<>(),
                                        new MyDictionary<>(),
                                        new MyList<>(),
                                        new MyDictionary<>(),
                                        new MyHeap<>(),
                                        t4
                                ),
                                log_filename
                        )
                )
        ));

        menu.addCommand(new RunExampleCommand(
                "95",
                t5.toString(),
                new Controller(
                        new Repo(
                                new PrgState(
                                        new MyStack<>(),
                                        new MyDictionary<>(),
                                        new MyList<>(),
                                        new MyDictionary<>(),
                                        new MyHeap<>(),
                                        t5
                                ),
                                log_filename
                        )
                )
        ));

        menu.addCommand(new RunExampleCommand(
                "96",
                t6.toString(),
                new Controller(
                        new Repo(
                                new PrgState(
                                        new MyStack<>(),
                                        new MyDictionary<>(),
                                        new MyList<>(),
                                        new MyDictionary<>(),
                                        new MyHeap<>(),
                                        t6
                                ),
                                log_filename
                        )
                )
        ));

        menu.show();
    }
}