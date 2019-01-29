/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.controller;

import bupt.pangu.entity.Plan;
import bupt.pangu.entity.Project;
import bupt.pangu.entity.Task;
import bupt.pangu.entity.Team;
import bupt.pangu.entity.TeamHasUser;
import bupt.pangu.entity.TeamHasUserPK;
import bupt.pangu.entity.User;
import bupt.pangu.entity.util.JsfUtil;
import bupt.pangu.facade.PlanFacade;
import bupt.pangu.facade.ProjectFacade;
import bupt.pangu.facade.TaskFacade;
import bupt.pangu.facade.TeamHasUserFacade;
import bupt.pangu.facade.UserFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.faces.application.FacesMessage;
import javax.faces.event.AbortProcessingException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 46639
 */
@Default
@Named
@SessionScoped
public class TeamDetailController implements Serializable {

    @Inject
    TeamController teamController;
    @Inject
    UserController userController;
    @Inject
    ProjectDetailController projectDetailController;
    @Inject
    TeamDetailController teamDetailController;
    private List<Project> projectList;
    private Project selectedProject;
    private User creator;
    private List<User> adminList;
    private List<User> memberList;
    private User selectedMember;
    private User findedUser;
    private Project newProject;
    @EJB
    private UserFacade userFacade;
    @EJB
    private TeamHasUserFacade teamHasUserFacade;
    @EJB
    private ProjectFacade projectFacade;
    @EJB
    private PlanFacade planFacade;
    @EJB
    private TaskFacade taskFacade;
    private String userPNBfind;
    private Boolean notFoundPNB;
    private Boolean existUserInTeam;
    private FacesMessage message;

    public TeamDetailController() {
    }

    public User getFindedUser() {
        return findedUser;
    }

    public Boolean getNotFoundPNB() {
        return notFoundPNB;
    }

    public Boolean getExistUserInTeam() {
        return existUserInTeam;
    }

    public String getUserPNBfind() {
        return userPNBfind;
    }

    public void setUserPNBfind(String userPNBfind) {
        this.userPNBfind = userPNBfind;
    }

    public User getCreator() {
        Team tmpTeam = teamController.getSelectedTeam();
        List<TeamHasUser> tmpTeamHasUserList = teamHasUserFacade.findUserByTeam(tmpTeam.getTeamId());
        for (TeamHasUser teamHasUser : tmpTeamHasUserList) {
            if ("Creator".equals(teamHasUser.getUserRole())) {
                creator = (User) userFacade.find(teamHasUser.getTeamHasUserPK().getUseruserId());
            }
        }
        return creator;
    }
//    取创建者名第一个字用于显示

    public String getFirstLetter(String userName) {
        return userName.substring(0, 1);
    }

    public List<User> getAdminList() {
        // System.out.println(teamController.getSelectedTeam().getTeamName());
        List<TeamHasUser> tmpTeamHasUserCollection = teamHasUserFacade.findUserByTeam(teamController.getSelectedTeam().getTeamId());
        if (tmpTeamHasUserCollection.isEmpty()) {
            return null;
        }
        List<User> tmpAdminList = new ArrayList<>();
        for (TeamHasUser teamHasUser : tmpTeamHasUserCollection) {
            if ("Admin".equals(teamHasUser.getUserRole())) {
                tmpAdminList.add((User) userFacade.find(teamHasUser.getTeamHasUserPK().getUseruserId()));
            }
        }
        adminList = tmpAdminList;
        if (tmpAdminList.isEmpty()) {
            return null;
        }
        return tmpAdminList;
    }

    public List<User> getMemberList() {
        List<TeamHasUser> tmpTeamHasUserCollection = teamHasUserFacade.findUserByTeam(teamController.getSelectedTeam().getTeamId());
        if (tmpTeamHasUserCollection.isEmpty()) {
            return null;
        }
        List<User> tmpMemberList = new ArrayList<>();
        for (TeamHasUser teamHasUser : tmpTeamHasUserCollection) {
            if ("Member".equals(teamHasUser.getUserRole())) {
                tmpMemberList.add((User) userFacade.find(teamHasUser.getTeamHasUserPK().getUseruserId()));
            }
        }
        memberList = tmpMemberList;
        if (tmpMemberList.isEmpty()) {
            return null;
        }
        return memberList;
    }

    public List<User> getMember() {
        List<TeamHasUser> tmpTeamHasUserCollection = teamHasUserFacade.findUserByTeam(teamController.getSelectedTeam().getTeamId());
        if (tmpTeamHasUserCollection.isEmpty()) {
            return null;
        }
        List<User> tmpMemberList = new ArrayList<>();
        for (TeamHasUser teamHasUser : tmpTeamHasUserCollection) {
            tmpMemberList.add((User) userFacade.find(teamHasUser.getTeamHasUserPK().getUseruserId()));
        }
        memberList = tmpMemberList;
        if (tmpMemberList.isEmpty()) {
            return null;
        }
        return memberList;
    }

