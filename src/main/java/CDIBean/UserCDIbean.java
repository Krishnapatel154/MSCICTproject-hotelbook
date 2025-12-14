package CDIBean;

import EJB.AdminBeanLocal;
import EJB.UserBeanLocal;
import Entity.Booking;
import Entity.Rooms;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;


@Named(value = "userCDIbean")
@SessionScoped
public class UserCDIbean implements Serializable {

    @EJB
    UserBeanLocal ubl;

    @EJB
    AdminBeanLocal abl;
    private Collection<Rooms> rooms;   // stores filtered rooms of selected hotel
    private Integer selectedHotelId;
    private Integer bookingRoomId;

    // --- Load Rooms of Selected Hotel ---
    public String viewRooms(Integer hotelId) {

        System.out.println("HOTEL ID CLICKED = " + hotelId);

        rooms = ubl.searchRoomsByHotel(hotelId);

        System.out.println("ROOMS FOUND = " + (rooms != null ? rooms.size() : 0));
        
    return "UserRoom.jsf?faces-redirect=true&hotelId=" + hotelId;
    }

    public Collection<Rooms> getRooms() {
        return rooms;
    }
      public Integer getSelectedHotelId() {
        return selectedHotelId;
    }
      
      //book btn 
      // ------------------------------
    // PAYMENT VARIABLES
    // ------------------------------
    private Integer selectedRoomId;
    private Integer userId;
    private String paymentMode;
    private String loggedInUser;
    

    
    

public Integer getBookingRoomId() { return bookingRoomId; }
public void setBookingRoomId(Integer bookingRoomId) { this.bookingRoomId = bookingRoomId; }


// ------------------------------
private java.util.Date checkIn;
private java.util.Date checkOut;

public java.util.Date getCheckIn() { return checkIn; }
public void setCheckIn(java.util.Date checkIn) { this.checkIn = checkIn; }

public java.util.Date getCheckOut() { return checkOut; }
public void setCheckOut(java.util.Date checkOut) { this.checkOut = checkOut; }


    // ------------------------------
    // GETTERS & SETTERS
    // ------------------------------
    public Integer getSelectedRoomId() {
        return selectedRoomId;
    }

    

    public Integer getUserId() {
        return userId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
      public String goToBooking(Integer roomId) {
    this.bookingRoomId = roomId;
    return "UserBooking.jsf?faces-redirect=true";
}
      
private Integer getUserIdFromSession() {
    FacesContext fc = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

    if (session != null) {
        return (Integer) session.getAttribute("userId");
    }
    return null;
}
//booking page
      
      private Integer selectedBookingId;

public Integer getSelectedBookingId() { return selectedBookingId; }

    public String bookRoomAndGoToPayment(Integer roomId, Integer hotelId) {
    try {
     Integer sessionUserId = getUserIdFromSession();
    if (sessionUserId == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Please login first", null));
            return null;
        }
        // Convert util.Date → sql.Date
        java.sql.Date sqlCheckIn = new java.sql.Date(checkIn.getTime());
        java.sql.Date sqlCheckOut = new java.sql.Date(checkOut.getTime());

        // 1️⃣ Insert booking into DB
        abl.addBooking(
            sessionUserId,
            roomId,
            sqlCheckIn,
            sqlCheckOut,
            "Pending"
        );

        // 2️⃣ Get bookingId of last inserted booking  
        //     You already have EJB method: getAllBookings()
        Integer bookingId = abl.getAllBookings()
                               .stream()
                               .map(b -> b.getBookingId())
                               .max(Integer::compare)
                               .orElse(null);

        // 3️⃣ Store these in session bean
        this.selectedRoomId = roomId;
        this.selectedHotelId = hotelId;
        this.userId = sessionUserId;
        this.selectedBookingId = bookingId;

        System.out.println("Booking inserted: " + bookingId);
        
        // 4️⃣ Redirect to payment page
        return "UserPayment.jsf?faces-redirect=true";

    } catch (Exception e) {
        e.printStackTrace();
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR,
            "Booking Failed", null));
        return null;
    }
}

    //final payment
    
    public String processPayment() {

        try {
            System.out.println("Processing payment...");
            System.out.println("Room: " + selectedRoomId);
            System.out.println("User: " + userId);
            System.out.println("Mode: " + paymentMode);

            // This will insert payment into DB  
            abl.addPayment(
                selectedBookingId,  // bookingId (temporary)
                new BigDecimal("1500"),  // amount (temporary)
                new java.sql.Date(System.currentTimeMillis()),
                paymentMode,
                "SUCCESS"
            );

            return "Paymentsuccess.jsf?faces-redirect=true";

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

   private Collection<Booking> bookingList;

public Collection<Booking> getBookingList() {
    HttpSession session = (HttpSession)
        FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSession(false);

    if (session != null && session.getAttribute("userId") != null) {
        int userId = (int) session.getAttribute("userId");
        bookingList = ubl.getBookingsOfUser(userId); // fetch only user's bookings
    }

    return bookingList;
}

}