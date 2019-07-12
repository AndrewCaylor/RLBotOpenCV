import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class ScreenShotIO {

	public static void saveMat(Mat m, String c, String s)
	{
		
		BufferedImage image = Mat2Buff(m);
		try {
			ImageIO.write(image, "png", new File("C:\\Users\\drewh\\Desktop\\RLScreenshot\\newMat"+ c + "_" + s +".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// inputs a mat and saves it to RLScreenShot folder
	
	public BufferedImage getShot()
	{
		try {
			BufferedImage img = ImageIO.read(new File("C:\\Users\\drewh\\Desktop\\RLScreenshot\\new.png"));
			return img;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public void saveImg(BufferedImage image)
	{
		try {
			ImageIO.write(image, "png", new File("C:\\Users\\drewh\\Desktop\\RLScreenshot\\new.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static BufferedImage Mat2Buff(Mat matrix) {     
		try {
	    MatOfByte mob=new MatOfByte();
	    Imgcodecs.imencode(".png", matrix, mob);
	    byte ba[]=mob.toArray();

	    BufferedImage bi=ImageIO.read(new ByteArrayInputStream(ba));
	    return bi;
		} catch (Exception e) { return null;}
	}
	
	public void newShot(String name)
	{
		try
		{
			//BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(2560-1440,30,1440, 900 ));

			ImageIO.write(image, "png", new File("C:\\Users\\drewh\\Desktop\\RLScreenshot\\"+ name + ".png"));
		}
		catch (Exception e) {}
	}
	
	public static void fullShot(String name)
	{
		try
		{
			//BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(0,0,2560, 1080 ));

			ImageIO.write(image, "png", new File("C:\\Users\\drewh\\Desktop\\RLScreenshot\\"+ name + ".png"));
		}
		catch (Exception e) {}
	}
	
	public static void ballCam(String name)
	{
		try
		{
			//BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(1205, 855, 150,25 ));

			ImageIO.write(image, "png", new File("C:\\Users\\drewh\\Desktop\\RLScreenshot\\"+ name + ".png"));
		}
		catch (Exception e) {}
	}
	
	public Mat getSrcMat()
	{
		try {
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(2560-1440,30,1440, 900 ));
			return buff2Mat(image);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static Mat getSrcMatCar()
	{
		try {
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(2560 - 1440 + 500,30 + 700,500, 200));
			return buff2Mat(image);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public BufferedImage getSrcBuffBallCam()
	{
		try {
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(1205, 855, 150,25 ));
			return image;
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static Mat buff2Mat(BufferedImage in) {
        Mat out;
        byte[] data;
        int r, g, b;

        if (in.getType() == BufferedImage.TYPE_INT_RGB) {
            out = new Mat(in.getHeight(), in.getWidth(), CvType.CV_8UC3);
            data = new byte[in.getWidth() * in.getHeight() * (int) out.elemSize()];
            int[] dataBuff = in.getRGB(0, 0, in.getWidth(), in.getHeight(), null, 0, in.getWidth());
            for (int i = 0; i < dataBuff.length; i++) {
                data[i * 3] = (byte) ((dataBuff[i] >> 0) & 0xFF);
                data[i * 3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
                data[i * 3 + 2] = (byte) ((dataBuff[i] >> 16) & 0xFF);
            }
        } else {
            out = new Mat(in.getHeight(), in.getWidth(), CvType.CV_8UC1);
            data = new byte[in.getWidth() * in.getHeight() * (int) out.elemSize()];
            int[] dataBuff = in.getRGB(0, 0, in.getWidth(), in.getHeight(), null, 0, in.getWidth());
            for (int i = 0; i < dataBuff.length; i++) {
                r = (byte) ((dataBuff[i] >> 0) & 0xFF);
                g = (byte) ((dataBuff[i] >> 8) & 0xFF);
                b = (byte) ((dataBuff[i] >> 16) & 0xFF);
                data[i] = (byte) ((0.21 * r) + (0.71 * g) + (0.07 * b));
            }
        }
        out.put(0, 0, data);
        return out;
    }
	
}
