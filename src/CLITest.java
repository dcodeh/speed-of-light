import java.text.SimpleDateFormat;

public class CLITest {

	public static void main(String[] args) {

		Location sun = new Sun();
		Vehicle photon = new Photon();
		Vehicle voyager = new Voyager();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy");
		
		System.out.println("Sun Distance: " + sun.getDistanceFromEarth());
		System.out.println("Photon Speed: " + photon.getVelocity());
		System.out.println("Voyager Speed" + voyager.getVelocity());
		
		Calculation vCalc = new Calculation(voyager, sun);
		Calculation pCalc = new Calculation(photon, sun);
		
		System.out.println("Voyager Arrival Date " + sdf.format(vCalc.getArrivalDate().getTime()));
		System.out.println("Photon Arrival Date " + sdf.format(pCalc.getArrivalDate().getTime()));
		
	}

}
