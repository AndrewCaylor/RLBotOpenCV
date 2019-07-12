import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Driver {

	Direction d = null;
	public boolean turningLeft;
	public boolean turningRight;
	public boolean drivingForward;
	
	public Driver() 
	{
	}
	
	
	public static void ToggleSpace()
	{
		try {
	        Robot robot = new Robot();

	        // Simulate a key press
	        robot.keyPress(KeyEvent.VK_SPACE);
	        robot.keyRelease(KeyEvent.VK_SPACE);
	        
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
	        drivingForward = true;

		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	
	
	public static  void ReleaseW()
	{
		try {
	        Robot robot = new Robot();

	        // Simulate a key press

	        robot.keyRelease(KeyEvent.VK_W);

		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	
	
	public static  void ReleaseA()
	{
		try {
	        Robot robot = new Robot();

	        // Simulate a key press

	        robot.keyRelease(KeyEvent.VK_A);

		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	

	public static void PressA()
	{
		try {
	        Robot robot = new Robot();

	        // Simulate a key press
	        robot.keyPress(KeyEvent.VK_A);

		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	public static void PressD()
	{
		try {
	        Robot robot = new Robot();

	        // Simulate a key press
	        robot.keyPress(KeyEvent.VK_D);

		} catch (AWTException e) {
		        e.printStackTrace();
		}
	}
	
	public static void ReleaseD()
	{
		try {
	        Robot robot = new Robot();

	        // Simulate a key press

	        robot.keyRelease(KeyEvent.VK_D);

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
	        Thread.sleep(100);
	        robot.keyRelease(KeyEvent.VK_S);

		} catch (AWTException e) {
		        e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}