///////////////////
// SQLEngine:
///////////////////
public class SQLEngine extends SQL {
    String databasePath = "~/";
    WriteFile writeFile = new WriteFile();
    ReadFile readFile = new ReadFile();

    //run - runs the accepted token value in the SQL engine
    public void run(Token token) {
        if(token.command == "comment" || token.command == "exit") {
            //do nothing on purpose
        } else if(token.command == "create database") {
            createDatabase(token);
        } else if(token.command == "drop database") {
            dropDatabase(token);
        } else if(token.command == "create table") {
            createTable(token);
        } else if(token.command == "drop table") {
            dropTable(token);
        } else if(token.command == "use") {
            use(token);
        } else if(token.command == "select * from") {
            selectAll(token);
        } else if(token.command == "alter table") {
            alterTable(token);
        } else if(token.command == "error") {
            console.warn(token.errorString);
        }
    }

    public void createDatabase(Token token) {

    }

    public void dropDatabase(Token token) {

    }

    public void createTable(Token token) {

    }

    public void dropTable(Token token) {

    }

    public void use(Token token) {

    }

    public void selectAll(Token token) {

    }

    public void alterTable(Token token) {

    }
}
