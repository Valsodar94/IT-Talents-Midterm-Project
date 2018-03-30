package userStuff;

import java.time.LocalDate;

import exceptions.InvalidInformationException;
import places.City;
import places.Place;
import website.Website;

public class Admin extends User{

	protected Admin(String firstName, String lastName, String city, String emailAdress, String password,
			String phoneNumber, LocalDate birthday) throws InvalidInformationException {
		super(firstName, lastName, city, emailAdress, password, phoneNumber, birthday);
	}

	public void addPlace(Place p) throws InvalidInformationException{
		if(p!=null) {
			if(p.isRestaurant()) {
				if(!(Website.getWebsite().getAllRestaurants(this).contains(p)))
					Website.getWebsite().getAllRestaurants(this).add(p);
				else
					throw new InvalidInformationException("The restaurant is already added in our system");
			} else {
				if(!(Website.getWebsite().getAllClubs(this).contains(p)))
					Website.getWebsite().getAllClubs(this).add(p);
				else
					throw new InvalidInformationException("The club is already added in our system");
			}
		}
		else 
			throw new InvalidInformationException("Podavash null za place");
	}

//	to add remove methods for Places and all other staff, also - add methods for events and offers or to leave them to their respective classes?!

}
