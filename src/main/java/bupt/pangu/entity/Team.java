/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.entity;

import bupt.pangu.controller.util.EntityInitializer;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ipidw
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"teamName"})})
@NamedQueries({
    @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t")
    , @NamedQuery(name = "Team.findByTeamId", query = "SELECT t FROM Team t WHERE t.teamId = :teamId")
    , @NamedQuery(name = "Team.findByTeamName", query = "SELECT t FROM Team t WHERE t.teamName = :teamName")
    , @NamedQuery(name = "Team.findByTeamDate", query = "SELECT t FROM Team t WHERE t.teamDate = :teamDate")})
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer teamId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String teamName;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date teamDate;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 0, max = 65535)
    @Column(nullable = false, length = 65535)
    private String teamIntro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teamteamId", fetch = FetchType.EAGER)
    private Collection<Project> projectCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team", fetch = FetchType.EAGER)
    private Collection<TeamHasUser> teamHasUserCollection;
    @Lob
    private byte[] teamPic;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teamteamId", fetch = FetchType.EAGER)
    private Collection<Photo> photoCollection;

    public Team() {
        EntityInitializer.init(this);
    }

    public Team(Integer teamId) {
        EntityInitializer.init(this);
        this.teamId = teamId;
    }

    public Team(Integer teamId, Date teamDate, String teamName, String teamIntro) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamDate = teamDate;
        this.teamIntro = teamIntro;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Date getTeamDate() {
        return teamDate;
    }

    public void setTeamDate(Date teamDate) {
        this.teamDate = teamDate;
    }

    public String getTeamIntro() {
        return teamIntro;
    }

    public void setTeamIntro(String teamIntro) {
        this.teamIntro = teamIntro;
    }

    public Collection<Project> getProjectCollection() {
        return projectCollection;
    }

    public void setProjectCollection(Collection<Project> projectCollection) {
        this.projectCollection = projectCollection;
    }

    public Collection<TeamHasUser> getTeamHasUserCollection() {
        return teamHasUserCollection;
    }

    public void setTeamHasUserCollection(Collection<TeamHasUser> teamHasUserCollection) {
        this.teamHasUserCollection = teamHasUserCollection;
    }

    public byte[] getTeamPic() {
        return teamPic;
    }

    public void setTeamPic(byte[] teamPic) {
        this.teamPic = teamPic;
    }

    public Collection<Photo> getPhotoCollection() {
        return photoCollection;
    }

    public void setPhotoCollection(Collection<Photo> photoCollection) {
        this.photoCollection = photoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teamId != null ? teamId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team) object;
        if ((this.teamId == null && other.teamId != null) || (this.teamId != null && !this.teamId.equals(other.teamId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bupt.pangu.entity.Team[ teamId=" + teamId + " ]";
    }

}
