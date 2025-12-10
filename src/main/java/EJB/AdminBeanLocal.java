 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SessionLocal.java to edit this template
 */
package EJB;

import Entity.Booking;
import Entity.GroupMaster;
import Entity.Hotels;
import Entity.Payment;
import Entity.Rooms;
import Entity.Users;
import jakarta.ejb.Local;
import java.util.Collection;

/**
 *
 * @author Admin
 */
@Local
public interface AdminBeanLocal {
    
     // HOTELS
     public void addHotel(String hotelName, String location, String city, Float rating, String description);
     public  void updateHotel(Integer hotelId, String hotelName, String location, String city, Float rating, String description);
     public void deleteHotel(Integer hotelId);
     public Collection<Hotels> getAllHotel();
     public Hotels findHotelById(Integer hotelId);
     public Collection<Hotels> findHotelByName(String hotelName);
     public Collection<Hotels> findHotelByLocation(String location);
     
        // ROOMS
   public  void addRoom(Integer hotelId, String roomType, java.math.BigDecimal roomPrice, Boolean availability);
    public void updateRoom(Integer roomId, Integer hotelId, String roomType, java.math.BigDecimal roomPrice, Boolean availability);
    public void deleteRoom(Integer roomId);
    public Collection<Rooms> getAllRooms();
    public Rooms findRoomById(Integer roomId);
    public Collection<Rooms> getRoomsByHotel(Integer hotelId);
    
    
    // BOOKINGS (admin management)
    public void addBooking(Integer userId, Integer roomId, java.sql.Date checkIn, java.sql.Date checkOut, String status);
    public void updateBooking(Integer bookingId, Integer userId, Integer roomId, java.sql.Date checkIn, java.sql.Date checkOut, String status);
    public void deleteBooking(Integer bookingId);
    public Collection<Booking> getAllBookings();
    public Booking findBookingById(Integer bookingId);
    public Collection<Booking> getBookingsByUser(Integer userId);
    public Collection<Booking> getBookingsByRoom(Integer roomId);

    // PAYMENTS
    public void addPayment(Integer bookingId, java.math.BigDecimal amount, java.sql.Date paymentDate, String paymentMode, String status);
    public void updatePayment(Integer paymentId, Integer bookingId, java.math.BigDecimal amount, java.sql.Date paymentDate, String paymentMode, String status);
    public void deletePayment(Integer paymentId);
    public Collection<Payment> getAllPayments();
    public Payment findPaymentById(Integer paymentId);
     public Collection<Payment> getPaymentsByBooking(Integer bookingId);
    
     // USERS & GROUPS (admin)
   public void addGroup(String groupName);
   public void updateGroup(Integer groupId, String groupName);
   public void deleteGroup(Integer groupId);
   public Collection<GroupMaster> getAllGroups();
   public GroupMaster findGroupById(Integer groupId);

    public void addUser(Integer groupId, String username, String email, String password, String phone);
    public void updateUser(Integer userId, Integer groupId, String username, String email, String password, String phone);
    public void deleteUser(Integer userId);
    public Collection<Users> getAllUsers();
    public Users findUserById(Integer userId);
   public Collection<Users> getUsersByGroup(Integer groupId);

    public Collection<Rooms> getRoomsByHotelId(Integer filterHotelId);
   // public Users loginUser(String email, String password);


    
}
 