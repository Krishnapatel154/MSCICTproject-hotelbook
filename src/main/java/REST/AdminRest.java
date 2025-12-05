/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package REST;

import EJB.AdminBeanLocal;
import Entity.Booking;
import Entity.GroupMaster;
import Entity.Hotels;
import Entity.Payment;
import Entity.Rooms;
import Entity.Users;
import jakarta.ejb.EJB;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

/**
 *
 * @author Admin
 */
@Path("admin")
public class AdminRest {
       @EJB
    AdminBeanLocal abl;
       
       //Hotel api
       
    @POST                                                                                                                                                                               
@Path("/add/{hotelName}/{location}/{city}/{rating}/{description}")
public String addHotel(
    @PathParam("hotelName") String hotelName,
    @PathParam("location") String location,
    @PathParam("city") String city,
    @PathParam("rating") Float rating,
    @PathParam("description") String description
) {
    abl.addHotel(hotelName, location, city, rating, description);
    return "Hotel Added Successfully";
}

   @PUT
    @Path("/update/{hotelId}/{hotelName}/{location}/{city}/{rating}/{description}")
    public String updateHotel(
            @PathParam("hotelId") Integer hotelId,
            @PathParam("hotelName") String hotelName,
            @PathParam("location") String location,
            @PathParam("city") String city,
            @PathParam("rating") Float rating,
            @PathParam("description") String description) {

        abl.updateHotel(hotelId, hotelName, location, city, rating, description);
        return "Hotel updated successfully";
    }

