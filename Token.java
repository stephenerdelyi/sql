///////////////////
// Token: The struct class that holds all tokenized data (used for processing in SQLEngine)
///////////////////
public class Token extends SQL {
    public String command; //CREATE DATABASE
    public String tblName; //tbl_1
    public String dbName; //db_1
    public String subCommand; //ADD
    public String attributes[] = new String[20]; //(a3 float,...
    public String selected[] = new String[20]; //(a3 float,...
    public String whereClause;
    public String testClause;
    public String valueClause;
    public String errorString; //error string for user to read
    public String workingString; //the working string used for processing
    public int selectedCount = 0;
    public int attributesCount = 0;

    //Token - default constructor that does nothing but is needed since we have an override
    Token() {
        //intentionally left blank
    }

    //Token - default constructor parameter override for cleaner errorToken creation
    Token(String command) {
        this.command = command; //set the token command
    }
}
