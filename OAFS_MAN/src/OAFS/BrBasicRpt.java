package OAFS;
// Generated 03-Oct-2017 13:20:33 by Hibernate Tools 4.3.1


import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * BrBasicRpt generated by hbm2java
 */
@XmlRootElement(name = "dataroot")
@XmlAccessorType (XmlAccessType.FIELD)
//@XmlType(propOrder={"_xFEFF_ID", "Name", "Email", "User_x0027_s_x0020_Proprietary_x0020_ID", "Primary_x0020_group", "Publication_x0020_type", "Date_x0020_of_x0020_acceptance_x0020_OR_x0020_Acceptance_x0020_Date_x0020_OR_x0020_MR_x0020_selected", "DOI", "eISSN", "ISBN-10_x0020_OR_x0020_ISBN", "ISBN-13", "ISSN", "Published_x0020_proceedings_x0020_OR_x0020_Journal", "Online_x0020_publication_x0020_date", "Book_x0020_title_x0020_OR_x0020_Report_x0020_title", "Publication_x0020_date_x0020_OR_x0020_Publication_x0020_Date_x0020_OR_x0020_Presented_x0020_date_x0020_OR_x0020_Date_x0020_a", "Status_x0020_OR_x0020_Publication_x0020_Status", "Publisher_x0020_licence", "Title", "ADMIN_x0020_ONLY_x0020_-_x0020_APC", "Do_x0020_you_x0020_want_x0020_to_x0020_apply_x0020_for_x0020_APC_x0020_funding_x003F_", "APC_x0020_comments", "BURA_x0020_url_x0020_OR_x0020_BURA_x0020_URL", "Corresponding_x0020_author_x0020_institution", "Data_x0020_access_x0020_statement", "ADMIN_x0020_ONLY_x0020_-_x0020_JISC_x0020_ID", "Times_x0020_cited_x0020__x0028_Web_x0020_of_x0020_Science_x0029_", "Times_x0020_cited_x0020__x0028_Scopus_x0029_", "Times_x0020_cited_x0020__x0028_Europe_x0020_PubMed_x0020_Central_x0029_", "Indexed_x0020_in_x0020_DOAJ", "DOAJ_x0020_CC_x0020_Licence", "Romeo_x0020_colour", "Brunel_x0020_DSpace_x0020_Repository_x0020_public_x0020_URL", "Deposited_x0020_to_x0020_Brunel_x0020_DSpace_x0020_Repository", "Item_x0020_status_x0020_in_x0020_Brunel_x0020_DSpace_x0020_Repository", "Embargo_x0020_period_x0020_requested_x0020_in_x0020_Brunel_x0020_DSpace_x0020_Repository", "arXiv_x0020_record_x0020_exists_x003F_", "Crossref_x0020_record_x0020_exists_x003F_", "DBLP_x0020_record_x0020_exists_x003F_", "Europe_x0020_PubMed_x0020_Central_x0020_record_x0020_exists_x003F_", "figshare_x0020_for_x0020_Institutions_x0020_record_x0020_exists_x003F_", "PubMed_x0020_record_x0020_exists_x003F", "RePEc_x0020_record_x0020_exists_x003F_", "SSRN_x0020_record_x0020_exists_x003F_"})
public class BrBasicRpt  implements java.io.Serializable {

     private BrBasicRptId id;
     private String name;
     private String email;
     private String primaryGroup;
     private String publicationType;
     private Date dateOfAcceptanceOrAcceptanceDateOrMrSelected;
     private String doi;
     private String eissn;
     private String isbn10OrIsbn;
     private String isbn13;
     private String issn;
     private String publishedProceedingsOrJournal;
     private Date onlinePublicationDate;
     private String bookTitleOrReportTitle;
     private String publicationDate;
     private String statusOrPublicationStatus;
     private String publisherLicence;
     private String title;
     private String adminOnlyApc;
     private String doYouWantToApplyForApcFunding;
     private String apcComments;
     private String buraUrlOrBuraUrl;
     private String correspondingAuthorInstitution;
     private String dataAccessStatement;
     private String adminOnlyJiscId;
     private Short timesCited_wos;
     private Short timesCited_sco;
     private Short timesCited_empc;
     private String indexedInDoaj;
     private String doajCcLicence;
     private String romeoColour;
     private String brunelDspaceRepositoryPublicUrl;
     private String depositedToBrunelDspaceRepository;
     private String itemStatusInBrunelDspaceRepository;
     private String embargoPeriodRequestedInBrunelDspaceRepository;
     private Byte arXivRecordExists;
     private Byte crossrefRecordExists;
     private Byte dblpRecordExists;
     private Byte europePubMedCentralRecordExists;
     private Byte figshareForInstitutionsRecordExists;
     private Byte pubMedRecordExists;
     private Byte rePecRecordExists;
     private Byte ssrnRecordExists;

