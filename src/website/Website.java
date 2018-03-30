package website;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import places.City;
import places.Place;
import services.Event;
import services.Offer;
import userStuff.Admin;
import userStuff.User;
// move the Website class to another package with Admin or probably redesigning the entire class and giving direct access of the collections to the admin class
public class Website {
	private static Website web = null;
	private String infoForTheWebstie;
	private String contacts;
	private String FAQ;
	
	
	private Set<Place> allRestaurants;
	private Set<Place> allClubs;
	private Set<Event> allEvents;
	private Set<Offer> allOffers;
	
	private Website() {
	
		allRestaurants = new TreeSet<>();
		allEvents = new TreeSet<>();
		allOffers = new TreeSet<>();
		allClubs = new TreeSet<>();
		
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
		if(web.equals(null))
			return new Website();
		else
			return web;
			
	}
	
//	to add exceptions for showPlaces methods. City na Place ne e sigyrno dali ste e String!
	public void showPlaces(City city, boolean isRestaurant) {
		if(city!=null) {
			if(isRestaurant) {
				for(Place p :allRestaurants) {
					if(p.getCity().equals(city.getName()))
						System.out.println(p);
				}
			} else {
				for(Place p: allClubs) {
					if(p.getCity().equals(city.getName()))
						System.out.println(p);
				}
			}
		}
	}
	public void showPlaces(String city,boolean isRestaurant, int num) {
		if(city!=null) {
			if(num>0) {
				int counter = 0;
				if(isRestaurant) {
					for(Place p: allRestaurants) {
						if(p.getCity().equals(city)) {
							System.out.println(p);
							counter++;
						}
						if(counter==num)
							break;
					}
				} else {
					for(Place p: allClubs) {
						if(p.getCity().equals(city)) {
							System.out.println(p);
							counter++;
						}
						if(counter==num)
							break;
					}
				}
			}
		}
	}
	public void showEvents(City city) {
		if(city!=null) {
			for(Event e:allEvents) {
				if(e.getPlace().getCity().equals(city.getName()));
					System.out.println(e);
			}
		}
	}
	public void showOffers(City city) {
		if(city!=null) {
			for(Offer o:allOffers) {
				if(o.getPlace().getCity().equals(city.getName()));
					System.out.println(o);
			}
		}
	}
	

	//	nujni sa systite metodi za clubs i restaurants. Otdelno trqbva da se napravi po oste 1 overload method za nachin na sortirane(default da byde po reiting)
	public String getInfoForTheWebstie() {
		return infoForTheWebstie;
	}
	public void setInfoForTheWebstie(String infoForTheWebstie) {
		this.infoForTheWebstie = infoForTheWebstie;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getFAQ() {
		return FAQ;
	}
	public void setFAQ(String fAQ) {
		FAQ = fAQ;
	}
// better check than User u!!!
	public Set<Place> getAllRestaurants(User u) {
		if(u instanceof Admin) 
			return this.allRestaurants;
		else
			return Collections.unmodifiableSet(allRestaurants);
	}

	public Set<Place> getAllClubs(User u) {
		if(u instanceof Admin) 
			return this.allClubs;
		else
			return Collections.unmodifiableSet(allClubs);
	}

	public Set<Event> getAllEvents(User u) {
		if(u instanceof Admin) 
			return this.allEvents;
		else
			return Collections.unmodifiableSet(allEvents);
	}

	public Set<Offer> getAllOffers(User u) {
		if(u instanceof Admin) 
			return this.allOffers;
		else
			return Collections.unmodifiableSet(allOffers);
	}
}
