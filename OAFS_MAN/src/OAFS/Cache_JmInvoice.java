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

@XmlRootElement(name = "JM_invoices")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cache_JmInvoice implements CacheInterface
{

    @XmlElement(name = "JM_invoice")
    private List<JmInvoice> JmInvoices = null;

    @Override
    public List<JmInvoice> getJM_invoices()
    {
        return JmInvoices;
    }

    @Override
    public void setJM_invoices(List<JmInvoice> JmInvoices)
    {
        this.JmInvoices = JmInvoices;
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
    public void commitObjectListToDB() throws HibernateException
    {

        for (JmInvoice j : this.getJM_invoices()) {
            DBHelper db = new DBHelper();
            Session sess = db.getFactory().openSession();
            Transaction tx = null;
            try {
                tx = sess.beginTransaction();
                JmInvoice t = new JmInvoice(j.getJmInvoiceId(), j.getName(),
                        j.getPassedToFinance(),
                        j.getPaymentMethod(), j.getPaymentStatus(),
                        j.getInvoiceFileName());
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
        List<JmInvoice> jmList = null;

        try {
            org.hibernate.Transaction tx = sess.beginTransaction();
            Query q = sess.createQuery(
                    "from JmInvoice " + optionalStringQueryParameters);
            jmList = (List<JmInvoice>) q.list();
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        this.setJM_invoices(jmList);
        sess.close();

    }
    
    @Override
    public void marshallXMLCache(String outputPath)
    {
        try {

            Cache_JmInvoice JmInvoice = new Cache_JmInvoice();
            JmInvoice.setJM_invoices(this.getJM_invoices());
            String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(
                    new Date());

            JAXBContext jaxbContext = JAXBContext.newInstance(
                    Cache_JmInvoice.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(JmInvoice, new File(
                    outputPath + "\\JmInvoice_data_" + timeStamp + ".xml"));
        }
        catch (JAXBException ex) {
            ex.printStackTrace();
        }
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
