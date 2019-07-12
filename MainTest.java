import org.opencv.core.Mat;

public class MainTest {

	public static void main(String [] args) throws Exception
	{
		System.load("C:\\users\\drewh\\Desktop\\opencv\\build\\java\\x64\\opencv_java401.dll");

		Direction d = new Direction();
						
		Thread.sleep(1000);
		
		while (true)
		{			
			d.updateThetas();	
			
			if (d.getLastAngle() != Double.NaN)
			{
				if (Math.sin(d.getLastAngle()) > .1 )
				{
					Driver.ReleaseD();
					Driver.PressA();
				}
				else if(Math.sin(d.getLastAngle()) < -.1)
				{
					Driver.ReleaseA();
					Driver.PressD();
				}
			}
		}
		
	}

	
}
