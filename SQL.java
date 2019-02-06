//////////////////////////////////////////////////////
//              PROJECT ASSIGNMENT #1               //
//      Stephen Erdelyi Jr - CS 457 - SQL Sim       //
//////////////////////////////////////////////////////
public class SQL {
    static boolean allowFatalExecution = true;
    static Console console = new Console();
    static Tokenizer tokenizer = new Tokenizer();

    public static void main(String[] args) {
        /////////////////////////////////////////////////////
        //                 STARTUP ACTIONS                 //
        /////////////////////////////////////////////////////
        console.printDiv();
        console.log("SQL SIMULATOR - PA 1 - Steve Erdelyi");
        console.printDiv();

        /////////////////////////////////////////////////////
        //                   SYSTEM READY                  //
        /////////////////////////////////////////////////////
        String retCommand = "";
        while(retCommand != "exit") {
            String ret = console.getInput();
            Token token = tokenizer.parse(ret);
            retCommand = token.command;
            if(retCommand == "error") {
                console.log("-- !" + token.errorString);
            } else if(retCommand == "comment" || retCommand == "exit") {
                //do nothing on purpose
            } else {
                console.log(retCommand);
                console.log(token.databaseValue);
            }
        }
        console.printNewline();
    }
}
