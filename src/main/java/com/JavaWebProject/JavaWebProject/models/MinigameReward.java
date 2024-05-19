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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "MinigameReward")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MinigameReward.findAll", query = "SELECT m FROM MinigameReward m"),
    @NamedQuery(name = "MinigameReward.findByRewardID", query = "SELECT m FROM MinigameReward m WHERE m.rewardID = :rewardID"),
    @NamedQuery(name = "MinigameReward.findByPoint", query = "SELECT m FROM MinigameReward m WHERE m.point = :point"),
    @NamedQuery(name = "MinigameReward.findByWeight", query = "SELECT m FROM MinigameReward m WHERE m.weight = :weight")})
public class MinigameReward implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RewardID")
    private Integer rewardID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Point")
    private double point;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Weight")
    private int weight;

    public MinigameReward() {
    }

    public MinigameReward(Integer rewardID) {
        this.rewardID = rewardID;
    }

    public MinigameReward(Integer rewardID, double point, int weight) {
        this.rewardID = rewardID;
        this.point = point;
        this.weight = weight;
    }

    public Integer getRewardID() {
        return rewardID;
    }

    public void setRewardID(Integer rewardID) {
        this.rewardID = rewardID;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rewardID != null ? rewardID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MinigameReward)) {
            return false;
        }
        MinigameReward other = (MinigameReward) object;
        if ((this.rewardID == null && other.rewardID != null) || (this.rewardID != null && !this.rewardID.equals(other.rewardID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.JavaWebProject.JavaWebProject.models.MinigameReward[ rewardID=" + rewardID + " ]";
    }
    
}
