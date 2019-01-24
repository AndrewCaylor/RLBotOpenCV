import java.awt.image.BufferedImage;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;


// gets the raw 0 - pi direction of the car
// does not care about anything else
public class Direction {

	ScreenShotIO _botIO;
		
	public Direction(ScreenShotIO scIO) 
	{
		_botIO = scIO;
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
	
	private double getAvgThetaRaw(double [] values) // Adjusts for a break. 0 to pi. raw average theta from where the average would be
	{
		if (breakExists(values))
		{
			values = adjustBreak(values);
		}
		
		double mean = mean(values);
		if(mean < 0) mean += Math.PI;
		
		return mean;
	}
	
	public double getRawTheta()
	{
		Mat src = _botIO.getSrcMatCar(); //gets screenshot of car
		Mat edgesImg = getEdges(src); //process image to find the countors
		double [][] lines = getLines(edgesImg); //finds the lines in the edges image
		double [] thetas = lines[1]; //only uses the thetas
		double avgTheta = getAvgThetaRaw(thetas); 
		/* adjusts for a break if there is one (if greater than pi/2 subtracts pi)
		 * finds the average
		 * if average is negative it will add pi*/
		
		return avgTheta;
		
	}

	private double standardDeviation(double [] values, double mean)
	{
		double totalDifference = 0;
		for (int i = 0; i < values.length; i ++)
		{
			totalDifference += Math.abs( mean - values[i]);
		}
		return totalDifference / values.length;
	}
	
	private boolean breakExists(double [] values) // if standard deviation is high there is likely a break in the data
	{	
		double mean = mean(values);
		
		if ( standardDeviation(values,  mean) > .5)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private double mean(double [] values)
	{
		double total = 0;
		for (int i = 0; i < values.length; i ++)
		{
			total += values[i];
		}
		double mean = total / values.length;
		return mean;
	}
	
	private double [] adjustBreak(double [] thetas) // subtracts pi from angles greater than pi/2
	{		
		if (breakExists(thetas))
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
}