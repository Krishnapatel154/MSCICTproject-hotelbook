package CDIBean;

import EJB.UserBeanLocal;
import Entity.Rooms;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;

@Named(value = "userCDIbean")
@SessionScoped
public class UserCDIbean implements Serializable {

    @EJB
    UserBeanLocal ubl;

    private Collection<Rooms> rooms;   // stores filtered rooms of selected hotel
    private Integer selectedHotelId;
    private Integer bookingRoomId;

    // --- Load Rooms of Selected Hotel ---
    public String viewRooms(Integer hotelId) {

        System.out.println("HOTEL ID CLICKED = " + hotelId);

        rooms = ubl.searchRoomsByHotel(hotelId);

        System.out.println("ROOMS FOUND = " + (rooms != null ? rooms.size() : 0));

        return "UserRoom.jsf?faces-redirect=true";
    }

    public Collection<Rooms> getRooms() {
        return rooms;
    }
      public Integer getSelectedHotelId() {
        return selectedHotelId;
    }
      
//booking
      
      private Integer loginUserId; 


   private java.util.Date checkInDate;
private java.util.Date checkOutDate;


    // ================== LOGIN USER ID SET ===================
    public Integer getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(Integer loginUserId) {
        this.loginUserId = loginUserId;
    }

    
    // ================== ROOMS LIST BY HOTEL ===================
    public void loadRoomsByHotel(Integer hotelId) {
        rooms = ubl.searchRoomsByHotel(hotelId);
    }


    // =============== STORE SELECTED ROOM ID ===================
    public Integer getBookingRoomId() {
        return bookingRoomId;
    }

    public void setBookingRoomId(Integer bookingRoomId) {
        this.bookingRoomId = bookingRoomId;
    }

    // =============== WHEN USER CLICKS BOOK ===================
    public String goToBooking(Integer roomId) {
        this.bookingRoomId = roomId;  
        return "UserBooking.jsf?faces-redirect=true";
    }

    // =============== DATES ===================
   public java.util.Date getCheckInDate() {
    return checkInDate;
}

public void setCheckInDate(java.util.Date checkInDate) {
    this.checkInDate = checkInDate;
}

public java.util.Date getCheckOutDate() {
    return checkOutDate;
}

public void setCheckOutDate(java.util.Date checkOutDate) {
    this.checkOutDate = checkOutDate;
}


    // =============== CONFIRM BOOKING ===================
    public String confirmBooking() {
    java.sql.Date cin = new java.sql.Date(checkInDate.getTime());
    java.sql.Date cout = new java.sql.Date(checkOutDate.getTime());

    ubl.createBooking(loginUserId, bookingRoomId, cin, cout);

    return "UserPayment.jsf?faces-redirect=true";
}


   
}
