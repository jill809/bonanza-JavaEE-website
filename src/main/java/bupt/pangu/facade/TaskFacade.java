/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.facade;

import bupt.pangu.entity.Plan;
import bupt.pangu.entity.Task;
import bupt.pangu.entity.User;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 46639
 */
@Stateful
public class TaskFacade extends AbstractFacade<Task> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TaskFacade() {
        super(Task.class);
    }

    public void Update(Task task, String str) {

        task.setTaskState(str);
    }

    public void removeUserFromTask(Task task, User user) {
        task.getUserCollection().remove(user);
        em.merge(task);
    }

    public List<Task> findTaskListByPlan(Plan plan) {
        List<Task> taskList = em.createNamedQuery("Task.findByPlanplanId").setParameter("planplanId", plan).getResultList();
        if (taskList.isEmpty()) {
            return null;
        }
        return taskList;
    }

    public Task findTaskListByname(String taskName) {
        List<Task> tasks = em.createNamedQuery("Task.findByTaskName").setParameter("taskName", taskName).getResultList();
        if (tasks.isEmpty()) {
            return null;
        }
        Task userNow = tasks.get(0);
        return userNow;
    }

    public List<Task> findTaskListByUser(User user) {
        List<Task> tasks = em.createNamedQuery("Task.findByUseruserId").setParameter("userId", user.getUserId()).getResultList();
        if (tasks.isEmpty()) {
            return null;
        }
        return tasks;
    }

}
