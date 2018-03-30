package userStuff;

import java.time.LocalDate;

import exceptions.InvalidInformationException;
import places.City;
import places.Place;
import services.Event;
import services.Offer;
import website.Website;
//to make a thread that clears events/offers at the beggining of each new date

public class Admin extends User{

	protected Admin(String firstName, String lastName, String city, String emailAdress, String password,
			String phoneNumber, LocalDate birthday) throws InvalidInformationException {
		super(firstName, lastName, city, emailAdress, password, phoneNumber, birthday);
	}

	public void addPlace(Place p) throws InvalidInformationException{
		if(p!=null) {
			if(p.isRestaurant()) {
				if(!(Website.getWebsite().getAllRestaurants(this).contains(p))) {
					System.out.println("The restaurant has been added.");
					Website.getWebsite().getAllRestaurants(this).add(p);
					addPlaceCityIfNotPresent(p.getCity());
				}
				else {
					throw new InvalidInformationException("The restaurant is already added in our system");
				}
			} else {
				if(!(Website.getWebsite().getAllClubs(this).contains(p))) {
					System.out.println("The club has been added.");
					Website.getWebsite().getAllClubs(this).add(p);
					addPlaceCityIfNotPresent(p.getCity());
				}
				else
					throw new InvalidInformationException("The club is already added in our system");
			}
		}
		else 
			throw new InvalidInformationException("Podavash null za place");
	}


	public void removePlace(Place p) throws InvalidInformationException {
		if(p!=null) {
			if(p.isRestaurant()) {
				if(!(Website.getWebsite().getAllRestaurants(this).contains(p))) {
					throw new InvalidInformationException("The restaurant is not in our system, nothing to remove.");
				}
				else {
					System.out.println("The restaurant has been sucessfully removed.");
					Website.getWebsite().getAllRestaurants(this).remove(p);
				}
			} else {
				if(!(Website.getWebsite().getAllClubs(this).contains(p))) {
					throw new InvalidInformationException("The club is not in our system, nothing to remove.");
				}
				else {
					System.out.println("The club has been sucessfully removed.");
					Website.getWebsite().getAllClubs(this).remove(p);
				}
			}
		}
		else 
			throw new InvalidInformationException("Podavash null za place");
	}
	
	public void addEvent(Event e) throws InvalidInformationException {
		if(e!=null) {
			if(Website.getWebsite().getAllEvents(this).contains(e)) {
				throw new InvalidInformationException("The event is already present in the system.");
			} else {
				System.out.println("The event has been added!");
				Website.getWebsite().getAllEvents(this).add(e);
			}
		} else 
			throw new InvalidInformationException("Podavash null za event!");
	}
	
	public void removeEvent(Event e) throws InvalidInformationException {
		if(e!=null) {
			if(Website.getWebsite().getAllEvents(this).contains(e)) {
				System.out.println("The event has been removed!");
				Website.getWebsite().getAllEvents(this).remove(e);
			} else {
				throw new InvalidInformationException("The event is not present in our system, nothing to remove");
			}
		}
		else
			throw new InvalidInformationException("Podavash null za place");
	}
	
	public void addOffer(Offer o) throws InvalidInformationException {
		if(o!=null) {
			if(Website.getWebsite().getAllOffers(this).contains(o)) {
				throw new InvalidInformationException("The offer is already present in the system.");
			} else {
				System.out.println("The offer has been added!");
				Website.getWebsite().getAllOffers(this).add(o);
			}
		} else
			throw new InvalidInformationException("Podavash null za offer");
	}
	
	public void removeOffer(Offer o) throws InvalidInformationException {
		if(o!=null) {
			if(Website.getWebsite().getAllOffers(this).contains(o)) {
				System.out.println("The offer is already present in the system.");
				Website.getWebsite().getAllOffers(this).remove(o);
			} else {
				throw new InvalidInformationException("The offer does not exist in our system, nothing to remove.");
			}
		} else
			throw new InvalidInformationException("Podavash null za offer");
	}
	
	private boolean doesCityExistInOurSystem(String city) {
		for(City c: Website.getWebsite().getAllCities(this)) {
			if(c.getName().equals(city))
				return true;
		}
		return false;
	}
	private void addPlaceCityIfNotPresent(String city) throws InvalidInformationException {
		if(!(doesCityExistInOurSystem(city))) {
			City newCity = new City(city);
			Website.getWebsite().getAllCities(this).add(newCity);
		}
	}
	


}
