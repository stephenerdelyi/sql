///////////////////
// Lock:
///////////////////
public class Lock extends SQL {
    String tableName;
    WriteFile writeFile = new WriteFile(); //the file writer, used to write files
    ReadFile readFile = new ReadFile(); //the file reader, used to read files

    //Lock - default constructor parameter override for cleaner errorToken creation
    Lock(String tableName) {
        this.tableName = tableName; //set the token command
        if(!readFile.fileExists("locks/")) {
            console.log("Need to make locks directory");
            writeFile.mkdir("locks/");
        }
    }

    public boolean acquire() {
        if(!isLocked()) {
            writeFile.createFile("locks/" + tableName);
            return true;
        }

        return false;
    }

    public boolean release() {
        if(isLocked()) {
            writeFile.deleteFile("locks/" + tableName);
            return true;
        }

        return false;
    }

    public boolean isLocked() {
        return readFile.fileExists("locks/" + tableName);
    }
}
