package website;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

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

	public void dataFromJson() {
		File cities = new File("JsonFiles" + File.separator + "cities.json");
		File clubs = new File("JsonFiles" + File.separator + "clubs.json");
		File restaurants = new File("JsonFiles" + File.separator + "restaurants.json");
		System.out.println("Loading website data...");

		try (Reader citiesReader = new FileReader(cities);
				Reader clubsReader = new FileReader(clubs);
				Reader restaurantsReader = new FileReader(restaurants);) {
			Gson gson = new Gson();
			JsonElement citiesJson = gson.fromJson(citiesReader, JsonElement.class);
			JsonElement clubsJson = gson.fromJson(clubsReader, JsonElement.class);
			JsonElement restaurantsJson = gson.fromJson(restaurantsReader, JsonElement.class);
			String citiesResult = gson.toJson(citiesJson);
			String clubsResult = gson.toJson(clubsJson);
			String restaurantsResult = gson.toJson(restaurantsJson);

			Type setType1 = new TypeToken<HashSet<City>>() {
			}.getType();
			Type setType2 = new TypeToken<TreeSet<City>>() {
			}.getType();
			if (clubsResult != null && clubsResult.trim().length() > 0) {
				this.allCities = gson.fromJson(citiesResult, setType1);
			}
			if (citiesResult != null && citiesResult.trim().length() > 0) {
				this.allClubs = gson.fromJson(clubsResult, setType2);
			}
			if (restaurantsResult != null && restaurantsResult.trim().length() > 0) {
				this.allRestaurants = gson.fromJson(restaurantsResult, setType2);
			}
		} catch (FileNotFoundException e) {
			System.out.println("This file does not exist!");
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void usersFromJson() {
		File file = new File("JsonFiles" + File.separator + "users.json");
		System.out.println("Loading user data...");

		try (Reader reader = new FileReader(file);) {
			Gson gson = new Gson();
			JsonElement json = gson.fromJson(reader, JsonElement.class);
			String result = gson.toJson(json);

			Type setType = new TypeToken<ArrayList<User>>() {
			}.getType();
			if (result != null && result.trim().length() > 0) {
				UserAdministration.allUsers = gson.fromJson(result, setType);
			} else {
				System.out.println("Tuka e problema!!!");
			}

		} catch (FileNotFoundException e) {
			System.out.println("This file does not exist!");
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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
		if(u!=null) {
			if (u.isAdmin()) {
				return this.allRestaurants;
			}
		}
		return new TreeSet<>(allRestaurants);	
	}


	public Set<Place> getAllClubs(User u) {
		if (u instanceof Admin)
			return this.allClubs;
		else
			return Collections.unmodifiableSet(allClubs);
	}

	public ArrayList<Place> getAllClubs() {
		return new ArrayList<>(allClubs);
	}
	public ArrayList<Place> getAllRestaurants() {
		return new ArrayList<>(allRestaurants);
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

	public void getAllCities() {
		System.out.println(this.allCities);
	}

}
