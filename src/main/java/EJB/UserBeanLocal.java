/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SessionLocal.java to edit this template
 */
package EJB;

import Entity.Booking;
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
public interface UserBeanLocal {
       // Bookings for user
   public void createBooking(Integer userId, Integer roomId, java.sql.Date checkIn, java.sql.Date checkOut);
   public void cancelBooking(Integer bookingId, Integer userId);
   public Collection<Booking> getBookingsOfUser(Integer userId);
    
     // Payments by user
   public void makePayment(Integer bookingId, java.math.BigDecimal amount, java.sql.Date paymentDate, String paymentMode);
  public  Collection<Payment> getPaymentsOfUser(Integer userId);

    // Search rooms
   public Collection<Rooms> searchRoomsByHotel(Integer hotelId);
   public Rooms findRoomById(Integer roomId);
   
public Users findUserByUsernamePassword(String username, String password);

}
