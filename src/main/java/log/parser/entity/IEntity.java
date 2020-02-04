package log.parser.entity;

import java.util.List;

public interface IEntity {
    EntityType getType();
    String getName();
    List <String> getParams();
    IEntity setName(String name);
    IEntity setParams(List <String> params);
}
