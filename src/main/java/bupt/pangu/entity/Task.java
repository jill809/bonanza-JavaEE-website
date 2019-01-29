/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.entity;

import bupt.pangu.controller.util.EntityInitializer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t")
    , @NamedQuery(name = "Task.findByTaskId", query = "SELECT t FROM Task t WHERE t.taskId = :taskId")
    , @NamedQuery(name = "Task.findByTaskDate", query = "SELECT t FROM Task t WHERE t.taskDate = :taskDate")
    , @NamedQuery(name = "Task.findByTaskName", query = "SELECT t FROM Task t WHERE t.taskName = :taskName")
    , @NamedQuery(name = "Task.findByTaskState", query = "SELECT t FROM Task t WHERE t.taskState = :taskState")
    , @NamedQuery(name = "Task.findByPlanplanId", query = "SELECT t FROM Task t WHERE t.planplanId = :planplanId")
    ,@NamedQuery(name = "Task.findByUseruserId", query = "SELECT t FROM Task t join t.userCollection u WHERE u.userId=:userId")})
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer taskId;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date taskDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String taskName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(nullable = false, length = 9)
    private String taskState;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 0, max = 65535)
    @Column(nullable = false, length = 65535)
    private String taskIntro;
    @JoinTable(name = "task_has_user", joinColumns = {
        @JoinColumn(name = "task_taskId", referencedColumnName = "taskId", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "user_userId", referencedColumnName = "userId", nullable = false)})
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<User> userCollection;
    @JoinColumn(name = "plan_planId", referencedColumnName = "planId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Plan planplanId;

    public Task() {
        EntityInitializer.init(this);
    }

    public Task(Integer taskId) {
        EntityInitializer.init(this);
        this.taskId = taskId;
    }

    public Task(Integer taskId, Date taskDate, String taskName, String taskState, String taskIntro) {
        this.taskId = taskId;
        this.taskDate = taskDate;
        this.taskName = taskName;
        this.taskState = taskState;
        this.taskIntro = taskIntro;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getTaskIntro() {
        return taskIntro;
    }

    public void setTaskIntro(String taskIntro) {
        this.taskIntro = taskIntro;
    }

    public Collection<User> getUserCollection() {
        if (userCollection == null) {
            userCollection = new ArrayList<User>();
        }
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    public Plan getPlanplanId() {
        return planplanId;
    }

    public void setPlanplanId(Plan planplanId) {
        this.planplanId = planplanId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskId != null ? taskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.taskId == null && other.taskId != null) || (this.taskId != null && !this.taskId.equals(other.taskId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bupt.pangu.entity.Task[ taskId=" + taskId + " ]";
    }

}
