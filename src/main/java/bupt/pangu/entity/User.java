/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.entity;

import bupt.pangu.controller.util.EntityInitializer;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import javax.persistence.ManyToMany;
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
 * @author 46639
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"userPhoneNum"})})
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userId = :userId")
    , @NamedQuery(name = "User.findByUserDate", query = "SELECT u FROM User u WHERE u.userDate = :userDate")
    , @NamedQuery(name = "User.findByUserPhoneNum", query = "SELECT u FROM User u WHERE u.userPhoneNum = :userPhoneNum")
    , @NamedQuery(name = "User.findByUserPassword", query = "SELECT u FROM User u WHERE u.userPassword = :userPassword")
    , @NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName")
    , @NamedQuery(name = "User.findByUserGender", query = "SELECT u FROM User u WHERE u.userGender = :userGender"),})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(nullable = false, length = 11)
    private String userPhoneNum;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date userDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(nullable = false, length = 32)
    private String userPassword;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(nullable = false, length = 6)
    private String userGender;
    @Lob
    private byte[] userPic;

    @ManyToMany(mappedBy = "userCollection", fetch = FetchType.EAGER)
    private Collection<Task> taskCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "useruserId", fetch = FetchType.EAGER)
    private Collection<Comment> commentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private Collection<TeamHasUser> teamHasUserCollection;

    public User() {
        EntityInitializer.init(this);
        // TODO: 用常量来代替这个值
        this.userGender = "Male";
    }

    public User(Integer userId, Date userDate, String userPhoneNum, String userPassword, String userName, String userGender) {
        this.userId = userId;
        this.userDate = userDate;
        this.userPhoneNum = userPhoneNum;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userGender = userGender;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getUserDate() {
        return userDate;
    }

    public void setUserDate(Date userDate) {
        this.userDate = userDate;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) throws NoSuchAlgorithmException {
        // 生成一个MD5加密计算摘要
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 计算md5函数
        md.update(userPassword.getBytes());
        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
        this.userPassword = new BigInteger(1, md.digest()).toString(16);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public byte[] getUserPic() {
        return userPic;
    }

    public void setUserPic(byte[] userPic) {
        this.userPic = userPic;
    }

    public Collection<Task> getTaskCollection() {
        return taskCollection;
    }

    public void setTaskCollection(Collection<Task> taskCollection) {
        this.taskCollection = taskCollection;
    }

    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    public Collection<TeamHasUser> getTeamHasUserCollection() {
        return teamHasUserCollection;
    }

    public void setTeamHasUserCollection(Collection<TeamHasUser> teamHasUserCollection) {
        this.teamHasUserCollection = teamHasUserCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bupt.pangu.entity.User[ userId=" + userId + " ]";
    }

}
