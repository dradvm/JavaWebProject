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
import java.time.LocalDateTime;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "Notification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n"),
    @NamedQuery(name = "Notification.findByNotificationID", query = "SELECT n FROM Notification n WHERE n.notificationID = :notificationID"),
    @NamedQuery(name = "Notification.findBySender", query = "SELECT n FROM Notification n WHERE n.sender = :sender"),
    @NamedQuery(name = "Notification.findByReceiver", query = "SELECT n FROM Notification n WHERE n.receiver = :receiver"),
    @NamedQuery(name = "Notification.findByNotificationContents", query = "SELECT n FROM Notification n WHERE n.notificationContents = :notificationContents"),
    @NamedQuery(name = "Notification.findByNotificationTime", query = "SELECT n FROM Notification n WHERE n.notificationTime = :notificationTime")})
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "NotificationID")
    private Integer notificationID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Sender")
    private String sender;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Receiver")
    private String receiver;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "NotificationContents")
    private String notificationContents;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NotificationTime")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime notificationTime;

    public Notification() {
    }

    public Notification(Integer notificationID) {
        this.notificationID = notificationID;
    }

    public Notification(Integer notificationID, String sender, String receiver, String notificationContents, LocalDateTime notificationTime) {
        this.notificationID = notificationID;
        this.sender = sender;
        this.receiver = receiver;
        this.notificationContents = notificationContents;
        this.notificationTime = notificationTime;
    }

    public Integer getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(Integer notificationID) {
        this.notificationID = notificationID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getNotificationContents() {
        return notificationContents;
    }

    public void setNotificationContents(String notificationContents) {
        this.notificationContents = notificationContents;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notificationID != null ? notificationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this.notificationID == null && other.notificationID != null) || (this.notificationID != null && !this.notificationID.equals(other.notificationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.JavaWebProject.JavaWebProject.models.Notification[ notificationID=" + notificationID + " ]";
    }
    
}
