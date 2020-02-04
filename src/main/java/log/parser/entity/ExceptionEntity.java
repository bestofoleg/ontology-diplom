package log.parser.entity;

import java.util.List;

public class ExceptionEntity implements IEntity {
    private String name;

    private List <String> params;

    @Override
    public EntityType getType() {
        return EntityType.EXCEPTION;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List <String> getParams() {
        return params;
    }

    @Override
    public IEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public IEntity setParams(List <String> params) {
        this.params = params;
        return this;
    }

    @Override
    public String toString() {
        return "ExceptionEntity{" +
                "name='" + name + '\'' +
                ", params=" + params +
                '}';
    }
}
