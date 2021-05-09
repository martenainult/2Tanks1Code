package HelloWorld;

import robocode.*;
import robocode.Robot;

import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;


/**
 * Tank makes an 8 figure
 *
 * @author Kristjan Pekk
 * @author Marten Vainult
 */
public class ThomasTheTank extends AdvancedRobot {
	int dist = 50; // distance to move when we're hit
	int baseTurn = 15000;

	/**
	 * run:  Fire's main run function
	 */
	public void run() {
		// Set colors
		setBodyColor(Color.BLUE);
		setGunColor(Color.WHITE);
		setRadarColor(Color.WHITE);
		setScanColor(Color.red);
		setBulletColor(Color.red);

		// Spin the gun around slowly... forever
		while (true) {
			scan();

			setTurnRight(baseTurn);
			setMaxVelocity(4);
			ahead(10000);
		}
	}

	/**
	 * onScannedRobot:  Fire!
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// If the other robot is close by, and we have plenty of life,
		// fire hard!
		if (e.getDistance() < 50 && getEnergy() > 50) {
			fire(3);
		} // otherwise, fire 1.
		else {
			fire(1);
		}
		// Call scan again, before we turn the gun
		scan();
	}

	/**
	 * onHitByBullet:  Turn perpendicular to the bullet, and move a bit.
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		turnRight(normalRelativeAngleDegrees(90 - (getHeading() - e.getHeading())));

		ahead(dist);
		dist *= -1;
		scan();
	}

	/**
	 * onHitRobot:  Aim at it.  Fire Hard!
	 */
	public void onHitRobot(HitRobotEvent e) {
		double turnGunAmt = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());

		turnGunRight(turnGunAmt);
		fire(3);
	}
}
