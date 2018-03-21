package userStuff;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import enums.Cities;
import exceptions.IncorrectPasswordException;
import exceptions.InvalidEmailException;
import exceptions.InvalidPasswordException;
import exceptions.InvalidPhoneNumberException;
import exceptions.InvalidRegistrationException;

public class User {
	private String firstName;
	private String lastName;
	private Cities city;
	private String emailAdress;
	private String password;
	private String phoneNumber;
	private LocalDate birthday;
//	private List<Reservation> myReservations;
//	private List<Zavedenie> lybimiZavedeniq;
//	private List<Zavedenie> dnevnikZaZavedeniq;
//	private List<Comment> comments;
//	private List<Zavedenie> pastReservations;
	
	private User(String firstName, String lastName, Cities city, String emailAdress, String password, String phoneNumber, LocalDate birthday) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.city = city;
		this.emailAdress = emailAdress;
		this.password = password;
		this.birthday=birthday;
//		this.myReservations = new ArrayList<>();
//		this.lybimiZavedeniq = new ArrayList<>();
//		this.dnevnikZaZavedeniq = new ArrayList<>();
//		this.comments = new ArrayList<>();
//		this.pastReservations = new ArrayList<>();
	}
	public static User register(String firstName, String lastName, Cities city, String emailAdress, String password, String phoneNumber, LocalDate birthday) throws InvalidRegistrationException{
		if(checkForValidString(firstName)) {
			if(checkForValidString(lastName)) {
				if(city!=null) {
					try {
						if(checkForValidEMail(emailAdress)) {
							if(checkForValidPassword(password)) {
								if(checkForValidPhoneNumber(phoneNumber)) {
									if(birthday!=null)
										return new User(firstName,lastName,city,emailAdress,password,phoneNumber,birthday);
									else
										throw new InvalidRegistrationException("Neuspeshna registraciq.Podavash null za birthday..");
								}
							}
						}
					} catch (InvalidEmailException | InvalidPasswordException | InvalidPhoneNumberException e) {
						System.out.println("Neuspeshna registraciq! Prichina: " + e.getMessage());
					}
				} else 
					throw new InvalidRegistrationException("Neuspeshna registraciq.Podavash null za city..");
			}	
		} else 
			throw new InvalidRegistrationException("Neuspeshna registraciq!!!");
		return null;
	}
	public void changePassword(String newPassword,String oldPassword) {
		try {
			if(checkForPasswordMatch(oldPassword)) {
				if(checkForValidPassword(newPassword)) 
					this.password=newPassword;
			}
		} catch (IncorrectPasswordException | InvalidPasswordException e) {
			System.out.println("The password was not changed! Reason: " + e.getMessage());
		}
	}

	public void changeEMail(String oldPassword, String newEMail) {
		try {
			if(checkForPasswordMatch(oldPassword)) {
				if(checkForValidEMail(newEMail))
					this.emailAdress=newEMail;
			}
		} catch (IncorrectPasswordException | InvalidEmailException e) {
			System.out.println("The email was not changed! Reason: " + e.getMessage());

		}
	}
	public void changeFirstName(String firstName, String password) {
		try {
			if(checkForPasswordMatch(password)) {
				if(checkForValidString(firstName)) {
					this.firstName = firstName;
				}
			}
		} catch (IncorrectPasswordException e) {
			System.out.println("The name was not changed! Reason: " + e.getMessage());
		}
	}
	public void changeLastName(String lastName, String password) {
		try {
			if(checkForPasswordMatch(password)) {
				if(checkForValidString(lastName)) {
					this.firstName = lastName;
				}
			}
		} catch (IncorrectPasswordException e) {
			System.out.println("The last name was not changed! Reason: " + e.getMessage());
		}
	}
	public void changeCity(String password,Cities newCity) {
		try {
			if(checkForPasswordMatch(password)) {
				if(newCity!=null) {
					this.city = newCity;
				}
			}
		} catch (IncorrectPasswordException e) {
			System.out.println("The city was not changed! Reason: " + e.getMessage());
		}
	}
	public void changeMobileNumber(String password, String phoneNumber) {
		try {
			if(checkForPasswordMatch(password))
				if(checkForValidPhoneNumber(phoneNumber))
					this.phoneNumber=phoneNumber;
		} catch (IncorrectPasswordException | InvalidPhoneNumberException e) {
			System.out.println("The mobile number was not changed! Reason: " + e.getMessage());

		}
	}
	public void changeBirthday(String password, LocalDate birthday) {
		try {
			if(checkForPasswordMatch(password)) {
				if(birthday!=null) {
					this.birthday = birthday;
				}
			}
		} catch (IncorrectPasswordException e) {
			System.out.println("The birthday was not changed! Reason: " + e.getMessage());
		}
	}
