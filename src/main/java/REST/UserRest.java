/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package REST;

import EJB.UserBeanLocal;
import Entity.Booking;
import Entity.Payment;
import Entity.Rooms;
import jakarta.ejb.EJB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
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
@Path("user")
public class UserRest {
       @EJB
    UserBeanLocal ubl;
       
        // ------------------ BOOKING ------------------------

    @POST
    @Path("/booking/create/{userId}/{roomId}/{checkIn}/{checkOut}")
    public String createBooking(
            @PathParam("userId") Integer userId,
            @PathParam("roomId") Integer roomId,
            @PathParam("checkIn") String checkIn,
            @PathParam("checkOut") String checkOut) {

        Date cin = Date.valueOf(checkIn); 
        Date cout = Date.valueOf(checkOut);

        ubl.createBooking(userId, roomId, cin, cout);
        return "Booking created successfully";
    }
  
     @POST
    @Path("/booking/cancel/{bookingId}/{userId}")
    public String cancelBooking(
            @PathParam("bookingId") Integer bookingId,
            @PathParam("userId") Integer userId) {

        ubl.cancelBooking(bookingId, userId);
        return "Booking cancelled successfully";
    }

    @GET
    @Path("/booking/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Booking> getBookingsOfUser(@PathParam("userId") Integer userId) {
        return ubl.getBookingsOfUser(userId);
    }
    
    
     // ------------------ PAYMENT ------------------------
    @POST
    @Path("/payment/make/{bookingId}/{amount}/{paymentDate}/{paymentMode}")
    public String makePayment(
            @PathParam("bookingId") Integer bookingId,
            @PathParam("amount") BigDecimal amount,
            @PathParam("paymentDate") String paymentDate,
            @PathParam("paymentMode") String paymentMode) {

        Date pDate = Date.valueOf(paymentDate);

        ubl.makePayment(bookingId, amount, pDate, paymentMode);
        return "Payment successful";                            
    }


    @GET
    @Path("/payment/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Payment> getPaymentsOfUser(@PathParam("userId") Integer userId) {
        return ubl.getPaymentsOfUser(userId);
    }

 // ------------------ ROOMS ------------------------

    @GET
    @Path("/rooms/hotel/{hotelId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Rooms> searchRoomsByHotel(@PathParam("hotelId") Integer hotelId) {
        return ubl.searchRoomsByHotel(hotelId);
    }


    @GET
    @Path("/rooms/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Rooms findRoomById(@PathParam("roomId") Integer roomId) {
        return ubl.findRoomById(roomId);
    }
}
