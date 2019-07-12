
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class BoostPadFilter{

	private Mat img;
	
	public BoostPadFilter(Mat img)
	{

		this.img = img;
	}

	// finds all colors in a given range and increments a counter
	public boolean facingForward()
	{		
		int count = 0;
		for (int col = 0; col < img.cols(); col ++)
		{
			for (int row = 0; row < img.rows(); row ++)
			{
				double [] rgb = img.get(row, col);
				
				boolean redInRange = rgb[2] > 230;
				//boolean greenInRange = rgb[1] > 150;
				boolean blueInRange = rgb[0] < 50;
				
				if (redInRange & blueInRange)
				{
					count ++;
				}
			}
		}
		
		if (count > 5)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
