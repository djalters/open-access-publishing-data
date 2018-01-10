/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OAFS_main;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

//import the cache classes so can initialise the program with the correct data
import OAFS.Manager;
import java.net.MalformedURLException;
import java.text.ParseException;
import javax.xml.bind.JAXBException;

/**
 *
 * @author walte
 */
public class Run
{

    //initialise the directories and XMLnamespaces
    private final FileDir XMLInputPath;
    private final FileDir XMLOutputPath;
    private final FileDir XMLSchemaPath;

    private Set<String> XMLnamespaces; //holds valid namespaces
    private Document xmlFileDom = null; //initialise as null

    public Run(String currentStringPath)
    {
        //file directory class finds paths to XML data for processing and validating for all use cases
        this.XMLInputPath = new FileDir(true, "XMLInput", currentStringPath);
        this.XMLOutputPath = new FileDir(true, "XMLOutput", currentStringPath);
        this.XMLSchemaPath = new FileDir(true, "XMLSchema", currentStringPath);

        setXMLnamespaces();

    }

    public FileDir getXMLInputPath()
    {
        return XMLInputPath;
    }

    public FileDir getXMLOutputPath()
    {
        return XMLOutputPath;
    }

    public FileDir getXMLSchemaPath()
    {
        return XMLSchemaPath;
    }

    public void runUseCase(int useCaseNumber) throws ParserConfigurationException, SAXException, IOException, JAXBException, InstantiationException, IllegalAccessException, ClassNotFoundException, MalformedURLException, InterruptedException, ParseException
    {

        switch (useCaseNumber) {
            case 1: //use case 1 & use case 2 - OAFS data input (insert/update) from XML file
                //precondition: Must be valid XML in the XMLInputdirectory
                System.out.println("Running use case 1");
                int count = 0;
                Boolean xmlTest = false;
                String[] schemaFiles = this.getXMLSchemaPath().getFiles().toArray(
                        new String[0]);
                //get the XMLnamespaces, and validate the types against xsd schema
                for (String filePath : this.getXMLInputPath().getFiles()) {
                    Properties prop = getProperties();
                    String xmlSchemaNS = "";
                    Boolean xmlFileParsed = xmlFileParsed(filePath);
                    System.out.println("running " + filePath);
                    if (xmlFileParsed) {
                        while ((xmlTest == false) && (count < schemaFiles.length)) {
                            xmlTest = validateXML(this.getXmlFileDom(),
                                    schemaFiles[count]);
                            //Maintenence for XML schema validation
                            xmlTest = true;
                            count++;
                        }
                        if (xmlTest == true) { //valid xml confirmed against one of the schemas held by the program

                            String xmlFileParsedNameSpace = this.getNameSpace(
                                    this.getXmlFileDom());

                            System.out.println(
                                    "parsed namespace from dom = " + xmlFileParsedNameSpace);

                            if (this.getXMLnamespaces().contains(
                                    xmlFileParsedNameSpace)) { //checks to ensure the namespace is agreed
                                System.out.println(
                                        "the namespace exists in my list  = " + xmlFileParsedNameSpace);

                                prop.setProperty("filePath", filePath);
                                prop.setProperty("dataType",
                                        xmlFileParsedNameSpace); //also DB table??

                                Manager OAFSManager = new Manager(prop);

                                OAFSManager.unMarshalXML();

                            }
                            else {

                            }
                        }
                        else {
                            System.out.println(
                                    filePath + " could not be validated against a schema or dom is null");
                        }
                        count = 0; //reset count
                        xmlTest = false; //so subsequent files will be testing against schemas
                    }

                }
                break;
            case 4: //use case 4 - OAFS data output to XML file
                //set properties and the datatype
                Properties prop = getProperties();
                prop.setProperty("filePath", getXMLOutputPath().getPath());
                prop.setProperty("dataType", "xmlns:SY_OUTPUTS"); //S/S
                prop.setProperty("optionalQuery", "where funding_request='Yes'");
                Manager OAFSManager = new Manager(prop);
                OAFSManager.marshalXML();
                break;
            case 5:  //use case 5 - enhance data from REST Apis (oaDoi) and output to database
                Properties props = getProperties();
                props.setProperty("dataType", "oaDoi");
                Manager OSManager = new Manager(props);
                OSManager.updateOADoi();
                break;
            case 6:  //use case 6 - enhance data from REST Apis (Core) and output to database
                Properties propss = getProperties();
                propss.setProperty("dataType", "core");
                Manager CManager = new Manager(propss);
                CManager.updateCoreByDoi();
                break;
            case 7:  //use case 7 - enhance data from REST Apis (Sref) and output to database
                Properties propsss = getProperties();
                propsss.setProperty("dataType", "sref");
                Manager DManager = new Manager(propsss);
                DManager.updateSherpaRef();
                break;
            default:
                break;
        }
    }

    public Boolean xmlFileParsed(String XMLfilepath)
    {
        Boolean result = true;
        Document document = null;

        try {
            DocumentBuilderFactory parser = DocumentBuilderFactory.newInstance();
            parser.setNamespaceAware(true);
            DocumentBuilder builder = parser.newDocumentBuilder();
            document = builder.parse(new File(XMLfilepath));
            System.out.println("parsed successfully");
        }
        catch (SAXException | IOException | ParserConfigurationException ex) {
            result = false;
            System.out.println("exception in parsing xmlFile");
        }
        finally {
            setXmlFileDom(document);
            return result;
        }
    }

    public Boolean validateXML(Document XMLfile, String XMLschemapath)
    {
        Boolean valid = true;
        try {

            SchemaFactory factory = SchemaFactory.newInstance(
                    XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Source schemaFile = new StreamSource(new File(XMLschemapath));
            Schema schema = factory.newSchema(schemaFile);

            Validator validator = schema.newValidator();

            DOMSource doms = new DOMSource(XMLfile);

            validator.validate(doms);

        }
        catch (SAXException ex) {
            //ex.printStackTrace();

            valid = false;
        }
        catch (IOException ex) {
            //System.out.println("IO exception in validateXML - SCHEMPATH " + XMLschemapath);
            valid = false;
        }

        finally {
            return valid;
        }
    }

    public String getNameSpace(Document doc) throws ParserConfigurationException, SAXException,
            IOException
    {
        String returnNS = "";

        Element root = doc.getDocumentElement();

        NamedNodeMap attributes = root.getAttributes();
        Node node = attributes.item(0);
        if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
            returnNS = node.getNodeName();

        }

        return returnNS;
    }

    public Properties getProperties()
    {
        return createPropertiesFile();
    }

    private Properties createPropertiesFile()
    {

        Properties prop = new Properties();

        return prop;
    }

    public Set<String> getXMLnamespaces()
    {
        return XMLnamespaces;
    }

    private void setXMLnamespaces()
    {
        this.XMLnamespaces = new HashSet<>();
        this.XMLnamespaces.add("xmlns:JM_invoices");
        this.XMLnamespaces.add("xmlns:SY_OUTPUTS");
        this.XMLnamespaces.add("xmlns:JM_ARTICLES");
        this.XMLnamespaces.add("oaDoi");
        this.XMLnamespaces.add("xmlns:BrBasic");
    }

    public Document getXmlFileDom()
    {
        return xmlFileDom;
    }

    public void setXmlFileDom(Document xmlFileDom)
    {
        this.xmlFileDom = xmlFileDom;
    }

}
