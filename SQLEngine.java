import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

///////////////////
// SQLEngine: The brains of the SQL simulator - handles all tokenized db/table actions
///////////////////
public class SQLEngine extends SQL {
    String databaseLocation = System.getProperty("user.home") + "/cs457/pa" + version + "/"; //the location of the database, currently set to ~/cs457/paN/
    WriteFile writeFile = new WriteFile(); //the file writer, used to write files
    ReadFile readFile = new ReadFile(); //the file reader, used to read files
    String currentDB = ""; //the current database value (changed with the use command)

    //run - runs the tokenized data in the SQL engine
    public void run(Token token) {
        if(token.command == "comment" || token.command == "exit") {
            //intentionally left blank
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

    //parseFile -
    public void parseFile(String fileLocation) {
        String fileText[] = new String[100];
        fileText = readFile.read(fileLocation).split("\n");
        for(int i = 0; i < fileText.length; i++) {
            Token token = tokenizer.parse(fileText[i]);
            run(token);
        }
    }

    //createDatabase - creates a new database in the database file directory
    public void createDatabase(Token token) {
        if(databaseExists(token.dbName)) {
            console.warn("Failed to create database " + token.dbName + " because it already exists");
        } else {
            new File(databaseLocation + token.dbName).mkdirs();
            console.success("Database " + token.dbName + " created");
        }
    }

    //dropDatabase - drops an active database in the database file directory
    public void dropDatabase(Token token) {
        if(!databaseExists(token.dbName)) {
            console.warn("Failed to delete " + token.dbName + " because it does not exist");
        } else {
            File dbFolder = new File(databaseLocation + token.dbName);
            //remove all files in the database
            String tables[] = dbFolder.list();
            for(String table: tables){
                File currentFile = new File(dbFolder.getPath(), table);
                currentFile.delete();
            }
            //delete empty folder
            dbFolder.delete();
            console.success("Database " + token.dbName + " deleted");
        }
        //reset the currentDB if we just dropped it
        if(currentDB == token.dbName) {
            currentDB = "";
        }
    }

    //createTable - creates a new table in the current database file directory
    public void createTable(Token token) {
        if(currentDB == "") {
            console.warn("Failed to create table " + token.tblName + " because there is no database in use");
        } else {
            if(tableExists(token.tblName)) {
                console.warn("Failed to create table " + token.tblName + " because it already exists");
            } else {
                //create attribute String
                String attributeString = "";
                for(int i = 0; i < token.attributes.length; i++) {
                    attributeString += " | " + token.attributes[i];
                }
                attributeString = attributeString.substring(3); //trim the first pipe from the string
                String tablePath = databaseLocation + currentDB + "/" + token.tblName;
                writeFile.write(tablePath, false, attributeString);
                console.success("Table " + token.tblName + " created");
            }
        }
    }

    //dropTable - drops an active table in the current database file directory
    public void dropTable(Token token) {
        if(currentDB == "") {
            console.warn("Failed to delete table " + token.tblName + " because there is no database in use");
        } else {
            if(!tableExists(token.tblName)) {
                console.warn("Failed to delete table " + token.tblName + " because it does not exist");
            } else {
                File tblFolder = new File(databaseLocation + currentDB + "/" + token.tblName);
                tblFolder.delete();
                console.success("Table " + token.tblName + " deleted");
            }
        }
    }

    //use - switches the currentDB context
    public void use(Token token) {
        if(databaseExists(token.dbName)) {
            currentDB = token.dbName;
            console.success("Using database " + token.dbName);
        } else {
            console.warn("Failed to use " + token.dbName + " beacuase it does not exist");
        }
    }

    //selectAll - selects all table content from the selected table in the current database file directory
    public void selectAll(Token token) {
        if(currentDB == "") {
            console.warn("Failed to select table " + token.tblName + " because there is no database in use");
        } else {
            if(!tableExists(token.tblName)) {
                console.warn("Failed to select table " + token.tblName + " because it does not exist");
            } else {
                String fileText = readFile.read(databaseLocation + currentDB + "/" + token.tblName);
                console.log("➤  " + fileText.replace("\n", " "));
            }
        }
    }

    //alterTable - alters the table content from the selected table in the current database file directory
    public void alterTable(Token token) {
        if(currentDB == "") {
            console.warn("Failed to alter table " + token.tblName + " because there is no database in use");
        } else {
            if(!tableExists(token.tblName)) {
                console.warn("Failed to alter table " + token.tblName + " because it does not exist");
            } else {
                if(token.subCommand.equals("add")) {
                    String tablePath = databaseLocation + currentDB + "/" + token.tblName;
                    writeFile.write(tablePath, true, " | " + token.attributes[0]);
                    console.success("Table " + token.tblName + " modified");
                }
            }
        }
    }

    //databaseExists - returns whether or not the specified database exists in the database file directory
    public boolean databaseExists(String dbName) {
        Path dbPath = Paths.get(databaseLocation + dbName);
        if(Files.exists(dbPath)) {
            return true;
        }
        return false;
    }

    //tableExists - returns whether or not the specified table exists in the current database file directory
    public boolean tableExists(String tblName) {
        Path tblPath = Paths.get(databaseLocation + currentDB + "/" + tblName);
        if(Files.exists(tblPath)) {
            return true;
        }
        return false;
    }
}
