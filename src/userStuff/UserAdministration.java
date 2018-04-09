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
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import exceptions.InvalidDateException;
import exceptions.InvalidInformationException;
import places.Place;
import services.Event;
import services.Offer;
import services.Reservation;
import website.Website;

public abstract class UserAdministration {
	private static boolean isLogged;
	public static List<User> allUsers = new ArrayList<>();
	private static User currentUser;
	private static String adminPass = "";

	public static User register(String firstName, String lastName, String city, String emailAdress, String password,
			String phoneNumber, LocalDate birthday, boolean isAdmin, String adminPass)
			throws InvalidInformationException {
		if (checkForValidString(firstName)) {
			if (checkForValidString(lastName)) {
				if (city != null) {
					if (UserAdministration.checkForValidEMail(emailAdress)) {
						if (checkForValidPassword(password)) {
							if (checkForValidPhoneNumber(phoneNumber)) {				
								if (birthday != null) {
									if (isAdmin) {
										if (UserAdministration.adminPass.equals(adminPass)) {
											System.out.println("Registration successful!");
											Admin a = new Admin(firstName, lastName, city, emailAdress, password,
													phoneNumber, birthday);
											UserAdministration.allUsers.add(a);
											return a;
										} else
											throw new InvalidInformationException(
													"Registration unsuccessful. Wrong admin pass");
									} else {
										System.out.println("Registration successful!");
										User u = new User(firstName, lastName, city, emailAdress, password, phoneNumber,
												birthday);
										UserAdministration.allUsers.add(u);
										return u;
									}
								} else
									throw new InvalidInformationException(
											"Registration unsuccessful. Null for birthday.");
							}
						}
					}
				} else
					throw new InvalidInformationException("Registration unsuccessful. Null for city.");
			}
		}
		return null;
	}

	public static void login(String email, String password) throws InvalidInformationException {
		if (!isLogged) {
			if (password != null && email != null) {
				for (User user : UserAdministration.allUsers) {
					if (user.getEmailAdress().equals(email)) {
						if (user.getPassword().equals(password)) {
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

	// without extraOptions
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, User user, Place place) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref,
					user, place);
			// Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			// Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}

	// with extraOptions
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, String extraOptions, User user, Place place) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref,
					extraOptions, user, place);
			// Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			// Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}

	// without extraOptions, with offer
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, User user, Place place, Offer offer) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref,
					user, place, offer);
			// Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			// Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}

	// without extraOptions, with event
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, User user, Place place, Event event) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref,
					user, place, event);
			// Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			// Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}

	// with extraOptions and offer
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, String extraOptions, User user, Place place, Offer offer) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref,
					extraOptions, user, place, offer);
			// Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			// Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}

	// with extraOptions and event
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, String extraOptions, User user, Place place, Event event) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref,
					extraOptions, user, place, event);
			// Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			// Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}

	public static void usersToJson() {
		File users = new File("JsonFiles" + File.separator + "users.json");
		Website.writeToJson(UserAdministration.allUsers, users);
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
			}else {
				System.out.println("Tuka e problema!!!");
			}

		} catch (FileNotFoundException e) {
			System.out.println("This file does not exist!");
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void logout() {

		if (currentUser != null) {
			System.out.println("Logout successful.");
			currentUser = null;
			isLogged = false;
		} else {
			System.out.println("You are not logged in!");
		}
	}

	public static void deleteUser(String password) {
		if (currentUser != null) {
			if (password.equals(currentUser.getPassword())) {
				UserAdministration.allUsers.remove(currentUser);
				logout();

			}
		}
	}

	public static void getAllUsers() {
		System.out.println(UserAdministration.allUsers);
	}

	public static boolean checkForValidEMail(String eMail) throws InvalidInformationException {
		if (eMail != null && Place.isValidEmailAddress(eMail)) {
			if (!allUsers.isEmpty()) {
				for (User u : allUsers) {
					if (u.getEmailAdress().equals(eMail)) {
						throw new InvalidInformationException("You already have a registration with this e-mail!");
					}
				}
				return true;
			}else {
				return true;
			}
		} else
			throw new InvalidInformationException("Missing input...");
	}

	public static boolean checkForValidPhoneNumber(String phoneNumber) throws InvalidInformationException {
		if (phoneNumber != null) {
			if (phoneNumber.trim().length() == 10) {
				for (int i = 0; i < phoneNumber.length(); i++) {
					if (!(Character.isDigit(phoneNumber.charAt(i))))
						throw new InvalidInformationException("Nomera trqbva da se systoi samo ot cifri!");
					else
						return true;
				}
			} else
				throw new InvalidInformationException("Nomera trqbva da sydyrza tochno 10 cifri!");
		} else
			throw new InvalidInformationException("Podavash null za phonenumber");
		return false;
	}

	public static boolean checkForValidString(String str) throws InvalidInformationException {
		if ((str != null) && (str.trim().length() > 0))
			return true;
		else
			throw new InvalidInformationException("Podavash null za String ili imash po-malko ot 1 znak");
	}

	protected static boolean checkForPasswordMatch(String password) throws InvalidInformationException {
		if (password != null) {
			if (password.equals(currentUser.getPassword()))
				return true;
			else
				throw new InvalidInformationException("Parolite ne syvpadat!!");
		} else {
			throw new InvalidInformationException("Podavash null za parola..");
		}
	}

	public static boolean checkForValidPassword(String password) throws InvalidInformationException {
		if (password != null) {
			if (password.trim().length() > 5)
				return true;
			else
				throw new InvalidInformationException("Dylzhinata na parolata trqbva da e pone 5 znaka");
		} else
			throw new InvalidInformationException("Podavash null za parola..");
	}

	public static boolean isLogged() {
		return isLogged;
	}

	public static User getU() {
		return currentUser;
	}
}
