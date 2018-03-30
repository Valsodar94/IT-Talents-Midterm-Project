package main;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {

	public static void main(String[] args) {
		LocalTime d = LocalTime.now();
		int dt = LocalDateTime.now().getHour();
		System.out.println(d);
		System.out.println(dt);
	}

}
