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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import OAFS_main.DBHelper;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@XmlRootElement(name = "JM_articles")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cache_JmArticle implements CacheInterface
{

    @XmlElement(name = "JM_article")
    private List<JmArticle> JM_articles = null;

    @Override
    public List<JmArticle> getJM_articles()
    {
        return JM_articles;
    }

    @Override
    public void setJM_articles(List<JmArticle> JM_articles)
    {
        this.JM_articles = JM_articles;
    }

    //other types must return null
    @Override
    public List<JmInvoice> getJM_invoices()
    {
        return null;
    }

    @Override
    public void setJM_invoices(List<JmInvoice> JM_invoices)
    {
        //do nothing
    }

    @Override
    public void commitObjectListToDB() throws HibernateException
    {

        for (JmArticle j : this.getJM_articles()) {
            DBHelper db = new DBHelper();
            Session sess = db.getFactory().openSession();
            Transaction tx = null;
            try {
                tx = sess.beginTransaction();
                JmArticle t = new JmArticle(j.getJmId(), j.getName(), j.getIds(),
                        j.getJournalTitle(), j.getPublisher(),
                        j.getWorkflowComplete());
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
        List<JmArticle> jmList = null;

        try {
            org.hibernate.Transaction tx = sess.beginTransaction();
            Query q = sess.createQuery(
                    "from JmArticle " + optionalStringQueryParameters);
            jmList = (List<JmArticle>) q.list();
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        this.setJM_articles(jmList);
        sess.close();
    }

    @Override
    public void marshallXMLCache(String outputPath)
    {
        try {

            Cache_JmArticle JmArticle = new Cache_JmArticle();
            JmArticle.setJM_articles(this.getJM_articles());
            String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(
                    new Date());

            JAXBContext jaxbContext = JAXBContext.newInstance(
                    Cache_JmArticle.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(JmArticle, new File(
                    outputPath + "\\JmArticle_data_" + timeStamp + ".xml"));
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

    @Override
    public List<OaDoiLocation> getOaDois()
    {
        return null;
    }

    @Override
    public List<Core> getCore()
    {
        return null;
    }

    @Override
    public void setOaDois(List<OaDoiLocation> OaDois)
    {
        //do nothing
    }

    @Override
    public void setCore(List<Core> CoreList)
    {
        //do nothing
    }



}
