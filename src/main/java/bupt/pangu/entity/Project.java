/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.entity;

import bupt.pangu.controller.util.EntityInitializer;

import java.io.Serializable;
import java.util.Calendar;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ipidw
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p")
    , @NamedQuery(name = "Project.findByProjectId", query = "SELECT p FROM Project p WHERE p.projectId = :projectId")
    , @NamedQuery(name = "Project.findByProjectDate", query = "SELECT p FROM Project p WHERE p.projectDate = :projectDate")
    , @NamedQuery(name = "Project.findByProjectName", query = "SELECT p FROM Project p WHERE p.projectName = :projectName")
    , @NamedQuery(name = "Project.findByProjectState", query = "SELECT p FROM Project p WHERE p.projectState = :projectState")
    , @NamedQuery(name = "Project.findByProjectStartTime", query = "SELECT p FROM Project p WHERE p.projectStartTime = :projectStartTime")
    , @NamedQuery(name = "Project.findByProjectFinishTime", query = "SELECT p FROM Project p WHERE p.projectFinishTime = :projectFinishTime")
    , @NamedQuery(name = "Project.findByProjectTeamteamId", query = "SELECT p FROM Project p WHERE p.teamteamId = :teamteamId")})
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer projectId;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date projectDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String projectName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(nullable = false, length = 9)
    private String projectState;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date projectStartTime;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date projectFinishTime;
    @JoinColumn(name = "team_teamId", referencedColumnName = "teamId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Team teamteamId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectprojectId", fetch = FetchType.EAGER)
    private Collection<Comment> commentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectprojectId", fetch = FetchType.EAGER)
    private Collection<Plan> planCollection;

    public Project() {
        this.projectId = -1;
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        this.projectDate = time;
        this.projectState = "Unfinish";
        EntityInitializer.init(this);
    }

    public Project(Integer projectId) {
        EntityInitializer.init(this);
        this.projectId = projectId;
    }

    public Project(Integer projectId, Date projectDate, String projectName, String projectState, Date projectStartTime, Date projectFinishTime) {
        this.projectId = projectId;
        this.projectDate = projectDate;
        this.projectName = projectName;
        this.projectState = projectState;
        this.projectStartTime = projectStartTime;
        this.projectFinishTime = projectFinishTime;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getProjectDate() {
        return projectDate;
    }

    public void setProjectDate(Date projectDate) {
        this.projectDate = projectDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectState() {
        return projectState;
    }

    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }

    public Date getProjectStartTime() {
        return projectStartTime;
    }

    public void setProjectStartTime(Date projectStartTime) {
        this.projectStartTime = projectStartTime;
    }

    public Date getProjectFinishTime() {
        return projectFinishTime;
    }

    public void setProjectFinishTime(Date projectFinishTime) {
        this.projectFinishTime = projectFinishTime;
    }

    public Team getTeamteamId() {
        return teamteamId;
    }

    public void setTeamteamId(Team teamteamId) {
        this.teamteamId = teamteamId;
    }

    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    public Collection<Plan> getPlanCollection() {
        return planCollection;
    }

    public void setPlanCollection(Collection<Plan> planCollection) {
        this.planCollection = planCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectId != null ? projectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.projectId == null && other.projectId != null) || (this.projectId != null && !this.projectId.equals(other.projectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bupt.pangu.entity.Project[ projectId=" + projectId + " ]";
    }

}
