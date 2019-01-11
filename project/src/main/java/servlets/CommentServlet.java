package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import po.Album;
import po.Comment;
import po.User;
import service.CommentService;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet(urlPatterns="/comment")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		try {
		String comment_text= (String) request.getParameter("input_comment");
		User user = (User) request.getSession().getAttribute("userInfo");
		int location =Integer.parseInt(request.getParameter("location"));
		int albumId = ((ArrayList<Album>) request.getSession().getAttribute("albumArrayList")).get(location).getId();
		out.println(user+comment_text);
		Comment comment=new Comment(albumId,comment_text,user.getUserName());
		CommentService service = new CommentService();
		service.addComment(comment);
		request.getRequestDispatcher("/ImagesServlet?method=findall&pageNumber=0").forward(request, response);
		}catch (Exception e) {
			e.printStackTrace(out);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
