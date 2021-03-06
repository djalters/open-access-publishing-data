package OAFS;
// Generated 02-Nov-2017 19:44:37 by Hibernate Tools 4.3.1



/**
 * CoreId generated by hbm2java
 */
public class CoreId  implements java.io.Serializable {


     private int coreId;
     private String id;
     private int coreRepoId;

    public CoreId() {
    }

    public CoreId(int coreId, String id, int coreRepoId) {
       this.coreId = coreId;
       this.id = id;
       this.coreRepoId = coreRepoId;
    }
   
    public int getCoreId() {
        return this.coreId;
    }
    
    public void setCoreId(int coreId) {
        this.coreId = coreId;
    }
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public int getCoreRepoId() {
        return this.coreRepoId;
    }
    
    public void setCoreRepoId(int coreRepoId) {
        this.coreRepoId = coreRepoId;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CoreId) ) return false;
		 CoreId castOther = ( CoreId ) other; 
         
		 return (this.getCoreId()==castOther.getCoreId())
 && ( (this.getId()==castOther.getId()) || ( this.getId()!=null && castOther.getId()!=null && this.getId().equals(castOther.getId()) ) )
 && (this.getCoreRepoId()==castOther.getCoreRepoId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getCoreId();
         result = 37 * result + ( getId() == null ? 0 : this.getId().hashCode() );
         result = 37 * result + this.getCoreRepoId();
         return result;
   }   


}


