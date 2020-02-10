import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        /*File ontoFile = new File("test.owl");
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
        ontologyRepository.saveOntology();*/

        /*File file = new File("test.log");
        ILogReader logReader = new FileLogReaderImpl(file);
        ILogParser logParser = new LogParserImpl();
        List<IEntity> entities = logParser.getEntitiesFromLogs(logReader);
        entities.forEach(System.out::println);*/

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("context.xml");
        ITestClass test = (ITestClass) context.getBean("testObject");
        test.test(null);
    }
}
