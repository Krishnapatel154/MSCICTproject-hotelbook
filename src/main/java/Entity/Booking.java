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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "booking")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Booking.findAll", query = "SELECT b FROM Booking b"),
    @NamedQuery(name = "Booking.findByBookingId", query = "SELECT b FROM Booking b WHERE b.bookingId = :bookingId"),
 @NamedQuery(
    name = "Booking.findByUserId",
    query = "SELECT b FROM Booking b WHERE b.users.userId = :userId"
)
,
 //   @NamedQuery(name = "Booking.findByRoomId", query = "SELECT b FROM Booking b WHERE b.roomId = :roomId"),
    @NamedQuery(name = "Booking.findByCheckInDate", query = "SELECT b FROM Booking b WHERE b.checkInDate = :checkInDate"),
    @NamedQuery(name = "Booking.findByCheckOutDate", query = "SELECT b FROM Booking b WHERE b.checkOutDate = :checkOutDate"),
    @NamedQuery(name = "Booking.findByStatus", query = "SELECT b FROM Booking b WHERE b.status = :status")})
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "booking_id")
    private Integer bookingId;
//    @Column(name = "user_id")
//    private Integer userId;
//    @Column(name = "room_id")
//    private Integer roomId;
   @Column(name = "check_in_date")
    @Temporal(TemporalType.DATE)
    private Date checkInDate;
    @Column(name = "check_out_date")
    @Temporal(TemporalType.DATE)
    private Date checkOutDate;
    @Size(max = 9)
    @Column(name = "status")
    private String status;
   
    @JsonbTransient
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "booking")
private Payment payment;
     @JsonbTransient
    @ManyToOne
@JoinColumn(name = "room_id",referencedColumnName="room_id")
private Rooms rooms;
    @JsonbTransient
    @ManyToOne
@JoinColumn(name = "user_id",referencedColumnName="user_id")
private Users users;
    
    
    public Booking() {
    }

    public Booking(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }
//
//    public Integer getRoomId() {
//        return roomId;
//    }
//
//    public void setRoomId(Integer roomId) {
//        this.roomId = roomId;
//    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
     public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Rooms getRooms() {
        return rooms;
    }

    public void setRooms(Rooms rooms) {
        this.rooms = rooms;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookingId != null ? bookingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) object;
        if ((this.bookingId == null && other.bookingId != null) || (this.bookingId != null && !this.bookingId.equals(other.bookingId))) {
            return false;
        }
        return true;
    }

  
    
}
