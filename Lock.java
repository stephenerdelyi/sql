///////////////////
// Lock: Used as a mutex locking system that ensures only one table is accessed at a time when using transactions
///////////////////
public class Lock extends SQL {
    String lockLocation; //the location of the lock
    WriteFile writeFile = new WriteFile(); //the file writer, used to write files
    ReadFile readFile = new ReadFile(); //the file reader, used to read files

    //Lock - default constructor sets the lock location
    Lock(String lockLocation) {
        this.lockLocation = lockLocation; //set the token command
    }

    //acquire - acquires the lock if possible
    public boolean acquire() {
        if(!isLocked()) {
            writeFile.createFile(lockLocation + "_lock");
            return true;
        }

        return false;
    }

    //release - releases a lock if in posession of one
    public boolean release() {
        if(isLocked()) {
            writeFile.deleteFile(lockLocation + "_lock");
            return true;
        }

        return false;
    }

    //isLocked - returns whether or not a lock is acquired
    public boolean isLocked() {
        return readFile.fileExists(lockLocation + "_lock");
    }
}
