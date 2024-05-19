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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "Voucher")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Voucher.findAll", query = "SELECT v FROM Voucher v"),
    @NamedQuery(name = "Voucher.findByVoucherID", query = "SELECT v FROM Voucher v WHERE v.voucherID = :voucherID"),
    @NamedQuery(name = "Voucher.findByStartDate", query = "SELECT v FROM Voucher v WHERE v.startDate = :startDate"),
    @NamedQuery(name = "Voucher.findByEndDate", query = "SELECT v FROM Voucher v WHERE v.endDate = :endDate"),
    @NamedQuery(name = "Voucher.findByVoucherValue", query = "SELECT v FROM Voucher v WHERE v.voucherValue = :voucherValue"),
    @NamedQuery(name = "Voucher.findByMaxValue", query = "SELECT v FROM Voucher v WHERE v.maxValue = :maxValue")})
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "VoucherID")
    private Integer voucherID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VoucherValue")
    private double voucherValue;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MaxValue")
    private Double maxValue;
    @OneToMany(mappedBy = "voucherID")
    private Collection<CateringOrder> cateringOrderCollection;
    @JoinColumn(name = "CatererEmail", referencedColumnName = "CatererEmail")
    @ManyToOne(optional = false)
    private Caterer catererEmail;
    @JoinColumn(name = "TypeID", referencedColumnName = "TypeID")
    @ManyToOne(optional = false)
    private VoucherType typeID;

    public Voucher() {
    }

    public Voucher(Integer voucherID) {
        this.voucherID = voucherID;
    }

    public Voucher(Integer voucherID, Date startDate, Date endDate, double voucherValue) {
        this.voucherID = voucherID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.voucherValue = voucherValue;
    }

    public Integer getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(Integer voucherID) {
        this.voucherID = voucherID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getVoucherValue() {
        return voucherValue;
    }

    public void setVoucherValue(double voucherValue) {
        this.voucherValue = voucherValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    @XmlTransient
    public Collection<CateringOrder> getCateringOrderCollection() {
        return cateringOrderCollection;
    }

    public void setCateringOrderCollection(Collection<CateringOrder> cateringOrderCollection) {
        this.cateringOrderCollection = cateringOrderCollection;
    }

    public Caterer getCatererEmail() {
        return catererEmail;
    }

    public void setCatererEmail(Caterer catererEmail) {
        this.catererEmail = catererEmail;
    }

    public VoucherType getTypeID() {
        return typeID;
    }

    public void setTypeID(VoucherType typeID) {
        this.typeID = typeID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (voucherID != null ? voucherID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Voucher)) {
            return false;
        }
        Voucher other = (Voucher) object;
        if ((this.voucherID == null && other.voucherID != null) || (this.voucherID != null && !this.voucherID.equals(other.voucherID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.JavaWebProject.JavaWebProject.models.Voucher[ voucherID=" + voucherID + " ]";
    }
    
}
