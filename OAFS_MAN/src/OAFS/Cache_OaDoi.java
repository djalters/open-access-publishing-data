/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OAFS;

/**
 *
 * @author walte
 */
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import OAFS_main.DBHelper;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Cache_OaDoi implements CacheInterface
{

    @XmlElement(name = "OaDois")
    private List<OaDoiLocation> OaDois = null;

    //not implemented by Cache
    private List<String> DoiList;

    public Cache_OaDoi()
    {
        //public constructor - allows construction of object - outside of cache parameters
        //this means I can pass this object a list of Doi's
        //not implemented by Cache Interface
        //Overloaded
        this.DoiList = DoiList;
    }

    public Cache_OaDoi(List<String> DoiList)
    {
        //public constructor - allows construction of object - outside of cache parameters
        //this means I can pass this object a list of Doi's
        //not implemented by Cache Interface
        this.DoiList = DoiList;
    }

    @Override
    public List<OaDoiLocation> getOaDois()
    {
        return OaDois;
    }

    @Override
    public void setOaDois(List<OaDoiLocation> OaDois)
    {
        this.OaDois = OaDois;
    }

    //other types must return null so ...factory can return a collection
    @Override
    public List<JmInvoice> getJM_invoices()
    {
        return null;
    }

    @Override
    public List<JmArticle> getJM_articles()
    {
        return null;
    }

    //hibernate
    @Override
    public void setJM_articles(List<JmArticle> JM_articles)
    {
        //do nothing
    }

    @Override
    public void setJM_invoices(List<JmInvoice> JM_invoices)
    {
        //do nothing
    }

    @Override
    public void commitObjectListToDB() throws HibernateException
    {
        //this retains function of other caches, if manipulated store of objects needs persisting, this can be utilised
        for (OaDoiLocation o : this.getOaDois()) {
            //OaDoiParser call = new OaDoiParser(o.getId().getDoi());
            //OA parsing, now another script, populates the list as before.
            //if oa parser is successful I have a list of objects
            DBHelper db = new DBHelper();
            Session sess = db.getFactory().openSession();
            Transaction tx = null;
            try {
                tx = sess.beginTransaction();

                OaDoiLocation t = new OaDoiLocation(o.getId(),
                        o.getVersion(),
                        o.getEvidence(),
                        o.getHostType(),
                        o.getIsBest(),
                        o.getLicense(),
                        o.getUpdated(),
                        o.getUrl(),
                        o.getUrlForLandingPage(),
                        o.getUrlForPdf(),
                        o.getIsOa(),
                        o.getJournalIsOa(),
                        o.getJournalName(),
                        o.getJournalPublisher()
                );

                sess.saveOrUpdate(t);
                tx.commit();

            }
            catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            }
            finally {
                sess.close();
            }

        }
    }

    @Override
    public void setObjectListFromDB(String optionalStringQueryParameters)
    {

        DBHelper db = new DBHelper();
        Session sess = db.getFactory().openSession();
        List<OaDoiLocation> oaList = null;

        try {
            org.hibernate.Transaction tx = sess.beginTransaction();
            Query q = sess.createQuery(
                    "from OaDoiLocation " + optionalStringQueryParameters);
            oaList = (List<OaDoiLocation>) q.list();

        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        this.setOaDois(oaList);

        sess.close();

    }

    @Override
    public void marshallXMLCache(String outputPath)
    {
        try {

            Cache_OaDoi oadoi = new Cache_OaDoi();
            oadoi.setOaDois(this.getOaDois());

            String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(
                    new Date());

            JAXBContext jaxbContext = JAXBContext.newInstance(Cache_OaDoi.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(oadoi, new File(
                    outputPath + "\\OAdoi_data_" + timeStamp + ".xml"));
        }
        catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<BrBasicRpt_import> getBrBasicRpt_import()
    {
        return null;
    }

    @Override
    public void setBrBasicRpt_import(List<BrBasicRpt_import> BrBasicRpt_imports)
    {
        //do nothing
    }

    public List<String> getDoiList()
    {
        return this.DoiList;
    }

    public void callOADoiAPI()
    {
        //Precondition - DOI list is initialised
        //calls list of DOIs and runs through class which calls the API, adds new objects to the list

        for (String o : this.getDoiList()) {
            try {
                OaDoiParser call = new OaDoiParser(o);
                if (call.getState()) { //means object was created successfully

                    for (int i = 0; i < call.getOaDois().size(); i++) {

                        DBHelper db = new DBHelper();
                        Session sess = db.getFactory().openSession();
                        Transaction tx = null;
                        try {
                            tx = sess.beginTransaction();

                            OaDoiLocationId iidd = new OaDoiLocationId(
                                    call.getOaDoiObjAtIndex(i).getId().getDoi(),
                                    call.getOaDoiObjAtIndex(i).getId().getId());

                            OaDoiLocation t = new OaDoiLocation(iidd,
                                    call.getOaDoiObjAtIndex(i).getVersion(),
                                    call.getOaDoiObjAtIndex(i).getEvidence(),
                                    call.getOaDoiObjAtIndex(i).getHostType(),
                                    call.getOaDoiObjAtIndex(i).getIsBest(),
                                    call.getOaDoiObjAtIndex(i).getLicense(),
                                    call.getOaDoiObjAtIndex(i).getUpdated(),
                                    call.getOaDoiObjAtIndex(i).getUrl(),
                                    call.getOaDoiObjAtIndex(i).getUrlForLandingPage(),
                                    call.getOaDoiObjAtIndex(i).getUrlForPdf(),
                                    call.getOaDoiObjAtIndex(i).getIsOa(),
                                    call.getOaDoiObjAtIndex(i).getJournalIsOa(),
                                    call.getOaDoiObjAtIndex(i).getJournalName(),
                                    call.getOaDoiObjAtIndex(i).getJournalPublisher()
                            );

                            sess.saveOrUpdate(t);
                            tx.commit();

                        }
                        catch (Exception e) {
                            if (tx != null) {
                                tx.rollback();
                            }
                            throw e;
                        }
                        finally {
                            sess.close();
                        }

                    }
                }
            }
            catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<Core> getCore()
    {
        return null;
    }

    @Override
    public void setCore(List<Core> CoreList)
    {
        //do nothing
    }
}
