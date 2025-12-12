    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package EJB;

import Entity.Booking;
import Entity.GroupMaster;
import Entity.Hotels;
import Entity.Payment;
import Entity.Rooms;
import Entity.Users;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author Admin
 */
@Stateless
public class AdminBean implements AdminBeanLocal {

    @PersistenceContext(unitName = "Hotelbooking")
    EntityManager em;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @Override
    public void addHotel(String hotelName, String location, String city, Float rating, String description) {
        Hotels h = new Hotels();
        h.setHotelName(hotelName);
        h.setLocation(location);
        h.setCity(city);
        h.setRating(rating);
        h.setDescription(description);
        em.persist(h);
    }

    @Override
    public void updateHotel(Integer hotelId, String hotelName, String location, String city, Float rating, String description) {
        Hotels h = em.find(Hotels.class, hotelId);
        if (h == null) {
            throw new IllegalArgumentException("Invalid Hotel ID: " + hotelId);
        }
        h.setHotelName(hotelName);
        h.setLocation(location);
        h.setCity(city);
        h.setRating(rating);
        h.setDescription(description);
        em.merge(h);
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteHotel(Integer hotelId) {
        Hotels h = em.find(Hotels.class, hotelId);
        if (h != null) {
            em.remove(h);
        }
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Hotels> getAllHotel() {
        try {
            return em.createNamedQuery("Hotels.findAll", Hotels.class).getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Hotels findHotelById(Integer hotelId) {
        return em.find(Hotels.class, hotelId);
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Hotels> findHotelByName(String hotelName) {
        try {
            return em.createNamedQuery("Hotels.findByHotelName", Hotels.class)
                    .setParameter("hotelName", hotelName)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Hotels> findHotelByLocation(String location) {
        try {
            return em.createNamedQuery("Hotels.findByLocation", Hotels.class)
                    .setParameter("location", location)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //Room
    @Override
    public void addRoom(Integer hotelId, String roomType, BigDecimal roomPrice, Boolean availability) {
        Rooms r = new Rooms();
        r.setRoomType(roomType);
        r.setRoomPrice(roomPrice);
        r.setAvailability(availability);

        if (hotelId != null) {
            Hotels h = em.find(Hotels.class, hotelId);
            if (h == null) {
                throw new IllegalArgumentException("Invalid Hotel ID: " + hotelId);
            }
            r.setHotels(h);                // set FK object (Rooms has setHotels)
            // maintain collection on Hotels side if you have roomsCollection and want to add:
            Collection<Rooms> rooms = h.getRoomsCollection();
            if (rooms != null) {
                rooms.add(r);
                h.setRoomsCollection(rooms);
            }
        }
        em.persist(r);
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateRoom(Integer roomId, Integer hotelId, String roomType, BigDecimal roomPrice, Boolean availability) {
        Rooms r = em.find(Rooms.class, roomId);
        if (r == null) {
            throw new IllegalArgumentException("Invalid Room ID: " + roomId);
        }

        r.setRoomType(roomType);
        r.setRoomPrice(roomPrice);
        r.setAvailability(availability);

        if (hotelId != null) {
            Hotels h = em.find(Hotels.class, hotelId);
            if (h == null) {
                throw new IllegalArgumentException("Invalid Hotel ID: " + hotelId);
            }
            r.setHotels(h);
        }

        em.merge(r);
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteRoom(Integer roomId) {

        Rooms r = em.find(Rooms.class, roomId);
        if (r != null) {
            em.remove(r);
        }
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Rooms> getAllRooms() {
        try {
            return em.createNamedQuery("Rooms.findAll", Rooms.class).getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    @Override
    public Rooms findRoomById(Integer roomId) {
        return em.find(Rooms.class, roomId);
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Rooms> getRoomsByHotel(Integer hotelId) {
        try {
            return em.createNamedQuery("Rooms.findByHotelId", Rooms.class)
                    .setParameter("hotelId", hotelId)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //Booking
    @Override
    public void addBooking(Integer userId, Integer roomId, Date checkIn, Date checkOut, String status) {
        Booking b = new Booking();

        if (userId != null) {
            Users u = em.find(Users.class, userId);
            if (u == null) {
                throw new IllegalArgumentException("Invalid User ID: " + userId);
            }
            b.setUsers(u);
            // keep user's booking collection updated if needed
            Collection<Booking> userBookings = u.getBookingCollection();
            if (userBookings != null) {
                userBookings.add(b);
                u.setBookingCollection(userBookings);
            }
        }

        if (roomId != null) {
            Rooms r = em.find(Rooms.class, roomId);
            if (r == null) {
                throw new IllegalArgumentException("Invalid Room ID: " + roomId);
            }
            b.setRooms(r);
            Collection<Booking> roomBookings = r.getBookingCollection();
            if (roomBookings != null) {
                roomBookings.add(b);
                r.setBookingCollection(roomBookings);
            }
        }

        b.setCheckInDate(checkIn);
        b.setCheckOutDate(checkOut);
        b.setStatus(status);

        em.persist(b);
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateBooking(Integer bookingId, Integer userId, Integer roomId, Date checkIn, Date checkOut, String status) {
        Booking b = em.find(Booking.class, bookingId);
        if (b == null) {
            throw new IllegalArgumentException("Invalid Booking ID: " + bookingId);
        }

        if (userId != null) {
            Users u = em.find(Users.class, userId);
            if (u == null) {
                throw new IllegalArgumentException("Invalid User ID: " + userId);
            }
            b.setUsers(u);
        }

        if (roomId != null) {
            Rooms r = em.find(Rooms.class, roomId);
            if (r == null) {
                throw new IllegalArgumentException("Invalid Room ID: " + roomId);
            }
            b.setRooms(r);
        }

        b.setCheckInDate(checkIn);
        b.setCheckOutDate(checkOut);
        b.setStatus(status);

        em.merge(b);
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteBooking(Integer bookingId) {
        Booking b = em.find(Booking.class, bookingId);
        if (b != null) {
            em.remove(b);
        }
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Booking> getAllBookings() {
        try {
            return em.createNamedQuery("Booking.findAll", Booking.class).getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Booking findBookingById(Integer bookingId) {
        return em.find(Booking.class, bookingId);
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Booking> getBookingsByUser(Integer userId) {
        try {
            return em.createNamedQuery("Booking.findByUserId", Booking.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Booking> getBookingsByRoom(Integer roomId) {
        try {
            return em.createNamedQuery("Booking.findByRoomId", Booking.class)
                    .setParameter("roomId", roomId)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //Payment
    @Override
    public void addPayment(Integer bookingId, BigDecimal amount, Date paymentDate, String paymentMode, String status) {
        Payment p = new Payment();
        p.setAmount(amount);
        p.setPaymentDate(paymentDate);
        p.setPaymentMode(paymentMode);
        p.setStatus(status);

        if (bookingId != null) {
            Booking b = em.find(Booking.class, bookingId);
            if (b == null) {
                throw new IllegalArgumentException("Invalid Booking ID: " + bookingId);
            }

            p.setBooking(b);
            b.setPayment(p); // if Booking has setPayment
            em.merge(b);
            em.persist(p);

        }
    }

    @Override
    public void updatePayment(Integer paymentId, Integer bookingId, BigDecimal amount, Date paymentDate, String paymentMode, String status) {
        Payment p = em.find(Payment.class, paymentId);
        if (p == null) {
            throw new IllegalArgumentException("Invalid Payment ID: " + paymentId);
        }

        if (bookingId != null) {
            Booking b = em.find(Booking.class, bookingId);
            if (b == null) {
                throw new IllegalArgumentException("Invalid Booking ID: " + bookingId);
            }
            p.setBooking(b);
        }

        p.setAmount(amount);
        p.setPaymentDate(paymentDate);
        p.setPaymentMode(paymentMode);
        p.setStatus(status);

        em.merge(p);
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deletePayment(Integer paymentId) {
        Payment p = em.find(Payment.class, paymentId);
        if (p != null) {
            em.remove(p);
        }
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Payment> getAllPayments() {
        try {
            return em.createNamedQuery("Payment.findAll", Payment.class).getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Payment findPaymentById(Integer paymentId) {
        return em.find(Payment.class, paymentId);
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Payment> getPaymentsByBooking(Integer bookingId) {
        try {
            return em.createNamedQuery("Payment.findByBookingId", Payment.class)
                    .setParameter("bookingId", bookingId)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //user and group
    @Override
    public void addGroup(String groupName) {
        GroupMaster g = new GroupMaster();
        if (groupName == null || groupName.trim().isEmpty()) {
            throw new IllegalArgumentException("Group name cannot be empty");
        }
        g.setGroupName(groupName);

        em.persist(g);
    }

    @Override
    public void updateGroup(Integer groupId, String groupName) {
        GroupMaster g = em.find(GroupMaster.class, groupId);

        if (g == null) {
            throw new IllegalArgumentException("Invalid Group ID");
        }

        if (groupName == null || groupName.trim().isEmpty()) {
            throw new IllegalArgumentException("Group name cannot be empty");
        }

        g.setGroupName(groupName);
        em.merge(g);
    }

    @Override
    public void deleteGroup(Integer groupId) {
        GroupMaster g = em.find(GroupMaster.class, groupId);

        if (g == null) {
            throw new IllegalArgumentException("Invalid Group ID");
        }

        em.remove(g);
    }

    @Override
    public Collection<GroupMaster> getAllGroups() {
        try {
            return em.createQuery("SELECT g FROM GroupMaster g", GroupMaster.class)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public GroupMaster findGroupById(Integer groupId) {
        return em.find(GroupMaster.class, groupId);
    }

    @Override
    public void addUser(Integer groupId, String username, String email, String password, String phone) {
        GroupMaster g = em.find(GroupMaster.class, groupId);
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if (g == null) {
            throw new IllegalArgumentException("Invalid Group ID");
        }

        Users u = new Users();
        u.setGroupMaster(g);
        u.setUsername(username);
        u.setEmail(email);
        System.out.println("hash object = " + passwordHash);

        u.setPassword(passwordHash.generate(password.toCharArray()));
        //        u.setPassword(password);
        u.setPhone(phone);

        try {
            em.persist(u);
        } catch (ConstraintViolationException ex) {
            ex.getConstraintViolations().forEach(v -> {
                System.out.println("PROPERTY: " + v.getPropertyPath());
                System.out.println("VALUE: " + v.getInvalidValue());
                System.out.println("MESSAGE: " + v.getMessage());
            });
            throw ex;
        }

    }

    @Override
    public void updateUser(Integer userId, Integer groupId, String username, String email, String password, String phone) {
        Users u = em.find(Users.class, userId);
        if (u == null) {
            throw new IllegalArgumentException("Invalid User ID");
        }

        GroupMaster g = em.find(GroupMaster.class, groupId);
        if (g == null) {
            throw new IllegalArgumentException("Invalid Group ID");
        }

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        //    u.setGroupId(groupId);
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(password);
        u.setPhone(phone);

        em.merge(u);
    }

    @Override
    public void deleteUser(Integer userId) {
        Users u = em.find(Users.class, userId);
        if (u == null) {
            throw new IllegalArgumentException("Invalid User ID");
        }

        em.remove(u);
    }

    @Override
    public Collection<Users> getAllUsers() {
        try {
            return em.createQuery("SELECT u FROM Users u", Users.class)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Users findUserById(Integer userId) {
        return em.find(Users.class, userId);
    }

    @Override
    public Collection<Users> getUsersByGroup(Integer groupId) {
        try {
            return em.createQuery(
                    "SELECT u FROM Users u WHERE  u.groupMaster.groupId  = :groupId",
                    Users.class)
                    .setParameter("groupId", groupId)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
  
    @Override
    public Collection<Rooms> getRoomsByHotelId(Integer filterHotelId) {
 try {
        return em.createQuery("SELECT r FROM Rooms r WHERE r.hotels.hotelId = :hid", Rooms.class)
                .setParameter("hid", filterHotelId)
                .getResultList();
    } catch (Exception e) {
        return Collections.emptyList();
    }    }

    @Override
    public Integer processPayment(Integer userId, Integer roomId, String paymentMode) {
         Users u = em.find(Users.class, userId);
    Rooms r = em.find(Rooms.class, roomId);

    if (u == null) throw new IllegalArgumentException("Invalid User ID");
    if (r == null) throw new IllegalArgumentException("Invalid Room ID");

    // 1. Create Booking
    Booking b = new Booking();
    b.setUsers(u);
    b.setRooms(r);
    b.setCheckInDate(new java.sql.Date(System.currentTimeMillis()));
    b.setCheckOutDate(new java.sql.Date(System.currentTimeMillis()));
    b.setStatus("PAID");
    em.persist(b);
    em.flush();

    // 2. Create Payment
    Payment p = new Payment();
    p.setBooking(b);
    p.setAmount(r.getRoomPrice());
    p.setPaymentDate(new java.sql.Date(System.currentTimeMillis()));
    p.setPaymentMode(paymentMode);
    p.setStatus("SUCCESS");

    em.persist(p);
    em.flush();

    return p.getPaymentId();
    }

}
