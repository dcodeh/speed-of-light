/**
 * TODO docs
 * @author cody
 *
 */
public class Vehicle {

	// in km/s
	private double speed;
	private String name;
	
	public Vehicle(double speed, String name) {
		this.speed = speed;
		this.name = name;
	}
	
	public double getVelocity() {
		return speed;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
