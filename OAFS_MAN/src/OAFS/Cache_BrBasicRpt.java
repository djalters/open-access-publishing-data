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
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@XmlRootElement(name = "dataroot")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cache_BrBasicRpt implements CacheInterface
{

    @XmlElement(name = "BR_BASIC_RPT_DATA")
    private List<BrBasicRpt_import> BrBasicRpts_import = null;

    @Override
    public List<BrBasicRpt_import> getBrBasicRpt_import()
    {
        return BrBasicRpts_import;
    }

    @Override
    public void setBrBasicRpt_import(List<BrBasicRpt_import> BrBasicRpt_imports)
    {
        this.BrBasicRpts_import = BrBasicRpt_imports;
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

        for (BrBasicRpt_import b : this.getBrBasicRpt_import()) {
            DBHelper db = new DBHelper();
            Session sess = db.getFactory().openSession();
            Transaction tx = null;
            try {
                tx = sess.beginTransaction();

                BrBasicRptId i = new BrBasicRptId(b.getId(),
                        b.getUserProprietaryId());

                BrBasicRpt t = new BrBasicRpt(
                        i,
                        b.getName(),
                        b.getEmail(),
                        b.getPrimaryGroup(),
                        b.getPublicationType(),
                        b.getDateOfAcceptanceOrAcceptanceDateOrMrSelected(),
                        b.getDoi(),
                        b.getEissn(),
                        b.getIsbn10OrIsbn(),
                        b.getIsbn13(),
                        b.getIssn(),
                        b.getPublishedProceedingsOrJournal(),
                        b.getOnlinePublicationDate(),
                        b.getBookTitleOrReportTitle(),
                        b.getPublicationDate(),
                        b.getStatusOrPublicationStatus(),
                        b.getPublisherLicence(),
                        b.getTitle(),
                        b.getAdminOnlyApc(),
                        b.getDoYouWantToApplyForApcFunding(),
                        b.getApcComments(),
                        b.getBuraUrlOrBuraUrl(),
                        b.getCorrespondingAuthorInstitution(),
                        b.getDataAccessStatement(),
                        b.getAdminOnlyJiscId(),
                        b.gettimesCited_wos(),
                        b.gettimesCited_sco(),
                        b.gettimesCited_empc(),
                        b.getIndexedInDoaj(),
                        b.getDoajCcLicence(),
                        b.getRomeoColour(),
                        b.getBrunelDspaceRepositoryPublicUrl(),
                        b.getDepositedToBrunelDspaceRepository(),
                        b.getItemStatusInBrunelDspaceRepository(),
                        b.getEmbargoPeriodRequestedInBrunelDspaceRepository(),
                        b.getArXivRecordExists(),
                        b.getCrossrefRecordExists(),
                        b.getDblpRecordExists(),
                        b.getEuropePubMedCentralRecordExists(),
                        b.getFigshareForInstitutionsRecordExists(),
                        b.getPubMedRecordExists(),
                        b.getRePecRecordExists(),
                        b.getSsrnRecordExists()
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
        List<BrBasicRpt> jmList = null;

        try {
            org.hibernate.Transaction tx = sess.beginTransaction();
            Query q = sess.createQuery(
                    "from BrBasicRpt " + optionalStringQueryParameters);
            jmList = (List<BrBasicRpt>) q.list();
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        sess.close();
    }

    @Override
    public void marshallXMLCache(String outputPath)
    {
        //Not yet implemented
//        try {
//
//            Cache_BrBasicRpt BrBasicRpt = new Cache_BrBasicRpt();
//            BrBasicRpt.setBrBasicRpt(this.getBrBasicRpt());
//            String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(
//                    new Date());
//
//            JAXBContext jaxbContext = JAXBContext.newInstance(Cache_BrBasicRpt.class);
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//            jaxbMarshaller.marshal(BrBasicRpt, new File(
//                    outputPath + "\\BrBasicRpt_data_" + timeStamp + ".xml"));
//        }
//        catch (JAXBException ex) {
//            ex.printStackTrace();
//        }
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
