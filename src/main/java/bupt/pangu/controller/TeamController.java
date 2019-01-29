/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.controller;

import bupt.pangu.entity.Project;
import bupt.pangu.entity.Team;
import bupt.pangu.entity.TeamHasUser;
import bupt.pangu.entity.TeamHasUserPK;
import bupt.pangu.entity.User;
import bupt.pangu.entity.util.JsfUtil;
import bupt.pangu.facade.ProjectFacade;
import bupt.pangu.facade.TeamFacade;
import bupt.pangu.facade.TeamHasUserFacade;
import bupt.pangu.facade.UserFacade;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author 46639
 */
@Default
@Named
@SessionScoped
public class TeamController implements Serializable {

    @EJB
    private UserFacade userFacade;
    @EJB
    private TeamHasUserFacade teamHasUserFacade;
    @EJB
    private TeamFacade teamFacade;
    @EJB
    private ProjectFacade projectFacade;
    @Inject
    UserController userController;
    @Inject
    TeamDetailController teamDetailController;
    private List<Team> teamList;
    private Team selectedTeam = null;
    private Team newTeam;
    private User user;
    private String userRole;
    private String teamNameToFind;

    public TeamController() {
    }

    public void setTeamNameToFind(String teamNameToFind) {
        this.teamNameToFind = teamNameToFind;
    }

    public String getTeamNameToFind() {
        return teamNameToFind;
    }

    public List<Team> getTeamList() {
//        System.out.print("进入getTeamList中");
//        System.out.print("user:" + userController.getUserId());
        teamList = teamHasUserFacade.findTeamByUserId(userController.getUserId());
        return teamList;
    }

    public Team getSelectedTeam() {
        return selectedTeam;
    }

    public String getUserRole() {
        TeamHasUser teamHasUser = teamHasUserFacade.findByUseIdAndTeamId(userController.getUser(), selectedTeam);
        userRole = teamHasUser.getUserRole();
        return userRole;
    }

    public String findSelectedTeam(Team team) {
        selectedTeam = team;
        return "teamDetail";
    }

//    添加新团队
    private TeamFacade teamFacade() {
        return teamFacade;
    }

    public Team prepareCreate() {
        if (newTeam == null) {
            newTeam = new Team();
        }
        return newTeam;
    }

    public User getUser() {
        User tmpUser = (User) userFacade.find(1);
        user = tmpUser;
        return user;
    }

    //    显示新建团队成功
    public void showMessage() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "您已经成功新建项目", "Echoes in eternity.");
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    public String createTeam() {
        //在team表中添加
        teamFacade.create(newTeam);
        //在关系表中添加,先获取当前user，然后
        // newTeam = teamFacade.findByTeamName(newTeam.getTeamName());
//        System.out.println("newTeam的名字" + newTeam.getTeamName());
//        System.out.println("newTeam的名字" + newTeam.getTeamId());
        TeamHasUserPK newTeamUserPK = new TeamHasUserPK(newTeam.getTeamId(), userController.getUserId());
        TeamHasUser newTeamUser = new TeamHasUser(newTeamUserPK, "Creator");
        teamHasUserFacade.create(newTeamUser);
        // showMessage();
        newTeam = null;
//        System.out.println("把newTeam置为null了！");
        return "team.xhtml";

    }

    public void delTeam(Team team) {
        List<Project> projects = projectFacade.findProjectListByTeam(team);
        if (projects == null) {
            teamFacade.remove(team);
            return;
        }
        for (Project project : projects) {
            if (project == null) {
                return;
            }
            teamDetailController.delPro(project);
        }
        teamFacade.remove(team);

    }

    public void update() {
        try {
            System.out.println(selectedTeam.getTeamName());
            teamFacade().edit(selectedTeam);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public String findTeam() {
        Team findedTeam = teamFacade.findByTeamName(teamNameToFind);
        if (findedTeam == null) {
            return "joinTeam_error_notexist";
        }

        List<TeamHasUser> teamHasUserList = teamHasUserFacade.findTHSByUser(userController.getUser().getUserId());
        if (teamHasUserList == null) {
            TeamHasUserPK teamHasUserPK = new TeamHasUserPK(findedTeam.getTeamId(), userController.getUser().getUserId());
            TeamHasUser teamHasUser = new TeamHasUser(teamHasUserPK, "Member");
            teamHasUserFacade.create(teamHasUser);
            return "team";
        } else {
            for (TeamHasUser teamHasUser : teamHasUserList) {
                if (teamHasUser.getTeamHasUserPK().getTeamteamId() == findedTeam.getTeamId()) {
                    return "joinTeam_error_already";
                }
            }
        }
        TeamHasUserPK teamHasUserPK = new TeamHasUserPK(findedTeam.getTeamId(), userController.getUser().getUserId());
        TeamHasUser teamHasUser = new TeamHasUser(teamHasUserPK, "Member");
        teamHasUserFacade.create(teamHasUser);
        return "team";

    }
}
