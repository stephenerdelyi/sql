///////////////////
// Tokenizer:
///////////////////
public class Tokenizer extends SQL {
    public String lastReadFile;

    //read - read the file and store it to the lastReadFile variable
    public Token parse(String inputString) {
        Token token = new Token();
        inputString = inputString.toLowerCase();
        if(inputString.startsWith("create database")) {
            token.command = "create database";
        } else if(inputString.startsWith("drop database")) {
            token.command = "drop database";
        } else if(inputString.startsWith("use")) {
            token.command = "use";
        } else if(inputString.startsWith("create table")) {
            token.command = "create table";
        } else if(inputString.startsWith("drop table")) {
            token.command = "drop table";
        } else if(inputString.startsWith("select * from")) {
            token.command = "select * from";
        } else if(inputString.startsWith("alter table")) {
            token.command = "alter table";
        } else if(inputString.startsWith("--")) {
            token.command = "comment";
        } else if(inputString.startsWith(".exit")) {
            token.command = "exit";
        } else {
            token.command = "error";
        }

        return token;
    }
}
