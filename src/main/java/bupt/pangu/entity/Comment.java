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
    @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c")
    , @NamedQuery(name = "Comment.findByCommentId", query = "SELECT c FROM Comment c WHERE c.commentId = :commentId")
    , @NamedQuery(name = "Comment.findByCommentDate", query = "SELECT c FROM Comment c WHERE c.commentDate = :commentDate")})
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer commentId;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentDate;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 0, max = 65535)
    @Column(nullable = false, length = 65535)
    private String commentText;
    @OneToMany(mappedBy = "commentReplyTo", fetch = FetchType.EAGER)
    private Collection<Comment> commentCollection;
    @JoinColumn(name = "commentReplyTo", referencedColumnName = "commentId")
    @ManyToOne(fetch = FetchType.EAGER)
    private Comment commentReplyTo;
    @JoinColumn(name = "project_projectId", referencedColumnName = "projectId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Project projectprojectId;
    @JoinColumn(name = "user_userId", referencedColumnName = "userId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User useruserId;

    public Comment() {
        EntityInitializer.init(this);
    }

    public Comment(Integer commentId) {
        EntityInitializer.init(this);
        this.commentId = commentId;
    }

    public Comment(String commentText) {
        EntityInitializer.init(this);
        this.commentText = commentText;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    public Comment getCommentReplyTo() {
        return commentReplyTo;
    }

    public void setCommentReplyTo(Comment commentReplyTo) {
        this.commentReplyTo = commentReplyTo;
    }

    public Project getProjectprojectId() {
        return projectprojectId;
    }

    public void setProjectprojectId(Project projectprojectId) {
        this.projectprojectId = projectprojectId;
    }

    public User getUseruserId() {
        return useruserId;
    }

    public void setUseruserId(User useruserId) {
        this.useruserId = useruserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commentId != null ? commentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.commentId == null && other.commentId != null) || (this.commentId != null && !this.commentId.equals(other.commentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bupt.pangu.entity.Comment[ commentId=" + commentId + " ]";
    }

}
