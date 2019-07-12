
public class Main {
	public static Direction direction;
	public static Driver vroom;
	public static ScreenShotIO _botIO = new ScreenShotIO();
	public static double lastDirectionCheck = 0;
	public static boolean switched;

	public static void resetDirection() //goes out of ball cam and back in again to see what direction the car is facing (LEFT / RIGHT) no theta
	{	
			boolean prevFaceRight = direction.faceRight;
			vroom.ReleaseA();
			vroom.ReleaseD();
			try { Thread.sleep(50); } catch (InterruptedException e) { System.out.println("heck"); }
			direction.checkFaceRight();
			
			if (direction.faceRight != prevFaceRight)
			{
				switched = true;
				System.out.println("yeet");
			}
			else
			{
				switched = false;
			}
			
			lastDirectionCheck = System.currentTimeMillis();
	}
	
	public static void driveInDirection()
	{
		if (direction.faceRight)
		{
			vroom.ReleaseD();
			vroom.PressA();
		}
		else
		{
			vroom.ReleaseA();
			vroom.PressD();
		}
	}
	
	public static void main (String [] args) throws InterruptedException
	{
		long startTime = System.currentTimeMillis();
		lastDirectionCheck = 0;
		
		System.load("C:\\users\\drewh\\Desktop\\opencv\\build\\java\\x64\\opencv_java401.dll");

		vroom = new Driver(direction);
		direction = new Direction(_botIO, vroom);
		Thread.sleep(1000);
		
		//vroom.PressW();
		
		while(System.currentTimeMillis() - startTime < 10000)
		{
			direction.updateTrackList();

			long currentTime = System.currentTimeMillis();
			
			
			double newTheta = direction.getTheta();
			
			if (!direction.faceRight)
			{
				newTheta += Math.PI;
			}
			
			System.out.println(newTheta);
			System.out.println(direction.faceRight);
			if (currentTime - lastDirectionCheck > 2000)
			{
				resetDirection();
				//driveInDirection();	
			}
			else
			{
				if (newTheta < Math.PI)
				{
					vroom.ReleaseD();
					vroom.PressA();					
				}
				else if (newTheta > Math.PI)
				{
					vroom.ReleaseA();
					vroom.PressD();
				}
			}
		}
	}
}