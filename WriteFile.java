import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

///////////////////
// WriteFile: file writer used to write output log file content
///////////////////
public class WriteFile extends SQL {
    //createFile - creates a file named by the input string
    public void createFile(String fileName) {
        write(fileName, false, "");
    }

    //write - writes output to the given file name
    public void write(String fileName, boolean appendMode, String inputString) {
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter(fileName, appendMode);
            bw = new BufferedWriter(fw);
            if (inputString != "") {
                bw.write(inputString);
            }
        } catch (IOException e) {
            console.error("The file \"" + fileName + "\" can not be written to because it could not be opened or it is corrupted");
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                console.error("The file \"" + fileName + "\" can not be written to because it could not be opened or it is corrupted");
            }
        }
    }
}
