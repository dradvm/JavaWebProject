/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.models;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "BannerType")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BannerType.findAll", query = "SELECT b FROM BannerType b"),
    @NamedQuery(name = "BannerType.findByTypeID", query = "SELECT b FROM BannerType b WHERE b.typeID = :typeID"),
    @NamedQuery(name = "BannerType.findByTypePrice", query = "SELECT b FROM BannerType b WHERE b.typePrice = :typePrice"),
    @NamedQuery(name = "BannerType.findByTypeDescription", query = "SELECT b FROM BannerType b WHERE b.typeDescription = :typeDescription")})
public class BannerType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TypeID")
    private Integer typeID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TypePrice")
    private double typePrice;
    @Size(max = 150)
    @Column(name = "TypeDescription")
    private String typeDescription;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeID")
    private Collection<Banner> bannerCollection;

    public BannerType() {
    }

    public BannerType(Integer typeID) {
        this.typeID = typeID;
    }

    public BannerType(Integer typeID, double typePrice) {
        this.typeID = typeID;
        this.typePrice = typePrice;
    }

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public double getTypePrice() {
        return typePrice;
    }

    public void setTypePrice(double typePrice) {
        this.typePrice = typePrice;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    @XmlTransient
    public Collection<Banner> getBannerCollection() {
        return bannerCollection;
    }

    public void setBannerCollection(Collection<Banner> bannerCollection) {
        this.bannerCollection = bannerCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (typeID != null ? typeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BannerType)) {
            return false;
        }
        BannerType other = (BannerType) object;
        if ((this.typeID == null && other.typeID != null) || (this.typeID != null && !this.typeID.equals(other.typeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.JavaWebProject.JavaWebProject.models.BannerType[ typeID=" + typeID + " ]";
    }
    
}
