/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.facade;

import bupt.pangu.entity.Task;
import bupt.pangu.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 46639
 */
@Stateful
@SessionScoped
public class UserFacade extends AbstractFacade<User> implements java.io.Serializable {

    private static final Logger logger = Logger.getLogger(UserFacade.class.getName());

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    void initLog() {
        logger.log(Level.INFO, "Current entity manager: {0}", em);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    public User findByUserPNB(User user) {
        List<User> users = em.createNamedQuery("User.findByUserPhoneNum").setParameter("userPhoneNum", user.getUserPhoneNum()).getResultList();
        if (users.isEmpty()) {
            return null;
        }

        User userNow = users.get(0);
        return userNow;

    }

    public User findByInputPNB(String phnumber) {
        List<User> users = em.createNamedQuery("User.findByUserPhoneNum").setParameter("userPhoneNum", phnumber).getResultList();
        if (users.isEmpty()) {
            return null;
        }
        User user = users.get(0);
        return user;
    }

    public List<Task> findTaskByUser(User user) {
        if (user.getTaskCollection().isEmpty()) {
            System.out.println("getTaskCollection为empty");
            return null;
        }
        List<Task> taskList = new ArrayList<Task>();
        for (Task task : user.getTaskCollection()) {
            taskList.add(task);
        }
        return taskList;
    }

}
