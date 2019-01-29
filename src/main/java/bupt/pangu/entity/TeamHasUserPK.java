/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ipidw
 */
@Embeddable
public class TeamHasUserPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "team_teamId", nullable = false)
    private int teamteamId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_userId", nullable = false)
    private int useruserId;

    public TeamHasUserPK() {
    }

    public TeamHasUserPK(int teamteamId, int useruserId) {
        this.teamteamId = teamteamId;
        this.useruserId = useruserId;
    }

    public int getTeamteamId() {
        return teamteamId;
    }

    public void setTeamteamId(int teamteamId) {
        this.teamteamId = teamteamId;
    }

    public int getUseruserId() {
        return useruserId;
    }

    public void setUseruserId(int useruserId) {
        this.useruserId = useruserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) teamteamId;
        hash += (int) useruserId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TeamHasUserPK)) {
            return false;
        }
        TeamHasUserPK other = (TeamHasUserPK) object;
        if (this.teamteamId != other.teamteamId) {
            return false;
        }
        if (this.useruserId != other.useruserId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bupt.pangu.entity.TeamHasUserPK[ teamteamId=" + teamteamId + ", useruserId=" + useruserId + " ]";
    }

}
