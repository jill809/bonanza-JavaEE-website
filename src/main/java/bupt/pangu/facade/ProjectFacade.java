/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.facade;

import bupt.pangu.entity.Project;
import bupt.pangu.entity.Team;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 46639
 */
@Stateless
public class ProjectFacade extends AbstractFacade<Project> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjectFacade() {
        super(Project.class);
    }

    public List<Project> FindProjectByTeamList(List<Team> teamList) {
        if (teamList == null) {
            return null;
        }
        List<Project> projectList = new ArrayList<>();
        for (Team team : teamList) {
            List<Project> tmpProjectList = em.createNamedQuery("Project.findByProjectTeamteamId").setParameter("teamteamId", team).getResultList();
            for (Project project : tmpProjectList) {
                projectList.add(project);
            }
        }
        if (projectList.isEmpty()) {
            return null;
        }
        return projectList;
    }

    public List<Project> findProjectListByTeam(Team teamteamId) {
        List<Project> projectList = em.createNamedQuery("Project.findByProjectTeamteamId").setParameter("teamteamId", teamteamId).getResultList();
        if (projectList.isEmpty()) {
            return null;
        }
        return projectList;
    }
    
    public Project findProjectListByName(String name){
        List<Project> projectList = em.createNamedQuery("Project.findByProjectName").setParameter("projectName", name).getResultList();
        if(projectList.isEmpty()){
            return null;
        }
        Project current = projectList.get(0);
        return current;
    }

}
