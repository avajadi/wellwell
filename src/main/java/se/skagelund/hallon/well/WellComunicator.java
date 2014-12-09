package se.skagelund.hallon.well;

public class WellComunicator {

	public int readDistance() {
		return (int)(Math.random() * 55 + 60);
	}

	public Double readAirTemperature() {
		return Math.random()*20-5;
	}

	public Double readWaterTemperature() {
		return Math.random()*7;
	}

}
