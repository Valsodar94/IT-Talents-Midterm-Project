package places;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import exceptions.InvalidInformationException;
import services.Event;
import services.Offer;
import website.Website;

public class City {
	private final String name;
	private List<String> regions;
	private Set<Place> restaurants;
	private Set<Place> clubs;
	private Set<Event> events;
	private Set<Offer> offers;

	public City(String name) throws InvalidInformationException {
		if (Place.isValidString(name)) {
			this.name = name;
		} else {
			throw new InvalidInformationException("Please, enter a valid city name");
		}
		this.regions = new ArrayList<String>();
		this.restaurants = new HashSet<>();
		this.clubs = new HashSet<>();
		this.events = new HashSet<>();
		this.offers = new HashSet<>();
	}

	public void addRegion(String region) {
		if(Place.isValidString(region) && !(regions.contains(region))) {
			regions.add(region);
		}
	}
	
	public boolean hasRegion(String region) {
		if(regions.contains(region)) {
			return true;
		}
		return false;
	}
	public void addRestaurant(Place p) {
		if(p!=null) {
			Website w = Website.getWebsite();
			if(w.getAllRestaurants(null).contains(p)) {
				restaurants.add(p);
			}
		}
	}
	public void removeRestaurant(Place p) {
		if(p!=null) {
			Website w = Website.getWebsite();
			if(w.getAllRestaurants(null).contains(p)) {
				restaurants.remove(p);
			}
		}
	}
	public void addClub(Place p) {
		if(p!=null) {
			Website w = Website.getWebsite();
			if(w.getAllClubs(null).contains(p)) {
				clubs.add(p);
			}
		}
	}
	public void removeClub(Place p) {
		if(p!=null) {
			Website w = Website.getWebsite();
			if(w.getAllClubs(null).contains(p)) {
				clubs.remove(p);
			}
		}
	}
	public void addEvent(Event e) {
		if(e!=null) {
			Website w = Website.getWebsite();
			if(w.getAllEvents(null).contains(e)) {
				this.events.add(e);
			}
		}
	}
	public void removeEvent(Event e) {
		if(e!=null) {
			Website w = Website.getWebsite();
			if(w.getAllEvents(null).contains(e)) {
				this.events.remove(e);
			}
		}
	}
	public void addOffer(Offer o) {
		if(o!=null) {
			Website w = Website.getWebsite();
			if(w.getAllOffers(null).contains(o)) {
				this.offers.add(o);
			}
		}
	}
	public void removeOffer(Offer o) {
		if(o!=null) {
			Website w = Website.getWebsite();
			if(w.getAllOffers(null).contains(o)) {
				this.offers.remove(o);
			}
		}
	}
	//getters and setters
	public String getName() {
		return name;
	}

	public List<String> getRegions() {
		return Collections.unmodifiableList(regions);
	}

	public Set<Place> getRestaurants() {
		return Collections.unmodifiableSet(restaurants);
	}

	public Set<Event> getEvents() {
		return Collections.unmodifiableSet(events);
	}

	public Set<Offer> getOffers() {
		return Collections.unmodifiableSet(offers);
	}
	public Set<Place> getClubs() {
		return Collections.unmodifiableSet(clubs);
	}

}
