package userStuff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import exceptions.InvalidDateException;
import exceptions.InvalidInformationException;
import places.City;
import places.Place;
import services.Event;
import services.Offer;
import services.Reservation;
import website.Website;

public abstract class UserAdministration {
	private static boolean isLogged;
	private static List<User> allUsers= new ArrayList<>();;
	private static User currentUser;
	private static String adminPass = "";

	
	
	public static User register(String firstName, String lastName, String city, String emailAdress, String password, String phoneNumber, LocalDate birthday,boolean isAdmin, String adminPass) throws InvalidInformationException {
		if(isAdmin) {
			if(UserAdministration.adminPass.equals(adminPass)) {
				Admin a = new Admin(firstName, lastName, city, emailAdress, password, phoneNumber, birthday);
				allUsers.add(a);
				return a;
			} else
				throw new InvalidInformationException("Registraciq neuspeshna! greshna parola za admin");
		}
		else {
			User u = new User(phoneNumber, phoneNumber, city, phoneNumber, phoneNumber, phoneNumber, birthday);
			UserAdministration.allUsers.add(u);
			return u;
		}
	}
	
	public static void usersFromJson() {
		File file = new File("JsonFiles"+File.separator+"users.json");
		System.out.println("Loading user data...");
		
		try (Reader reader = new FileReader(file);){
			Gson gson =  new Gson();		
			JsonElement json = gson.fromJson(reader, JsonElement.class);
		    String result = gson.toJson(json);
			
			Type setType = new TypeToken<ArrayList<User>>(){}.getType();
			allUsers = gson.fromJson(result, setType);
			
		} catch (FileNotFoundException e) {
			System.out.println("This file does not exist!");
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void login(String password, String email) throws InvalidInformationException{
		if(!isLogged) {
			if(password!=null && email!=null) {
				for(User user: allUsers) {
					if(currentUser.getEmailAdress().equals(email)) {
						if(currentUser.getPassword().equals(password)) {
							System.out.println("Login uspeshen");
							isLogged = true;
							currentUser = user;
						} else {
							throw new InvalidInformationException("Login neuspeshen! Nepravilna parola!");
						}
					} else {
						throw new InvalidInformationException(
								"Login neuspeshen! Nqma registriran potrebtiel na takyv email");
					}
				}
			} else {
				throw new InvalidInformationException("Login neuspeshen! Podavash mi null za email/parola!");
			}
		} else {
			throw new InvalidInformationException("Login neuspeshen! Veche ima vpisan potrebitel");
		}
	}
	//without extraOptions
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, User user, Place place) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, user, place);
			//Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			//Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}
	//with extraOptions
		public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
				String locationPref, String extraOptions, User user, Place place) {
			try {
				Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, extraOptions, user, place);
				//Adding reservation to the user's reservations
				r.getUser().addReservation(r);
				//Adding reservation to the place's reservations
				r.getPlace().addReservation(r);
			} catch (InvalidInformationException | InvalidDateException e) {
				e.printStackTrace();
			}
		}
	//without extraOptions, with offer
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, User user, Place place, Offer offer) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, user, place, offer);
			//Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			//Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}
	//without extraOptions, with event
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, User user, Place place, Event event) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, user, place, event);
			//Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			//Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}
	//with extraOptions and offer
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, String extraOptions, User user, Place place, Offer offer) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, extraOptions, user, place, offer);
			//Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			//Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}
	//with extraOptions and event
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, String extraOptions, User user, Place place, Event event) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, extraOptions, user, place, event);
			//Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			//Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}

	//dublira se s reservation checker
	/*public static void refreshPresentAndPastReservations() {
		LocalDateTime now = LocalDateTime.now();
		Iterator<Reservation> it = currentUser.getMyReservations().iterator();
		while(it.hasNext()) {
			Reservation r = it.next();
			if(r.getDateAndTime().isBefore(now)) {
				currentUser.getPastReservations().add(r);
				it.remove();
			}
		}
	}*/
	
	public static void usersToJson() {
		File users = new File("JsonFiles" + File.separator + "users.json");
		Website.writeToJson(allUsers, users);
	}

	public static void logout() {

		if(currentUser!=null) {
			System.out.println("Logout successful.");
			currentUser=null;
			isLogged = false;
		} else {
			System.out.println("You are not logged in!");
		}
	}
	
	public static void deleteUser(String password) {
		if(currentUser!=null) {
			if(password.equals(currentUser.getPassword())) {
				allUsers.remove(currentUser);
				logout();

			}
		}
	}

	// da namerq shablon za email check
	public static boolean checkForValidEMail(String eMail) throws InvalidInformationException {
		if (eMail != null) {
			if (eMail.trim().length() > 10) {
				if (eMail.contains("@")) {
					for (User u : allUsers) {
						if (u.getEmailAdress().equals(eMail)) {
							throw new InvalidInformationException("You already have a registration with this e-mail!");
						}
					}
					return true;
				} else
					throw new InvalidInformationException("Invalid e-mail! It must contain @!");
			} else
				throw new InvalidInformationException("Invalid input for e-mail!");
		} else
			throw new InvalidInformationException("Missing input...");

	}

	public static boolean isLogged() {
		return isLogged;
	}

	public static User getU() {
		return currentUser;
	}
}
