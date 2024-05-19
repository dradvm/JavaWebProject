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
@Table(name = "Dish")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dish.findAll", query = "SELECT d FROM Dish d"),
    @NamedQuery(name = "Dish.findByDishID", query = "SELECT d FROM Dish d WHERE d.dishID = :dishID"),
    @NamedQuery(name = "Dish.findByDishName", query = "SELECT d FROM Dish d WHERE d.dishName = :dishName"),
    @NamedQuery(name = "Dish.findByDishImage", query = "SELECT d FROM Dish d WHERE d.dishImage = :dishImage"),
    @NamedQuery(name = "Dish.findByDishDescription", query = "SELECT d FROM Dish d WHERE d.dishDescription = :dishDescription"),
    @NamedQuery(name = "Dish.findByDishPrice", query = "SELECT d FROM Dish d WHERE d.dishPrice = :dishPrice"),
    @NamedQuery(name = "Dish.findByDishStatus", query = "SELECT d FROM Dish d WHERE d.dishStatus = :dishStatus")})
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DishID")
    private Integer dishID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DishName")
    private String dishName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DishImage")
    private String dishImage;
    @Size(max = 200)
    @Column(name = "DishDescription")
    private String dishDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DishPrice")
    private double dishPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DishStatus")
    private int dishStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dish")
    private Collection<OrderDetails> orderDetailsCollection;
    @JoinColumn(name = "CatererEmail", referencedColumnName = "CatererEmail")
    @ManyToOne(optional = false)
    private Caterer catererEmail;

    public Dish() {
    }

    public Dish(Integer dishID) {
        this.dishID = dishID;
    }

    public Dish(Integer dishID, String dishName, String dishImage, double dishPrice, int dishStatus) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.dishImage = dishImage;
        this.dishPrice = dishPrice;
        this.dishStatus = dishStatus;
    }

    public Integer getDishID() {
        return dishID;
    }

    public void setDishID(Integer dishID) {
        this.dishID = dishID;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }

    public double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public int getDishStatus() {
        return dishStatus;
    }

    public void setDishStatus(int dishStatus) {
        this.dishStatus = dishStatus;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dishID != null ? dishID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dish)) {
            return false;
        }
        Dish other = (Dish) object;
        if ((this.dishID == null && other.dishID != null) || (this.dishID != null && !this.dishID.equals(other.dishID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.JavaWebProject.JavaWebProject.models.Dish[ dishID=" + dishID + " ]";
    }
    
}
