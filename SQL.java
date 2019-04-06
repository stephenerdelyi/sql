//////////////////////////////////////////////////////
//              PROJECT ASSIGNMENT #3               //
//      Stephen Erdelyi Jr - CS 457 - SQL Sim       //
//////////////////////////////////////////////////////
public class SQL {
    static boolean allowFatalExecution = true;
    static Console console = new Console();
    static Tokenizer tokenizer = new Tokenizer();
    static SQLEngine sql = new SQLEngine();
    static int version = 3;

    public static void main(String[] args) {
        /////////////////////////////////////////////////////
        //                 STARTUP ACTIONS                 //
        /////////////////////////////////////////////////////
        console.printDiv();
        console.log("SQL SIMULATOR - PA " + version + " - Steve Erdelyi");
        console.printDiv();

        /////////////////////////////////////////////////////
        //                   SYSTEM READY                  //
        /////////////////////////////////////////////////////
        if(args.length == 1) {
            sql.parseFile(args[0]);
        } else {
            String retCommand = "";
            while(retCommand != "exit") {
                Token token = tokenizer.parse(console.getInput());
                retCommand = token.command;
                sql.run(token);
            }
        }
        console.printNewline();
    }
}
