package imageprocess;

import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class PHASH {
public static class ImagePHash {
	public int size = 32;
	public int smallerSize = 8;
	public ImagePHash() {initCoefficients();}
	public ImagePHash(int size, int smallerSize) {
		this.size = size;
		this.smallerSize = smallerSize;
		initCoefficients();
	}
	public int distance(String s1, String s2) {
		int counter = 0;
		for (int k = 0; k < s1.length();k++) {
			if(s1.charAt(k) != s2.charAt(k)) {counter++;}
		}
		return counter;
	}

	public String getHash(InputStream is) throws Exception {
		BufferedImage sourceimage = ImageIO.read(is);
		
		sourceimage = resize(sourceimage, size, size);

		sourceimage = grayscale(sourceimage);
		double[][] vals = new double[size][size];
		for (int x = 0; x < sourceimage.getWidth(); x++) {
			for (int y = 0; y < sourceimage.getHeight(); y++) {
				vals[x][y] = getBlue(sourceimage, x, y);
			}
		}

		long start = System.currentTimeMillis();
		double[][] dctVals = applyDCT(vals);
		System.out.println("DCT_COST_TIME: " + (System.currentTimeMillis() - start));

		double total = 0;
		for (int x = 0; x <= smallerSize; x++) {
			for (int y = 0; y <= smallerSize; y++) {
				total += dctVals[x][y];
			}
		}
		total -= dctVals[0][0];
		double avg = total / (double) ((smallerSize * smallerSize) - 1);

		String hash = "";
		for (int x = 0; x < smallerSize; x++) {
			for (int y = 0; y < smallerSize; y++) {
					hash += (dctVals[x][y] > avg?"1":"0");
			}
		}
		System.out.println(hash);
		return hash;
}
	
public BufferedImage resize(BufferedImage image, int width, int height) {
	BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(image, 0, 0, width, height, null);
	g.dispose();
	return resizedImage;
}

public ColorConvertOp colorConvert = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);

public BufferedImage grayscale(BufferedImage img) {
    colorConvert.filter(img, img);
    return img;
}

public int getBlue(BufferedImage img, int x, int y) { return (img.getRGB(x, y)) & 0xff; }

public double[] c;
public void initCoefficients() {
    c = new double[size];
    for (int i=1;i<size;i++) {
        c[i]=1;
    }
    c[0]=1/Math.sqrt(2.0);
}

private double[][] applyDCT(double[][] f) {
    int N = size;
    double[][] F = new double[N][N];
    for (int u=0;u<N;u++) {
        for (int v=0;v<N;v++) {
            double sum = 0.0;
            for (int i=0;i<N;i++) {
                for (int j=0;j<N;j++) {
                    sum+=Math.cos(((2*i+1)/(2.0*N))*u*Math.PI)*Math.cos(((2*j+1)/(2.0*N))*v*Math.PI)*(f[i][j]);
                }
            }
            sum*=((c[u]*c[v])/4.0);
            F[u][v] = sum;
        }
    }
    return F;
}

public boolean imgChk(String img1, String img2, int tv){
    ImagePHash p = new ImagePHash();
    String image1;
    String image2;
    try {
        image1 = p.getHash(new FileInputStream(new File(img1)));
        image2 = p.getHash(new FileInputStream(new File(img2)));
        int dt = p.distance(image1, image2);
        System.out.println("["+img1 + "] : [" + img2 + "] Score is " + dt);
        if (dt <= tv)
            return true;
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
	}
public static void main(String[] args) throws FileNotFoundException, Exception {
	int width = 100;
	int height = 100;
	int sum = 0;
	boolean a = true;
	String format = "jpg";
	HashMap hints = new HashMap();
	hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
	hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
	hints.put(EncodeHintType.MARGIN, 2);
	ImagePHash p = new ImagePHash();
	String imagePath = "D:\\Imagecopyright-blockchain";//工程目录路径
	File file = new File(imagePath+"\\image");
	String[] filelist = file.list();
//将目标图片与制定文件夹中的图片一一循环比对	
	for (int i = 0; i < filelist.length; i++) {
		System.out.print("\n");
		System.out.println("Compare to image"+"["+i+"]"+":");
		a = p.imgChk(imagePath+"\\Stephen-Flower.jpg", imagePath+"\\image\\"+filelist[i], 10);
		if(!a) {sum = sum +1;System.out.println("These two images are not similar.");}
		else {System.out.println("These two images are similar.");}
}
//当目标图片与所有图片都不相似，则输出其二维码，同时生成有二维码的图片，计算哈希值，写入txt文件
	if(sum==filelist.length) {	
	System.out.print("\n");
	System.out.println("Congratulations! The image passes the similarity test！Generating the QRCode for the image.........");
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode("Stephen，Flower", BarcodeFormat.QR_CODE, width, height, hints);
			Path outputfile = new File(imagePath+"\\QRcode.jpg").toPath();//生成该图片的水印二维码
			MatrixToImageWriter.writeToPath(bitMatrix, format, outputfile);
			} 
			catch (Exception e) {e.printStackTrace(); }
		}
	System.out.println("Successfully generated!");
	System.out.println("Generating the combined image........");
	QR_synthesis.Synthesis(imagePath+"\\Stephen-Flower.jpg",imagePath+"\\QRcode.jpg");//生成结合图片
	System.out.println("Successfully generated! The combined Hash is:");
	String hash = p.getHash(new FileInputStream(imagePath+"\\combineIma.jpg"));
	try {
        File writeName = new File("Hash.txt"); //哈希值写入txt文件
        writeName.createNewFile(); 
        try (FileWriter writer = new FileWriter(writeName);
             BufferedWriter out = new BufferedWriter(writer)
        ) {
            out.write(hash); 
            out.flush();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
}


