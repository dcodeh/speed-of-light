/**
 * TODO docs
 * @author cody
 *
 */
public class Moon extends Location {
	
	// in millions of km
	private static final double DIST_FROM_EARTH = 384402 / 1000000;
	
	public Moon() {
		super(DIST_FROM_EARTH);
	}

}
