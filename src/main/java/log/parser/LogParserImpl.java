package log.parser;

import log.parser.entity.ExceptionEntity;
import log.parser.entity.IEntity;
import log.reader.ILogReader;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogParserImpl implements ILogParser {
    private static final Pattern EXCEPTION_NAME_PATTERN = Pattern.compile("\\.([^\\.|^\\(|^\\)|^\\s]*)Exception");

    private static final Pattern EXCEPTION_MESSAGE_PATTERN = Pattern.compile("Exception: (.*[^\\\n])");

    @Override
    public List<IEntity> getEntitiesFromLogs(ILogReader logReader) {
        String sourceContent = logReader.readLogs();
        return EXCEPTION_NAME_PATTERN
                .matcher(sourceContent).results()
                .map(matchName -> new ExceptionEntity()
                        .setName(matchName.group().replace(".", "").replaceFirst("\\\n(.*)", ""))
                        .setParams(
                                EXCEPTION_MESSAGE_PATTERN
                                    .matcher(sourceContent)
                                    .results()
                                    .map(matchMessage -> matchMessage.group().replace("Exception: ", ""))
                                    .collect(Collectors.toList())
                        )).collect(Collectors.toList());
    }
}
