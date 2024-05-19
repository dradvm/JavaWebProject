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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "District")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "District.findAll", query = "SELECT d FROM District d"),
    @NamedQuery(name = "District.findByDistrictID", query = "SELECT d FROM District d WHERE d.districtID = :districtID"),
    @NamedQuery(name = "District.findByDistrictName", query = "SELECT d FROM District d WHERE d.districtName = :districtName")})
public class District implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DistrictID")
    private Integer districtID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DistrictName")
    private String districtName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtID")
    private Collection<Customer> customerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtID")
    private Collection<CateringOrder> cateringOrderCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtID")
    private Collection<DeliveryAddress> deliveryAddressCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtID")
    private Collection<Caterer> catererCollection;
    @JoinColumn(name = "CityID", referencedColumnName = "CityID")
    @ManyToOne(optional = false)
    private City cityID;

    public District() {
    }

    public District(Integer districtID) {
        this.districtID = districtID;
    }

    public District(Integer districtID, String districtName) {
        this.districtID = districtID;
        this.districtName = districtName;
    }

    public Integer getDistrictID() {
        return districtID;
    }

    public void setDistrictID(Integer districtID) {
        this.districtID = districtID;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    @XmlTransient
    public Collection<Customer> getCustomerCollection() {
        return customerCollection;
    }

    public void setCustomerCollection(Collection<Customer> customerCollection) {
        this.customerCollection = customerCollection;
    }

    @XmlTransient
    public Collection<CateringOrder> getCateringOrderCollection() {
        return cateringOrderCollection;
    }

    public void setCateringOrderCollection(Collection<CateringOrder> cateringOrderCollection) {
        this.cateringOrderCollection = cateringOrderCollection;
    }

    @XmlTransient
    public Collection<DeliveryAddress> getDeliveryAddressCollection() {
        return deliveryAddressCollection;
    }

    public void setDeliveryAddressCollection(Collection<DeliveryAddress> deliveryAddressCollection) {
        this.deliveryAddressCollection = deliveryAddressCollection;
    }

    @XmlTransient
    public Collection<Caterer> getCatererCollection() {
        return catererCollection;
    }

    public void setCatererCollection(Collection<Caterer> catererCollection) {
        this.catererCollection = catererCollection;
    }

    public City getCityID() {
        return cityID;
    }

    public void setCityID(City cityID) {
        this.cityID = cityID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (districtID != null ? districtID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof District)) {
            return false;
        }
        District other = (District) object;
        if ((this.districtID == null && other.districtID != null) || (this.districtID != null && !this.districtID.equals(other.districtID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.JavaWebProject.JavaWebProject.models.District[ districtID=" + districtID + " ]";
    }
    
}
