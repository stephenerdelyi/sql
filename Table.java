///////////////////
// Table: The struct class that holds all table data read through from a file (used for processing in SQLEngine)
///////////////////
public class Table extends SQL {
    String data[][] = new String[100][100]; //holds all data
    int numColumns = 0; //the number of columns in the table
    int numRows = 0; //the number of rows in the table
    WriteFile writeFile = new WriteFile(); //the file writer, used to write files
    ReadFile readFile = new ReadFile(); //the file reader, used to read files
    String fileLocation; //the file location of the table's file format

    //Table - default constructor that initializes the fileLocation and parses the data
    Table(String inputLocation) {
        fileLocation = inputLocation;
        parse();
    }

    //parse - parses the file at fileLocation and loads the data into the data array
    public void parse() {
        String fileText[] = readFile.read(fileLocation).replaceAll("(?m)^[ \t]*\r?\n", "").split("\n");

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

    //export - exports the data in the data array to the file at fileLocation
    public void export() {
        String exportString = "";
        for(int i = 0; i < numRows; i++) {
            String valueString = "";
            for(int j = 0; j < numColumns; j++) {
                valueString += data[i][j] + " | ";
            }
            valueString = valueString.substring(0, valueString.length() - 3);
            if(!valueString.replace("|","").trim().equals("")) {
                exportString += valueString + "\n";
            }
        }
        writeFile.write(fileLocation, false, exportString);
    }

    //delete - removes a row at rowValue
    public void delete(int rowValue) {
        for(int i = 0; i < numColumns; i++) {
            data[rowValue][i] = "";
        }
    }

    //getColumnValue - returns a column value using an input test string
    public int getColumnValue(String inputValue) {
        for(int i = 0; i < numColumns; i++) {
            if(data[0][i].contains(inputValue)) {
                return i;
            }
        }
        return -1;
    }

    //getRowValue - returns a row value based on an input test string and column to search on
    public int getRowValue(String inputValue, int columnValue) {
        for(int i = 0; i < numRows; i++) {
            if(inputValue.equals(data[i][columnValue])) {
                return i;
            }
        }
        return -1;
    }

    //print - prints out the entire data payload
    public void print() {
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numColumns; j += 3) {
                console.data(data[i][j] + " | " + data[i][j + 1] + " | " + data[i][j + 2]);
            }
        }
    }
}
