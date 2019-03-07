import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {
    private static Logger _logger = null;

    private Logger() {
    }

    public static Logger getLogger() {
        if (_logger == null)
            _logger = new Logger();
        return _logger;
    }

    public synchronized void log(String m) {
        try {
            writeToFile("log.txt", m + "\n");
        } catch (Exception e) {
        }
    }

    public synchronized void storeAccess(String ip, int matrNumber) {
        try {
            writeToFile("clientData.csv", new Date() + ", [" + ip + "], " + String.valueOf(matrNumber) + "\n");
        } catch (IOException e) {
            this.log(e.getMessage());
        }
    }

    public void writeToFile(String path, String message) throws IOException {
        FileWriter fw = new FileWriter(path, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(message);
        bw.flush();
        bw.close();
        fw.close();
    }
}
