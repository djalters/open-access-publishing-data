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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Cache_Core implements CacheInterface
{

    @XmlElement(name = "Core")
    private List<Core> CoreList = null;

    //not implemented by Cache
    private List<String[]> TitleList; //searching CORE by title

    private List<String> repoList; //search core by repo, to get location data

    public Cache_Core()
    {

    }

    public Cache_Core(List<String[]> TitleList)
    {
        //pass string array of BRAD ID, TITLE, DOI
        this.TitleList = TitleList;
    }

    @Override
    public List<Core> getCore()
    {
        return this.CoreList;
    }

    @Override
    public void setCore(List<Core> CoreList)
    {
        this.CoreList = CoreList;
    }

    @Override
    public List<OaDoiLocation> getOaDois()
    {
        return null;
    }

    @Override
    public void setOaDois(List<OaDoiLocation> OaDois)
    {
        //do nothing
    }

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
//        Not implemented
//        for (Core o : this.getCore()) {
//            DBHelper db = new DBHelper();
//            Session sess = db.getFactory().openSession();
//            Transaction tx = null;
//            try {
//                    tx = sess.beginTransaction();
//                    System.out.println(
//                            o.getId().getDoi() + " isOA: " + o.getIsOa());
//                    
//                    OaDoiLocation t = new OaDoiLocation(o.getId(),
//                            o.getVersion(),
//                            o.getEvidence(),
//                            o.getHostType(),
//                            o.getIsBest(),
//                            o.getLicense(),
//                            o.getUpdated(),
//                            o.getUrl(),
//                            o.getUrlForLandingPage(),
//                            o.getUrlForPdf(),
//                            o.getIsOa(),
//                            o.getJournalIsOa(),
//                            o.getJournalName(),
//                            o.getJournalPublisher()
//                    );
//                    
//                    sess.saveOrUpdate(t);
//                    tx.commit();
//
//            }
//            catch (Exception e) {
//                if (tx != null) {
//                    tx.rollback();
//                }
//                throw e;
//            }
//            finally {
//                sess.close();
//            }
//
//        }
    }

    @Override
    public void setObjectListFromDB(String optionalStringQueryParameters)
    {

        DBHelper db = new DBHelper();
        Session sess = db.getFactory().openSession();
        List<Core> Core = null;

        try {
            org.hibernate.Transaction tx = sess.beginTransaction();
            Query q = sess.createQuery(
                    "from Core " + optionalStringQueryParameters);
            Core = (List<Core>) q.list();

        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        this.setCore(Core);

        sess.close();

    }

    @Override
    public void marshallXMLCache(String outputPath)
    {
        try {

            Cache_Core oadoi = new Cache_Core();
            oadoi.setCore(this.getCore());

            String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(
                    new Date());

            JAXBContext jaxbContext = JAXBContext.newInstance(Cache_Core.class);
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
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return null;
    }

    @Override
    public void setBrBasicRpt_import(List<BrBasicRpt_import> BrBasicRpt_imports)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //do nothing
    }

    public List<String[]> getTitleList()
    {
        //not implemented by Cache
        return this.TitleList;
    }

    public void callCoreAPI() throws InterruptedException
    {
        //calls list of DOIs and runs through API, adds new ibjects to the list
        //this version requires that a list of DOIs are set to object - can't be done through the Cache, must be created seperately

        Long startTime = (Long) System.currentTimeMillis();
        Long elapsedTime = 0L;
        Long elapsedSeconds = 0L;
        //long secondsDisplay = elapsedSeconds % 60;
        //long elapsedMinutes = elapsedSeconds / 60;
        int reqCount = 0;

        for (int o = 0; o < this.getTitleList().size(); o++) {

            String[] entry = this.getTitleList().get(o);
            String bradId = entry[0];
            String articleTitle = entry[1];
            String doi = entry[2];

            elapsedTime = (Long) System.currentTimeMillis() - startTime;
            elapsedSeconds = elapsedTime / 1000; // i think should should discard the remainder

            if ((reqCount > 9) && (elapsedSeconds < 10)) {

                try {

                    TimeUnit.SECONDS.sleep(10 - elapsedSeconds);
                    //TimeUnit.SECONDS.sleep(5);
                    startTime = (Long) System.currentTimeMillis();
                    elapsedSeconds = 0L;
                    reqCount = 0;
                }
                catch (InterruptedException ex) {
                    //ex.printStackTrace();
                }

            }

            CoreParser call = new CoreParser(entry); //2 search by doi
            TimeUnit.SECONDS.sleep(1); //sleep for 1 seconds, maybe flooding the server, i dunno
            call.callApi();
            if (call.getExtraApiCalls() > 0) { //extra call, doi/title etc.
                reqCount = reqCount + 2;
            }
            else {
                reqCount = reqCount + 1;
            }

            if (call.getState()) { //means an object was created successfully
                for (int i = 0; i < call.getCores().size(); i++) {

                    DBHelper db = new DBHelper();
                    Session sess = db.getFactory().openSession();
                    Transaction tx = null;
                    try {
                        tx = sess.beginTransaction();

                        CoreId iidd = new CoreId(
                                call.getCoreObjAtIndex(i).getId().getCoreId(),
                                call.getCoreObjAtIndex(i).getId().getId(),
                                call.getCoreObjAtIndex(i).getId().getCoreRepoId());

                        Core t = new Core(iidd,
                                call.getCoreObjAtIndex(i).getOpenDoarId(),
                                call.getCoreObjAtIndex(i).getRepoName(),
                                call.getCoreObjAtIndex(i).getFullTextUrls(),
                                call.getCoreObjAtIndex(i).getOai(),
                                call.getCoreObjAtIndex(i).getTitleOrDoi()
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
    }

    public List<String> getRepoList()
    {
        return repoList;
    }

    public String getRepoListAtIndex(int index)
    {
        return repoList.get(index);
    }

    public void setRepoList(List<String> repoList)
    {
        this.repoList = repoList;
    }

    public void callCoreLocationAPI() throws InterruptedException
    {
        //Using list of DOIs, runs through API, adds new objects to the list
        //Commits transactions to database

        //Core constraint - No more than 10 requests in 10 seconds
        Long startTime = (Long) System.currentTimeMillis();
        Long elapsedTime = 0L;
        Long elapsedSeconds = 0L;

        int reqCount = 0;

        for (int o = 0; o < this.getRepoList().size(); o++) {
            String entry = this.getRepoListAtIndex(o);
            elapsedTime = (Long) System.currentTimeMillis() - startTime;
            elapsedSeconds = elapsedTime / 1000;

            if ((reqCount > 9) && (elapsedSeconds < 10)) {
                //Core constraint
                try {
                    TimeUnit.SECONDS.sleep(10 - elapsedSeconds);
                    startTime = (Long) System.currentTimeMillis();
                    elapsedSeconds = 0L;
                    reqCount = 0;
                }
                catch (InterruptedException ex) {
                    //ex.printStackTrace();
                }

            }

            CoreParser call = new CoreParser(); //object to retrieve results from the service
            call.setRepoentry(entry);
            TimeUnit.SECONDS.sleep(1);
            call.callLocationApi();
            if (call.getExtraApiCalls() > 0) {
                reqCount = reqCount + 2;
            }
            else {
                reqCount = reqCount + 1;
            }

            if (call.getState()) { //means an object was created successfully
                for (int i = 0; i < call.getCoreLocations().size(); i++) {
                    DBHelper db = new DBHelper();
                    Session sess = db.getFactory().openSession();
                    Transaction tx = null;
                    try {
                        tx = sess.beginTransaction();
                        CoreLocation t = new CoreLocation(
                                call.getCoreLocationObjAtIndex(i).getCoreRepoId(),
                                call.getCoreLocationObjAtIndex(i).getName(),
                                call.getCoreLocationObjAtIndex(i).getLatitude(),
                                call.getCoreLocationObjAtIndex(i).getLongitude(),
                                call.getCoreLocationObjAtIndex(i).getCountryCode()
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
    }
}
