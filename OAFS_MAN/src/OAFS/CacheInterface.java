/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OAFS;

import java.util.List;

/**
 *
 * @author walte
 */
public interface CacheInterface
{

    //set cache object collections    
    //For factory accessibility a list of each classtype is implemented by all caches
    //Cache Factory only supports XML unmarshalling classes. Web services not supported
    List<JmInvoice> getJM_invoices();

    List<JmArticle> getJM_articles();

    List<OaDoiLocation> getOaDois();

    List<BrBasicRpt_import> getBrBasicRpt_import();

    List<Core> getCore();

    //cache object collections
    void setJM_invoices(List<JmInvoice> JM_invoices);

    void setJM_articles(List<JmArticle> JM_articles);

    void setOaDois(List<OaDoiLocation> OaDois);

    void setBrBasicRpt_import(List<BrBasicRpt_import> BrBasicRpt_imports);

    void setCore(List<Core> CoreList);

    //Database operations
    void commitObjectListToDB(); //appropriate Hibernate query configured for each classtype commits transactions

    void setObjectListFromDB(String optionalStringQueryParameters); //Sets list attributes from Hibernate query.

    void marshallXMLCache(String outputPath);
}
