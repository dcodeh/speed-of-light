/**
 * TODO Docs
 * @author cody
 *
 */
public class Sun extends Location {

	// in millions of km
	private static final double DIST_FROM_EARTH = 149597900.00 / 1000000;
	
	public Sun() {
		super(DIST_FROM_EARTH);
	}

}
