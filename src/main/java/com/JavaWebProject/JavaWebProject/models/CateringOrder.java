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
@Table(name = "CateringOrder")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CateringOrder.findAll", query = "SELECT c FROM CateringOrder c"),
    @NamedQuery(name = "CateringOrder.findByOrderID", query = "SELECT c FROM CateringOrder c WHERE c.orderID = :orderID"),
    @NamedQuery(name = "CateringOrder.findByOrderAddress", query = "SELECT c FROM CateringOrder c WHERE c.orderAddress = :orderAddress"),
    @NamedQuery(name = "CateringOrder.findByOrderTime", query = "SELECT c FROM CateringOrder c WHERE c.orderTime = :orderTime"),
    @NamedQuery(name = "CateringOrder.findByCreateDate", query = "SELECT c FROM CateringOrder c WHERE c.createDate = :createDate"),
    @NamedQuery(name = "CateringOrder.findByOrderState", query = "SELECT c FROM CateringOrder c WHERE c.orderState = :orderState"),
    @NamedQuery(name = "CateringOrder.findByNumOfTables", query = "SELECT c FROM CateringOrder c WHERE c.numOfTables = :numOfTables"),
    @NamedQuery(name = "CateringOrder.findByServiceFee", query = "SELECT c FROM CateringOrder c WHERE c.serviceFee = :serviceFee"),
    @NamedQuery(name = "CateringOrder.findByPointDiscount", query = "SELECT c FROM CateringOrder c WHERE c.pointDiscount = :pointDiscount"),
    @NamedQuery(name = "CateringOrder.findByVoucherDiscount", query = "SELECT c FROM CateringOrder c WHERE c.voucherDiscount = :voucherDiscount"),
    @NamedQuery(name = "CateringOrder.findByValue", query = "SELECT c FROM CateringOrder c WHERE c.value = :value"),
    @NamedQuery(name = "CateringOrder.findByNote", query = "SELECT c FROM CateringOrder c WHERE c.note = :note")})
public class CateringOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "OrderID")
    private Integer orderID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "OrderAddress")
    private String orderAddress;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OrderTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CreateDate")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "OrderState")
    private String orderState;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NumOfTables")
    private int numOfTables;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ServiceFee")
    private double serviceFee;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PointDiscount")
    private Double pointDiscount;
    @Column(name = "VoucherDiscount")
    private Double voucherDiscount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Value")
    private double value;
    @Size(max = 200)
    @Column(name = "Note")
    private String note;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cateringOrder")
    private Collection<OrderDetails> orderDetailsCollection;
    @JoinColumn(name = "CatererEmail", referencedColumnName = "CatererEmail")
    @ManyToOne(optional = false)
    private Caterer catererEmail;
    @JoinColumn(name = "CustomerEmail", referencedColumnName = "CustomerEmail")
    @ManyToOne(optional = false)
    private Customer customerEmail;
    @JoinColumn(name = "DistrictID", referencedColumnName = "DistrictID")
    @ManyToOne(optional = false)
    private District districtID;
    @JoinColumn(name = "VoucherID", referencedColumnName = "VoucherID")
    @ManyToOne
    private Voucher voucherID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderID")
    private Collection<CatererRating> catererRatingCollection;

    public CateringOrder() {
    }

    public CateringOrder(Integer orderID) {
        this.orderID = orderID;
    }

    public CateringOrder(Integer orderID, String orderAddress, Date orderTime, Date createDate, String orderState, int numOfTables, double serviceFee, double value) {
        this.orderID = orderID;
        this.orderAddress = orderAddress;
        this.orderTime = orderTime;
        this.createDate = createDate;
        this.orderState = orderState;
        this.numOfTables = numOfTables;
        this.serviceFee = serviceFee;
        this.value = value;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public int getNumOfTables() {
        return numOfTables;
    }

    public void setNumOfTables(int numOfTables) {
        this.numOfTables = numOfTables;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Double getPointDiscount() {
        return pointDiscount;
    }

    public void setPointDiscount(Double pointDiscount) {
        this.pointDiscount = pointDiscount;
    }

    public Double getVoucherDiscount() {
        return voucherDiscount;
    }

    public void setVoucherDiscount(Double voucherDiscount) {
        this.voucherDiscount = voucherDiscount;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @XmlTransient
    public Collection<OrderDetails> getOrderDetailsCollection() {
        return orderDetailsCollection;
    }

    public void setOrderDetailsCollection(Collection<OrderDetails> orderDetailsCollection) {
        this.orderDetailsCollection = orderDetailsCollection;
    }

    public Caterer getCatererEmail() {
        return catererEmail;
    }

    public void setCatererEmail(Caterer catererEmail) {
        this.catererEmail = catererEmail;
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

    public Voucher getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(Voucher voucherID) {
        this.voucherID = voucherID;
    }

    @XmlTransient
    public Collection<CatererRating> getCatererRatingCollection() {
        return catererRatingCollection;
    }

    public void setCatererRatingCollection(Collection<CatererRating> catererRatingCollection) {
        this.catererRatingCollection = catererRatingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderID != null ? orderID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CateringOrder)) {
            return false;
        }
        CateringOrder other = (CateringOrder) object;
        if ((this.orderID == null && other.orderID != null) || (this.orderID != null && !this.orderID.equals(other.orderID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.JavaWebProject.JavaWebProject.models.CateringOrder[ orderID=" + orderID + " ]";
    }
    
}
