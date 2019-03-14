///////////////////
// Table: The struct class that holds all tokenized data (used for processing in SQLEngine)
///////////////////
public class Table extends SQL {
    String data[][] = new String[100][100]; //CREATE DATABASE
    int numColumns = 0;
    int numRows = 0;
    WriteFile writeFile = new WriteFile(); //the file writer, used to write files
    ReadFile readFile = new ReadFile(); //the file reader, used to read files
    String fileLocation;

    //Token - default constructor that does nothing but is needed since we have an override
    Table(String inputLocation) {
        fileLocation = inputLocation;
        parse();
    }

    public void parse() {
        String fileText[] = readFile.read(fileLocation).split("\n");

        for(int i = 0; i < fileText.length; i++) {
            numRows++;
            String valueArray[] = fileText[i].split(" \\| ");
            for(int j = 0; j < valueArray.length; j++) {
                numColumns++;
                data[i][j] = valueArray[j];
            }
        }

        //recompute column value
        numColumns = numColumns / numRows;
    }

    public int getColumnValue(String inputValue) {
        for(int i = 0; i < numColumns; i++) {
            if(data[0][i].contains(inputValue)) {
                return i;
            }
        }
        return -1;
    }

    public int getRowValue(String inputValue, int columnValue) {
        for(int i = 0; i < numRows; i++) {
            if(inputValue.equals(data[i][columnValue])) {
                return i;
            }
        }
        return -1;
    }



    public void print() {
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numColumns; j += 3) {
                console.log("   " + data[i][j] + " | " + data[i][j + 1] + " | " + data[i][j + 2]);
            }
        }
    }
}
