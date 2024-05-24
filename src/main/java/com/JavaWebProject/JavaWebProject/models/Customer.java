/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.models;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "Customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByCustomerEmail", query = "SELECT c FROM Customer c WHERE c.customerEmail = :customerEmail"),
    @NamedQuery(name = "Customer.findByPassword", query = "SELECT c FROM Customer c WHERE c.password = :password"),
    @NamedQuery(name = "Customer.findByPoint", query = "SELECT c FROM Customer c WHERE c.point = :point"),
    @NamedQuery(name = "Customer.findByRollChance", query = "SELECT c FROM Customer c WHERE c.rollChance = :rollChance"),
    @NamedQuery(name = "Customer.findByActive", query = "SELECT c FROM Customer c WHERE c.active = :active"),
    @NamedQuery(name = "Customer.findByProfileImage", query = "SELECT c FROM Customer c WHERE c.profileImage = :profileImage"),
    @NamedQuery(name = "Customer.findByFullName", query = "SELECT c FROM Customer c WHERE c.fullName = :fullName"),
    @NamedQuery(name = "Customer.findByPhone", query = "SELECT c FROM Customer c WHERE c.phone = :phone"),
    @NamedQuery(name = "Customer.findByGender", query = "SELECT c FROM Customer c WHERE c.gender = :gender"),
    @NamedQuery(name = "Customer.findByAddress", query = "SELECT c FROM Customer c WHERE c.address = :address"),
    @NamedQuery(name = "Customer.findByBirthday", query = "SELECT c FROM Customer c WHERE c.birthday = :birthday"),
    @NamedQuery(name = "Customer.findByCreateDate", query = "SELECT c FROM Customer c WHERE c.createDate = :createDate")})
public class Customer implements Serializable {

    @Column(name = "Point")
    private Integer point;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CustomerEmail")
    private String customerEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Password")
    private String password;
    @Column(name = "RollChance")
    private Integer rollChance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Active")
    private int active;
    @Size(max = 100)
    @Column(name = "ProfileImage")
    private String profileImage;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "FullName")
    private String fullName;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Phone")
    private String phone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Gender")
    private int gender;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "Address")
    private String address;
    @Column(name = "Birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CreateDate")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @JoinColumn(name = "DistrictID", referencedColumnName = "DistrictID")
    @ManyToOne(optional = false)
    private District districtID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerEmail")
    private Collection<CateringOrder> cateringOrderCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerEmail")
    private Collection<DeliveryAddress> deliveryAddressCollection;

    public Customer() {
    }

    public Customer(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Customer(String customerEmail, String password, int active, String fullName, String phone, int gender, String address, Date createDate) {
        this.customerEmail = customerEmail;
        this.password = password;
        this.active = active;
        this.fullName = fullName;
        this.phone = phone;
        this.gender = gender;
        this.address = address;
        this.createDate = createDate;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Integer getRollChance() {
        return rollChance;
    }

    public void setRollChance(Integer rollChance) {
        this.rollChance = rollChance;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public District getDistrictID() {
        return districtID;
    }

    public void setDistrictID(District districtID) {
        this.districtID = districtID;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerEmail != null ? customerEmail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerEmail == null && other.customerEmail != null) || (this.customerEmail != null && !this.customerEmail.equals(other.customerEmail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.JavaWebProject.JavaWebProject.models.Customer[ customerEmail=" + customerEmail + " ]";
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
    
}
