package userStuff;

import java.time.LocalDate;

import exceptions.InvalidInformationException;
import places.City;
import places.Place;
import services.Event;
import services.Offer;
import website.Website;


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
					addRestaurantInCityRestaurants(p);
				}
				else {
					throw new InvalidInformationException("The restaurant is already added in our system");
				}
			} else {
				if(!(Website.getWebsite().getAllClubs(this).contains(p))) {
					System.out.println("The club has been added.");
					Website.getWebsite().getAllClubs(this).add(p);
					addPlaceCityIfNotPresent(p.getCity());
					addClubInCityClubs(p);
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
					removeRestaurantFromCityRestaurants(p);
				}
			} else {
				if(!(Website.getWebsite().getAllClubs(this).contains(p))) {
					throw new InvalidInformationException("The club is not in our system, nothing to remove.");
				}
				else {
					System.out.println("The club has been sucessfully removed.");
					Website.getWebsite().getAllClubs(this).remove(p);
					removeClubFromCityClubs(p);
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
				addEventInCityEvents(e);
			}
		} else 
			throw new InvalidInformationException("Podavash null za event!");
	}
	
	public void removeEvent(Event e) throws InvalidInformationException {
		if(e!=null) {
			if(Website.getWebsite().getAllEvents(this).contains(e)) {
				System.out.println("The event has been removed!");
				Website.getWebsite().getAllEvents(this).remove(e);
				removeEventFromCityEvents(e);
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
				addOfferInCityOffers(o);
			}
		} else
			throw new InvalidInformationException("Podavash null za offer");
	}
	
	public void removeOffer(Offer o) throws InvalidInformationException {
		if(o!=null) {
			if(Website.getWebsite().getAllOffers(this).contains(o)) {
				System.out.println("The offer is already present in the system.");
				Website.getWebsite().getAllOffers(this).remove(o);
				removeOfferFromCityOffers(o);
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
	private void addRestaurantInCityRestaurants(Place p) {
		for(City c: Website.getWebsite().getAllCities(this)) {
			if(c.getName().equals(p.getName())) {
				c.addRestaurant(p);
			}
		}
	}
	private void addClubInCityClubs(Place p) {
		for(City c: Website.getWebsite().getAllCities(this)) {
			if(c.getName().equals(p.getName())) {
				c.addClub(p);
			}
		}
	}
	private void removeRestaurantFromCityRestaurants(Place p) {
		for(City c: Website.getWebsite().getAllCities(this)) {
			if(c.getName().equals(p.getName())) {
				c.removeRestaurant(p);
			}
		}
	}
	private void removeClubFromCityClubs(Place p) {
		for(City c: Website.getWebsite().getAllCities(this)) {
			if(c.getName().equals(p.getName())) {
				c.removeClub(p);
			}
		}
	}
	private void addOfferInCityOffers(Offer o) {
		for(City c: Website.getWebsite().getAllCities(this)) {
			if(c.getName().equals(o.getPlace().getName())) {
				c.addOffer(o);
			}
		}
	}
	private void removeOfferFromCityOffers(Offer o) {
		for(City c: Website.getWebsite().getAllCities(this)) {
			if(c.getName().equals(o.getPlace().getName())) {
				c.removeOffer(o);
			}
		}
	}
	private void addEventInCityEvents(Event e) {
		for(City c: Website.getWebsite().getAllCities(this)) {
			if(c.getName().equals(e.getPlace().getName())) {
				c.addEvent(e);
			}
		}
	}
	private void removeEventFromCityEvents(Event e) {
		for(City c: Website.getWebsite().getAllCities(this)) {
			if(c.getName().equals(e.getPlace().getName())) {
				c.removeEvent(e);
			}
		}
	}
}
