/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt.pangu.entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;

/**
 *
 * @author ipidw
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Photo.findAll", query = "SELECT p FROM Photo p")
    , @NamedQuery(name = "Photo.findByPhotoId", query = "SELECT p FROM Photo p WHERE p.photoId = :photoId")})
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer photoId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(nullable = false)
    private byte[] photoPic;
    @JoinColumn(name = "team_teamId", referencedColumnName = "teamId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Team teamteamId;

    public Photo() {
    }

    public Photo(Integer photoId) {
        this.photoId = photoId;
    }

    public Photo(Integer photoId, byte[] photoPic) {
        this.photoId = photoId;
        this.photoPic = photoPic;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public byte[] getPhotoPic() {
        return photoPic;
    }

    public void setPhotoPic(byte[] photoPic) {
        this.photoPic = photoPic;
    }

    public Team getTeamteamId() {
        return teamteamId;
    }

    public void setTeamteamId(Team teamteamId) {
        this.teamteamId = teamteamId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (photoId != null ? photoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Photo)) {
            return false;
        }
        Photo other = (Photo) object;
        if ((this.photoId == null && other.photoId != null) || (this.photoId != null && !this.photoId.equals(other.photoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bupt.pangu.entity.Photo[ photoId=" + photoId + " ]";
    }

}
