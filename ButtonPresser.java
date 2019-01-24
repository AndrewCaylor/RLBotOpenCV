import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ButtonPresser {

	Direction direction = null;
	public boolean turningLeft = false;
	public boolean turningRight = false;
	public boolean drivingForward;
	public boolean ballCam;
	public long timeStartedTurning = 0;
	public long timeStoppedTurning = 0;
	
	public ButtonPresser() 
	{
	}
	
	public void backFlip() //400 millis
	{
		PressS();		
		try { Thread.sleep(300); } catch (InterruptedException e) { System.out.println("heck"); }
		Jump();
		try { Thread.sleep(50); } catch (InterruptedException e) { System.out.println("heck"); }

		Jump();
  		ReleaseS();
		try { Thread.sleep(50); } catch (InterruptedException e) { System.out.println("heck"); }

	}
	
	public void halfFlip()
	{
		backFlip();
		PressW();
		try { Thread.sleep(400); } catch (InterruptedException e) { System.out.println("heck"); }
		ReleaseW();
	}
	
	public void startTurn(String dir) // CAPS
	{
		if (dir.equals("RIGHT"))
		{
			PressD();
			ReleaseA();
		}
		else if (dir.equals("LEFT"))
		{
			PressA();
			ReleaseD();
		}
		
		timeStartedTurning = System.currentTimeMillis();
		timeStoppedTurning = 0;

	}
	
	public void StopTurn()
	{
		ReleaseA();
		ReleaseD();
		timeStoppedTurning = System.currentTimeMillis();
		timeStartedTurning = 0;
	}
	
	private double estTurnSpeed()
	{
		if (timeStartedTurning > 1)
		{
			long timeDriving = System.currentTimeMillis() - timeStartedTurning;
			
			double angVelocity = 4.2 * Math.atan((double) timeDriving * 30) / Math.PI;

			return angVelocity;
		}
		
		else if (timeStoppedTurning > 1)
		{
			long timeDriving = System.currentTimeMillis() - timeStartedTurning;
			
			double angVelocity = (- 4.2 * Math.atan((double) timeDriving * 30) / Math.PI) + 2.1;
			
			return angVelocity;
		}
		else
		{
			return 0;
		}
	}
	
	public double stopAngle()
	{
		double precentMax = estTurnSpeed() / 2.1;
		return precentMax * 10;	}
	
	public void ToggleSpace()
	{
		try {
	        Robot robot = new Robot();

	        // Simulate a key press
	        robot.keyPress(KeyEvent.VK_SPACE);
			try { Thread.sleep(50); } catch (InterruptedException e) { System.out.println("heck"); }
	        robot.keyRelease(KeyEvent.VK_SPACE);

	        ballCam = !ballCam;
	        
		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	
	public void Jump()
	{
		try {
	        Robot robot = new Robot();

	        // Simulate a key press
	        robot.keyPress(KeyEvent.VK_UP);
	        robot.delay(150);
	        robot.keyRelease(KeyEvent.VK_UP);

	        ballCam = !ballCam;
	        
		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	
	public void JumpMouse()
	{
		try {
	        Robot robot = new Robot();
	        robot.mouseMove(500, 10);
	        // Simulate a key press
	        robot.mousePress(InputEvent.BUTTON1_MASK);
	        robot.delay(10);
	        robot.mouseRelease(InputEvent.BUTTON1_MASK);

	        ballCam = !ballCam;
	        
		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	
	
	public void PressW()
	{
		try {
	        Robot robot = new Robot();
	        // Simulate a key press
	        robot.keyPress(KeyEvent.VK_W);

		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	
	public void PressE()
	{
		try {
	        Robot robot = new Robot();
	        // Simulate a key press
	        robot.keyPress(KeyEvent.VK_E);

		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	

	public  void ReleaseE()
	{
		try {
	        Robot robot = new Robot();

	        // Simulate a key press

	        robot.keyRelease(KeyEvent.VK_E);

		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	
	
	public  void ReleaseW()
	{
		try {
	        Robot robot = new Robot();

	        // Simulate a key press

	        robot.keyRelease(KeyEvent.VK_W);

		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	
	
	public  void ReleaseA()
	{
		try {
	        Robot robot = new Robot();

	        // Simulate a key press

	        robot.keyRelease(KeyEvent.VK_A);
	        turningLeft = false;

		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	

	public  void PressA()
	{
		try {
	        Robot robot = new Robot();

	        // Simulate a key press
	        robot.keyPress(KeyEvent.VK_A);
	        turningLeft = true;

		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	public  void PressD()
	{
		try {
	        Robot robot = new Robot();

	        // Simulate a key press
	        robot.keyPress(KeyEvent.VK_D);
			turningRight = true;

		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	
	public  void ReleaseD()
	{
		try {
	        Robot robot = new Robot();

	        // Simulate a key press

	        robot.keyRelease(KeyEvent.VK_D);
	        turningRight = false;

		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	public  void PressS()
	{
		try {
	        Robot robot = new Robot();

	        // Simulate a key press
	        robot.keyPress(KeyEvent.VK_S);


		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	
	public  void ReleaseS()
	{
		try {
	        Robot robot = new Robot();

	        robot.keyRelease(KeyEvent.VK_S);

		} catch (AWTException e) {
		        e.printStackTrace();
		} 
	}
}
