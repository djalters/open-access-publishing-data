/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OAFS;

import OAFS_main.Connection_MySQL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.xml.bind.JAXBException;

/**
 * @version 1
 * @author walte
 */
public class Manager
{

    private final Properties properties; //don't know if i need this or not

    /**
     *
     */
    public Manager()
    {
        this.properties = null;

    }

    /**
     *
     * @param prop the properties file defining system constants & behaviour
     */
    public Manager(Properties prop)
    {
        this.properties = prop;
    }

    /**
     * unMarshalls XML into Cache from file path and datatype and persists to
     * database
     *
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.ClassNotFoundException
     * @throws javax.xml.bind.JAXBException
     */
    public void unMarshalXML() throws InstantiationException, IllegalAccessException, ClassNotFoundException, JAXBException
    {

        CacheFactory XMLoafsFact = new CacheFactory();

        CacheInterface XMLcache = XMLoafsFact.getUnmarshallXMLCache(
                getProperties().getProperty("filePath"),
                getProperties().getProperty("dataType")); //get unmarshalled cache object of correct type
        
        XMLcache.commitObjectListToDB(); //commits the marshalled objects to the database.

    }

    /**
     * Marshals objects of correct type, with optional where clause from MySql
     * using Hibernate
     *
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.ClassNotFoundException
     * @throws javax.xml.bind.JAXBException
     */
    public void marshalXML() throws InstantiationException, IllegalAccessException, ClassNotFoundException, JAXBException
    {
        CacheFactory oafsFact = new CacheFactory();
        CacheInterface dbcache = oafsFact.getEmptyCache(
                getProperties().getProperty("dataType")); //get unmarshalled cache object of correct type
        dbcache.setObjectListFromDB(getProperties().getProperty("optionalQuery")); //gets a list of objects or query of objects
        dbcache.marshallXMLCache(getProperties().getProperty("filePath")); //marshals cache object of correct type

    }

    /**
     * Updates data from the OaDoi service, by DOI only
     *
     * @throws MalformedURLException
     * @throws IOException
     */
    public void updateOADoi() throws MalformedURLException, IOException
    {
        try {
            Connection_MySQL obj = new Connection_MySQL();
            Connection conn = obj.getConnection("OAFS", "root",
                    "gKub5t96k2YJ2VglhkAO");
            
            PreparedStatement Statement = conn.prepareStatement(
                    "SELECT DISTINCT doi FROM br_basic_rpt WHERE doi NOT IN (SELECT DISTINCT doi FROM oa_doi_location);");
            ResultSet result = Statement.executeQuery();

            List<String> doiList = new ArrayList<>();

            Integer testCount = 0; //i.e. results to return

            while(result.next()) {
                doiList.add(result.getString("doi"));
            }

            //Creates new object, with doi's referenced
            Cache_OaDoi oaDois = new Cache_OaDoi(doiList);

            //this calls the API, gets the data and adds them to the cache
            oaDois.callOADoiAPI();

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    
    /**
     * Updates data from the OaDoi service
     *
     * @throws MalformedURLException
     * @throws IOException
     */
    public void updateCoreByDoi() throws MalformedURLException, IOException, InterruptedException
    {
        try {

            Connection_MySQL obj = new Connection_MySQL();
            Connection conn = obj.getConnection("OAFS", "root",
                    "gKub5t96k2YJ2VglhkAO");
            
            PreparedStatement Statement = conn.prepareStatement(
                    "SELECT DISTINCT ID, Title, DOI FROM br_basic_rpt WHERE ID NOT IN (SELECT DISTINCT ID FROM core) AND (Length(DOI) > 0) ORDER BY ID ASC;");
            ResultSet result = Statement.executeQuery();

            List<String[]> aList = new ArrayList<>(); //BRAD id number, Title to be searched...

            while(result.next()) {
                String[] holdStr = new String [3];
                holdStr[0] = result.getString("ID");
                holdStr[1] = result.getString("Title");
                holdStr[2] = result.getString("DOI");
                aList.add(holdStr);
            }

            Cache_Core coreRes = new Cache_Core(aList);

            coreRes.callCoreAPI();

            Statement = conn.prepareStatement("SELECT DISTINCT ID, Title, DOI FROM br_basic_rpt WHERE ID NOT IN (SELECT DISTINCT ID FROM core) AND (DOI IS NULL) AND ((`Publication type`=\"Journal article\") OR (`Publication type`=\"Conference\") OR (`Publication type`=\"Chapter\")) ORDER BY ID ASC;");
            
            result = Statement.executeQuery();

            aList = new ArrayList<>(); //BRAD id number, Title to be searched...

            while(result.next()) {
                String[] holdStr = new String [3];
                holdStr[0] = result.getString("ID");
                holdStr[1] = result.getString("Title");
                holdStr[2] = result.getString("DOI");
                aList.add(holdStr);
            }
            
            coreRes = new Cache_Core(aList);

            coreRes.callCoreAPI();

            Statement = conn.prepareStatement("SELECT DISTINCT core_repo_id FROM oafs.core WHERE core_repo_id NOT IN (SELECT core_repo_id FROM oafs.core_location);");
            result = Statement.executeQuery();

            List<String> repoIds = new ArrayList<>();

            while(result.next()) {
                repoIds.add(result.getString("core_repo_id"));

            }
            coreRes.setRepoList(repoIds);
            coreRes.callCoreLocationAPI();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    
    /**
     * Updates data from the Sref service
     *
     * @throws MalformedURLException
     * @throws IOException
     */
    public void updateSherpaRef() throws MalformedURLException, IOException, InterruptedException, ParseException
    {
        try {

            Connection_MySQL obj = new Connection_MySQL();
            Connection conn = obj.getConnection("OAFS", "root",
                    "gKub5t96k2YJ2VglhkAO");

            PreparedStatement Statement = conn.prepareStatement(
                    "SELECT DISTINCT eISSN, ISSN FROM br_basic_rpt WHERE length(eISSN)>0 OR length(ISSN)>0;");
            
            ResultSet result = Statement.executeQuery();

            List<String[]> aList = new ArrayList<>(); //BRAD id number, Title to be searched...

            while(result.next()) {
                String[] holdStr = new String [2];
                holdStr[0] = result.getString("eISSN");
                holdStr[1] = result.getString("ISSN");
                aList.add(holdStr);
            }

           Cache_SherpaRef sref = new Cache_SherpaRef(aList);

            sref.callAPI();

            
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public Properties getProperties()
    {
        return properties;
    }

}
