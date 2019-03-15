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

    public void delete(int rowValue) {
        for(int i = 0; i < numColumns; i++) {
            data[rowValue][i] = "";
        }
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
                console.data(data[i][j] + " | " + data[i][j + 1] + " | " + data[i][j + 2]);
            }
        }
    }
}
