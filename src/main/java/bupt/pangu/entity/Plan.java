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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
    @NamedQuery(name = "Plan.findAll", query = "SELECT p FROM Plan p")
    , @NamedQuery(name = "Plan.findByPlanId", query = "SELECT p FROM Plan p WHERE p.planId = :planId")
    , @NamedQuery(name = "Plan.findByPlanDate", query = "SELECT p FROM Plan p WHERE p.planDate = :planDate")
    , @NamedQuery(name = "Plan.findByPlanName", query = "SELECT p FROM Plan p WHERE p.planName = :planName")
    , @NamedQuery(name = "Plan.findByPlanStartTime", query = "SELECT p FROM Plan p WHERE p.planStartTime = :planStartTime")
    , @NamedQuery(name = "Plan.findByPlanFinishTime", query = "SELECT p FROM Plan p WHERE p.planFinishTime = :planFinishTime")
    , @NamedQuery(name = "Plan.findByProjectprojectId", query = "SELECT p FROM Plan p WHERE p.projectprojectId = :projectprojectId")})
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer planId;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date planDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String planName;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 0, max = 65535)
    @Column(nullable = false, length = 65535)
    private String planIntro;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date planStartTime;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date planFinishTime;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planplanId", fetch = FetchType.EAGER)
    private Collection<Task> taskCollection;
    @JoinColumn(name = "project_projectId", referencedColumnName = "projectId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Project projectprojectId;

    public Plan() {
        EntityInitializer.init(this);
    }

    public Plan(Integer planId) {
        EntityInitializer.init(this);
        this.planId = planId;
    }

    public Plan(Integer planId, Date planDate, String planName, String planIntro, Date planStartTime, Date planFinishTime) {
        this.planId = planId;
        this.planDate = planDate;
        this.planName = planName;
        this.planIntro = planIntro;
        this.planStartTime = planStartTime;
        this.planFinishTime = planFinishTime;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanIntro() {
        return planIntro;
    }

    public void setPlanIntro(String planIntro) {
        this.planIntro = planIntro;
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanFinishTime() {
        return planFinishTime;
    }

    public void setPlanFinishTime(Date planFinishTime) {
        this.planFinishTime = planFinishTime;
    }

    public Collection<Task> getTaskCollection() {
        return taskCollection;
    }

    public void setTaskCollection(Collection<Task> taskCollection) {
        this.taskCollection = taskCollection;
    }

    public Project getProjectprojectId() {
        return projectprojectId;
    }

    public void setProjectprojectId(Project projectprojectId) {
        this.projectprojectId = projectprojectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planId != null ? planId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plan)) {
            return false;
        }
        Plan other = (Plan) object;
        if ((this.planId == null && other.planId != null) || (this.planId != null && !this.planId.equals(other.planId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bupt.pangu.entity.Plan[ planId=" + planId + " ]";
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (Exception exp) {
        }
        return null;
    }
}
