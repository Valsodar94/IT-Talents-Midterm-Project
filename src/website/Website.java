package website;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import places.City;
import places.Place;
import services.Event;
import services.Offer;
import userStuff.Admin;
import userStuff.User;
import userStuff.UserAdministration;

public class Website {
	private static Website web = null;
	private String infoForTheWebstie;
	private String contacts;
	private String FAQ;

	private Set<Place> allRestaurants;
	private Set<Place> allClubs;
	private Set<Event> allEvents;
	private Set<Offer> allOffers;
	private Set<City> allCities;

	private Website() {
		allRestaurants = new TreeSet<>();
		allEvents = new TreeSet<>();
		allOffers = new TreeSet<>();
		allClubs = new TreeSet<>();
		allCities = new HashSet<>();
	}

	public void citiesToJson() {
		File cities = new File("JsonFiles" + File.separator + "cities.json");
		writeToJson(this.allCities, cities);
	}
	
	public void clubsToJson() {
		File clubs = new File("JsonFiles" + File.separator + "clubs.json");
		writeToJson(this.allClubs, clubs);
	}
	
	public void restaurantsToJson() {
		File restaurants = new File("JsonFiles" + File.separator + "restaurants.json");
		writeToJson(this.allClubs, restaurants);
	}
	
	public static void writeToJson(Collection collection, File file) {
		try (FileWriter writer = new FileWriter(file);) {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.setPrettyPrinting().create();
			writer.write(gson.toJson(collection));
		} catch (IOException e) {
			System.out.println("Ops, sorry! The information is not saved to the file!");
			e.printStackTrace();
		}
	}

	private Website(String infoForTheWebstie) {
		this();
		setInfoForTheWebstie(infoForTheWebstie);
	}

	private Website(String infoForTheWebstie, String contacts) {
		this(infoForTheWebstie);
		setContacts(contacts);
	}

	private Website(String infoForTheWebstie, String contacts, String FAQ) {
		this(infoForTheWebstie, contacts);
		setFAQ(FAQ);
	}

	public static Website getWebsite() {
		if (web == null)
			return new Website();
		else
			return web;
	}

	public void showPlaces(String city, boolean isRestaurant) {
		if (city != null) {
			if (isRestaurant) {
				for (Place p : allRestaurants) {
					if (p.getCity().equals(city))
						System.out.println(p);
				}
			} else {
				for (Place p : allClubs) {
					if (p.getCity().equals(city))
						System.out.println(p);
				}
			}
		}
	}

	public void showPlaces(String city, boolean isRestaurant, int num) {
		if (city != null) {
			if (num > 0) {
				int counter = 0;
				if (isRestaurant) {
					for (Place p : allRestaurants) {
						if (p.getCity().equals(city)) {
							System.out.println(p);
							counter++;
						}
						if (counter == num)
							break;
					}
				} else {
					for (Place p : allClubs) {
						if (p.getCity().equals(city)) {
							System.out.println(p);
							counter++;
						}
						if (counter == num)
							break;
					}
				}
			}
		}
	}

	public void showEvents(String city) {
		if (city != null) {
			for (Event e : allEvents) {
				if (e.getPlace().getCity().equals(city))
					;
				System.out.println(e);
			}
		}
	}

	public void showOffers(String city) {
		if (city != null) {
			for (Offer o : allOffers) {
				if (o.getPlace().getCity().equals(city))
					;
				System.out.println(o);
			}
		}
	}

	// nujni sa systite metodi za clubs i restaurants. Otdelno trqbva da se napravi
	// po oste 1 overload method za nachin na sortirane(default da byde po reiting)

	public String getInfoForTheWebstie() {
		return infoForTheWebstie;
	}

	public void setInfoForTheWebstie(String infoForTheWebstie) {
		if (UserAdministration.getU() instanceof Admin) {
			if (infoForTheWebstie != null)
				this.infoForTheWebstie = infoForTheWebstie;
		}
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		if (UserAdministration.getU() instanceof Admin) {
			if (contacts != null)
				this.contacts = contacts;
		}
	}

	public String getFAQ() {
		return FAQ;
	}

	public void setFAQ(String FAQ) {
		if (UserAdministration.getU() instanceof Admin) {
			if (FAQ != null)
				this.FAQ = FAQ;
		}
	}

	public Set<Place> getAllRestaurants(User u) {
		if (u instanceof Admin)
			return this.allRestaurants;
		else
			return Collections.unmodifiableSet(allRestaurants);
	}

	public ArrayList<Place> getAllRestaurants() {
		return new ArrayList<Place>(this.allRestaurants);
	}

	public Set<Place> getAllClubs(User u) {
		if (u instanceof Admin)
			return this.allClubs;
		else
			return Collections.unmodifiableSet(allClubs);
	}

	public ArrayList<Place> getAllClubs() {
		return new ArrayList<Place>(this.allClubs);
	}

	public Set<Event> getAllEvents(User u) {
		if (u instanceof Admin)
			return this.allEvents;
		else
			return Collections.unmodifiableSet(allEvents);
	}

	public Set<Offer> getAllOffers(User u) {
		if (u instanceof Admin)
			return this.allOffers;
		else
			return Collections.unmodifiableSet(allOffers);
	}

	public Set<City> getAllCities(User u) {
		if (u instanceof Admin)
			return this.allCities;
		else
			return Collections.unmodifiableSet(allCities);
	}

}