    public BrBasicRpt() {
    }


    public BrBasicRpt(BrBasicRptId id) {
        this.id = id;
    }
    public BrBasicRpt(BrBasicRptId id, String name, String email, String primaryGroup, String publicationType, Date dateOfAcceptanceOrAcceptanceDateOrMrSelected, String doi, String eissn, String isbn10OrIsbn, String isbn13, String issn, String publishedProceedingsOrJournal, Date onlinePublicationDate, String bookTitleOrReportTitle, String publicationDate, String statusOrPublicationStatus, String publisherLicence, String title, String adminOnlyApc, String doYouWantToApplyForApcFunding, String apcComments, String buraUrlOrBuraUrl, String correspondingAuthorInstitution, String dataAccessStatement, String adminOnlyJiscId, Short timesCited_wos, Short timesCited_sco, Short timesCited_empc, String indexedInDoaj, String doajCcLicence, String romeoColour, String brunelDspaceRepositoryPublicUrl, String depositedToBrunelDspaceRepository, String itemStatusInBrunelDspaceRepository, String embargoPeriodRequestedInBrunelDspaceRepository, Byte arXivRecordExists, Byte crossrefRecordExists, Byte dblpRecordExists, Byte europePubMedCentralRecordExists, Byte figshareForInstitutionsRecordExists, Byte pubMedRecordExists, Byte rePecRecordExists, Byte ssrnRecordExists) {
       this.id = id;
       this.name = name;
       this.email = email;
       this.primaryGroup = primaryGroup;
       this.publicationType = publicationType;
       this.dateOfAcceptanceOrAcceptanceDateOrMrSelected = dateOfAcceptanceOrAcceptanceDateOrMrSelected;
       this.doi = doi;
       this.eissn = eissn;
       this.isbn10OrIsbn = isbn10OrIsbn;
       this.isbn13 = isbn13;
       this.issn = issn;
       this.publishedProceedingsOrJournal = publishedProceedingsOrJournal;
       this.onlinePublicationDate = onlinePublicationDate;
       this.bookTitleOrReportTitle = bookTitleOrReportTitle;
       this.publicationDate = publicationDate;
       this.statusOrPublicationStatus = statusOrPublicationStatus;
       this.publisherLicence = publisherLicence;
       this.title = title;
       this.adminOnlyApc = adminOnlyApc;
       this.doYouWantToApplyForApcFunding = doYouWantToApplyForApcFunding;
       this.apcComments = apcComments;
       this.buraUrlOrBuraUrl = buraUrlOrBuraUrl;
       this.correspondingAuthorInstitution = correspondingAuthorInstitution;
       this.dataAccessStatement = dataAccessStatement;
       this.adminOnlyJiscId = adminOnlyJiscId;
       this.timesCited_wos = timesCited_wos;
       this.timesCited_sco = timesCited_sco;
       this.timesCited_empc = timesCited_empc;
       this.indexedInDoaj = indexedInDoaj;
       this.doajCcLicence = doajCcLicence;
       this.romeoColour = romeoColour;
       this.brunelDspaceRepositoryPublicUrl = brunelDspaceRepositoryPublicUrl;
       this.depositedToBrunelDspaceRepository = depositedToBrunelDspaceRepository;
       this.itemStatusInBrunelDspaceRepository = itemStatusInBrunelDspaceRepository;
       this.embargoPeriodRequestedInBrunelDspaceRepository = embargoPeriodRequestedInBrunelDspaceRepository;
       this.arXivRecordExists = arXivRecordExists;
       this.crossrefRecordExists = crossrefRecordExists;
       this.dblpRecordExists = dblpRecordExists;
       this.europePubMedCentralRecordExists = europePubMedCentralRecordExists;
       this.figshareForInstitutionsRecordExists = figshareForInstitutionsRecordExists;
       this.pubMedRecordExists = pubMedRecordExists;
       this.rePecRecordExists = rePecRecordExists;
       this.ssrnRecordExists = ssrnRecordExists;
    }
    
    public BrBasicRptId getId() {
        return this.id;
    }
    
