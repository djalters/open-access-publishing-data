package OAFS;
// Generated 02-Nov-2017 19:44:37 by Hibernate Tools 4.3.1



/**
 * Core generated by hbm2java
 */
public class Core  implements java.io.Serializable {


     private CoreId id;
     private Integer openDoarId;
     private String repoName;
     private String fullTextUrls;
     private String oai;
     private String titleOrDoi;

    public Core() {
    }

	
    public Core(CoreId id) {
        this.id = id;
    }
    public Core(CoreId id, Integer openDoarId, String repoName, String fullTextUrls, String oai, String titleOrDoi) {
       this.id = id;
       this.openDoarId = openDoarId;
       this.repoName = repoName;
       this.fullTextUrls = fullTextUrls;
       this.oai = oai;
       this.titleOrDoi = titleOrDoi;
    }
   
    public CoreId getId() {
        return this.id;
    }
    
    public void setId(CoreId id) {
        this.id = id;
    }
    public Integer getOpenDoarId() {
        return this.openDoarId;
    }
    
    public void setOpenDoarId(Integer openDoarId) {
        this.openDoarId = openDoarId;
    }
    public String getRepoName() {
        return this.repoName;
    }
    
    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }
    public String getFullTextUrls() {
        return this.fullTextUrls;
    }
    
    public void setFullTextUrls(String fullTextUrls) {
        this.fullTextUrls = fullTextUrls;
    }
    public String getOai() {
        return this.oai;
    }
    
    public void setOai(String oai) {
        this.oai = oai;
    }

    public String getTitleOrDoi() {
        return this.titleOrDoi;
    }
    
    public void setTitleOrDoi(String titleOrDoi) {
        this.titleOrDoi = titleOrDoi;
    }


}


