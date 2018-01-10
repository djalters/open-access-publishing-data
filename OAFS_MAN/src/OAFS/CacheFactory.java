/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OAFS;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author walte
 */
public class CacheFactory
{

    /**
     * Precondition: valid filepath and datatype argument Postcondition: Return
     * an cache object of datatype, with unmarshalled XML data
     * @param filePath
     * @param dataType
     * @return
     * @throws JAXBException
     */
    public CacheInterface getUnmarshallXMLCache(String filePath, String dataType) throws JAXBException
    {
        if (dataType == null) {
            return null;
        }
        if (dataType.equalsIgnoreCase("xmlns:JM_invoices")) {
            System.out.println("jm invoices running");
            JAXBContext jaxbContext = JAXBContext.newInstance(
                    Cache_JmInvoice.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Cache_JmInvoice oafscache = (Cache_JmInvoice) jaxbUnmarshaller.unmarshal(
                    new File(filePath));
            return oafscache;
        }
        else if (dataType.equalsIgnoreCase("xmlns:JM_ARTICLES")) {
            System.out.println("OAFSCache_JM_articles running");
            JAXBContext jaxbContext = JAXBContext.newInstance(
                    Cache_JmArticle.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Cache_JmArticle oafscache = (Cache_JmArticle) jaxbUnmarshaller.unmarshal(
                    new File(filePath));
            return oafscache;
        }
        else if (dataType.equalsIgnoreCase("xmlns:BrBasic")) {
            System.out.println("OAFSCache_BrBasic running");
            JAXBContext jaxbContext = JAXBContext.newInstance(
                    Cache_BrBasicRpt.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Cache_BrBasicRpt oafscache = (Cache_BrBasicRpt) jaxbUnmarshaller.unmarshal(
                    new File(filePath));
            return oafscache;
        }
        return null;
    }

    /**
     * Precondition: valid datatype argument
     * Postcondition: Return an empty cache object of datatype
     * @param dataType
     * @return
     */
    public CacheInterface getEmptyCache(String dataType)
    {
        if (dataType == null) {

            return null;
        }
        if (dataType.equalsIgnoreCase("xmlns:JM_invoices")) {

            return new Cache_JmInvoice();

        }
        else if (dataType.equalsIgnoreCase("xmlns:JM_ARTICLES")) {

            return new Cache_JmArticle();

        }
        else if (dataType.equalsIgnoreCase("oaDoi")) {

            return new Cache_OaDoi();

        }
        else if (dataType.equalsIgnoreCase("xmlns:BrBasic")) {

            return new Cache_BrBasicRpt();

        }
        return null;
    }

}
