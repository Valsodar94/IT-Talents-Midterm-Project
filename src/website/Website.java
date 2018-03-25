package website;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import exceptions.InvalidInformationException;
import places.Place;
import services.Reservation;
import userStuff.User;

public class Website {
	private String infoForTheWebstie;
	private String contacts;
	private String FAQ;
	private boolean isLogged;
	private List<User> allUsers;
//	private Set<Restaurant> allRestaurants;
//	private Set<Club> allClubs;
//	private Set<Event> allEvents;
//	private Set<Offer> allOffers;
	private User u;
	
	public Website() {
		allUsers = new ArrayList<>();
//		allRestaurants = new TreeSet<>();
//		allClubs = new TreeSet<>();
//		allEvents = new TreeSet<>();
//		allOffers = new TreeSet<>();
	}

	public Website(String infoForTheWebstie) {
		this();
		setInfoForTheWebstie(infoForTheWebstie);
	}
	public Website(String infoForTheWebstie, String contacts) {
		this(infoForTheWebstie);
		setContacts(contacts);
	}
	public Website(String infoForTheWebstie, String contacts, String FAQ) {
		this(infoForTheWebstie, contacts);
		setFAQ(FAQ);
	}
	
	
	
	public void login(String password, String email) throws InvalidInformationException{
		if(!isLogged) {
			if(password!=null && email!=null) {
				for(User user: allUsers) {
					if(u.getEmailAdress().equals(email)) {
						if(u.getPassword().equals(password)) {
							System.out.println("Login uspeshen");
							this.isLogged = true;
							this.u = user;
							this.u.refreshPresentAndPastReservations();
						} else {
							throw new InvalidInformationException("Login neuspeshen! Nepravilna parola!");
						}
					} else {
						throw new InvalidInformationException("Login neuspeshen! Nqma registriran potrebtiel na takyv email");
					}
				}	
			} else {
				throw new InvalidInformationException("Login neuspeshen! Podavash mi null za email/parola!");
			}
		} else {
			throw new InvalidInformationException("Login neuspeshen! Veche ima vpisan potrebitel");
		}
	}
	
	
	
	public void logout() {
		if(this.u!=null) {
			System.out.println("Logout successful?");
			this.u=null;
		} else {
			System.out.println("Nqma vpisan potrebitel, kakyv logout iskash?");
		}
	}
	
//	public void showRestaurants(Cities city) {
//		if(city!=null) {
//			for(Restaurant r: allRestaurants) {
//				if(r.getCity().equals(city)) {
//					System.out.println(r.showInfo());
//				}
//			}
//		}
//	}
//	public void showRestaurants(Cities city, int num) {
//		if(city!=null) {
//			int counter=1;
//			for(Restaurant r: allRestaurants) {
//				if(r.getCity().equals(city) && counter<=num) {
//					System.out.println(r.showInfo());
//					counter++;
//				}
//			}
//		}
//	}
//	public void showClubs(Cities city) {
//		if(city!=null) {
//			for(Club c: allClubs) {
//				if(c.getCity().equals(city)) {
//					System.out.println(c.showInfo());
//				}
//			}
//		}
//	}
//	public void showClubs(Cities city, int num) {
//		if(city!=null) {
//			int counter=1;
//			for(Club c: allClubs) {
//				if(c.getCity().equals(city) && counter<=num) {
//					System.out.println(c.showInfo());
//					counter++;
//				}
//			}
//		}
//	}
	
	public void addUser(User u) {
		if(u!=null) {
			allUsers.add(u);
		}else {
			System.out.println("Potrebitelq ne e dobaven, podavash null");
		}
	}
	public void removeUser(User u ) {
		if(u!=null) {
			Iterator<User> it = allUsers.iterator();
			while(it.hasNext()) {
				User user = it.next();
				if(u.equals(user))
					it.remove();
			}
		}
	}
//	public void addPlace(Place p) throws InvalidInformationException{
//		if(p!=null) {
//			if(p instanceof Restaurant) {
//				allRestaurants.add(p);
//			} else
//				allClubs.add(p);
//		} else {
//			throw new InvalidInformationException("Podavash null za place");
//		}
//	}
//	to add remove methods for Places and all other staff, also - add methods for events and offers;
	
	
	
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
	
}
