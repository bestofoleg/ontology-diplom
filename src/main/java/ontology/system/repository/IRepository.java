package ontology.system.repository;

import ontology.system.connection.data.IConnectionData;

public interface IRepository <DATA> {
    void createConnection(IConnectionData connectionData);

    DATA add(DATA obj);

    DATA remove(DATA obj);
}
