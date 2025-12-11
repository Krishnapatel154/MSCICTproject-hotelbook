/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package EJB;

import Entity.Booking;
import Entity.Payment;
import Entity.Rooms;
import Entity.Users;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author Admin
 */
@Stateless
public class UserBean implements UserBeanLocal {
  @PersistenceContext(unitName = "Hotelbooking")
    EntityManager em;
  
  //booking
    @Override
    public void createBooking(Integer userId, Integer roomId,java.sql.Date checkIn, java.sql.Date checkOut) {
             Users u = em.find(Users.class, userId);
        Rooms r = em.find(Rooms.class, roomId);
        if (u == null) throw new IllegalArgumentException("Invalid user");
        if (r == null) throw new IllegalArgumentException("Invalid room");

        Booking b = new Booking();
        b.setUsers(u);
        b.setRooms(r);
        b.setCheckInDate(checkIn);
        b.setCheckOutDate(checkOut);
        b.setStatus("CONFIRMED");

        // update collections
        Collection<Booking> userBookings = u.getBookingCollection();
        if (userBookings != null) { userBookings.add(b); u.setBookingCollection(userBookings); }

        Collection<Booking> roomBookings = r.getBookingCollection();
        if (roomBookings != null) { roomBookings.add(b); r.setBookingCollection(roomBookings); }

        em.persist(b);
    }

    @Override
    public void cancelBooking(Integer bookingId, Integer userId) {
         Booking b = em.find(Booking.class, bookingId);
        if (b == null) throw new IllegalArgumentException("Invalid booking");
        if (b.getUsers() == null || !b.getUsers().getUserId().equals(userId)) {
            throw new SecurityException("User not authorized to cancel this booking");
        }
        em.remove(b);
    }

    @Override
    public Collection<Booking> getBookingsOfUser(Integer userId) {
           try {
            return em.createNamedQuery("Booking.findByUserId", Booking.class)
                     .setParameter("userId", userId)
                     .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }  
    }
   
    
    //payment 
    
    @Override
    public void makePayment(Integer bookingId, BigDecimal amount, Date paymentDate, String paymentMode) {
    Booking b = em.find(Booking.class, bookingId);
        if (b == null) throw new IllegalArgumentException("Invalid booking");

        Payment p = new Payment();
        p.setBooking(b);
        p.setAmount(amount);
        p.setPaymentDate(paymentDate);
        p.setPaymentMode(paymentMode);
        p.setStatus("Success");

        // set booking<->payment if mapped
        b.setPayment(p);

        em.persist(p);
        em.merge(b);
    }
    

    @Override
    public Collection<Payment> getPaymentsOfUser(Integer userId) {
         try {
            return em.createQuery(
                    "SELECT p FROM Payment p WHERE p.booking.users.userId = :userId", Payment.class)
                     .setParameter("userId", userId)
                     .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //Room
    
    @Override
    public Collection<Rooms> searchRoomsByHotel(Integer hotelId) {
           try {
            return em.createNamedQuery("Rooms.findByHotelId", Rooms.class)
                     .setParameter("hotelId", hotelId)
                     .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
     }

    @Override
    public Rooms findRoomById(Integer roomId) {
    return em.find(Rooms.class, roomId);
    }
    
   @Override
public Users findUserByUsernamePassword(String username, String password) {
    try {
        return em.createQuery(
            "SELECT u FROM Users u WHERE u.username = :uname AND u.password = :pass",
            Users.class
        )
        .setParameter("uname", username)
        .setParameter("pass", password)
        .getSingleResult();
    } catch (Exception e) {
        return null;
    }
}
}
