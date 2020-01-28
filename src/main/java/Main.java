import ontology.system.connection.data.OntologyConnectionData;
import ontology.system.repository.IOntologyRepository;
import ontology.system.repository.OntologyRepositoryImpl;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File ontoFile = new File("test.owl");
        OntologyConnectionData connectionData = new OntologyConnectionData(ontoFile, "testOnto");
        IOntologyRepository ontologyRepository = new OntologyRepositoryImpl();
        ontologyRepository.createConnection(connectionData);
        ontologyRepository.addOntClass("Test");
        ontologyRepository.addOntClass("TestChild");
        ontologyRepository.saveOntology();
        ontologyRepository.addSubClass("Test", "TestChild");
        ontologyRepository.addIndividual("TestChild", "TestInstance");
        ontologyRepository.addIndividual("TestChild", "NewTestInstance");
        ontologyRepository.addOntClass("NewTestChild");
        ontologyRepository.saveOntology();
        ontologyRepository.addSubClass("Test", "NewTestChild");
        ontologyRepository.saveOntology();
        ontologyRepository.addOntProperty("prop", "TestInstance", "NewTestInstance");
        ontologyRepository.addDatatypedProperty(
                "NewTestInstance",
                "name",
                "Boo"
        );
        ontologyRepository.saveOntology();
    }
}
