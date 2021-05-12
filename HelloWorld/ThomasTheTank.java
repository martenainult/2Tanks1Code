package HelloWorld;

import robocode.*;
import robocode.Robot;
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
	int baseTurn = 15000;

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

		setAdjustGunForRobotTurn(true);
		//setAdjustRadarForGunTurn(true);



		// Spin the gun around slowly... forever
		while (true) {
			scan();

			setTurnRight(baseTurn);
			setMaxVelocity(2);
			ahead(1000);
		}
	}

	/**
	 * onScannedRobot:  Fire!
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		setTurnRadarRight(2.0 * Utils.normalRelativeAngleDegrees(getHeading() + e.getBearing() - getRadarHeading()));
		setGuntoEnemy(e);
		if (e.getDistance() < 50 && getEnergy() > 50) {
			fire(3);
		} // otherwise, fire 1.
		else {
			fire(1);
		}
		// Call scan again, before we turn the gun
		scan();
	}

	private void setGuntoEnemy(ScannedRobotEvent e) {
		double suund = e.getBearing();
		if (suund < 0) {
			setTurnGunLeft(suund);
		} else {
			setTurnGunRight(suund);
		}
		scan();
	}


	/**
	 * onHitByBullet:  Turn perpendicular to the bullet, and move a bit.
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// lambine liikumine
		randomMovement();
		scan();
	}

	@Override
	public void onHitWall(HitWallEvent event) {
		double nurk = event.getBearing();
		turnRight((nurk % 90) + 180);
		scan();
		ahead((int) ((Math.random() * (300 - 100)) + 100));

	}

	public void randomMovement() {
		turnRight((int) ((Math.random() * (50)) + 20));
		scan();
		ahead((int) ((Math.random() * (300 - 100)) + 100));
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
