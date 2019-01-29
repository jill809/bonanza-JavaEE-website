/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ipidw
 */
@Entity
@Table(name = "team_has_user")
@NamedQueries({
    @NamedQuery(name = "TeamHasUser.findAll", query = "SELECT t FROM TeamHasUser t")
    , @NamedQuery(name = "TeamHasUser.findByTeamteamId", query = "SELECT t FROM TeamHasUser t WHERE t.teamHasUserPK.teamteamId = :teamteamId")
    , @NamedQuery(name = "TeamHasUser.findByUseruserId", query = "SELECT t FROM TeamHasUser t WHERE t.teamHasUserPK.useruserId = :useruserId")
    , @NamedQuery(name = "TeamHasUser.findByUserRole", query = "SELECT t FROM TeamHasUser t WHERE t.userRole = :userRole")
    , @NamedQuery(name = "TeamHasUser.findByUseruserIdTeamteamId", query = "SELECT t FROM TeamHasUser t WHERE t.teamHasUserPK.useruserId = :useruserId AND t.teamHasUserPK.teamteamId = :teamteamId")})
public class TeamHasUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TeamHasUserPK teamHasUserPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(nullable = false, length = 8)
    private String userRole;
    @JoinColumn(name = "team_teamId", referencedColumnName = "teamId", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Team team;
    @JoinColumn(name = "user_userId", referencedColumnName = "userId", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

    public TeamHasUser() {
    }

    public TeamHasUser(TeamHasUserPK teamHasUserPK) {
        this.teamHasUserPK = teamHasUserPK;
    }

    public TeamHasUser(TeamHasUserPK teamHasUserPK, String userRole) {
        this.teamHasUserPK = teamHasUserPK;
        this.userRole = userRole;
    }

    public TeamHasUser(int teamteamId, int useruserId) {
        this.teamHasUserPK = new TeamHasUserPK(teamteamId, useruserId);
    }

    public TeamHasUserPK getTeamHasUserPK() {
        return teamHasUserPK;
    }

    public void setTeamHasUserPK(TeamHasUserPK teamHasUserPK) {
        this.teamHasUserPK = teamHasUserPK;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teamHasUserPK != null ? teamHasUserPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TeamHasUser)) {
            return false;
        }
        TeamHasUser other = (TeamHasUser) object;
        if ((this.teamHasUserPK == null && other.teamHasUserPK != null) || (this.teamHasUserPK != null && !this.teamHasUserPK.equals(other.teamHasUserPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bupt.pangu.entity.TeamHasUser[ teamHasUserPK=" + teamHasUserPK + " ]";
    }

}
