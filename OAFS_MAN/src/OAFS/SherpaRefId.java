package OAFS;
// Generated 13-Nov-2017 00:07:24 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * SherpaRefId generated by hbm2java
 */
@Embeddable
public class SherpaRefId  implements java.io.Serializable {


     private int issnKey;
     private String repoType;
     private String articleVersion;
     private String openAccessRoute;

    public SherpaRefId() {
    }

    public SherpaRefId(int issnKey, String repoType, String articleVersion, String openAccessRoute) {
       this.issnKey = issnKey;
       this.repoType = repoType;
       this.articleVersion = articleVersion;
       this.openAccessRoute = openAccessRoute;
    }
   


    @Column(name="issn_key", nullable=false)
    public int getIssnKey() {
        return this.issnKey;
    }
    
    public void setIssnKey(int issnKey) {
        this.issnKey = issnKey;
    }


    @Column(name="repo_type", nullable=false, length=45)
    public String getRepoType() {
        return this.repoType;
    }
    
    public void setRepoType(String repoType) {
        this.repoType = repoType;
    }


    @Column(name="article_version", nullable=false, length=45)
    public String getArticleVersion() {
        return this.articleVersion;
    }
    
    public void setArticleVersion(String articleVersion) {
        this.articleVersion = articleVersion;
    }


    @Column(name="open_access_route", nullable=false, length=45)
    public String getOpenAccessRoute() {
        return this.openAccessRoute;
    }
    
    public void setOpenAccessRoute(String openAccessRoute) {
        this.openAccessRoute = openAccessRoute;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SherpaRefId) ) return false;
		 SherpaRefId castOther = ( SherpaRefId ) other; 
         
		 return (this.getIssnKey()==castOther.getIssnKey())
 && ( (this.getRepoType()==castOther.getRepoType()) || ( this.getRepoType()!=null && castOther.getRepoType()!=null && this.getRepoType().equals(castOther.getRepoType()) ) )
 && ( (this.getArticleVersion()==castOther.getArticleVersion()) || ( this.getArticleVersion()!=null && castOther.getArticleVersion()!=null && this.getArticleVersion().equals(castOther.getArticleVersion()) ) )
 && ( (this.getOpenAccessRoute()==castOther.getOpenAccessRoute()) || ( this.getOpenAccessRoute()!=null && castOther.getOpenAccessRoute()!=null && this.getOpenAccessRoute().equals(castOther.getOpenAccessRoute()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIssnKey();
         result = 37 * result + ( getRepoType() == null ? 0 : this.getRepoType().hashCode() );
         result = 37 * result + ( getArticleVersion() == null ? 0 : this.getArticleVersion().hashCode() );
         result = 37 * result + ( getOpenAccessRoute() == null ? 0 : this.getOpenAccessRoute().hashCode() );
         return result;
   }   


}


