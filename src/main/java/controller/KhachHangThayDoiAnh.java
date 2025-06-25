package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import database.KhachHangDAO;
import model.KhachHang;

/**
 * Servlet implementation class KhachHangThayDoiAnh
 */
@WebServlet("/khach-hang-thay-doi-anh")
public class KhachHangThayDoiAnh extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KhachHangThayDoiAnh() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Object obj = request.getSession().getAttribute("khachHang");
	    KhachHang khachHang = null;
	    String baoLoi = "";
	    boolean thanhCong = false;

	    if (obj != null) {
			khachHang = (KhachHang) obj;
			System.out.println("...");
		}

	    if (khachHang != null) {
	        try {
	        	String folder = getServletContext().getRealPath("/avatar");
	        	File uploadDir = new File(folder);
	        	if (!uploadDir.exists()) {
	        	    uploadDir.mkdirs();
	        	}
	            System.out.println("Folder: " + folder);

	            File file;
	            int maxFileSize = 5000 * 1024;
	            int maxMemSize = 5000 * 1024;

	            String contentType = request.getContentType();

	            if (contentType != null && contentType.indexOf("multipart/form-data") >= 0) {
	                DiskFileItemFactory factory = new DiskFileItemFactory();
	                factory.setSizeThreshold(maxMemSize);

	                ServletFileUpload upload = new ServletFileUpload(factory);
	                upload.setSizeMax(maxFileSize);

	                List<FileItem> files = upload.parseRequest(request);

	                for (FileItem fileItem : files) {
	                    if (!fileItem.isFormField()) {
	                        String fileName = System.currentTimeMillis() + "_" + fileItem.getName();
	                        String path = folder + File.separator + fileName;

	                        file = new File(path);
	                        fileItem.write(file);

	                        khachHang.setDuongDanAnh(fileName);
	                        KhachHangDAO khachHangDAO = new KhachHangDAO();
	                        khachHangDAO.updateImage(khachHang);
	                        khachHang = khachHangDAO.selectById(khachHang);

	                        // Cập nhật lại session
	                        request.getSession().setAttribute("khachHang", khachHang);

	                        thanhCong = true;
	                        baoLoi = "Cập nhật ảnh đại diện thành công!";
	                    }
	                }
	            } else {
	                baoLoi = "Dữ liệu gửi lên không đúng định dạng multipart/form-data!";
	            }

	        } catch (Exception e) {
	            baoLoi = "Lỗi khi cập nhật ảnh: " + e.getMessage();
	            e.printStackTrace();
	        }
	    } else {
	        baoLoi = "Bạn chưa đăng nhập!";
	    }

	    request.setAttribute("baoLoi", baoLoi);
	    request.setAttribute("thanhCong", thanhCong);
	    request.getRequestDispatcher("/khachhang/thaydoianh.jsp").forward(request, response);
	}


}