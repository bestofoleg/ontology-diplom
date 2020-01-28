package ontology.system.repository;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntProperty;

public interface IOntologyRepository {
    OntProperty addDatatypedProperty(String individualName, String dtPropertyName, Object typedLiteral);
    //DatatypeProperty addDatatypeProperty(String dtProperty, String propertyName, String ofClass);

    OntClass addOntClass(String ontClass);

    OntProperty addOntProperty(String ontProperty, String begin, String end);

    Individual addIndividual(String ontClass, String ontResource);

    OntClass addSubClass(String superClassName, String ontClassChild);

    OntModel saveOntology();

    Individual getIndividualByName(String name);
}
