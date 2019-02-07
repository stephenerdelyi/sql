///////////////////
// Token:
///////////////////
public class Token extends SQL {
    public String command; //CREATE DATABASE
    public String tableValue; //tbl_1
    public String databaseValue; //db_1
    public String subCommand; //ADD
    public String attributes[] = new String[20]; //(a3 float,...
    public String errorString; //error string for user to read
    public String workingString; //the working string used for processing

    Token() {

    }

    Token(String command) {
        this.command = command;
    }
}
