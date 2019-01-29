/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.facade;

import bupt.pangu.entity.Team;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 46639
 */
@Stateless
public class TeamFacade extends AbstractFacade<Team> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeamFacade() {
        super(Team.class);
    }

    public Team findByTeamName(String teamName) {
        List<Team> teamList = em.createNamedQuery("Team.findByTeamName").setParameter("teamName", teamName).getResultList();
        if (teamList.isEmpty()) {
            System.out.println("没有查到新建团队的名字的团队");
            return null;
        }
        System.out.println(teamList.get(0).getTeamName());
        return teamList.get(0);
    }

}
