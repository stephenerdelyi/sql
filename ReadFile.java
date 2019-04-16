import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

///////////////////
// ReadFile: file reader used to read table file content
///////////////////
public class ReadFile extends SQL {
    //read - read the file and return its contents in a string
    public String read(String fileName) {
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
            return stringBuffer.toString();
        } catch (IOException e) {
            console.error("The filename \"" + fileName + "\" does not exist or is corrupted");
        }
        return "";
    }

    //fileExists - returns wheather or not a file exists at a given location
    public boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
}
