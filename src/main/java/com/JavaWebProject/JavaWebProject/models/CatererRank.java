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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "CatererRank")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CatererRank.findAll", query = "SELECT c FROM CatererRank c"),
    @NamedQuery(name = "CatererRank.findByRankID", query = "SELECT c FROM CatererRank c WHERE c.rankID = :rankID"),
    @NamedQuery(name = "CatererRank.findByRankFee", query = "SELECT c FROM CatererRank c WHERE c.rankFee = :rankFee"),
    @NamedQuery(name = "CatererRank.findByRankCPO", query = "SELECT c FROM CatererRank c WHERE c.rankCPO = :rankCPO"),
    @NamedQuery(name = "CatererRank.findByRankMaxDish", query = "SELECT c FROM CatererRank c WHERE c.rankMaxDish = :rankMaxDish")})
public class CatererRank implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RankID")
    private Integer rankID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RankFee")
    private double rankFee;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RankCPO")
    private double rankCPO;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RankMaxDish")
    private int rankMaxDish;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rankID")
    private Collection<Caterer> catererCollection;

    public CatererRank() {
    }

    public CatererRank(Integer rankID) {
        this.rankID = rankID;
    }

    public CatererRank(Integer rankID, double rankFee, double rankCPO, int rankMaxDish) {
        this.rankID = rankID;
        this.rankFee = rankFee;
        this.rankCPO = rankCPO;
        this.rankMaxDish = rankMaxDish;
    }

    public Integer getRankID() {
        return rankID;
    }

    public void setRankID(Integer rankID) {
        this.rankID = rankID;
    }

    public double getRankFee() {
        return rankFee;
    }

    public void setRankFee(double rankFee) {
        this.rankFee = rankFee;
    }

    public double getRankCPO() {
        return rankCPO;
    }

    public void setRankCPO(double rankCPO) {
        this.rankCPO = rankCPO;
    }

    public int getRankMaxDish() {
        return rankMaxDish;
    }

    public void setRankMaxDish(int rankMaxDish) {
        this.rankMaxDish = rankMaxDish;
    }

    @XmlTransient
    public Collection<Caterer> getCatererCollection() {
        return catererCollection;
    }

    public void setCatererCollection(Collection<Caterer> catererCollection) {
        this.catererCollection = catererCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rankID != null ? rankID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatererRank)) {
            return false;
        }
        CatererRank other = (CatererRank) object;
        if ((this.rankID == null && other.rankID != null) || (this.rankID != null && !this.rankID.equals(other.rankID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.JavaWebProject.JavaWebProject.models.CatererRank[ rankID=" + rankID + " ]";
    }
    
}
