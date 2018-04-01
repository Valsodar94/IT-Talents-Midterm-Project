package Demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

import exceptions.InvalidInformationException;
import places.City;
import places.Place;
import userStuff.User;
import userStuff.UserAdministration;
import website.Website;

public class Demo {
	static Scanner sc = new Scanner(System.in);
	private static final String WELCOME_MESSAGE = "Welcome in our site ";

	public static void main(String[] args) throws InvalidInformationException {
		Website w = Website.getWebsite();
		System.out.println(WELCOME_MESSAGE);
		while(true) {
			showAvailableOptions();
			int answer = receiveACommandFromTheUser();
			switch(answer) {
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
				Website.getWebsite().showPlaces(name,false,num);
				break;
			case 3:
				showOffers();
				break;
			case 4:
				showEvents();
				break;
			case 5:
				getReservationDetails();
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
			default: continue;
			}
			
		}
			
	}
	private static void getReservationDetails() {
		Place place = getPlaceIfExists();
		if(place.equals(null))
			return;
		int nmbOfChildren = 0;
		if(place.isRestaurant())
			System.out.println("Enter the number of children for the reservation");
			nmbOfChildren = getNumberOfPeople();
		LocalDateTime dateAndTimeOfReservation = getDateTime();
		System.out.println("Enter the number of people for the reservation");
		int nmbOfPeople = getNumberOfPeople();
		showOffersAndEventsInPlace(Place p);
	}
private static int getNumberOfPeople() {
		return sc.nextInt();
	}
//	probably make a universal check in Reservation class to see if the DateTime is valid
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
	private static Place getPlaceIfExists(){
		String placeName = getPlaceName();
		for(Place p: Website.getWebsite().getAllRestaurants(UserAdministration.getU())) {
			if(p.getName().equals(placeName)) {
				return p;
			}
		}
		for(Place p: Website.getWebsite().getAllClubs(UserAdministration.getU())) {
			if(p.getName().equals(placeName)) {
				return p;
			}
		}
		System.out.println("There is no place with such name in our system. Please check the name!");
		System.out.println("Would you like to enter another name or exit the reservation menu?");
		System.out.println("Enter 1 for another name and anything else for exit.");
		int answer = getAnswer();
		if(answer == 1)
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
		if(currentUser!=null) {
			switch(optionNmb) {
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
			case 93:
				updateWebsiteInfo();
				break;
			case 94:
				updateWebsiteFAQ();
				break;
			case 95:
				updateWebsiteContacts();
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
//	update info/faq/contacts from files?
	private static void updateWebsiteInfo() {
		System.out.println("Enter the information you want to be shown as information for the website: ");
		String info =sc.nextLine();
		Website.getWebsite().setInfoForTheWebstie(info);
	}
	
	private static void showAvailableOptions() {
		System.out.println("What would you like to do?");
		System.out.println("Type: 1, to look at the restaurants we have to offer");
		System.out.println("Type: 2, to look at the clubs we have to offer");
		System.out.println("Type: 3, to look at the offers we have available");
		System.out.println("Type: 4, to look at the scheduled events");
		System.out.println("Type 5, to make a reservation");
		System.out.println("Type 6, to cancel reserrvation");
		if(!(UserAdministration.isLogged())) {
			System.out.println("Type 7, to leave a comment of a past reservation");
			System.out.println("Type 8, to register");
			System.out.println("Type 9, to log in");
		}else {
			if(UserAdministration.getU() instanceof User) {
				System.out.println("Type 10, to logout");
				System.out.println("Type 11, to change something in your profile(name, adress, etc.)");
				System.out.println("Type 12, to delete your profile");
				System.out.println();
			} else {
				System.out.println("Type 93, to update the info for the website");
				System.out.println("Type 94 to update website's FAQ");
				System.out.println("Type 95 to update the website's contacts");
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
	private static void logIn() throws InvalidInformationException {
		String password = getPassword();
		String email = getEmail();
		UserAdministration.login(password, email);
	}
	

	private static User makeRegistration() throws InvalidInformationException {
		String firstName = getFirstName();
		String lastName = getLastName();
		String city = getCity();
		String emailAdress = getEmail();
		String password = getPassword(); 
		String phoneNumber = getPhoneNumber(); 
		LocalDate birthday = getBirthday();
		boolean isAdmin = isAdmin();
		String adminPass = null;
		if(isAdmin) 
			adminPass = getAdminPass();
		return UserAdministration.register(firstName, lastName, city, emailAdress, password, phoneNumber, birthday, isAdmin, adminPass);
	}


	private static String getAdminPass() {
		System.out.println("Type the admin password: ");
		return sc.nextLine();
	}


	private static boolean isAdmin() {
		System.out.println("Are you going to be an admin(You require admin password for this)");
		System.out.println("Type yes or no");
		String answer = sc.next();
		if(answer.equalsIgnoreCase("yes"))
			return true;
		else
			return false;
	}


	private static LocalDate getBirthday() {
		System.out.println("Enter you birth date.");
		System.out.println("Day : ");
		int day = sc.nextInt();
		System.out.println("Month: ");
		int month = sc.nextInt();
		System.out.println("Year: ");
		int year = sc.nextInt();
		return LocalDate.of(year, month, day);
	}


	private static String getPhoneNumber() {
		System.out.println("Type your phone number");
		return sc.next();
	}


	private static String getPassword() {
		System.out.println("Type your password");
		return sc.next();
	}


	private static String getEmail() {
		System.out.println("Type your email");
		return sc.next();
	}


	private static String getCity() {
		System.out.println("Type your city");
		return sc.next();
	}


	private static String getLastName() {
		System.out.println("Type your last name");
		return sc.next();
	}


	private static String getFirstName() {
		System.out.println("Type your first name");
		return sc.next();
	}


	private static void showOptionsForClubsDesplay() {
		System.out.println("Great choice! Now choose one of the following, so you can only"
				+ " look at the clubs you are interested in");
		System.out.println("This is the list of the cities we have clubs for booking.");
		System.out.println("Please type the name of the city in which you look for a club");
	}


	private static int getNmbOfPlacesToShow() {
		System.out.println("Enter the number of places you want to see");
		return sc.nextInt();
		
	}

	private static void showOptionsForRestaurantDesplay() {
		System.out.println("Great choice! Now choose one of the following, so you can only"
				+ " look at the restaurants you are interested in");
		System.out.println("This is the list of the cities we have restaurants for booking.");
		System.out.println("Please type the name of the city in which you look for a restaurant");
	}
//	to make a collection containing all cities with Places and iterate it here showing it to the user.
	private static void showCities(boolean isRestaurant) {
		
	}

	private static String getCityName() {
		return sc.next();
	}

	private static int receiveACommandFromTheUser() {
		
		return sc.nextInt();
	}
}
