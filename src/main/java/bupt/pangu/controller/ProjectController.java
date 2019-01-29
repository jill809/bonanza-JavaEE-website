/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.controller;

import bupt.pangu.entity.Project;
import bupt.pangu.entity.Team;
import bupt.pangu.entity.TeamHasUser;
import bupt.pangu.entity.User;
import bupt.pangu.facade.PlanFacade;
import bupt.pangu.facade.ProjectFacade;
import bupt.pangu.facade.TeamHasUserFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 46639
 */
@Named("projectController")
@SessionScoped
@Default
public class ProjectController implements Serializable {

    @EJB
    private ProjectFacade projectFacade;
    @EJB
    private TeamHasUserFacade teamHasUserFacade;
    @EJB
    private PlanFacade planFacade;
    private TeamHasUser teamHasUser;
    private User user;
    private Team team;
    @Inject
    private UserController userController;
    @Inject
    private ProjectDetailController projectDetailController;
    private Project project;

    public ProjectController() {

    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        System.out.println(project.getProjectName());
        this.project = project;
    }

    public String findSelectedProject(Project project) {
        this.project = project;
        return "projectDetail";
    }

    public TeamHasUser getTeamHasUser() {
        TeamHasUser tmpTeamHasUser = teamHasUserFacade.findByUseIdAndTeamId(user, team);
        teamHasUser = tmpTeamHasUser;
        return teamHasUser;
    }

    private TeamHasUserFacade getTeamHasUserFacade() {
        return teamHasUserFacade;
    }

    private ProjectFacade getProjectFacade() {
        return projectFacade;
    }

    public List<Project> getManagedProject() {
        return getProjectFacade().FindProjectByTeamList(teamHasUserFacade.findTeamManagerByUserId(userController.getUserId()));
    }

    public List<Project> getMemberedProject() {
        return getProjectFacade().FindProjectByTeamList(teamHasUserFacade.findTeamMemberedByUserId(userController.getUserId()));
    }

    public void update() {
        try {
            projectFacade.edit(project);
        } catch (Exception e) {
        }
    }

}
