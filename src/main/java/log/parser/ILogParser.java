package log.parser;

import log.parser.entity.IEntity;
import log.reader.ILogReader;

import java.util.List;

public interface ILogParser {
    List <IEntity> getEntitiesFromLogs(ILogReader logReader);
}
