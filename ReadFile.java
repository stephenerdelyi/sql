import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

///////////////////
// ReadFile: file reader used to read input and config file content
///////////////////
public class ReadFile extends SQL {
    public String lastReadFile;

    //read - read the file and store it to the lastReadFile variable
    public void read(String fileName) {
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            fileReader.close();
            lastReadFile = stringBuffer.toString();
        } catch (IOException e) {
            console.error("The filename \"" + fileName + "\" does not exist or is corrupted");
        }
    }
}
