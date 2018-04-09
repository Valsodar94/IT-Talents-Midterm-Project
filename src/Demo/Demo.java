package Demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;


import exceptions.InvalidInformationException;
import places.City;
import places.Place;
import threads.ReservationChecker;
import userStuff.Admin;
import userStuff.User;
import userStuff.UserAdministration;
import website.Website;

public class Demo {
	static Scanner sc = new Scanner(System.in);
	private static final String WELCOME_MESSAGE = "Welcome in our site ";

	public static void main(String[] args){
		try {
			Website w = Website.getWebsite();
			loadData(w);
			//for me to check if json is working
			System.out.println();
			UserAdministration.getAllUsers();
			w.getAllCities();
			w.getAllClubs();
			w.getAllRestaurants();
			System.out.println();
			// reservation checker
			Thread checker = new ReservationChecker(w);
			checker.setDaemon(true);
			checker.start();
			//
			System.out.println(WELCOME_MESSAGE);
			boolean wantToExit = false;
			while (!wantToExit) {
				showAvailableOptions();
				int answer = receiveACommandFromTheUser();
				switch (answer) {
				case 1:
					showOptionsForRestaurantDesplay();
					showCities(true);
					String name = getCityName();
					int num = getNmbOfPlacesToShow();
					Website.getWebsite().showPlaces(name, true, num);
					break;
				case 2:
					showOptionsForClubsDesplay();
					showCities(false);
					name = getCityName();
					num = getNmbOfPlacesToShow();
					Website.getWebsite().showPlaces(name, false, num);
					break;
				case 3:
					showOffers();
					break;
				case 4:
					showEvents();
					break;
				case 5:
					getReservationDetailsAndMakeReservation();
					break;
				case 6:
					cancelReservation();
					break;
				case 7:
					leaveAComment();
					break;
				case 8:
					makeRegistration();
					break;
				case 9:
					logIn();
					break;
				case 10:
					UserAdministration.logout();
					break;
				case 11:
					int optionNmb = changeProfileOptions();
					changeTheChosenOption(optionNmb);
					break;
				case 12:
					deleteProfile();
					break;
				case 93:
					updateWebsiteInfo();
					break;
				case 94:
					updateWebsiteFAQ();
					break;
				case 95:
					updateWebsiteContacts();
					break;
				case 99:
					wantToExit = exitSite(w);
					break;
				default:
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {

		}
	}
	//Dobavi i opciq exit sait kato case
	private static boolean exitSite(Website w) {
		w.citiesToJson();
		w.clubsToJson();
		w.restaurantsToJson();
		UserAdministration.usersToJson();
		System.out.println("Saving all data to json...");
		return true;
	}
	
	private static void loadData(Website w) {
		UserAdministration.usersFromJson();
		w.dataFromJson();
	}

	private static void leaveAComment(){
		System.out.println("Enter the reservation id of the reservation you want to comment");
		String reservationID = sc.next();
		System.out.println("Enter your comment for the place: ");
		String comment = sc.nextLine();
		System.out.println("Now enter your rating (1-6).");
		int rating = sc.nextInt();
		User u = UserAdministration.getU();
		try {
			u.leaveComment(reservationID, comment, rating);
		} catch (InvalidInformationException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void cancelReservation() {
		System.out.println("Enter the reservationId of the reservation you want to cancel");
		String reservationID = sc.next();
		User u = UserAdministration.getU();
		try {
			u.cancelReservation(reservationID);
		} catch (InvalidInformationException e) {
			System.out.println(
					"No such reservation. Enter 1 if you want to enter another reservationID and any other number to exit.");
			int nmb = getAnswer();
			if (nmb == 1)
				cancelReservation();
			else
				return;
		}
	}

	private static void getReservationDetailsAndMakeReservation() {
		Place place = getPlaceIfExists();
		if (place == null)
			return;
		int nmbOfChildren = 0;
		if (place.isRestaurant())
			System.out.println("Enter the number of children for the reservation");
		nmbOfChildren = getNumberOfPeople();
		LocalDateTime dateAndTimeOfReservation = getDateTime();
		System.out.println("Enter the number of people for the reservation");
		int nmbOfPeople = getNumberOfPeople();
		String locationPref = pickLocationPref(place);
		makeReservation(place, nmbOfChildren, nmbOfPeople, dateAndTimeOfReservation, locationPref);
	}

	private static void makeReservation(Place place, int nmbOfChildren, int nmbOfPeople,
			LocalDateTime dateAndTimeOfReservation, String locationPref) {
		UserAdministration.makeReservation(dateAndTimeOfReservation, nmbOfPeople, nmbOfChildren, locationPref,
				UserAdministration.getU(), place);

	}

	private static String pickLocationPref(Place p) {
		showLocationPrefOptions(p);
		System.out.println("Type the name of the option you want");
		String locationPref = sc.next();
		for (String s : p.getLocationPrefs()) {
			if (locationPref.equals(s))
				return locationPref;
		}
		System.out.println("This option for location pref doesn't exist. A random location pref is chosen for you.");
		return p.getLocationPrefs().get((int) (Math.random() * p.getLocationPrefs().size()));
	}

	private static void showLocationPrefOptions(Place p) {
		System.out.println("This are the location options you have for this place: ");
		for (String s : p.getLocationPrefs()) {
			System.out.println(p);
		}
	}

	private static void showOffersAndEventsInPlace(Place place) {
		// a for-each to go through all offers and events in this Place
	}

	private static int getNumberOfPeople() {
		return sc.nextInt();
	}

	// probably make a universal check in Reservation class to see if the DateTime
	// is valid
	private static LocalDateTime getDateTime() {
		System.out.println("Enter the month of the reservation: ");
		int month = sc.nextInt();
		System.out.println("Enter the day of the reservation: ");
		int day = sc.nextInt();
		System.out.println("Enter hour of reservation: ");
		int hour = sc.nextInt();
		return LocalDateTime.of(2018, month, day, hour, 0);

	}

	private static int getAnswer() {
		return sc.nextInt();
	}

	// to replace the name check with a better one?
	private static Place getPlaceIfExists() {
		String placeName = getPlaceName();
		for (Place p : Website.getWebsite().getAllRestaurants(UserAdministration.getU())) {
			if (p.getName().equals(placeName)) {
				return p;
			}
		}
		for (Place p : Website.getWebsite().getAllClubs(UserAdministration.getU())) {
			if (p.getName().equals(placeName)) {
				return p;
			}
		}
		System.out.println("There is no place with such name in our system. Please check the name!");
		System.out.println("Would you like to enter another name or exit the reservation menu?");
		System.out.println("Enter 1 for another name and anything else for exit.");
		int answer = getAnswer();
		if (answer == 1)
			getPlaceIfExists();
		return null;
	}

	private static String getPlaceName() {
		System.out.println("Please enter the name of the restaurant or club you want to reserve in: ");
		return sc.next();
	}

	private static void showEvents() {
		System.out.println("For which city would you like to see the scheduled events? ");
		String city = sc.next();
		System.out.println("This are the events for " + city + ": ");
		Website.getWebsite().showEvents(city);
	}

	private static void showOffers() {
		System.out.println("For which city would you like to see the available offers? ");
		String city = sc.next();
		System.out.println("This are the offers for " + city + ": ");
		Website.getWebsite().showOffers(city);
	}

	private static void changeTheChosenOption(int optionNmb) {
		User currentUser = UserAdministration.getU();
		if (currentUser != null) {
			switch (optionNmb) {
			case 1:
				String firstName = getFirstName();
				String password = getPassword();
				currentUser.changeFirstName(firstName, password);
				break;
			case 2:
				String lastName = getFirstName();
				password = getPassword();
				currentUser.changeLastName(lastName, password);
				break;
			case 3:
				String city = getCity();
				password = getPassword();
				currentUser.changeCity(password, city);
				break;
			case 4:
				String email = getEmail();
				password = getPassword();
				currentUser.changeEMail(password, email);
				break;
			case 5:
				String newPassword = getPassword();
				password = getPassword();
				currentUser.changePassword(newPassword, password);
				break;
			case 6:
				String phoneNumber = getPhoneNumber();
				password = getPassword();
				currentUser.changeMobileNumber(password, phoneNumber);
				break;
			case 7:
				LocalDate birthday = getBirthday();
				password = getPassword();
				currentUser.changeBirthday(password, birthday);
				break;
			default:
				System.out.println("Ne beshe izbrana nito edna opciq za promqna na profil, vryshtane v glavnoto menu.");
			}

		}

	}

	private static void updateWebsiteContacts() {
		System.out.println("Enter the information you want to be shown as contacts.");
		String contacts = sc.nextLine();
		Website.getWebsite().setContacts(contacts);
	}

	private static void updateWebsiteFAQ() {
		System.out.println("Enter the information you want to be shown as FAQ.");
		String FAQ = sc.nextLine();
		Website.getWebsite().setFAQ(FAQ);
	}

	// update info/faq/contacts from files?
	private static void updateWebsiteInfo() {
		System.out.println("Enter the information you want to be shown as information for the website: ");
		String info = sc.nextLine();
		Website.getWebsite().setInfoForTheWebstie(info);
	}

	private static void showAvailableOptions() {
		System.out.println("What would you like to do?");
		System.out.println("Type: 1, to look at the restaurants we have to offer");
		System.out.println("Type: 2, to look at the clubs we have to offer");
		System.out.println("Type: 3, to look at the offers we have available");
		System.out.println("Type: 4, to look at the scheduled events");
		if (!(UserAdministration.isLogged())) {
			System.out.println("Type 8, to register");
			System.out.println("Type 9, to log in");
			System.out.println("Type 99, to exit website.");
		} else {
			if (UserAdministration.getU() instanceof User) {
				System.out.println("Type 5, to make a reservation");
				System.out.println("Type 6, to cancel reserrvation");
				System.out.println("Type 7, to leave a comment of a past reservation");
				System.out.println("Type 10, to logout");
				System.out.println("Type 11, to change something in your profile(name, adress, etc.)");
				System.out.println("Type 12, to delete your profile");
				System.out.println("Type 99, to exit website.");
				System.out.println();

				if(UserAdministration.getU() instanceof Admin) {
					System.out.println("Type 93, to update the website's info.");
					System.out.println("Type 94, to update the website's FAQ.");
					System.out.println("Type 95, to update the website's contacts.");
					System.out.println("Type 99, to exit website.");

				}

			}
		}

	}

	private static void deleteProfile() {
		String password = getPassword();
		UserAdministration.deleteUser(password);
	}

	private static int changeProfileOptions() {
		System.out.println("You can change one of the following things in your profile: "
				+ "Type 1 to change first name, 2 - second name, 3 - city, 4 - email, 5 - password, 6 -phone number, 7 - birtday, and any other number to cancel.");
		return sc.nextInt();
	}

	private static void logIn(){
		System.out.println("Enter your email: ");
		String email = sc.next();
		System.out.println("Enter your password: ");
		String password = sc.next();
		try {
			UserAdministration.login(email, password);
		} catch (InvalidInformationException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void makeRegistration(){
		String firstName = getFirstName();
		String lastName = getLastName();
		String city = getCity();
		String emailAdress = getEmail();
		String password = getPassword();
		String phoneNumber = getPhoneNumber();
		LocalDate birthday = getBirthday();
		boolean isAdmin = isAdmin();
		String adminPass = null;
		if (isAdmin)
			adminPass = getAdminPass();
		try {
			UserAdministration.register(firstName, lastName, city, emailAdress, password, phoneNumber, birthday,
					isAdmin, adminPass);
		} catch (InvalidInformationException e) {
			System.out.println("Registration unsuccessful! ");
			System.out.println(e.getMessage());
		}
	}

	private static String getAdminPass() {
		System.out.println("Type the admin password: (must be at least five characters)");
		return sc.nextLine();
	}

	private static boolean isAdmin() {
		System.out.println("Are you going to be an admin(Required admin password for this)");
		System.out.println("Type yes or no");
		String answer = sc.next();
		if (answer.equalsIgnoreCase("yes"))
			return true;
		else
			return false;
	}

	private static LocalDate getBirthday() {
		System.out.println("Enter you birth date.");
		int day = 0;
		while(day <1||day>365) {
			System.out.println("Day: ");
			day = sc.nextInt();
		}
		int month = 0;
		while(month<1||month>12) {
			System.out.println("Month: ");
			month = sc.nextInt();
		}
		int year = -1;
		while(year < 0) {
			System.out.println("Year: ");
			year = sc.nextInt();
		}
		return LocalDate.of(year, month, day);
	}

	private static String getPhoneNumber() {
		System.out.println("Type your phoneNumber");
		String phoneNumber = sc.next();
		return phoneNumber;
	}

	private static String getPassword() {
		System.out.println("Type your password");
		String password = sc.next();
		return password;
	}

	private static String getEmail() {
		System.out.println("Type your email");
		String email = sc.next();
		return email;
	}

	private static String getCity() {
		String city = null;
		while(city==null || city.trim().length()==0) {
			System.out.println("Type your city");
			city = sc.next();
		}
		return city;
	}

	private static String getLastName() {
		String lastName = null;
		while(lastName==null || lastName.trim().length()==0) {
			System.out.println("Type your last name");
			lastName = sc.next();
		}
		return lastName;
	}

	private static String getFirstName() {
		String firstName = null;
		while(firstName==null || firstName.trim().length()==0) {
			System.out.println("Type your first name");
			firstName = sc.next();
		}
		return firstName;
	}

	private static void showOptionsForClubsDesplay() {
		System.out.println("Great choice! Now choose one of the following, so you can only"
				+ " look at the clubs you are interested in");
		System.out.println("This is the list of the cities we have clubs for booking:");

	}

	private static int getNmbOfPlacesToShow() {
		System.out.println("Enter the number of places you want to see");
		return sc.nextInt();

	}

	private static void showOptionsForRestaurantDesplay() {
		System.out.println("Great choice! Now choose one of the following, so you can only"
				+ " look at the restaurants you are interested in");
		System.out.println("This is the list of the cities we have restaurants for booking:");
	}

	private static void showCities(boolean isRestaurant) {
		Website w = Website.getWebsite();
		for (City c : w.getAllCities(null)) {
			if (isRestaurant) {
				if (c.getRestaurants().size() > 0) {
					System.out.println(c.getName());
				}
			} else {
				if (c.getClubs().size() > 0) {
					System.out.println(c.getName());
				}
			}
		}
		if (isRestaurant)
			System.out.println("In which of the above cities would you like to see our Restaurants? ");
		else
			System.out.println("In which of the above cities would you like to see our Clubs? ");

	}

	private static String getCityName() {
		return sc.next();
	}

	private static int receiveACommandFromTheUser() {

		return sc.nextInt();
	}
}