    public void setId(BrBasicRptId id) {
        this.id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPrimaryGroup() {
        return this.primaryGroup;
    }
    
    public void setPrimaryGroup(String primaryGroup) {
        this.primaryGroup = primaryGroup;
    }
    
    public String getPublicationType() {
        return this.publicationType;
    }
    
    public void setPublicationType(String publicationType) {
        this.publicationType = publicationType;
    }
    
    public Date getDateOfAcceptanceOrAcceptanceDateOrMrSelected() {
        return this.dateOfAcceptanceOrAcceptanceDateOrMrSelected;
    }
    
    public void setDateOfAcceptanceOrAcceptanceDateOrMrSelected(Date dateOfAcceptanceOrAcceptanceDateOrMrSelected) {
        this.dateOfAcceptanceOrAcceptanceDateOrMrSelected = dateOfAcceptanceOrAcceptanceDateOrMrSelected;
    }
    
    public String getDoi() {
        return this.doi;
    }
    
    public void setDoi(String doi) {
        this.doi = doi;
    }
    
    public String getEissn() {
        return this.eissn;
    }
    
    public void setEissn(String eissn) {
        this.eissn = eissn;
    }
    
    public String getIsbn10OrIsbn() {
        return this.isbn10OrIsbn;
    }
    
    public void setIsbn10OrIsbn(String isbn10OrIsbn) {
        this.isbn10OrIsbn = isbn10OrIsbn;
    }
    
    public String getIsbn13() {
        return this.isbn13;
    }
    
    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }
    
    public String getIssn() {
        return this.issn;
    }
    
    public void setIssn(String issn) {
        this.issn = issn;
    }
    
    public String getPublishedProceedingsOrJournal() {
        return this.publishedProceedingsOrJournal;
    }
    
    public void setPublishedProceedingsOrJournal(String publishedProceedingsOrJournal) {
        this.publishedProceedingsOrJournal = publishedProceedingsOrJournal;
    }
    
    public Date getOnlinePublicationDate() {
        return this.onlinePublicationDate;
    }
    
    public void setOnlinePublicationDate(Date onlinePublicationDate) {
        this.onlinePublicationDate = onlinePublicationDate;
    }
    
    public String getBookTitleOrReportTitle() {
        return this.bookTitleOrReportTitle;
    }
    
    public void setBookTitleOrReportTitle(String bookTitleOrReportTitle) {
        this.bookTitleOrReportTitle = bookTitleOrReportTitle;
    }
    
    public String getPublicationDate() {
        return this.publicationDate;
    }
    
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
    
    public String getStatusOrPublicationStatus() {
        return this.statusOrPublicationStatus;
    }
    
    public void setStatusOrPublicationStatus(String statusOrPublicationStatus) {
        this.statusOrPublicationStatus = statusOrPublicationStatus;
    }
    
    public String getPublisherLicence() {
        return this.publisherLicence;
    }
    
    public void setPublisherLicence(String publisherLicence) {
        this.publisherLicence = publisherLicence;
    }
    
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAdminOnlyApc() {
        return this.adminOnlyApc;
    }
    
    public void setAdminOnlyApc(String adminOnlyApc) {
        this.adminOnlyApc = adminOnlyApc;
    }
    public String getDoYouWantToApplyForApcFunding() {
        return this.doYouWantToApplyForApcFunding;
    }
    
    public void setDoYouWantToApplyForApcFunding(String doYouWantToApplyForApcFunding) {
        this.doYouWantToApplyForApcFunding = doYouWantToApplyForApcFunding;
    }
    public String getApcComments() {
        return this.apcComments;
    }
    
    public void setApcComments(String apcComments) {
        this.apcComments = apcComments;
    }
    public String getBuraUrlOrBuraUrl() {
        return this.buraUrlOrBuraUrl;
    }
    
    public void setBuraUrlOrBuraUrl(String buraUrlOrBuraUrl) {
        this.buraUrlOrBuraUrl = buraUrlOrBuraUrl;
    }
    public String getCorrespondingAuthorInstitution() {
        return this.correspondingAuthorInstitution;
    }
    
    public void setCorrespondingAuthorInstitution(String correspondingAuthorInstitution) {
        this.correspondingAuthorInstitution = correspondingAuthorInstitution;
    }
    public String getDataAccessStatement() {
        return this.dataAccessStatement;
    }
    
    public void setDataAccessStatement(String dataAccessStatement) {
        this.dataAccessStatement = dataAccessStatement;
    }
    public String getAdminOnlyJiscId() {
        return this.adminOnlyJiscId;
    }
    
    public void setAdminOnlyJiscId(String adminOnlyJiscId) {
        this.adminOnlyJiscId = adminOnlyJiscId;
    }
    public Short gettimesCited_wos() {
        return this.timesCited_wos;
    }
    
