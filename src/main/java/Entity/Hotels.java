/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import jakarta.json.bind.annotation.JsonbTransient;
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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "hotels")
@NamedQueries({
    @NamedQuery(name = "Hotels.findAll", query = "SELECT h FROM Hotels h"),
    @NamedQuery(name = "Hotels.findByHotelId", query = "SELECT h FROM Hotels h WHERE h.hotelId = :hotelId"),
    @NamedQuery(name = "Hotels.findByHotelName", query = "SELECT h FROM Hotels h WHERE h.hotelName = :hotelName"),
    @NamedQuery(name = "Hotels.findByLocation", query = "SELECT h FROM Hotels h WHERE h.location = :location"),
    @NamedQuery(name = "Hotels.findByCity", query = "SELECT h FROM Hotels h WHERE h.city = :city"),
    @NamedQuery(name = "Hotels.findByRating", query = "SELECT h FROM Hotels h WHERE h.rating = :rating"),
    @NamedQuery(name = "Hotels.findByDescription", query = "SELECT h FROM Hotels h WHERE h.description = :description")})
public class Hotels implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "hotel_id")
    private Integer hotelId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "hotel_name")
    private String hotelName;
    @Size(max = 100)
    @Column(name = "location")
    private String location;
    @Size(max = 50)
    @Column(name = "city")
    private String city;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating")
    private Float rating;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Column(name = "image_path")
    private String imagepath;
     @JsonbTransient
    @OneToMany(cascade = CascadeType.ALL,mappedBy ="hotels")
    private Collection<Rooms> roomsCollection;
    
    public Hotels() {
    }

    public Hotels(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Hotels(Integer hotelId, String hotelName) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
   
    public String getImagePath() {
    return imagepath;
    }

    public void setImagePath(String imagePath) {
        this.imagepath = imagePath;
    }
    
    public Collection<Rooms> getRoomsCollection() {
        return roomsCollection;
    }

    public void setRoomsCollection(Collection<Rooms> roomsCollection) {
        this.roomsCollection = roomsCollection;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hotelId != null ? hotelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hotels)) {
            return false;
        }
        Hotels other = (Hotels) object;
        if ((this.hotelId == null && other.hotelId != null) || (this.hotelId != null && !this.hotelId.equals(other.hotelId))) {
            return false;
        }
        return true;
    }

    
    
    
}
