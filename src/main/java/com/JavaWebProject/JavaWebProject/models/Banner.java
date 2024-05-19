/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.models;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "Banner")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Banner.findAll", query = "SELECT b FROM Banner b"),
    @NamedQuery(name = "Banner.findByBannerID", query = "SELECT b FROM Banner b WHERE b.bannerID = :bannerID"),
    @NamedQuery(name = "Banner.findByBannerImage", query = "SELECT b FROM Banner b WHERE b.bannerImage = :bannerImage"),
    @NamedQuery(name = "Banner.findByBannerStartDate", query = "SELECT b FROM Banner b WHERE b.bannerStartDate = :bannerStartDate"),
    @NamedQuery(name = "Banner.findByBannerEndDate", query = "SELECT b FROM Banner b WHERE b.bannerEndDate = :bannerEndDate")})
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BannerID")
    private Integer bannerID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "BannerImage")
    private String bannerImage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BannerStartDate")
    @Temporal(TemporalType.DATE)
    private Date bannerStartDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BannerEndDate")
    @Temporal(TemporalType.DATE)
    private Date bannerEndDate;
    @JoinColumn(name = "TypeID", referencedColumnName = "TypeID")
    @ManyToOne(optional = false)
    private BannerType typeID;
    @JoinColumn(name = "CatererEmail", referencedColumnName = "CatererEmail")
    @ManyToOne(optional = false)
    private Caterer catererEmail;

    public Banner() {
    }

    public Banner(Integer bannerID) {
        this.bannerID = bannerID;
    }

    public Banner(Integer bannerID, String bannerImage, Date bannerStartDate, Date bannerEndDate) {
        this.bannerID = bannerID;
        this.bannerImage = bannerImage;
        this.bannerStartDate = bannerStartDate;
        this.bannerEndDate = bannerEndDate;
    }

    public Integer getBannerID() {
        return bannerID;
    }

    public void setBannerID(Integer bannerID) {
        this.bannerID = bannerID;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public Date getBannerStartDate() {
        return bannerStartDate;
    }

    public void setBannerStartDate(Date bannerStartDate) {
        this.bannerStartDate = bannerStartDate;
    }

    public Date getBannerEndDate() {
        return bannerEndDate;
    }

    public void setBannerEndDate(Date bannerEndDate) {
        this.bannerEndDate = bannerEndDate;
    }

    public BannerType getTypeID() {
        return typeID;
    }

    public void setTypeID(BannerType typeID) {
        this.typeID = typeID;
    }

    public Caterer getCatererEmail() {
        return catererEmail;
    }

    public void setCatererEmail(Caterer catererEmail) {
        this.catererEmail = catererEmail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bannerID != null ? bannerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Banner)) {
            return false;
        }
        Banner other = (Banner) object;
        if ((this.bannerID == null && other.bannerID != null) || (this.bannerID != null && !this.bannerID.equals(other.bannerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.JavaWebProject.JavaWebProject.models.Banner[ bannerID=" + bannerID + " ]";
    }
    
}
