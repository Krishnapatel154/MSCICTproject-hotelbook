/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIBean;

import EJB.AdminBeanLocal;
import Entity.Hotels;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
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

    private Hotels selectedHotel;

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
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
            abl.addHotel(hotelName, location, city, rating, description);
            return "Hotel.jsf?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // READ - List All Hotels
    private Collection<Hotels> hotels;

    public Collection<Hotels> getHotels() {
//        if (hotels == null) {
            hotels = abl.getAllHotel();
//        }
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
        }
        return "EditHotel.jsf?faces-redirect=true";
    }

    // UPDATE
    public String updateHotel() {
        try {
            abl.updateHotel(hotelId, hotelName, location, city, rating, description);
             hotels = null;
            return "Hotel.jsf?faces-redirect=true";
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
            return "Hotel.jsf?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