//	public void addReservation(Reservation r) {
//		if(r!=null) {
//			this.myReservations.add(r);
//		}
//	}
//	public void addLubimoZavedenie(Zavedenie z) {
//		if(z!=null) {
//			this.lybimiZavedeniq.add(z);
//		}
//	}
//	public void removeLubimoZavedenie(Zavedenie z) {
//		if(z!=null) {
//			if(this.lybimiZavedeniq.contains(z)) {
//				this.lybimiZavedeniq.remove(z);
//			}
//		}
//	}
	
	
	
	
	private static boolean  checkForValidPhoneNumber(String phoneNumber) throws InvalidPhoneNumberException{
		if(phoneNumber!=null) {
			if(phoneNumber.trim().length()==10) {
				for(int i=0;i<phoneNumber.length();i++) {
					if(!(Character.isDigit(i))) 
						throw new InvalidPhoneNumberException("Nomera trqbva da se systoi samo ot cifri!");
					else 
						return true;
				}
			} else 
				throw new InvalidPhoneNumberException("Nomera trqbva da sydyrza tochno 10 cifri!");
		} else 
			throw new InvalidPhoneNumberException("Podavash null za phonenumber");
		return false;
	}

	private static boolean checkForValidString(String str) {
		if((str!=null) && (str.trim().length()>0))
			return true;
		return false;
	}

	private static boolean checkForValidEMail(String eMail) throws InvalidEmailException{
		if(eMail!=null) {
			if(eMail.trim().length()>10) {
				if(eMail.contains("@"))
					return true;
				else
					throw new InvalidEmailException("Email-a ti e nevaliden! Ne sydyrzha @");
			} else 
				throw new InvalidEmailException("Nevaliden email!");
		} else
			throw new InvalidEmailException("Podavash mi null za email...");
	}

	private boolean checkForPasswordMatch(String password) throws IncorrectPasswordException{
		if(password!=null) {
			if(password.equals(this.password))
				return true;
			else
				throw new IncorrectPasswordException("Parolite ne syvpadat!!");
		} else {
			throw new IncorrectPasswordException("Podavash null za parola..");
		}
	}


	private static boolean checkForValidPassword (String password) throws InvalidPasswordException {
		if(password!=null) {
			if(password.trim().length()>5) 
				return true;
			else 
				throw new InvalidPasswordException("Dylzhinata na parolata trqbva da e pone 5 znaka");
		} else 
			throw new InvalidPasswordException("Podavash null za parola..");
	}
	

//	getters and setters:
	public String getFirstName() {
		return firstName;
	}
	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	private void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Cities getCity() {
		return city;
	}
	private void setCity(Cities city) {
		this.city = city;
	}
	public String getEmailAdress() {
		return emailAdress;
	}
	private void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public LocalDate getBirthday() {
		return birthday;
	}
	
//	public List<Reservation> getMyReservations() {
//		return Collections.unmodifiableList(this.myReservations);
//	}
//	
//	public List<Zavedenie> getLybimiZavedeniq() {
//		return Collections.unmodifiableList(this.lybimiZavedeniq);
//	}
//	
//	public List<Zavedenie> getDnevnikZaZavedeniq() {
//		return Collections.unmodifiableList(this.dnevnikZaZavedeniq);
//	}
//	
//	public List<Comment> getComments() {
//		return Collections.unmodifiableList(this.comments);
//	}
//
//	public List<Zavedenie> getPastReservations() {
//		return Collections.unmodifiableList(this.pastReservations);
//	}
	
}