    @DELETE
@Path("/delete/{hotelId}")
public String deleteHotel(@PathParam("hotelId") Integer hotelId) {

    abl.deleteHotel(hotelId);   // EJB method call
    return "Hotel deleted successfully";
}

@GET
@Path("/all")
@Produces(MediaType.APPLICATION_JSON)
public Collection<Hotels> getAllHotels() {
    return abl.getAllHotel();
}

@GET
@Path("/find/{hotelId}")
@Produces(MediaType.APPLICATION_JSON)
public Hotels findHotelById(@PathParam("hotelId") Integer hotelId) {
    return abl.findHotelById(hotelId);
}
  
@GET
@Path("/find/name/{hotelName}")
@Produces(MediaType.APPLICATION_JSON)
public Collection<Hotels> findHotelByName(@PathParam("hotelName") String hotelName) {
    return abl.findHotelByName(hotelName);
}

@GET
@Path("/find/location/{location}")
@Produces(MediaType.APPLICATION_JSON)
public Collection<Hotels> findHotelByLocation(@PathParam("location") String location) {
    return abl.findHotelByLocation(location);
}
  

//room api

@POST
@Path("/addRoom/{hotelId}/{roomType}/{roomPrice}/{availability}")
public String addRoom(
        @PathParam("hotelId") Integer hotelId,
        @PathParam("roomType") String roomType,
        @PathParam("roomPrice") BigDecimal roomPrice,
        @PathParam("availability") Boolean availability) {

    abl.addRoom(hotelId, roomType, roomPrice, availability);
    return "Room added successfully";
}
 @PUT
@Path("/updateRoom/{roomId}/{hotelId}/{roomType}/{roomPrice}/{availability}")
public String updateRoom(
        @PathParam("roomId") Integer roomId,
        @PathParam("hotelId") Integer hotelId,
        @PathParam("roomType") String roomType,
        @PathParam("roomPrice") BigDecimal roomPrice,
        @PathParam("availability") Boolean availability) {

    abl.updateRoom(roomId, hotelId, roomType, roomPrice, availability);
    return "Room updated successfully";
}

@DELETE
@Path("/deleteRoom/{roomId}")
public String deleteRoom(@PathParam("roomId") Integer roomId) {
    abl.deleteRoom(roomId);
    return "Room deleted successfully";
}
@GET
@Path("/allRoom")
@Produces(MediaType.APPLICATION_JSON)
public Collection<Rooms> getAllRooms() {
    return abl.getAllRooms();
}
@GET
@Path("/findRoom/{roomId}")
@Produces(MediaType.APPLICATION_JSON)
public Rooms findRoomById(@PathParam("roomId") Integer roomId) {
    return abl.findRoomById(roomId);
}
@GET
@Path("/hotel/{hotelId}")
@Produces(MediaType.APPLICATION_JSON)
public Collection<Rooms> getRoomsByHotel(@PathParam("hotelId") Integer hotelId) {
    return abl.getRoomsByHotel(hotelId);
}
 
//booking api

@POST
@Path("/booking/add/{userId}/{roomId}/{checkIn}/{checkOut}/{status}")
public String addBooking(
        @PathParam("userId") Integer userId,
        @PathParam("roomId") Integer roomId,
        @PathParam("checkIn") String checkIn,
        @PathParam("checkOut") String checkOut,
        @PathParam("status") String status) {

    Date cin = Date.valueOf(checkIn);      // yyyy-mm-dd format
    Date cout = Date.valueOf(checkOut);

    abl.addBooking(userId, roomId, cin, cout, status);
    return "Booking added successfully";
}

@PUT
@Path("/booking/update/{bookingId}/{userId}/{roomId}/{checkIn}/{checkOut}/{status}")
public String updateBooking(
        @PathParam("bookingId") Integer bookingId,
        @PathParam("userId") Integer userId,
        @PathParam("roomId") Integer roomId,
        @PathParam("checkIn") String checkIn,
        @PathParam("checkOut") String checkOut,
        @PathParam("status") String status) {

    Date cin = Date.valueOf(checkIn);
    Date cout = Date.valueOf(checkOut);

    abl.updateBooking(bookingId, userId, roomId, cin, cout, status);
    return "Booking updated successfully";
}
@DELETE
@Path("/booking/delete/{bookingId}")
public String deleteBooking(@PathParam("bookingId") Integer bookingId) {
    abl.deleteBooking(bookingId);
    return "Booking deleted successfully";
}

@GET
@Path("/booking/all")
@Produces(MediaType.APPLICATION_JSON)
public Collection<Booking> getAllBookings() {
    return abl.getAllBookings();
}

@GET
@Path("/booking/{bookingId}")
@Produces(MediaType.APPLICATION_JSON)
public Booking getBookingById(@PathParam("bookingId") Integer bookingId) {
    return abl.findBookingById(bookingId);
}

@GET
@Path("/booking/user/{userId}")
@Produces(MediaType.APPLICATION_JSON)
public Collection<Booking> getBookingsByUser(@PathParam("userId") Integer userId) {
    return abl.getBookingsByUser(userId);
}

@GET
@Path("/booking/room/{roomId}")
@Produces(MediaType.APPLICATION_JSON)
public Collection<Booking> getBookingsByRoom(@PathParam("roomId") Integer roomId) {
    return abl.getBookingsByRoom(roomId);
}

//payment api 

@POST
@Path("/payment/add/{bookingId}/{amount}/{paymentDate}/{paymentMode}/{status}")
public String addPayment(
        @PathParam("bookingId") Integer bookingId,
        @PathParam("amount") BigDecimal amount,
        @PathParam("paymentDate") String paymentDate,
        @PathParam("paymentMode") String paymentMode, 
        @PathParam("status") String status) {

    Date pDate = Date.valueOf(paymentDate);  // yyyy-mm-dd

    abl.addPayment(bookingId, amount, pDate, paymentMode, status);
    return "Payment added successfully";
}

@PUT    
@Path("/payment/update/{paymentId}/{bookingId}/{amount}/{paymentDate}/{paymentMode}/{status}")
public String updatePayment(
        @PathParam("paymentId") Integer paymentId,
        @PathParam("bookingId") Integer bookingId,
        @PathParam("amount") BigDecimal amount,
        @PathParam("paymentDate") String paymentDate,
        @PathParam("paymentMode") String paymentMode,
        @PathParam("status") String status) {

    Date pDate = Date.valueOf(paymentDate);

    abl.updatePayment(paymentId, bookingId, amount, pDate, paymentMode, status);
    return "Payment updated successfully";
}
@DELETE
@Path("/payment/delete/{paymentId}")
public String deletePayment(@PathParam("paymentId") Integer paymentId) {
    abl.deletePayment(paymentId);
    return "Payment deleted successfully";
}
@GET
@Path("/payment/all")
@Produces(MediaType.APPLICATION_JSON)
public Collection<Payment> getAllPayments() {
    return abl.getAllPayments();
}
@GET
@Path("/payment/{paymentId}")
@Produces(MediaType.APPLICATION_JSON)
public Payment getPaymentById(@PathParam("paymentId") Integer paymentId) {
    return abl.findPaymentById(paymentId);
}
@GET
@Path("/payment/booking/{bookingId}")
@Produces(MediaType.APPLICATION_JSON)
public Collection<Payment> getPaymentsByBooking(@PathParam("bookingId") Integer bookingId) {
    return abl.getPaymentsByBooking(bookingId);
}

//group and user

@POST
@Path("/group/add/{groupName}")
public String addGroup(@PathParam("groupName") String groupName) {
    try {
        abl.addGroup(groupName);
        return "Group added successfully";

    } catch (Exception e) {
        e.printStackTrace();
        return "Error: " + e.getMessage();
    }
}

@PUT
@Path("/group/update/{groupId}/{groupName}")
public String updateGroup(
        @PathParam("groupId") Integer groupId,
        @PathParam("groupName") String groupName) {

    try {
        abl.updateGroup(groupId, groupName);
        return "Group updated successfully";

    } catch (Exception e) {
        e.printStackTrace();
        return "Error: " + e.getMessage();
    }
}

@DELETE
@Path("/group/delete/{groupId}")
public String deleteGroup(@PathParam("groupId") Integer groupId) {
    try {
        abl.deleteGroup(groupId);
        return "Group deleted successfully";

    } catch (Exception e) {
        return "Error: " + e.getMessage();
    }
}

@GET
@Path("/group/all")
@Produces(MediaType.APPLICATION_JSON)
public Collection<GroupMaster> getAllGroups() {
    return abl.getAllGroups();
}

@GET
@Path("/group/{groupId}")
@Produces(MediaType.APPLICATION_JSON)
public GroupMaster findGroup(@PathParam("groupId") Integer groupId) {
    return abl.findGroupById(groupId);
}


@POST
@Path("/user/add/{groupId}/{username}/{email}/{password}/{phone}")
public String addUser(
        @PathParam("groupId") Integer groupId,
        @PathParam("username") String username,
        @PathParam("email") String email,
        @PathParam("password") String password,
        @PathParam("phone") String phone) { 

    try {
        abl.addUser(groupId, username, email, password, phone);
        return "User added successfully";

    } catch (Exception e) {
        e.printStackTrace();
        return "Error: " + e.getMessage();
    }
}

@PUT
@Path("/user/update/{userId}/{groupId}/{username}/{email}/{password}/{phone}")
public String updateUser(
        @PathParam("userId") Integer userId,
        @PathParam("groupId") Integer groupId,
        @PathParam("username") String username,
        @PathParam("email") String email,
        @PathParam("password") String password,
        @PathParam("phone") String phone) {

    try {
        abl.updateUser(userId, groupId, username, email, password, phone);
        return "User updated successfully";

    } catch (Exception e) {
        return "Error: " + e.getMessage();
    }
}

@DELETE
@Path("/user/delete/{userId}")
public String deleteUser(@PathParam("userId") Integer userId) {

    try {
        abl.deleteUser(userId);
        return "User deleted successfully";

    } catch (Exception e) {
        return "Error: " + e.getMessage();
    }
}

@GET
@Path("/user/all")
@Produces(MediaType.APPLICATION_JSON)
public Collection<Users> getAllUsers() {
    return abl.getAllUsers();
}

@GET
@Path("/user/{userId}")
@Produces(MediaType.APPLICATION_JSON)
public Users findUser(@PathParam("userId") Integer userId) {
    return abl.findUserById(userId);
}

@GET
@Path("/user/group/{groupId}")
@Produces(MediaType.APPLICATION_JSON)
public Collection<Users> getUsersByGroup(@PathParam("groupId") Integer groupId) {
    return abl.getUsersByGroup(groupId);
}


}

