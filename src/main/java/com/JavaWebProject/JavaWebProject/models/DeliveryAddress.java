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
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "DeliveryAddress")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DeliveryAddress.findAll", query = "SELECT d FROM DeliveryAddress d"),
    @NamedQuery(name = "DeliveryAddress.findByAddressID", query = "SELECT d FROM DeliveryAddress d WHERE d.addressID = :addressID"),
    @NamedQuery(name = "DeliveryAddress.findByAddress", query = "SELECT d FROM DeliveryAddress d WHERE d.address = :address")})
public class DeliveryAddress implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AddressID")
    private Integer addressID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "Address")
    private String address;
    @JoinColumn(name = "CustomerEmail", referencedColumnName = "CustomerEmail")
    @ManyToOne(optional = false)
    private Customer customerEmail;
    @JoinColumn(name = "DistrictID", referencedColumnName = "DistrictID")
    @ManyToOne(optional = false)
    private District districtID;

    public DeliveryAddress() {
    }

    public DeliveryAddress(Integer addressID) {
        this.addressID = addressID;
    }

    public DeliveryAddress(Integer addressID, String address) {
        this.addressID = addressID;
        this.address = address;
    }

    public Integer getAddressID() {
        return addressID;
    }

    public void setAddressID(Integer addressID) {
        this.addressID = addressID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Customer getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(Customer customerEmail) {
        this.customerEmail = customerEmail;
    }

    public District getDistrictID() {
        return districtID;
    }

    public void setDistrictID(District districtID) {
        this.districtID = districtID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (addressID != null ? addressID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeliveryAddress)) {
            return false;
        }
        DeliveryAddress other = (DeliveryAddress) object;
        if ((this.addressID == null && other.addressID != null) || (this.addressID != null && !this.addressID.equals(other.addressID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.JavaWebProject.JavaWebProject.models.DeliveryAddress[ addressID=" + addressID + " ]";
    }
    
}
