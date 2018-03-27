package userStuff;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import exceptions.InvalidInformationException;
import places.City;
import services.Reservation;
import website.Website;

public abstract class UserAdministration {
	private static boolean isLogged;
	private static List<User> allUsers= new ArrayList<>();;
	private static User u;
	private static String adminPass = "";

	
	
	public User register(String firstName, String lastName, City city, String emailAdress, String password, String phoneNumber, LocalDate birthday, Website w,boolean isAdmin, String adminPass) throws InvalidInformationException {
		if(isAdmin) {
			if(UserAdministration.adminPass.equals(adminPass)) {
				Admin a = new Admin(firstName, lastName, city, emailAdress, password, phoneNumber, birthday, w);
				allUsers.add(a);
				return a;
			} else 
				throw new InvalidInformationException("Registraciq neuspeshna! greshna parola za admin");
		}
		else {
			User u = new User(phoneNumber, phoneNumber, city, phoneNumber, phoneNumber, phoneNumber, birthday, w);
			allUsers.add(u);
			return u;
		}
	}
	public static void login(String password, String email) throws InvalidInformationException{
		if(!isLogged) {
			if(password!=null && email!=null) {
				for(User user: allUsers) {
					if(u.getEmailAdress().equals(email)) {
						if(u.getPassword().equals(password)) {
							System.out.println("Login uspeshen");
							isLogged = true;
							u = user;
							refreshPresentAndPastReservations();
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
	
	private static void refreshPresentAndPastReservations() {
		LocalDateTime now = LocalDateTime.now();
		Iterator<Reservation> it = u.getMyReservations().iterator();
		while(it.hasNext()) {
			Reservation r = it.next();
			if(r.getDateAndTime().isBefore(now)) {
				u.getPastReservations().add(r);
				it.remove();
			}
		}
	}
	
	public static void logout() {
		if(u!=null) {
			System.out.println("Logout successful?");
			u=null;
		} else {
			System.out.println("Nqma vpisan potrebitel, kakyv logout iskash?");
		}
	}
	
	
//	to add password check before deleting!!!
	public static void deleteUser(User u ) {
		if(u!=null) {
			Iterator<User> it = allUsers.iterator();
			while(it.hasNext()) {
				User user = it.next();
				if(u.equals(user))
					it.remove();
			}
		}
	}
//	da namerq shablon za email check 
	public static boolean checkForValidEMail(String eMail) throws InvalidInformationException{
		if(eMail!=null) {
			if(eMail.trim().length()>10) {
				if(eMail.contains("@")) {
					for(User u: allUsers) {
						if(u.getEmailAdress().equals(eMail)) {
							throw new InvalidInformationException("Veche ima potrebitel s takyv meil!");
						}
					}
					 return true;
				}
				else
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
		return u;
	}
}