    public void settimesCited_wos(Short timesCited_wos) {
        this.timesCited_wos = timesCited_wos;
    }
    public Short gettimesCited_sco() {
        return this.timesCited_sco;
    }
    
    public void settimesCited_sco(Short timesCited_sco) {
        this.timesCited_sco = timesCited_sco;
    }
    public Short gettimesCited_empc() {
        return this.timesCited_empc;
    }
    
    public void settimesCited_empc(Short timesCited_empc) {
        this.timesCited_empc = timesCited_empc;
    }
    public String getIndexedInDoaj() {
        return this.indexedInDoaj;
    }
    
    public void setIndexedInDoaj(String indexedInDoaj) {
        this.indexedInDoaj = indexedInDoaj;
    }
    public String getDoajCcLicence() {
        return this.doajCcLicence;
    }
    
    public void setDoajCcLicence(String doajCcLicence) {
        this.doajCcLicence = doajCcLicence;
    }
    public String getRomeoColour() {
        return this.romeoColour;
    }
    
    public void setRomeoColour(String romeoColour) {
        this.romeoColour = romeoColour;
    }

    public String getBrunelDspaceRepositoryPublicUrl() {
        return this.brunelDspaceRepositoryPublicUrl;
    }
    
    public void setBrunelDspaceRepositoryPublicUrl(String brunelDspaceRepositoryPublicUrl) {
        this.brunelDspaceRepositoryPublicUrl = brunelDspaceRepositoryPublicUrl;
    }
    public String getDepositedToBrunelDspaceRepository() {
        return this.depositedToBrunelDspaceRepository;
    }
    
    public void setDepositedToBrunelDspaceRepository(String depositedToBrunelDspaceRepository) {
        this.depositedToBrunelDspaceRepository = depositedToBrunelDspaceRepository;
    }
    public String getItemStatusInBrunelDspaceRepository() {
        return this.itemStatusInBrunelDspaceRepository;
    }
    
    public void setItemStatusInBrunelDspaceRepository(String itemStatusInBrunelDspaceRepository) {
        this.itemStatusInBrunelDspaceRepository = itemStatusInBrunelDspaceRepository;
    }
    public String getEmbargoPeriodRequestedInBrunelDspaceRepository() {
        return this.embargoPeriodRequestedInBrunelDspaceRepository;
    }
    
    public void setEmbargoPeriodRequestedInBrunelDspaceRepository(String embargoPeriodRequestedInBrunelDspaceRepository) {
        this.embargoPeriodRequestedInBrunelDspaceRepository = embargoPeriodRequestedInBrunelDspaceRepository;
    }
    public Byte getArXivRecordExists() {
        return this.arXivRecordExists;
    }
    
    public void setArXivRecordExists(Byte arXivRecordExists) {
        this.arXivRecordExists = arXivRecordExists;
    }
    public Byte getCrossrefRecordExists() {
        return this.crossrefRecordExists;
    }
    
    public void setCrossrefRecordExists(Byte crossrefRecordExists) {
        this.crossrefRecordExists = crossrefRecordExists;
    }
    public Byte getDblpRecordExists() {
        return this.dblpRecordExists;
    }
    
    public void setDblpRecordExists(Byte dblpRecordExists) {
        this.dblpRecordExists = dblpRecordExists;
    }
    public Byte getEuropePubMedCentralRecordExists() {
        return this.europePubMedCentralRecordExists;
    }
    
    public void setEuropePubMedCentralRecordExists(Byte europePubMedCentralRecordExists) {
        this.europePubMedCentralRecordExists = europePubMedCentralRecordExists;
    }
    public Byte getFigshareForInstitutionsRecordExists() {
        return this.figshareForInstitutionsRecordExists;
    }
    
    public void setFigshareForInstitutionsRecordExists(Byte figshareForInstitutionsRecordExists) {
        this.figshareForInstitutionsRecordExists = figshareForInstitutionsRecordExists;
    }

    public Byte getPubMedRecordExists() {
        return this.pubMedRecordExists;
    }
    
    public void setPubMedRecordExists(Byte pubMedRecordExists) {
        this.pubMedRecordExists = pubMedRecordExists;
    }

    public Byte getRePecRecordExists() {
        return this.rePecRecordExists;
    }
    
    public void setRePecRecordExists(Byte rePecRecordExists) {
        this.rePecRecordExists = rePecRecordExists;
    }
    public Byte getSsrnRecordExists() {
        return this.ssrnRecordExists;
    }
    
    public void setSsrnRecordExists(Byte ssrnRecordExists) {
        this.ssrnRecordExists = ssrnRecordExists;
    }
}


