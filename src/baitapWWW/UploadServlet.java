package baitapWWW;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadSerlet
 */
@WebServlet("/UploadServlet.html")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private File file;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.getRequestDispatcher("fileUpload.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean isMultipart;
		String filePath;
		int maxFileSize = 50*1024;
		int maxMemSize = 5*1024;
		filePath = getServletContext().getInitParameter("file_upload");
		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if(!isMultipart) {
			out.print("file not uploaded");
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(maxFileSize);
		factory.setRepository(new File("D:\\tempServlet"));
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxFileSize);
		try {
			List fileItems = upload.parseRequest(request);
			Iterator i = fileItems.iterator();
			while(i.hasNext()){
				FileItem fi = (FileItem) i.next();
				if(!fi.isFormField()) {
					String fieldName =  fi.getFieldName();
					String fileName = fi.getName();
					String contentType = fi.getContentType();
					boolean isMemory = fi.isInMemory();
					long sizeInBytes = fi.getSize();
					if(fileName.lastIndexOf("\\")>=0) {
						  file = new File("D:\\tempServlet"+"\\"+fieldName.substring(fieldName.lastIndexOf("\\")));
						  System.out.println(filePath);
					}else {
							file = new File("D:\\tempServlet"+"\\"+fileName.substring(fileName.lastIndexOf("\\")+1));
							 System.out.println(filePath);
					}
					fi.write(file);
					out.println("File Uploaded : "+fileName);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			out.println(e);
		}
	}

}
