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
@Table(name = "CatererRating")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CatererRating.findAll", query = "SELECT c FROM CatererRating c"),
    @NamedQuery(name = "CatererRating.findByRatingID", query = "SELECT c FROM CatererRating c WHERE c.ratingID = :ratingID"),
    @NamedQuery(name = "CatererRating.findByRate", query = "SELECT c FROM CatererRating c WHERE c.rate = :rate"),
    @NamedQuery(name = "CatererRating.findByComment", query = "SELECT c FROM CatererRating c WHERE c.comment = :comment")})
public class CatererRating implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RatingID")
    private Integer ratingID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Rate")
    private double rate;
    @Size(max = 200)
    @Column(name = "Comment")
    private String comment;
    @JoinColumn(name = "CatererEmail", referencedColumnName = "CatererEmail")
    @ManyToOne(optional = false)
    private Caterer catererEmail;
    @JoinColumn(name = "OrderID", referencedColumnName = "OrderID")
    @ManyToOne(optional = false)
    private CateringOrder orderID;

    public CatererRating() {
    }

    public CatererRating(Integer ratingID) {
        this.ratingID = ratingID;
    }

    public CatererRating(Integer ratingID, double rate) {
        this.ratingID = ratingID;
        this.rate = rate;
    }

    public Integer getRatingID() {
        return ratingID;
    }

    public void setRatingID(Integer ratingID) {
        this.ratingID = ratingID;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Caterer getCatererEmail() {
        return catererEmail;
    }

    public void setCatererEmail(Caterer catererEmail) {
        this.catererEmail = catererEmail;
    }

    public CateringOrder getOrderID() {
        return orderID;
    }

    public void setOrderID(CateringOrder orderID) {
        this.orderID = orderID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ratingID != null ? ratingID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatererRating)) {
            return false;
        }
        CatererRating other = (CatererRating) object;
        if ((this.ratingID == null && other.ratingID != null) || (this.ratingID != null && !this.ratingID.equals(other.ratingID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.JavaWebProject.JavaWebProject.models.CatererRating[ ratingID=" + ratingID + " ]";
    }
    
}
