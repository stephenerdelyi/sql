///////////////////
// Token: The struct class that holds all tokenized data (used for processing in SQLEngine)
///////////////////
public class Token extends SQL {
    public String command; //CREATE DATABASE
    public String tblName; //tbl_1
    public String tblSuffix; //E
    public String secondTblName; //tbl_2
    public String secondTblSuffix; //S
    public String dbName; //db_1
    public String subCommand; //ADD
    public String attributes[] = new String[20]; //(a3 float,...
    public String selected[] = new String[20]; //(a3 float,...
    public String whereClause; //the value that comes directly after "where" keyword
    public String secondWhereClause; //the second where clause for table joins
    public String testClause; //the test value, ie =, !=, >, <, etc
    public String valueClause; //the value that comes directly after the test clause
    public String setClause; //the set clause (used only in update)
    public String setValueClause; //the set value clause (used only in update)
    public String errorString; //error string for user to read
    public String workingString; //the working string used for processing
    public int selectedCount = 0; //the number of selected values
    public int attributesCount = 0; //the number of attribute values

    //Token - default constructor that does nothing but is needed since we have an override
    Token() {
        //intentionally left blank
    }

    //Token - default constructor parameter override for cleaner errorToken creation
    Token(String command) {
        this.command = command; //set the token command
    }
}
