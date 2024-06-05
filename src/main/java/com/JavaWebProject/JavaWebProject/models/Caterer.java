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
 * @author Voke
 */
@Entity
@Table(name = "Caterer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Caterer.findAll", query = "SELECT c FROM Caterer c"),
    @NamedQuery(name = "Caterer.findByCatererEmail", query = "SELECT c FROM Caterer c WHERE c.catererEmail = :catererEmail"),
    @NamedQuery(name = "Caterer.findByPassword", query = "SELECT c FROM Caterer c WHERE c.password = :password"),
    @NamedQuery(name = "Caterer.findByCatererRating", query = "SELECT c FROM Caterer c WHERE c.catererRating = :catererRating"),
    @NamedQuery(name = "Caterer.findByRankStartDate", query = "SELECT c FROM Caterer c WHERE c.rankStartDate = :rankStartDate"),
    @NamedQuery(name = "Caterer.findByRankEndDate", query = "SELECT c FROM Caterer c WHERE c.rankEndDate = :rankEndDate"),
    @NamedQuery(name = "Caterer.findByDescription", query = "SELECT c FROM Caterer c WHERE c.description = :description"),
    @NamedQuery(name = "Caterer.findByActive", query = "SELECT c FROM Caterer c WHERE c.active = :active"),
    @NamedQuery(name = "Caterer.findByProfileImage", query = "SELECT c FROM Caterer c WHERE c.profileImage = :profileImage"),
    @NamedQuery(name = "Caterer.findByFullName", query = "SELECT c FROM Caterer c WHERE c.fullName = :fullName"),
    @NamedQuery(name = "Caterer.findByPhone", query = "SELECT c FROM Caterer c WHERE c.phone = :phone"),
    @NamedQuery(name = "Caterer.findByGender", query = "SELECT c FROM Caterer c WHERE c.gender = :gender"),
    @NamedQuery(name = "Caterer.findByAddress", query = "SELECT c FROM Caterer c WHERE c.address = :address"),
    @NamedQuery(name = "Caterer.findByBirthday", query = "SELECT c FROM Caterer c WHERE c.birthday = :birthday"),
    @NamedQuery(name = "Caterer.findByCreateDate", query = "SELECT c FROM Caterer c WHERE c.createDate = :createDate")})
public class Caterer implements Serializable {

    @Column(name = "Point")
    private Integer point;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PaymentInformation")
    private String paymentInformation;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CatererEmail")
    private String catererEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Password")
    private String password;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CatererRating")
    private Double catererRating;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RankStartDate")
    @Temporal(TemporalType.DATE)
    private Date rankStartDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RankEndDate")
    @Temporal(TemporalType.DATE)
    private Date rankEndDate;
    @Size(max = 200)
    @Column(name = "Description")
    private String description;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catererEmail")
    private Collection<CateringOrder> cateringOrderCollection;
    @JoinColumn(name = "RankID", referencedColumnName = "RankID")
    @ManyToOne(optional = false)
    private CatererRank rankID;
    @JoinColumn(name = "DistrictID", referencedColumnName = "DistrictID")
    @ManyToOne(optional = false)
    private District districtID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catererEmail")
    private Collection<Voucher> voucherCollection;

    public Caterer() {
    }

    public Caterer(String catererEmail) {
        this.catererEmail = catererEmail;
    }

    public Caterer(String catererEmail, String password, Date rankStartDate, Date rankEndDate, int active, String fullName, String phone, int gender, String address, Date createDate) {
        this.catererEmail = catererEmail;
        this.password = password;
        this.rankStartDate = rankStartDate;
        this.rankEndDate = rankEndDate;
        this.active = active;
        this.fullName = fullName;
        this.phone = phone;
        this.gender = gender;
        this.address = address;
        this.createDate = createDate;
    }

    public String getCatererEmail() {
        return catererEmail;
    }

    public void setCatererEmail(String catererEmail) {
        this.catererEmail = catererEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getCatererRating() {
        return catererRating;
    }

    public void setCatererRating(Double catererRating) {
        this.catererRating = catererRating;
    }

    public Date getRankStartDate() {
        return rankStartDate;
    }

    public void setRankStartDate(Date rankStartDate) {
        this.rankStartDate = rankStartDate;
    }

    public Date getRankEndDate() {
        return rankEndDate;
    }

    public void setRankEndDate(Date rankEndDate) {
        this.rankEndDate = rankEndDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @XmlTransient
    public Collection<CateringOrder> getCateringOrderCollection() {
        return cateringOrderCollection;
    }

    public void setCateringOrderCollection(Collection<CateringOrder> cateringOrderCollection) {
        this.cateringOrderCollection = cateringOrderCollection;
    }

    public CatererRank getRankID() {
        return rankID;
    }

    public void setRankID(CatererRank rankID) {
        this.rankID = rankID;
    }

    public District getDistrictID() {
        return districtID;
    }

    public void setDistrictID(District districtID) {
        this.districtID = districtID;
    }

    @XmlTransient
    public Collection<Voucher> getVoucherCollection() {
        return voucherCollection;
    }

    public void setVoucherCollection(Collection<Voucher> voucherCollection) {
        this.voucherCollection = voucherCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catererEmail != null ? catererEmail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caterer)) {
            return false;
        }
        Caterer other = (Caterer) object;
        if ((this.catererEmail == null && other.catererEmail != null) || (this.catererEmail != null && !this.catererEmail.equals(other.catererEmail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.JavaWebProject.JavaWebProject.models.Caterer[ catererEmail=" + catererEmail + " ]";
    }

    public String getPaymentInformation() {
        return paymentInformation;
    }

    public void setPaymentInformation(String paymentInformation) {
        this.paymentInformation = paymentInformation;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
    
}
