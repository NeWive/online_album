package servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import po.Album;
import po.Photo;
import service.PhotoService;
import utility.ConfKit;
import utility.Global;

/**
 *  
 * 
 *  Servlet 3.0 
 * 
 *  http://www.cnblogs.com/xdp-gacl/p/4224960.html
 * 
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = "/upload")
@MultipartConfig //   ע   ʶ  Servlet֧        
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PhotoService photoService = new PhotoService();

	public FileUploadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(getServletContext().getRealPath("."));
	}


	/**
	 *  user upload file
	 * @throws IOException 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
			{
		request.setCharacterEncoding("utf-8");
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try {
			

		//TODO initialize configuration
		Global.conf_path=request.getServletContext().getRealPath(Global.conf);
//		Global.conf_path="./system.conf";
		 
		// ָ        
		 String savePath = null;
		 savePath=ConfKit.getProperty("imgs");
		 savePath=getServletContext().getRealPath(savePath);
		 if(savePath==null) {
			 doGet(request, response);
			 return;
		 }

		
		//             
		Collection<Part> parts = request.getParts();

		if (parts.size() == 1) { //           
			/**
			 * Servlet 3.0   multipart/form-data   POST     װ  Part   Part             
			 * Part part = parts[0];      Part    
			 * ͨ    file  (<input type="file" name="file">)   
			 */
			Part part = request.getPart("file");

			/**
			 * Servlet3 
			 *                   form-data; name="file"; filename="snmp4j--api.zip"
			 */
			String header = part.getHeader("content-disposition");
			//      
			String fileName = getFileName(header);
			String absolutePath = savePath + fileName;
			//        
			part.write(absolutePath);
			Photo photo = new Photo();
			photo.setPhotoPath(absolutePath);
			HttpSession session = request.getSession();
			int albumId = ((Album)session.getAttribute("album")).getId();
			photo.setAlbumId(albumId);
			photoService.addPhoto(photo);
            request.getRequestDispatcher("/ImagesServlet?method=findall&pageNumber=0").forward(request, response);
		} else { //            
			for (Part part : parts) { //              
				//         form-data; name="file"; filename="abc.jpg"
				String header = part.getHeader("content-disposition");
				//     
				String fileName = getFileName(header);
				//       ·  
				part.write(savePath + File.separator + fileName);

			}
		}

//		PrintWriter out = response.getWriter();
		out.println("          ");
		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace(out);
		} catch (ServletException e) {
			e.printStackTrace(out);
		}
		out.flush();
		out.close();
	}

	/**
	 *                        
	 * 
	 *            
	 *      Google       form-data; name="file"; filename="abc.jpg" 
	 * IE form-data; name="file"; filename="E:\abc.jpg"
	 * 
	 * @param header 
	 * @return 
	 */
	public String getFileName(String header) {
		String[] tempArr1 = header.split(";");
		String[] tempArr2 = tempArr1[2].split("=");
		//      
		String fileName = tempArr2[1].substring(tempArr2[1].lastIndexOf("\\") + 1).replaceAll("\"", "");
		return fileName;
	}
}