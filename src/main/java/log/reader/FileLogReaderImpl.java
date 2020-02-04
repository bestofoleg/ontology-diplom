package log.reader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLogReaderImpl implements ILogReader<File> {
    private File file;

    public FileLogReaderImpl() {}

    public FileLogReaderImpl(File file) {
        this.file = file;
    }

    @Override
    public String readLogs(File file) {
        try {
            return Files.lines(Paths.get(file.getAbsolutePath())).reduce("", String::concat);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String readLogs() {
        return readLogs(file);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
