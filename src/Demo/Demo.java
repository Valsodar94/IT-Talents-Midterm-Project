package Demo;

import java.time.LocalDate;
import java.util.Scanner;

import places.City;
import userStuff.User;
import userStuff.UserAdministration;
import website.Website;

public class Demo {
	static Scanner sc = new Scanner(System.in);
	private static final String WELCOME_MESSAGE = "Welcome in our site ";

	public static void main(String[] args) {
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
				w.showPlaces(name, true, num);
				break;
			case 2:
				showOptionsForClubsDesplay();
				showCities(false);
				name = getCityName();
				num = getNmbOfPlacesToShow();
				w.showPlaces(name,false,num);
				break;
			case 8:
				showRegistrationRequirements();
				makeRegistration();
			}
		}
			
	}


	private static User makeRegistration() {
		String firstName = getFirstName();
		String lastName = getLastName();
		String city = getCity();
		String emailAdress = getEmail();
		String password = getPassword(); 
		String phoneNumber = getPhoneNumber(); 
		LocalDate birthday = getBirtday();
		boolean isAdmin = isAdmin();
		String adminPass = null;
		if(isAdmin) 
			adminPass = getAdminPass();
		
		
		User newUser = UserAdministration.register(firstName, lastName, city, emailAdress, password, phoneNumber, birthday, isAdmin, adminPass);
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


	private static LocalDate getBirtday() {
		System.out.println("Type your birthday.");
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


	private static void showRegistrationRequirements() {
		System.out.println();
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

	private static void showAvailableOptions() {
		System.out.println("What would you like to do?");
		System.out.println("Type: 1, to look at the restaurants we have to offer");
		System.out.println("Type: 2, to look at the clubs we have to offer");
		System.out.println("Type: 3, to look at the offers we have available");
		System.out.println("Type: 4, to look at the scheduled events");
		System.out.println("Type 5, to make a reservation");
		System.out.println("Type 6, to cancel reserrvation");
		System.out.println("Type 7, to leave a comment of a past reservation");
		if(!(UserAdministration.isLogged())) {
			System.out.println("Type 8, to register");
			System.out.println("Type 9, to log in");
		}else {
			if(UserAdministration.getU() instanceof User) {
				System.out.println("Type 10, to logout");
				System.out.println("Type 11, to change something in your profile(name, adress, etc.)");
				System.out.println("Type 12, to delete your profile");
				System.out.println();
			} else {
//				admin options
			}
		}
		
	}
		
	
}
