/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.facade;

import bupt.pangu.entity.Team;
import bupt.pangu.entity.TeamHasUser;
import bupt.pangu.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 46639
 */
@Stateful
public class TeamHasUserFacade extends AbstractFacade<TeamHasUser> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeamHasUserFacade() {
        super(TeamHasUser.class);
    }

    public List<Team> findTeamByUserId(Integer userId) {
        List<TeamHasUser> teamHasUserList = em.createNamedQuery("TeamHasUser.findByUseruserId").setParameter("useruserId", userId).getResultList();
        if (teamHasUserList.isEmpty()) {
            return null;
        }
        List<Team> teamList = new ArrayList<>();
        for (TeamHasUser teamHasUser : teamHasUserList) {
            List<Team> tmpTeamList = em.createNamedQuery("Team.findByTeamId").setParameter("teamId", teamHasUser.getTeamHasUserPK().getTeamteamId()).getResultList();
            if (tmpTeamList.isEmpty()) {
                continue;
            }
//            System.out.println("进入teamHasUserFacade,并获取到" + tmpTeamList.get(0).getTeamName());
            teamList.add(tmpTeamList.get(0));
        }
        if (teamList.isEmpty()) {
            return null;
        }
        return teamList;
    }

    public TeamHasUser findByUseIdAndTeamId(User user, Team team) {

        List<TeamHasUser> teamHasUserList = em.createNamedQuery("TeamHasUser.findByUseruserIdTeamteamId")
            .setParameter("useruserId", user.getUserId()).setParameter("teamteamId", team.getTeamId()).getResultList();
        TeamHasUser teamHasUser = teamHasUserList.get(0);
        return teamHasUser;
    }

    public User findCreatorByTeamId(Team team) {
        List<TeamHasUser> teamHasUserList = em.createNamedQuery("TeamHasUser.findByTeamteamId").setParameter("teamteamId", team.getTeamId()).getResultList();
        for (TeamHasUser teamHasUser : teamHasUserList) {
            if ("Creator".equals(teamHasUser.getUserRole())) {
                return teamHasUser.getUser();
            }
        }
        return null;
    }

    public List<User> findAdminByTeamId(Team team) {
        List<User> adminUserList = new ArrayList<User>();
        List<TeamHasUser> teamHasUserList = em.createNamedQuery("TeamHasUser.findByTeamteamId").setParameter("teamteamId", team.getTeamId()).getResultList();
        for (TeamHasUser teamHasUser : teamHasUserList) {
            if ("Admin".equals(teamHasUser.getUserRole())) {
                adminUserList.add(teamHasUser.getUser());
            }
        }
        return adminUserList;
    }

    public List<Team> findTeamManagerByUserId(Integer id) {
        List<Team> managedTeam = new ArrayList<>();
        List<TeamHasUser> teamHasUserList = em.createNamedQuery("TeamHasUser.findByUseruserId").setParameter("useruserId", id).getResultList();
        if (teamHasUserList.isEmpty()) {
            return null;
        }
        teamHasUserList.stream().filter((teamHasUser) -> (("Admin".equals(teamHasUser.getUserRole())) || ("Creator".equals(teamHasUser.getUserRole())))).forEachOrdered((TeamHasUser teamHasUser) -> {
            managedTeam.add(teamHasUser.getTeam());
        });
        if (managedTeam.isEmpty()) {
            return null;
        }
        return managedTeam;
    }

    public List<Team> findTeamMemberedByUserId(Integer id) {
        List<Team> memberedTeam = new ArrayList<>();
        List<TeamHasUser> teamHasUserList = em.createNamedQuery("TeamHasUser.findByUseruserId").setParameter("useruserId", id).getResultList();
        if (teamHasUserList.isEmpty()) {
            return null;
        }
        teamHasUserList.stream().filter((teamHasUser) -> ("Member".equals(teamHasUser.getUserRole()))).forEachOrdered((teamHasUser) -> {
            memberedTeam.add(teamHasUser.getTeam());
        });
        if (memberedTeam.isEmpty()) {
            return null;
        }
        return memberedTeam;
    }

    public List<TeamHasUser> findUserByTeam(Integer id) {
        List<TeamHasUser> teamHasUsers = em.createNamedQuery("TeamHasUser.findByTeamteamId").setParameter("teamteamId", id).getResultList();
        if (teamHasUsers.isEmpty()) {
            return null;
        }
        return teamHasUsers;
    }

    public List<TeamHasUser> findTHSByUser(Integer id) {
        List<TeamHasUser> teamHasUsers = em.createNamedQuery("TeamHasUser.findByUseruserId").setParameter("useruserId", id).getResultList();
        if (teamHasUsers.isEmpty()) {
            return null;
        }
        return teamHasUsers;
    }
}
