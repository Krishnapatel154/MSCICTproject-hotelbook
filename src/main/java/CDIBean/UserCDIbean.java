package CDIBean;

import EJB.UserBeanLocal;
import Entity.Rooms;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Collection;

@Named(value = "userCDIbean")
@SessionScoped
public class UserCDIbean implements Serializable {

    @EJB
    UserBeanLocal ubl;

    private Collection<Rooms> rooms;   // stores filtered rooms of selected hotel
    private Integer selectedHotelId;

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
}
