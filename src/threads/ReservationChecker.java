package threads;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import places.Place;
import services.Reservation;
import userStuff.UserAdministration;
import website.Website;

public class ReservationChecker extends Thread {

	private static final int RESERVATION_DURATION = 120;
	Website website;

	public ReservationChecker(Website website) {
		if (website != null) {
			this.website = website;
		}
	}

	private List<Reservation> sumAllReservations() {
		List<Reservation> reservations = new ArrayList<>();
		for (Place place : website.getAllClubs()) {
			reservations.addAll(place.getAllReservations());
		}
		for (Place place : website.getAllRestaurants()) {
			reservations.addAll(place.getAllReservations());
		}
		return reservations;

	}

	@Override
	public void run() {
		while (true) {
			try {
				// checks for past reservations every 2h
				Thread.sleep(2 * 60 * 60 * 1000);
				List<Reservation> allReservations = sumAllReservations();
				for (Reservation res : allReservations) {
					LocalTime currentTime = LocalTime.now();
					long elapsedMinutes = Duration.between(res.getTime(), currentTime).toMinutes();
					if (elapsedMinutes >= RESERVATION_DURATION) {
						System.out.println("Reservation " + res.getReservationID() + " is passed!");
						res.getPlace().increaseCurrentCapacity();
						res.getPlace().removeReservation(res);
						res.getUser().removeReservation(res);
					}
				}
			} catch (InterruptedException e) {
				return;
			}

		}
	}

}
