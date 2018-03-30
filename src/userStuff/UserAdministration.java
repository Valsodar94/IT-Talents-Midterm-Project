package userStuff;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
<<<<<<< HEAD
	private static List<User> allUsers = new ArrayList<>();;
	private static User u;
	private static String adminPass = "";

	public User register(String firstName, String lastName, City city, String emailAdress, String password,
			String phoneNumber, LocalDate birthday, Website w, boolean isAdmin, String adminPass)
			throws InvalidInformationException {
		if (isAdmin) {
			if (UserAdministration.adminPass.equals(adminPass)) {
				Admin a = new Admin(firstName, lastName, city, emailAdress, password, phoneNumber, birthday, w);
=======
	private static List<User> allUsers= new ArrayList<>();;
	private static User currentUser;
	private static String adminPass = "";

	
	
	public static User register(String firstName, String lastName, String city, String emailAdress, String password, String phoneNumber, LocalDate birthday,boolean isAdmin, String adminPass) throws InvalidInformationException {
		if(isAdmin) {
			if(UserAdministration.adminPass.equals(adminPass)) {
				Admin a = new Admin(firstName, lastName, city, emailAdress, password, phoneNumber, birthday);
>>>>>>> 75c5ad8aab04394b4c694a65ecfa755d2268d26a
				allUsers.add(a);
				return a;
			} else
				throw new InvalidInformationException("Registraciq neuspeshna! greshna parola za admin");
<<<<<<< HEAD
		} else {
			User u = new User(phoneNumber, phoneNumber, city, phoneNumber, phoneNumber, phoneNumber, birthday, w);
=======
		}
		else {
			User u = new User(phoneNumber, phoneNumber, city, phoneNumber, phoneNumber, phoneNumber, birthday);
>>>>>>> 75c5ad8aab04394b4c694a65ecfa755d2268d26a
			allUsers.add(u);
			return u;
		}
	}
<<<<<<< HEAD

	public static void login(String password, String email) throws InvalidInformationException {
		if (!isLogged) {
			if (password != null && email != null) {
				for (User user : allUsers) {
					if (u.getEmailAdress().equals(email)) {
						if (u.getPassword().equals(password)) {
=======
	public static void login(String password, String email) throws InvalidInformationException{
		if(!isLogged) {
			if(password!=null && email!=null) {
				for(User user: allUsers) {
					if(currentUser.getEmailAdress().equals(email)) {
						if(currentUser.getPassword().equals(password)) {
>>>>>>> 75c5ad8aab04394b4c694a65ecfa755d2268d26a
							System.out.println("Login uspeshen");
							isLogged = true;
							currentUser = user;
							refreshPresentAndPastReservations();
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

	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, int discount, User user, Place place) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, discount, user, place);
			//Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			//Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}
	
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, int discount, User user, Place place, Offer offer) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, discount, user, place, offer);
			//Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			//Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}
	
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, int discount, User user, Place place, Event event) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, discount, user, place, event);
			//Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			//Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}
	
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, int discount, String extraOptions, User user, Place place, Offer offer) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, discount, extraOptions, user, place, offer);
			//Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			//Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}
	
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, int discount, String extraOptions, User user, Place place, Event event) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, discount, extraOptions, user, place, event);
			//Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			//Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}

	private static void refreshPresentAndPastReservations() {
		LocalDateTime now = LocalDateTime.now();
<<<<<<< HEAD
		Iterator<Reservation> it = u.getMyReservations().iterator();
		while (it.hasNext()) {
			Reservation r = it.next();
			if (r.getDateAndTime().isBefore(now)) {
				u.getPastReservations().add(r);
=======
		Iterator<Reservation> it = currentUser.getMyReservations().iterator();
		while(it.hasNext()) {
			Reservation r = it.next();
			if(r.getDateAndTime().isBefore(now)) {
				currentUser.getPastReservations().add(r);
>>>>>>> 75c5ad8aab04394b4c694a65ecfa755d2268d26a
				it.remove();
			}
		}
	}

	public static void logout() {
<<<<<<< HEAD
		if (u != null) {
			System.out.println("Logout successful?");
			u = null;
=======
		if(currentUser!=null) {
			System.out.println("Logout successful?");
			currentUser=null;
			isLogged = false;
>>>>>>> 75c5ad8aab04394b4c694a65ecfa755d2268d26a
		} else {
			System.out.println("Nqma vpisan potrebitel, kakyv logout iskash?");
		}
	}
<<<<<<< HEAD

	// to add password check before deleting!!!
	public static void deleteUser(User u) {
		if (u != null) {
			Iterator<User> it = allUsers.iterator();
			while (it.hasNext()) {
				User user = it.next();
				if (u.equals(user))
					it.remove();
=======
	
	
	public static void deleteUser(String password) {
		if(currentUser!=null) {
			if(password.equals(currentUser.getPassword())) {
				allUsers.remove(currentUser);
				logout();
>>>>>>> 75c5ad8aab04394b4c694a65ecfa755d2268d26a
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
							throw new InvalidInformationException("Veche ima potrebitel s takyv meil!");
						}
					}
					return true;
				} else
					throw new InvalidInformationException("Email-a ti e nevaliden! Ne sydyrzha @");
			} else
				throw new InvalidInformationException("Nevaliden email!");
		} else
			throw new InvalidInformationException("Podavash mi null za email...");

	}

	public static boolean isLogged() {
		return isLogged;
	}

	public static User getU() {
		return currentUser;
	}
}
