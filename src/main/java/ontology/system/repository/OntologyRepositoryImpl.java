package ontology.system.repository;

import ontology.system.connection.data.IConnectionData;
import ontology.system.connection.data.OntologyConnectionData;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class OntologyRepositoryImpl implements IOntologyRepository {
    private OntModel ontModel;

    private OntologyConnectionData ontologyConnectionData;

    @Override
    public Individual getIndividualByName(String name) {
        Individual individual = ontModel.getIndividual(name);

        if (individual == null)
            throw new IllegalArgumentException("Invalid name of individual. " + name + " is exists?");

        return individual;
    }

    @Override
    public OntClass addOntClass(String ontClass) {
        if (ontModel == null)
            throw new RuntimeException("Connection is not set!");

        return ontModel.createClass(ontologyConnectionData.getOntologyPath() + ontClass);
    }

    @Override
    public OntProperty addOntProperty(String ontProperty, String begin, String end) {
        if (ontModel == null)
            throw new RuntimeException("Connection is not set!");

        ObjectProperty property =
                ontModel.createObjectProperty(ontologyConnectionData.getOntologyPath() + ontProperty);

        if (property == null)
            throw new RuntimeException("Can't create a property with name " + ontProperty + ". ");

        Individual beginIndividual = ontModel.getIndividual(ontologyConnectionData.getOntologyPath() + begin);

        if (beginIndividual == null)
            throw new IllegalArgumentException("Can't find begin parameter Individual with name = " + begin + ".");

        Individual endIndividual = ontModel.getIndividual(ontologyConnectionData.getOntologyPath() + end);

        if (endIndividual == null)
            throw new IllegalArgumentException("Can't find end parameter Individual with name = " + end + ".");


        beginIndividual.addProperty(property, endIndividual);

        return property;
    }

    @Override
    public OntProperty addDatatypedProperty(String individualName, String dtPropertyName, Object typedLiteral) {
        Individual individual =
                ontModel.getIndividual(ontologyConnectionData.getOntologyPath() + individualName);

        DatatypeProperty property =
                ontModel.createDatatypeProperty(ontologyConnectionData.getOntologyPath() + dtPropertyName);

        if (individual == null)
            throw new IllegalArgumentException("individualName is not found.");

        if (typedLiteral == null)
            throw new IllegalArgumentException("typed literal is null.");

        individual.addProperty(property, ontModel.createTypedLiteral(typedLiteral));

        return property;
    }

    @Override
    public Individual addIndividual(String ontClass, String ontResource) {
        OntClass ontClassObject = ontModel.getOntClass(ontologyConnectionData.getOntologyPath() + ontClass);

        if (ontClassObject == null) {
            throw new RuntimeException("Super class has not founded!" +
                    ontologyConnectionData.getOntologyPath() + ontClass);
        }

        return ontModel.createIndividual(
                ontologyConnectionData.getOntologyPath() + ontResource,
                ontClassObject
        );
    }

    @Override
    public OntClass addSubClass(String superClassName, String ontClassChild) {
        OntClass superClass =
                ontModel.getOntClass(ontologyConnectionData.getOntologyPath() + superClassName);

        OntClass ontChildObject =
                ontModel.getOntClass(ontologyConnectionData.getOntologyPath() + ontClassChild);

        if (superClass == null)
            throw new IllegalArgumentException("super class is not found.");

        if (ontChildObject == null)
            throw new IllegalArgumentException("child class is not found.");

        superClass.addSubClass(ontChildObject);

        return superClass;
    }

    @Override
    public OntModel saveOntology() {
        if (!ontologyConnectionData.getOntologyFile().exists()) {
            try {
                ontologyConnectionData.getOntologyFile().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        try (Writer writer = new PrintWriter(ontologyConnectionData.getOntologyFile())) {
            ontModel.createOntology(ontologyConnectionData.getOntologyModelName());
            ontModel.write(writer);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return ontModel;
    }

    public void createConnection(IConnectionData connectionData) {
        if (connectionData instanceof OntologyConnectionData) {
            ontologyConnectionData =
                    (OntologyConnectionData) connectionData;

            if (!ontologyConnectionData.getOntologyFile().exists()) {
                Writer writer = null;

                try {
                    ontologyConnectionData.getOntologyFile().createNewFile();
                    writer = new PrintWriter(ontologyConnectionData.getOntologyFile());
                    ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
                    ontModel.write(writer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ontModel = (OntModel) ModelFactory
                    .createOntologyModel(OntModelSpec.OWL_MEM)
                    .read("file:///" + ontologyConnectionData.getOntologyFile().getAbsolutePath());
        } else {
            throw new IllegalArgumentException("Wrong connection data impl - Connection data should be instance" +
                    "of " + OntologyConnectionData.class.getCanonicalName());
        }

    }

    public OntResource add(OntResource obj) {
        if (obj == null)
            throw new IllegalArgumentException("resource is not found.");

        if (ontModel == null)
            throw new RuntimeException("Connection is not set!");

        return ontModel.createOntResource(obj.getNameSpace() + obj.getLocalName());
    }

    public OntResource remove(OntResource obj) {
        return null;
    }

    public OntModel getOntModel() {
        return ontModel;
    }

    @Override
    public String toString() {
        return "OntologyRepositoryImpl{" +
                "ontModel=" + ontModel +
                ", ontologyConnectionData=" + ontologyConnectionData +
                '}';
    }
}

