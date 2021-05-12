package HelloWorld;

import robocode.*;
import robocode.util.Utils;

import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;


/**
 * Tank makes an 8 figure
 *
 * @author Kristjan Pekk
 * @author Marten Vainult
 */
public class ThomasTheTank extends AdvancedRobot {
	int baseTurn = 300;

	/**
	 * run:  Fire's main run function
	 */
	public void run() {
		// Set colors
		setBodyColor(Color.BLUE);
		setGunColor(Color.WHITE);
		setRadarColor(Color.WHITE);
		setScanColor(Color.blue);
		setBulletColor(Color.red);

		//setAdjustGunForRobotTurn(true);
		//setAdjustRadarForGunTurn(true);



		// Spin the gun around slowly... forever
		while (true) {
			scan();
			setTurnRight(baseTurn);
			setMaxVelocity(8);
			scan();
			ahead(200);
		}
	}

	/**
	 * onScannedRobot:  Fire!
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		setTurnGunRight(2.0 * Utils.normalRelativeAngleDegrees(getHeading() + e.getBearing() - getGunHeading()));
		//setGuntoEnemy(e);
		if (e.getDistance() < 50 && getEnergy() > 50) {
			fire(3);
			initiateRambo(e);
		} // otherwise, fire 1.
		else {
			fire(1);
		}
		// Call scan again, before we turn the gun
		scan();
	}

	@Override
	public void onBulletHit(BulletHitEvent event) {
		scan();
	}

	private void initiateRambo(ScannedRobotEvent e) {
		for (int i = 0; i < 10; i++) {
			setTurnGunRight(2.0 * Utils.normalRelativeAngleDegrees(getHeading() + e.getBearing() - getGunHeading()));
			//setGuntoEnemy(e);
			if (e.getDistance() < 50 && getEnergy() > 50) {
				fire(3);
			} // otherwise, fire 1.
			else {
				fire(1);
			}
		}
		scan();
	}

	public double calculateDistanceBetweenPointsWithHypot(
			double x1,
			double y1,
			double x2,
			double y2) {

		double ac = Math.abs(y2 - y1);
		double cb = Math.abs(x2 - x1);

		return Math.hypot(ac, cb);
	}


	/**
	 * onHitByBullet:  Turn perpendicular to the bullet, and move a bit.
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// lambine liikumine
		int i = (int) Math.floor(Math.random() * 5);
		if (i < 2) randomMovement();
		scan();
	}

	@Override
	public void onHitWall(HitWallEvent event) {
		double nurk = event.getBearing();
		turnRight((nurk % 90) + 180);
		scan();
		ahead((int) ((Math.random() * (200 - 100)) + 100));

	}

	public void randomMovement() {
		turnRight((int) ((Math.random() * (60)) + 20));
		scan();
		ahead((int) ((Math.random() * (200 - 100)) + 50));
	}


	/**
	 * onHitRobot:  Aim at it.  Fire Hard!
	 */
	public void onHitRobot(HitRobotEvent e) {
		for (int i = 0; i < 10; i++) {
			setTurnGunRight(Utils.normalRelativeAngleDegrees(getHeading() + e.getBearing() - getGunHeading()));
			//setGuntoEnemy(e);
			if (getEnergy() > 30) {
				fire(3);
			} // otherwise, fire 1.
			else {
				fire(1);
			}
		}
		scan();

	}
}
