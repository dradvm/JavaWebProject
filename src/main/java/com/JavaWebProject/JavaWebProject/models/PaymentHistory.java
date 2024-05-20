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
@Table(name = "PaymentHistory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentHistory.findAll", query = "SELECT p FROM PaymentHistory p"),
    @NamedQuery(name = "PaymentHistory.findByPaymentID", query = "SELECT p FROM PaymentHistory p WHERE p.paymentID = :paymentID"),
    @NamedQuery(name = "PaymentHistory.findByValue", query = "SELECT p FROM PaymentHistory p WHERE p.value = :value"),
    @NamedQuery(name = "PaymentHistory.findByPaymentTime", query = "SELECT p FROM PaymentHistory p WHERE p.paymentTime = :paymentTime"),
    @NamedQuery(name = "PaymentHistory.findByDescription", query = "SELECT p FROM PaymentHistory p WHERE p.description = :description")})
public class PaymentHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PaymentID")
    private Integer paymentID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Value")
    private double value;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PaymentTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "Description")
    private String description;
    @JoinColumn(name = "CatererEmail", referencedColumnName = "CatererEmail")
    @ManyToOne(optional = false)
    private Caterer catererEmail;
    @JoinColumn(name = "TypeID", referencedColumnName = "TypeID")
    @ManyToOne(optional = false)
    private PaymentType typeID;

    public PaymentHistory() {
    }

    public PaymentHistory(Integer paymentID) {
        this.paymentID = paymentID;
    }

    public PaymentHistory(Integer paymentID, double value, Date paymentTime, String description) {
        this.paymentID = paymentID;
        this.value = value;
        this.paymentTime = paymentTime;
        this.description = description;
    }

    public Integer getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Integer paymentID) {
        this.paymentID = paymentID;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Caterer getCatererEmail() {
        return catererEmail;
    }

    public void setCatererEmail(Caterer catererEmail) {
        this.catererEmail = catererEmail;
    }

    public PaymentType getTypeID() {
        return typeID;
    }

    public void setTypeID(PaymentType typeID) {
        this.typeID = typeID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentID != null ? paymentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentHistory)) {
            return false;
        }
        PaymentHistory other = (PaymentHistory) object;
        if ((this.paymentID == null && other.paymentID != null) || (this.paymentID != null && !this.paymentID.equals(other.paymentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.JavaWebProject.JavaWebProject.models.PaymentHistory[ paymentID=" + paymentID + " ]";
    }
    
}
