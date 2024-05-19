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
@Table(name = "Report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r"),
    @NamedQuery(name = "Report.findByReportID", query = "SELECT r FROM Report r WHERE r.reportID = :reportID"),
    @NamedQuery(name = "Report.findByReporter", query = "SELECT r FROM Report r WHERE r.reporter = :reporter"),
    @NamedQuery(name = "Report.findByReportee", query = "SELECT r FROM Report r WHERE r.reportee = :reportee"),
    @NamedQuery(name = "Report.findByReportDescription", query = "SELECT r FROM Report r WHERE r.reportDescription = :reportDescription"),
    @NamedQuery(name = "Report.findByReportDate", query = "SELECT r FROM Report r WHERE r.reportDate = :reportDate"),
    @NamedQuery(name = "Report.findByReportStatus", query = "SELECT r FROM Report r WHERE r.reportStatus = :reportStatus")})
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ReportID")
    private Integer reportID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Reporter")
    private String reporter;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Reportee")
    private String reportee;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "ReportDescription")
    private String reportDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ReportDate")
    @Temporal(TemporalType.DATE)
    private Date reportDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ReportStatus")
    private int reportStatus;

    public Report() {
    }

    public Report(Integer reportID) {
        this.reportID = reportID;
    }

    public Report(Integer reportID, String reporter, String reportee, String reportDescription, Date reportDate, int reportStatus) {
        this.reportID = reportID;
        this.reporter = reporter;
        this.reportee = reportee;
        this.reportDescription = reportDescription;
        this.reportDate = reportDate;
        this.reportStatus = reportStatus;
    }

    public Integer getReportID() {
        return reportID;
    }

    public void setReportID(Integer reportID) {
        this.reportID = reportID;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getReportee() {
        return reportee;
    }

    public void setReportee(String reportee) {
        this.reportee = reportee;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public int getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(int reportStatus) {
        this.reportStatus = reportStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportID != null ? reportID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.reportID == null && other.reportID != null) || (this.reportID != null && !this.reportID.equals(other.reportID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.JavaWebProject.JavaWebProject.models.Report[ reportID=" + reportID + " ]";
    }
    
}
