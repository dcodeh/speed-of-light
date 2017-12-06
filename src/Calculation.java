import java.util.Calendar;

/**
 * TODO docs
 * @author cody
 *
 */
public class Calculation {

	private Vehicle vehicle;
	private Location loc;
	private Calendar launchDate;
	
	public Calculation(Vehicle v, Location l) {
		this.vehicle = v;
		this.loc = l;
		launchDate = Calendar.getInstance();
	}
	
	public double getTripDuration() {
	
		// distance = rate * time
		// time = distance / rate
		
		// result is in seconds
		
		return loc.getDistanceFromEarth() / vehicle.getVelocity();
		
	}
	
	public Calendar getArrivalDate() {
		
		Calendar arrivalDate = (Calendar) launchDate.clone();
		arrivalDate.add(Calendar.SECOND, (int) getTripDuration());
		
		return arrivalDate;
		
	}
	
	public double  getArrivalDifference(Calculation c) {
		
		Calendar arrival1 = c.getArrivalDate();
		
		long diff = this.getArrivalDate().getTimeInMillis() - arrival1.getTimeInMillis();
		diff = Math.abs(diff);
		
		return (double) diff / 1000;
		
	}
	
}
