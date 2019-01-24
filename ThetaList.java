// processes new data to see if direction changed
// holds information about current angle adjustment


public class ThetaList {
	
	public double lastTheta = Double.NaN;
	public double lastLastTheta = Double.NaN;
	public double lastAdjustment = 0;
	public double lastLastAdjustment = 0;

	public Direction direction;
	
	public ThetaList()
	{ 
	}
	
	public ThetaList (boolean faceRight)
	{
		if (faceRight) lastAdjustment = 0;
		else lastAdjustment = -Math.PI;
	}
	
	
	 // adds the value but does processing to possibly change adjustment
	// adjustment not wanted/needed when adding only when sending most recent theta
	
	public void add(double value)
	{
		lastLastAdjustment = lastAdjustment;

		if (Double.isNaN(lastTheta) == false)
		{	
			if (Math.abs(lastTheta - value) > Math.PI/2)
			{
				if (value - lastTheta > Math.PI / 2)
				{
					lastAdjustment -= Math.PI;
				}
				else
				{
					lastAdjustment += Math.PI;
				}
			}
		}
		
		lastLastTheta = lastTheta;
		lastTheta = value;
	}
	
	public void addRAW(double value) // USED ONLY FOR ADDING WHEN NO OTHER PROCESSING IS NEEDED
	{
		lastLastTheta = lastTheta;
		lastTheta = value;
	}
	
	public double getLastThetaProcessed()
	{
		return getThetaNormalized(lastTheta + lastAdjustment);
	}
	
	private double getThetaNormalized(double theta) // only works if the imputted theta is already adjusted
	{		
		theta =  theta % (2 * Math.PI);
		
		if (theta > Math.PI)
		{
			theta -= 2 * Math.PI;
		}
		else if (theta < -Math.PI)
		{
			theta += 2 * Math.PI;
		}
		
		return theta;
	}
	
	public double angularVelocity()
	{
		if ( Double.isNaN(lastLastTheta) == false)
		{
			return (lastTheta + lastAdjustment) - (lastLastTheta + lastLastAdjustment);
		}
		return Double.NaN;
	}
}
