package imageprocess;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class QR_synthesis{
	
	public static void Synthesis(String img1, String img2) {	
	    try {
	        BufferedImage big = ImageIO.read(new File(img1));
	        BufferedImage small = ImageIO.read(new File(img2));
	       // BufferedImage small = code;

	        int x = (big.getWidth() - small.getWidth());
	        int y = (big.getHeight() - small.getHeight());
	        //int y = (big.getHeight() - small.getHeight() - 100);
	        
	        if (x<=0 || y<=0) {
	         int height=big.getHeight(null);
	         int width =big.getWidth(null);
	         BufferedImage newbig = new BufferedImage(width* 2, height * 2, BufferedImage.TYPE_INT_RGB);
	         newbig.getGraphics().drawImage(big, 0, 0,width * 2, height * 2, null);
	         big = newbig;
	         }
	        
	        Graphics2D g = big.createGraphics();
	        g.drawImage(small, 0, 0, small.getWidth(), small.getHeight(), null);
	        g.dispose();
	        ImageIO.write(big, "jpg", new File("D:\\Imagecopyright-blockchain\\combineIma.jpg"));
	    } 
	    
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
