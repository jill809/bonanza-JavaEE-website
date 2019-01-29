/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.facade;

import bupt.pangu.entity.Plan;
import bupt.pangu.entity.Project;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jill
 */
@Stateless
public class PlanFacade extends AbstractFacade<Plan> {

    @PersistenceContext(unitName = "abonaza1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanFacade() {
        super(Plan.class);
    }

    public List<Plan> findPlanListByproject(Project project) {
        List<Plan> planList = em.createNamedQuery("Plan.findByProjectprojectId").setParameter("projectprojectId", project).getResultList();
        if (planList.isEmpty()) {
            return null;
        }
        return planList;
    }

    public Plan findPlanListByname(String planName) {
        List<Plan> plans = em.createNamedQuery("Plan.findByPlanName").setParameter("planName", planName).getResultList();
        if (plans.isEmpty()) {
            return null;
        }
        Plan userNow = plans.get(0);
        return userNow;
    }
}
