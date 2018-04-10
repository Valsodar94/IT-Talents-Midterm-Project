package Demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import enums.ExtraReservationOptions;
import exceptions.InvalidInformationException;
import places.City;
import places.Place;
import threads.ReservationChecker;
import userStuff.Admin;
import userStuff.User;
import userStuff.UserAdministration;
import website.Website;

public class Demo {
	public static Scanner sc = new Scanner(System.in);
	private static final String WELCOME_MESSAGE = "Welcome to our site ";

	public static void main(String[] args){
		try {
			Website w = Website.getWebsite();
			loadData(w);
			// reservation checker
			Thread checker = new ReservationChecker(w);
			checker.setDaemon(true);
			checker.start();
			//
			System.out.println(WELCOME_MESSAGE);
			menu(w);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}
	private static void menu(Website w) {
		boolean wantToExit = false;
		while (!wantToExit) {
			showAvailableOptions();
			int answer = receiveACommandFromTheUser();
			switch (answer) {
			case 1:
				showOptionsForRestaurantDesplay();
				showCities(true);
				String name = getCityName();
				Website.getWebsite().showPlaces(name, true);
				break;
			case 2:
				showOptionsForClubsDesplay();
				showCities(false);
				name = getCityName();
				Website.getWebsite().showPlaces(name, false);
				break;
			case 3:
				showOffers();
				break;
			case 4:
				showEvents();
				break;
			case 5:
				UserAdministration.getReservationDetailsAndMakeReservation();
				break;
			case 6:
				User u = UserAdministration.getU();
				if(u!=null) {
					u.cancelReservation();
				} else {
					System.out.println("Login first!");
				}
				break;
			case 7:
				u = UserAdministration.getU();
				if(u!=null) {
					u.leaveAComment();
				} else {
					System.out.println("Login first!");
				}
				break;
			case 8:
				UserAdministration.makeRegistration();
				break;
			case 9:
				UserAdministration.logIn();
				break;
			case 10:
				UserAdministration.logout();
				break;
			case 11:
				int optionNmb = changeProfileOptions();
				changeTheChosenOption(optionNmb);
				break;
			case 12:
				UserAdministration.deleteProfile();
				break;
			case 90:
				addPlace();
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
	}
	
	private static void changeTheChosenOption(int optionNmb) {
		User currentUser = UserAdministration.getU();
		if (currentUser != null) {
			switch (optionNmb) {
			case 1:
				String firstName = UserAdministration.getFirstName();
				String password = UserAdministration.getPassword();
				currentUser.changeFirstName(firstName, password);
				break;
			case 2:
				String lastName = UserAdministration.getFirstName();
				password = UserAdministration.getPassword();
				currentUser.changeLastName(lastName, password);
				break;
			case 3:
				String city = UserAdministration.getCity();
				password = UserAdministration.getPassword();
				currentUser.changeCity(password, city);
				break;
			case 4:
				String email = UserAdministration.getEmail();
				password = UserAdministration.getPassword();
				currentUser.changeEMail(password, email);
				break;
			case 5:
				String newPassword = UserAdministration.getPassword();
				password = UserAdministration.getPassword();
				currentUser.changePassword(newPassword, password);
				break;
			case 6:
				String phoneNumber = UserAdministration.getPhoneNumber();
				password = UserAdministration.getPassword();
				currentUser.changeMobileNumber(password, phoneNumber);
				break;
			case 7:
				LocalDate birthday = UserAdministration.getBirthday();
				password = UserAdministration.getPassword();
				currentUser.changeBirthday(password, birthday);
				break;
			default:
				System.out.println("Ne beshe izbrana nito edna opciq za promqna na profil, vryshtane v glavnoto menu.");
			}
		}
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
				System.out.println("---USER OPTIONS---");
				System.out.println("Type 5, to make a reservation");
				System.out.println("Type 6, to cancel reserrvation");
				System.out.println("Type 7, to leave a comment of a past reservation");
				System.out.println("Type 10, to logout");
				System.out.println("Type 11, to change something in your profile(name, adress, etc.)");
				System.out.println("Type 12, to delete your profile");
				System.out.println("Type 99, to exit website.");
				System.out.println();

				if(UserAdministration.getU().isAdmin()) {
					System.out.println("---ADMINISTRATION OPTIONS---");
					System.out.println("Type 90, to add a place.");
					System.out.println("Type 93, to update the website's info.");
					System.out.println("Type 94, to update the website's FAQ.");
					System.out.println("Type 95, to update the website's contacts.");

				}

			}
		}

	}
		
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
	public static void updateWebsiteContacts() {
		System.out.println("Enter the information you want to be shown as contacts.");
		String contacts = Demo.sc.nextLine();
		Website.getWebsite().setContacts(contacts);
	}

	public static void updateWebsiteFAQ() {
		System.out.println("Enter the information you want to be shown as FAQ.");
		String FAQ = Demo.sc.nextLine();
		Website.getWebsite().setFAQ(FAQ);
	}
	private static void updateWebsiteInfo() {
		System.out.println("Enter the information you want to be shown as information for the website: ");
		String info = sc.nextLine();
		Website.getWebsite().setInfoForTheWebstie(info);
	}

	private static int changeProfileOptions() {
		System.out.println("You can change one of the following things in your profile: "
				+ "Type 1 to change first name, 2 - second name, 3 - city, 4 - email, 5 - password, 6 -phone number, 7 - birtday, and any other number to cancel.");
		return sc.nextInt();
	}

	private static void showOptionsForClubsDesplay() {
		System.out.println("Great choice! Now choose one of the following, so you can only"
				+ " look at the clubs you are interested in");
		System.out.println("This is the list of the cities we have clubs for booking:");

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
	
	private static void addPlace() {
		User currentUser = UserAdministration.getU();
		
		if(currentUser.isAdmin()) {
			System.out.println("You are logged in as an admin! You can add a place to our system!");
			
			System.out.println("Enter name for the place: ");
			String name = sc.next();
			
			while(!Place.isValidString(name)) {
				System.out.println("Enter a new name: ");
				name = sc.nextLine();
			}
		
			
			System.out.println("Enter address of this place: ");
			String address = sc.next();

			
			System.out.println("Enter email of the place: ");
			String email = sc.next();
			
			System.out.println("Is this place a restaurant: (true/false) ");
			Boolean isRestaurant = sc.nextBoolean();
			
			
			String charInput = null;
			if(isRestaurant) {
				System.out.println("Enter the kitchen types offered in your restaurant: (e.g Japanese,Bulgarian)");
				charInput = sc.next();
			}else {
				System.out.println("Enter the music type in your club: (e.g Chalga, RnB)");
				charInput = sc.nextLine();
			}
			
			System.out.println("Enter city: ");
			String city = sc.next();
			
			System.out.println("Enter the region in your city: (e.g Center, Drujba)");
			String region = sc.next();
			
			System.out.println("Enter the average cost in your place: (e.g min/max)");
			String avgCost = sc.next();
			
			System.out.println("Enter start of the working day for your place: ");
			System.out.println("Enter hour: ");
			int hours = sc.nextInt();
			System.out.println("Enter minutes: ");
			int min = sc.nextInt();
			LocalTime startTime = LocalTime.of(hours, min);
			
			
			System.out.println("Enter end of the working day for your place: ");
			System.out.println("Enter hour: ");
			int hoursEnd = sc.nextInt();
			System.out.println("Enter minutes: ");
			int minEnd = sc.nextInt();
			LocalTime closeTime = LocalTime.of(hoursEnd, minEnd);
			
			System.out.println("Enter the capacity of your place: ");
			int capacity = sc.nextInt();
			
			 List<String> locationPrefs = new ArrayList<>();
			 System.out.println("Enter location preferences for your tables: ");
			 boolean toContinue = true;
			 String prefs = null;
			 while(toContinue) {
				 prefs = sc.nextLine();
				 locationPrefs.add(prefs);
				 sc.nextLine();
				 System.out.println("Do you want to add more preferences? (yes/no)");
				 String answer = sc.next();
				 if(answer.equalsIgnoreCase("no")) {
					 toContinue = false;
				 }
			 }

			 
			 List<ExtraReservationOptions> extraReservationOptions = new ArrayList<>();
			 System.out.println("Enter extra reservation options for your place: ");
			 System.out.println("Choose the desired numbers -> NEAR_TABLE (1), WITH_BABYCHAIR (2), WITH_PLACE_FOR_INVALIDS (3), NEAR_STAGE (4), "
					 			+ "AT_THE_BAR (5), VIP_ZONE (6), HIGH_TABLE (7), SEPARE (8), STANDART_TABLE (9)");
			 toContinue = true;
			 int num = sc.nextInt();
			 while(toContinue) {	 
				 switch (num) {
				case 1:
					extraReservationOptions.add(ExtraReservationOptions.NEAR_TABLE);
					break;
				case 2:
					extraReservationOptions.add(ExtraReservationOptions.WITH_BABYCHAIR);
					break;
				case 3:
					extraReservationOptions.add(ExtraReservationOptions.WITH_PLACE_FOR_INVALIDS);
					break;
				case 4:
					extraReservationOptions.add(ExtraReservationOptions.NEAR_STAGE);
					break;
				case 5:
					extraReservationOptions.add(ExtraReservationOptions.AT_THE_BAR);
					break;
				case 6:
					extraReservationOptions.add(ExtraReservationOptions.VIP_ZONE);
					break;
				case 7:
					extraReservationOptions.add(ExtraReservationOptions.HIGH_TABLE);
					break;
				case 8:
					extraReservationOptions.add(ExtraReservationOptions.SEPARE);
					break;
				case 9:
					extraReservationOptions.add(ExtraReservationOptions.STANDART_TABLE);
					break;

				default:
					break;
				}

				 System.out.println("Do you want to add more preferences? (yes/no)");
				 String answer = sc.next();
				 
				 if(answer.equalsIgnoreCase("no")) {
					 toContinue = false;
				 }else {
					 num = sc.nextInt();
				 }
			 }
			try {
				Place p = new Place(name, address, email, isRestaurant, charInput, city, region, avgCost, startTime, closeTime, capacity, locationPrefs, extraReservationOptions);
				((Admin) currentUser).addPlace(p);
				System.out.println("This place was added to our website successfully!");
			} catch (InvalidInformationException e) {
				e.printStackTrace();
			}
		}
	}

	private static String getCityName() {
		return sc.next();
	}

	private static int receiveACommandFromTheUser() {

		return sc.nextInt();
	}
}
