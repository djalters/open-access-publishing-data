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
import java.util.concurrent.TimeUnit;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Cache_SherpaRef implements CacheInterface
{

    @XmlElement(name = "Core")
    private List<SherpaRef> SherpaRefList = null;

    //not implemented by Cache
    private List<String[]> TitleList; //searching SRef by ISSN

    public Cache_SherpaRef()
    {

    }

    public Cache_SherpaRef(List<String[]> TitleList)
    {

        this.TitleList = TitleList;
    }

    public List<SherpaRef> getSherpaRefList()
    {
        return SherpaRefList;
    }

    public void setSherpaRefList(List<SherpaRef> SherpaRefList)
    {
        this.SherpaRefList = SherpaRefList;
    }

    @Override
    public List<Core> getCore()
    {
        return null;
    }

    @Override
    public void setCore(List<Core> CoreList)
    {
        //this.CoreList = CoreList;
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
//        not implemented
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

            Cache_SherpaRef sref = new Cache_SherpaRef();
            sref.setCore(this.getCore());

            String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(
                    new Date());

            JAXBContext jaxbContext = JAXBContext.newInstance(
                    Cache_SherpaRef.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(sref, new File(
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

    public List<String[]> getTitleList()
    {
        //not implemented by Cache
        return this.TitleList;
    }

    public void callAPI() throws InterruptedException, ParseException
    {
        //TitleList represents ISSN inputs

        Long startTime = (Long) System.currentTimeMillis();
        Long elapsedTime = 0L;
        Long elapsedSeconds = 0L;

        int reqCount = 0;

        for (int o = 0; o < this.getTitleList().size(); o++) {

            String[] entry = this.getTitleList().get(o);
            SherpaRefParser call = new SherpaRefParser(entry); //2 search by issn
            TimeUnit.SECONDS.sleep(1); //sleep for 1 seconds
            try {
                call.callApi();
                if (call.getState()) { //means an object was created successfully
                    for (int i = 0; i < call.getSherpaRefs().size(); i++) {
                        DBHelper db = new DBHelper();
                        Session sess = db.getFactory().openSession();
                        Transaction tx = null;
                        try {
                            tx = sess.beginTransaction();

                            SherpaRefId iidd = new SherpaRefId(
                                    call.getObjAtIndex(i).getId().getIssnKey(),
                                    call.getObjAtIndex(i).getId().getOpenAccessRoute(),
                                    call.getObjAtIndex(i).getId().getArticleVersion(),
                                    call.getObjAtIndex(i).getId().getRepoType());

                            SherpaRef t = new SherpaRef(iidd,
                                    call.getObjAtIndex(i).getIssn1(),
                                    call.getObjAtIndex(i).getIssn2(),
                                    call.getObjAtIndex(i).getTitle(),
                                    call.getObjAtIndex(i).getPublisher(),
                                    call.getObjAtIndex(i).getDateLastRevised(),
                                    call.getObjAtIndex(i).getSrOutputType(),
                                    call.getObjAtIndex(i).getRepoTypeOtherTxt(),
                                    call.getObjAtIndex(i).getEmbargoInMonths(),
                                    call.getObjAtIndex(i).getNoDepositWithinThreeMonths(),
                                    call.getObjAtIndex(i).getInformationUrls());

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
            catch (org.json.simple.parser.ParseException ex) {
//                ex.printStackTrace();
                System.out.println(
                        "processing error - Failed trying to parse a non-numeric argument, 7");
            }
        }
    }
}
