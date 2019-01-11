package utility;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class ImageUtil {
	public static final String JPG = "jpg";
	public static final String PNG = "png";
	public static final String BMP = "bmp";
	public static final String GIF = "gif";

	/**
	 *   Servlet  , JSP   img srcָ   Servlet,    
	 * 
	 * @param response
	 * @param path
	 * @param isResponseClose
	 */
	public static void showImage(HttpServletResponse response,OutputStream out, String path, boolean isResponseClose) {
		try {
//			ServletOutputStream outStream = response.getOutputStream();//
			FileInputStream fis = new FileInputStream(path); // 
			byte data[] = new byte[4096];
			while (fis.read(data) > 0) {
				out.write(data);
			}
			fis.close();
			response.setContentType("image/*"); //   
			out.write(data); //        
			if (isResponseClose) {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *   Servlet   , JSP   img srcָ   Servlet,   
	 * 
	 * @param response
	 * @param data
	 * @param isResponseClose
	 */
	public static void showImage(HttpServletResponse response, byte[] data, boolean isResponseClose) {
		try {
			ServletOutputStream outStream = response.getOutputStream();//    
			//       
			outStream.write(data);
			response.setContentType("image/*"); //  
			outStream.write(data); //        
			if (isResponseClose) {
				outStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *  Servlet , JSP img  Servlet  
	 * 
	 * 
	 * @param image
	 * @param imgType
	 * @param isResponseClose
	 */
	public static void showImage(OutputStream out, BufferedImage image, String imgType,
			boolean isResponseClose) {
		try {
			ImageIO.write(image, imgType, out);
		} catch (IOException e) {
			e.printStackTrace(new PrintStream(out));
		}
	}

}
