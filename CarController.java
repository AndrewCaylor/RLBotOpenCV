
public class CarController {
	public ScreenShotIO scIO;
	public Direction direction;
	public ResetDirectionBallCam sideChecker;
	public ThetaList checkingList;
	public ButtonPresser vroom;
	
	public double getErrorTheta(double desiredTheta)
	{
		double rawTheta = direction.getRawTheta();
		checkingList.add(rawTheta);
		double currentTheta = checkingList.getLastThetaProcessed();
		return currentTheta - desiredTheta;
	}
	
	public CarController()
	{
		scIO = new ScreenShotIO();
		vroom = new ButtonPresser();
		direction = new Direction(scIO);
		checkingList = new ThetaList();
		sideChecker = new ResetDirectionBallCam(checkingList, vroom);
	}
	
	public void moveCarTowardDesiredDirection(double desiredTheta)
	{
		double error = getErrorTheta(desiredTheta);
		
		if (vroom.stopAngle() > error);
	}
	
	public static void main(String [] args)
	{
		
	}
	
}
