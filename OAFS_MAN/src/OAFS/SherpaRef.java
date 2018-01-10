package OAFS;
// Generated 13-Nov-2017 00:07:24 by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SherpaRef generated by hbm2java
 */
@Entity
@Table(name="sherpa_ref"
    ,catalog="oafs"
)
public class SherpaRef  implements java.io.Serializable {


     private SherpaRefId id;
     private String issn1;
     private String issn2;
     private String title;
     private String publisher;
     private Date dateLastRevised;
     private String srOutputType;
     private String repoTypeOtherTxt;
     private Integer embargoInMonths;
     private String noDepositWithinThreeMonths;
     private String informationUrls;

    public SherpaRef() {
    }

	
    public SherpaRef(SherpaRefId id) {
        this.id = id;
    }
    public SherpaRef(SherpaRefId id, String issn1, String issn2, String title, String publisher, Date dateLastRevised, String srOutputType, String repoTypeOtherTxt, Integer embargoInMonths, String noDepositWithinThreeMonths, String informationUrls) {
       this.id = id;
       this.issn1 = issn1;
       this.issn2 = issn2;
       this.title = title;
       this.publisher = publisher;
       this.dateLastRevised = dateLastRevised;
       this.srOutputType = srOutputType;
       this.repoTypeOtherTxt = repoTypeOtherTxt;
       this.embargoInMonths = embargoInMonths;
       this.noDepositWithinThreeMonths = noDepositWithinThreeMonths;
       this.informationUrls = informationUrls;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="issnKey", column=@Column(name="issn_key", nullable=false) ), 
        @AttributeOverride(name="repoType", column=@Column(name="repo_type", nullable=false, length=45) ), 
        @AttributeOverride(name="articleVersion", column=@Column(name="article_version", nullable=false, length=45) ), 
        @AttributeOverride(name="openAccessRoute", column=@Column(name="open_access_route", nullable=false, length=45) ) } )
    public SherpaRefId getId() {
        return this.id;
    }
    
    public void setId(SherpaRefId id) {
        this.id = id;
    }

    
    @Column(name="issn_1", length=9)
    public String getIssn1() {
        return this.issn1;
    }
    
    public void setIssn1(String issn1) {
        this.issn1 = issn1;
    }

    
    @Column(name="issn_2", length=9)
    public String getIssn2() {
        return this.issn2;
    }
    
    public void setIssn2(String issn2) {
        this.issn2 = issn2;
    }

    
    @Column(name="title", length=300)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    
    @Column(name="publisher", length=300)
    public String getPublisher() {
        return this.publisher;
    }
    
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="date_last_revised", length=10)
    public Date getDateLastRevised() {
        return this.dateLastRevised;
    }
    
    public void setDateLastRevised(Date dateLastRevised) {
        this.dateLastRevised = dateLastRevised;
    }

    
    @Column(name="SR_output_type", length=45)
    public String getSrOutputType() {
        return this.srOutputType;
    }
    
    public void setSrOutputType(String srOutputType) {
        this.srOutputType = srOutputType;
    }

    
    @Column(name="repo_type_other_txt", length=45)
    public String getRepoTypeOtherTxt() {
        return this.repoTypeOtherTxt;
    }
    
    public void setRepoTypeOtherTxt(String repoTypeOtherTxt) {
        this.repoTypeOtherTxt = repoTypeOtherTxt;
    }

    
    @Column(name="embargo_in_months")
    public Integer getEmbargoInMonths() {
        return this.embargoInMonths;
    }
    
    public void setEmbargoInMonths(Integer embargoInMonths) {
        this.embargoInMonths = embargoInMonths;
    }

    
    @Column(name="no_deposit_within_three_months", length=4)
    public String getNoDepositWithinThreeMonths() {
        return this.noDepositWithinThreeMonths;
    }
    
    public void setNoDepositWithinThreeMonths(String noDepositWithinThreeMonths) {
        this.noDepositWithinThreeMonths = noDepositWithinThreeMonths;
    }

    
    @Column(name="information_urls", length=65535)
    public String getInformationUrls() {
        return this.informationUrls;
    }
    
    public void setInformationUrls(String informationUrls) {
        this.informationUrls = informationUrls;
    }




}


