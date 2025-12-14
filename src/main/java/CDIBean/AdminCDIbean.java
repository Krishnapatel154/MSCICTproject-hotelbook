/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIBean;

import EJB.AdminBeanLocal;
import EJB.UserBeanLocal;
import Entity.Booking;
import Entity.Hotels;
import Entity.Payment;
import Entity.Rooms;
import Entity.Users;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;



/**
 *
 * @author Admin
 */
@Named(value = "adminCDIbean")
//@RequestScoped
@SessionScoped

public class AdminCDIbean implements Serializable {

    @EJB
    AdminBeanLocal abl;
    private String username;
    private String email;
    private String password;
    private String phone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AdminCDIbean() {
    }

    // Email validation
    private static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
    // Register user with DEFAULT group_id = 2

    public String registerUser() {

        if (!isValidEmail(email)) {
            System.out.println("Invalid email format");
            return null;
        }

        try {
            // group_id always = 2 (user)
            abl.addUser(2, username, email, password, phone);

            return "Login.jsf?faces-redirect=true";

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private String page = "welcome";

    public String getPage() {
        return page;
    }

    public String setPage(String p) {
        this.page = p;
        return null;  // Stay on same view, do not navigate
    }
   

    
    //hotel crud

    private Integer hotelId;
    private String hotelName;                           
    private String location;
    private String city;
    private Float rating;
    private String description;
    private String imagepath;

    private Hotels selectedHotel;

    public Integer gethotelId() {
        return hotelId;
    }

    public void sethotelId(Integer hotelId) {
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

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }



    public Hotels getSelectedHotel() {
        return selectedHotel;
    }

    public void setSelectedHotel(Hotels selectedHotel) {
        this.selectedHotel = selectedHotel;
    }

    public boolean isAdmin() {
        return true;
    }
  
    
    public String addHotel() {
        try {
                abl.addHotel(hotelName, location, city, rating, description,imagepath);
                hotels = null;
            return "AdminDashboard.jsf?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // READ - List All Hotels
    private Collection<Hotels> hotels;

    public Collection<Hotels> getHotels() {
      if (hotels == null) {
            hotels = abl.getAllHotel();
      }
        return hotels;
    }

    public void setHotels(Collection<Hotels> hotels) {
        this.hotels = hotels;
    }

    public Collection<Hotels> getAllHotels() {
        return abl.getAllHotel();
    }

    // LOAD hotel data into form (for editing)
    public String loadHotelForEdit(Integer id) {
        selectedHotel = abl.findHotelById(id);

        if (selectedHotel != null) {
            this.hotelId = selectedHotel.getHotelId();
            this.hotelName = selectedHotel.getHotelName();
            this.location = selectedHotel.getLocation();
            this.city = selectedHotel.getCity();
            this.rating = selectedHotel.getRating();
            this.description = selectedHotel.getDescription();
            this.imagepath=selectedHotel.getImagePath();
        }
        return "EditHotel.jsf?faces-redirect=true";
    }

    // UPDATE
    public String updateHotel() {
        try {
            abl.updateHotel(hotelId, hotelName, location, city, rating, description,imagepath);
             hotels = null;
            return "AdminDashboard.jsf?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // DELETE
    public String deleteHotel(Integer id) {
        try {
            abl.deleteHotel(id);
              hotels = null;
            return "AdminDashboard.jsf?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Room crud
private Integer roomId;
private Integer roomhotelId;   // FIXED (HotelId → hotelId)
private String roomType;
private BigDecimal roomPrice;
private Boolean availability;
private String roomimagepath;

public Integer getRoomHotelId() {
    return roomhotelId;
}

public void setRoomHotelId(Integer roomHotelId) {
    this.roomhotelId = roomHotelId;
}

public Integer getRoomId() {
    return roomId;
}

public void setRoomId(Integer roomId) {
    this.roomId = roomId;
}

public String getRoomType() {
    return roomType;
}

public void setRoomType(String roomType) {
    this.roomType = roomType;
}

public BigDecimal getRoomPrice() {
    return roomPrice;
}

public void setRoomPrice(BigDecimal roomPrice) {
    this.roomPrice = roomPrice;
}

public Boolean getAvailability() {
    return availability;
}

public void setAvailability(Boolean availability) {
    this.availability = availability;
}

    public String getRoomimagepath() {
        return roomimagepath;
    }

    public void setRoomimagepath(String roomimagepath) {
        this.roomimagepath = roomimagepath;
    }

private Rooms selectedRoom;

public Rooms getSelectedRoom() {
    return selectedRoom;
}

public void setSelectedRoom(Rooms selectedRoom) {
    this.selectedRoom = selectedRoom;
}

// ================== ADD ROOM ==================

public String addRoom() {
    try {
        abl.addRoom(roomhotelId, roomType, roomPrice, availability,roomimagepath);   // FIXED
        rooms = null; // reload table
        return "AdminDashboard.jsf?faces-redirect=true";
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

// ================== LIST ROOMS ==================

private Collection<Rooms> rooms;

public Collection<Rooms> getRooms() {
    rooms = abl.getAllRooms();
    return rooms;
}

public void setRooms(Collection<Rooms> rooms) {
    this.rooms = rooms;
}

// ================== LOAD ROOM FOR EDIT ==================

public String loadRoomForEdit(Integer id) {

    selectedRoom = abl.findRoomById(id);

    if (selectedRoom != null) {
        this.roomId = selectedRoom.getRoomId();
        this.roomhotelId  = selectedRoom.getHotels().getHotelId();  // FIXED
        this.roomType = selectedRoom.getRoomType();
        this.roomPrice = selectedRoom.getRoomPrice();
        this.availability = selectedRoom.getAvailability();
         this.roomimagepath= selectedRoom.getRoomimagepath();   
    }

    return "EditRoom.jsf?faces-redirect=true";
}

// ================== UPDATE ROOM ==================

public String updateRoom() {
    try {
        abl.updateRoom(roomId, roomhotelId, roomType, roomPrice, availability,roomimagepath);  // FIXED
        rooms = null; // refresh list
        return "AdminDashboard.jsf?faces-redirect=true";
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

// ================== DELETE ROOM ==================

public String deleteRoom(Integer id) {
    try {
        abl.deleteRoom(id);
        rooms = null;
        return "AdminDashboard.jsf?faces-redirect=true";
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
//userList

 private Collection<Users> userList;

    // Load all users whose Group ID = 2
    public Collection<Users> getUserList() {
        if (userList == null) {
            userList = abl.getUsersByGroup(2);
        }
        return userList;
    }
    
        public String deleteUser(Integer userId) {
        abl.deleteUser(userId);
        userList = abl.getUsersByGroup(2); // refresh
        return null;
    }
//booking
        
        
        private Integer bookingId;
    private String status;
   private Integer selectedBookingId;
private String selectedStatus;

public Integer getSelectedBookingId() {
    return selectedBookingId;
}

public void setSelectedBookingId(Integer selectedBookingId) {
    this.selectedBookingId = selectedBookingId;
}

public String getSelectedStatus() {
    return selectedStatus;
}

public void setSelectedStatus(String selectedStatus) {
    this.selectedStatus = selectedStatus;
}



    // ---------- GET BOOKING LIST ----------
    public Collection<Booking> getBookingList() {
        return abl.getAllBookings();
    }

    // ---------- UPDATE ONLY STATUS ----------
  public String updateBookingStatus(Integer bookingId) {

    Booking b = abl.findBookingById(bookingId);

    if (b != null) {

        java.sql.Date sqlIn  = new java.sql.Date(b.getCheckInDate().getTime());
        java.sql.Date sqlOut = new java.sql.Date(b.getCheckOutDate().getTime());

        b.setStatus(selectedStatus);

        abl.updateBooking(
            b.getBookingId(),
            b.getUsers().getUserId(),
            b.getRooms().getRoomId(),
            sqlIn,
            sqlOut,
            selectedStatus
        );
    }

    return "AdminDashboard.jsf?faces-redirect=true";
}
 
 

    // GETTERS SETTERS
    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
   //payment
    private Collection<Payment> paymentList;

    public Collection<Payment> getPaymentList() {
        if (paymentList == null) {
            paymentList = abl.getAllPayments();
        }
        return paymentList;
    }

    // Navigate to Payments page
    public String goToPayments() {
        return "Payments.jsf?faces-redirect=true";
    }
        
}
    