    public void setAdmin(User user) {
        findSelectedMember(user);
        setRole("Admin");
    }

    public void unSetAdmin(User user) {
        findSelectedMember(user);
        setRole("Member");
    }

    public void removeUserFromTeam(User selecteduser) {
        findSelectedMember(selecteduser);
        List<Project> tmpProjectList = projectFacade.findProjectListByTeam(teamController.getSelectedTeam());
        List<Task> taskToRemoveList = new ArrayList<>();

        if (tmpProjectList != null) {
            for (Project project : tmpProjectList) {
                List<Plan> tmpPlanList = planFacade.findPlanListByproject(project);
                if (tmpPlanList == null) {
                    continue;
                }
                for (Plan plan : tmpPlanList) {
                    List<Task> tmpTaskList = taskFacade.findTaskListByPlan(plan);
                    if (tmpTaskList == null) {
                        continue;
                    }
                    for (Task task : tmpTaskList) {
                        Collection<User> tmpUserCollection = task.getUserCollection();
                        if (tmpUserCollection.isEmpty()) {
                            continue;
                        }
                        for (User user : tmpUserCollection) {
                            if (Objects.equals(user.getUserId(), selectedMember.getUserId())) {
                                taskToRemoveList.add(task);
                            }
                        }
                    }
                }
            }
        }
        if (taskToRemoveList.isEmpty() == false) {
            for (Task task : taskToRemoveList) {
                taskFacade.removeUserFromTask(task, selectedMember);
            }
        }
        TeamHasUser teamHasUser = teamHasUserFacade.findByUseIdAndTeamId(selectedMember, teamController.getSelectedTeam());
        teamHasUserFacade.remove(teamHasUser);
    }

    public void setRole(String str) {
        TeamHasUser teamHasUser = teamHasUserFacade.findByUseIdAndTeamId(selectedMember, teamController.getSelectedTeam());
        teamHasUser.setUserRole(str);
        teamHasUserFacade.edit(teamHasUser);
    }

    public String findUser() {
        findedUser = userFacade.findByInputPNB(userPNBfind);
        if (findedUser == null) {
            notFoundPNB = true;
            existUserInTeam = false;
            return "findList_error.xhtml";
        }
        List<TeamHasUser> teamHasUserList = teamHasUserFacade.findTHSByUser(findedUser.getUserId());
        if (teamHasUserList == null) {
            return "findList_success.xhtml";
        }
        for (TeamHasUser teamHasUser : teamHasUserList) {
            if (teamHasUser.getTeamHasUserPK().getTeamteamId() == teamController.getSelectedTeam().getTeamId()) {
                existUserInTeam = true;
                return "findList_error.xhtml";
            }
        }
        return "listUserFound.xhtml";
    }

    public String addUser() {
        TeamHasUserPK teamHasUserPK = new TeamHasUserPK(teamController.getSelectedTeam().getTeamId(), findedUser.getUserId());
        TeamHasUser teamHasUser = new TeamHasUser(teamHasUserPK, "Member");
        teamHasUserFacade.create(teamHasUser);
        return "teamDetail.xhtml";
    }

    public void findSelectedMember(User user) throws AbortProcessingException {
        selectedMember = user;
    }

    public List<Project> getProjectList() {
        List<Project> projectList = projectFacade.findProjectListByTeam(teamController.getSelectedTeam());
        return projectList;
    }

    //新建项目
    public Project prepareProject() {
        if (newProject == null) {
            newProject = new Project();
        }
        return newProject;
    }

    public String createProject() {
        try {
            newProject.setTeamteamId(teamController.getSelectedTeam());
            projectFacade.create(newProject);
            newProject = projectFacade.findProjectListByName(newProject.getProjectName());
            return "teamDetail.xhtml";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(""
                + "/Bundle").getString("PersistenceErrorOccured"));
            return "team.xhtml";
        }

    }

    public void delPro(Project project) {
        List<Plan> plans = planFacade.findPlanListByproject(project);
        if (plans == null) {
            System.out.println("project没有plan");
            projectFacade.remove(project);
            return;
        }
        for (Plan plan : plans) {
            if (plan == null) {
                System.out.println("plan不存在");
                return;
            }
            projectDetailController.delPlan(plan);
        }
        projectFacade.remove(project);
    }

    public Boolean existUserInTeam(User user) {
        if (teamDetailController.getMember() == null) {
            return false;
        }
        return true;
    }
}
