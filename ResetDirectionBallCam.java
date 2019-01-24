// uses direction class to get the raw theta
// saves result to thetaList
public class ResetDirectionBallCam {

	ThetaList checkingList;
	Direction direction;
	ButtonPresser buttonPresser;
	
	public ResetDirectionBallCam(ThetaList cL, ButtonPresser b) 
	{
		checkingList = cL;
		buttonPresser = b;
	}
	

	private double checkFaceRight()
	{
		checkingList.add(direction.getRawTheta());;

		buttonPresser.ToggleSpace();
		
		try { Thread.sleep(50); } catch (InterruptedException e) { System.out.println("heck"); }
		
		checkingList.add(direction.getRawTheta());
		
		double difference = checkingList.lastTheta - checkingList.lastLastTheta;
		
		buttonPresser.ToggleSpace();
		
		boolean faceRight;
		
		if (difference < 0)
		{
			faceRight = true;
		}
		else
		{
			faceRight = false;
		}
		
		checkingList = new ThetaList(faceRight);
		
		return difference;
	}
	
	public void resetDirection() //goes out of ball cam and back in again to see what direction the car is facing (LEFT / RIGHT) no theta
	{
					
		buttonPresser.ReleaseA();
		buttonPresser.ReleaseD();
		
		try { Thread.sleep(50); } catch (InterruptedException e) { System.out.println("heck"); }
		
		checkFaceRight();
		
		try { Thread.sleep(200); } catch (InterruptedException e) { System.out.println("heck"); }
	}
}