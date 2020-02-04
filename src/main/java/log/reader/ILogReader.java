package log.reader;

public interface ILogReader <SOURCE> {
    String readLogs(SOURCE source);
    String readLogs();
}
