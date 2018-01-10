/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OAFS_main;

import OAFS_main.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author walte
 */
public class DBHelper {

    Session session = null;
    SessionFactory factory = null;

    public Session getSession()
    {
        return session;
    }
    
    public SessionFactory getFactory()
    {
        return factory;
    }

    public DBHelper() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        this.factory = HibernateUtil.getSessionFactory();
    }
    

}
