import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Direction {

	private static final int prevAnglesLength = 10;

	private ArrayList<Double> previousAngles = new ArrayList<Double>(); // These values are adjusted	
	private ArrayList<Long> times = new ArrayList<Long>(); // Times of detected angles

	
	public Direction() 
	{
	}
	
	
	public double getLastAngle()
	{
		if (previousAngles.size() > 0)
		{
			return previousAngles.get(previousAngles.size() - 1);
		}
		else
		{
			return Double.NaN;
		}
		
	}
	
	public void updateThetas()
	{
		System.out.println();
		Mat src = ScreenShotIO.getSrcMatCar(); //gets screenshot of car
		Mat edgesImg = getEdges(src); //process image to find the countors
		double [][] lines = getLines(edgesImg); //finds the lines in the edges image
		double [] thetas = lines[1]; //only uses the thetas
		
		thetas = adjustForBreak(thetas);
		
		double avgTheta = meanAngle(thetas); 
		
		double adjustedTheta = avgTheta;
		
		boolean isFacingForward = facingForward(src);
		
		if (!Double.isNaN(avgTheta))
		{				
			if (Math.cos(avgTheta) > .3 || Math.cos(avgTheta) < -.3)// checks if the angle is in the range in which direction can be known
			{
				if (isFacingForward)
				{
					System.out.print("forward");
					
					if (Math.cos(avgTheta)< -.3) //if opposite to what it should be
					{
						adjustedTheta += Math.PI;
					}
				}
				else
				{
					System.out.print("backward");
					
					if (Math.cos(avgTheta) > .3) //if opposite to what it should be
					{
						adjustedTheta += Math.PI;
					}
				}		

				if (previousAngles.size() >= prevAnglesLength)
				{
					previousAngles.remove(0); // removes oldest from list
					times.remove(0);
				}
				
				previousAngles.add(adjustedTheta); // adds newest
				times.add(System.currentTimeMillis());
		
			}
			else
			{
				System.out.println("idk");
			}
		}
		else
		{
			previousAngles =  new ArrayList<Double>();
			
			System.out.println("angle = nan");
		}
		
	}
	
	
	private Mat getEdges(Mat src)
	{
		Mat gray = new Mat();
		Imgproc.cvtColor(src,gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.medianBlur(gray, gray, 5); // tutorial told me to do this
        

        int low_threshold = 650;
        int high_threshold = 750;// trust me these are good numbers
        Mat edges = new Mat();
        Imgproc.Canny(gray, edges, low_threshold, high_threshold); //finds the countors

        return edges;
	}
	
	private double [][] getLines(Mat edgesImg)
	{
		Mat out = new Mat();
        Imgproc.HoughLines(edgesImg, out, 1, Math.PI/180, 30); // runs the actual detection
        
        // this exists because i hate dealing with the mat
        double [][] thetaRhos = new double[2][out.rows()];
		for (int i = 0; i < out.rows(); i ++)
		{
			thetaRhos[0][i] = out.get(i, 0)[0]; //rho
			thetaRhos[1][i] = out.get(i, 0)[1]; //theta
		}
		return thetaRhos;
	}
	
	//inputs all the angles found on the car
	private double [] adjustForBreak(double [] thetas) // subtracts pi from angles greater than pi/2
	{	
		double mean = meanAngle(thetas);
		
		if ( stdDevAngle(thetas,  mean) > .5)// if standard deviation is high there is likely a break in the data
		{			
			for (int i = 0; i < thetas.length; i ++)
			{				
				if (thetas[i] > Math.PI /2)
				{
					thetas[i] -= Math.PI;
				}
			}
		}
		return thetas; // average might be negative if there is a break and more angles are greater than pi/2
	}

	 private double meanAngle(double [] angles)
	 {
	        double x = 0.0;
	        double y = 0.0;
	 
	        for (double angle : angles) 
	        {
	            x += Math.cos(angle);
	            y += Math.sin(angle);
	        }
	        return Math.atan2(y / angles.length, x / angles.length);
	    }
	 
	 private double stdDevAngle(double [] angles, double mean)
	 {
		 	double total = 0;
		 	
		 	double meanSin = Math.sin(mean);
		 	double meanCos = Math.cos(mean);
	        
	        for (double angle : angles) 
	        {
	            double x = Math.cos(angle);
	            double y = Math.sin(angle);
	            
	            total += Math.atan2(Math.abs(y - meanSin), Math.abs(x - meanCos));
	        }
	        return total / angles.length;
	    }
	
	 
	private boolean facingForward(Mat img)
	{		
		int count = 0;
		for (int col = 0; col < img.cols(); col ++)
		{
			for (int row = 0; row < img.rows(); row ++)
			{
				double [] rgb = img.get(row, col);
				
				boolean redInRange = rgb[2] > 250;
				boolean greenInRange = rgb[1] < 50;
				boolean blueInRange = rgb[0] < 50;
				
				if (redInRange & blueInRange & greenInRange)
				{
					count ++;
				}
			}
		}
					
		if (count > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	// CURRENTLY UNUSED
	public double getAngVelocity()
	{
		if (previousAngles.size() >= 2)
		{
			int size = previousAngles.size();
			
			double sin2 = Math.sin(previousAngles.get(size - 1));
			double sin1 = Math.sin(previousAngles.get(size - 2));
			
			double cos2 = Math.cos(previousAngles.get(size - 1));
			double cos1 = Math.cos(previousAngles.get(size - 2));
			
			double time2 = times.get(size - 1);
			double time1 = times.get(size - 2);
			
			return Math.atan2(sin2 - sin1, cos2 - cos1) / (time2 - time1);
		}
		return Double.NaN;
	}
	
	public BufferedImage getImg(Mat overLay, double [][] lines)
	{
        for (int i = 0; i < lines[0].length; i++) 
        {
            double rho = lines[0][i];
            double theta = lines[1][i];
            double a = Math.cos(theta);
            double b = Math.sin(theta);
            double x0 = a*rho, y0 = b*rho;
            Point pt1 = new Point(Math.round(x0 + 1000*(-b)), Math.round(y0 + 1000*(a)));
            Point pt2 = new Point(Math.round(x0 - 1000*(-b)), Math.round(y0 - 1000*(a)));

            Imgproc.line(overLay, pt1, pt2, new Scalar(200, 0,0), 1, Imgproc.LINE_AA, 0);
        }
        return ScreenShotIO.Mat2Buff(overLay);
	}

}