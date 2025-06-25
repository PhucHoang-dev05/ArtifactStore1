package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.TacGiaDAO;
import model.TacGia;

/**
 * Servlet implementation class TacGiaServlet
 */
@WebServlet("/TacGiaServlet")
public class TacGiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TacGiaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html;charset=UTF-8");


        // Lấy giá trị hành động từ form
        String hanhDong = request.getParameter("hanhDong");

        if ("them-tac-gia".equals(hanhDong)) {
            themTacGia(request, response);
        } else if ("xoa".equals(hanhDong)) {
	        xoaTacGia(request, response);
	    }
    else {
        // Nếu không có action hoặc action không hợp lệ
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	 private void themTacGia(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		   String maTacGia = request.getParameter("matacgia");
           String hoVaTen = request.getParameter("hovaten");
           String ngaySinhStr = request.getParameter("ngaysinh");
           String tieuSu = request.getParameter("tieusu");

           // Chuyển đổi ngày sinh từ String sang java.sql.Date
           Date ngaySinh = null;
           if (ngaySinhStr != null && !ngaySinhStr.isEmpty()) {
               ngaySinh = Date.valueOf(ngaySinhStr);
           }

           // Tạo đối tượng TacGia
           TacGia tg = new TacGia(maTacGia, hoVaTen, ngaySinh, tieuSu);

           // Gọi DAO để thêm vào DB
           TacGiaDAO dao = new TacGiaDAO();
           int result = dao.insert(tg);

           // Chuyển hướng hoặc hiện thông báo
	        if (result != 0) {
           	String baoLoi = URLEncoder.encode("Thêm thành công", "UTF-8");
		        	response.sendRedirect(request.getContextPath() +"/admin/tacgia.jsp?msg=them_thanh_cong&baoLoi=" + baoLoi);
		        	System.out.println("...");

	        } else {
           	String baoLoi = URLEncoder.encode("Thêm thất bại", "UTF-8");
		        	response.sendRedirect(request.getContextPath() +"/admin/tacgia.jsp?msg=them_that_bai&baoLoi=" + baoLoi);

	        }
	 }

	 private void xoaTacGia(HttpServletRequest request, HttpServletResponse response)
		        throws ServletException, IOException {

		    // Lấy mã tác giả từ request
		    String maTacGia = request.getParameter("id");

		    // Tạo đối tượng TacGia chỉ với mã (vì delete chỉ cần mã)
		    TacGia tg = new TacGia();
		    tg.setMaTacGia(maTacGia);

		    // Gọi DAO để xóa
		    TacGiaDAO dao = new TacGiaDAO();
		    int result = dao.delete(tg);

		    // Thông báo kết quả
		    String baoLoi;
		    if (result != 0) {
		        baoLoi = URLEncoder.encode("Xóa thành công", "UTF-8");
		        response.sendRedirect(request.getContextPath() + "/admin/tacgia.jsp?msg=xoa_thanh_cong&baoLoi=" + baoLoi);
		    } else {
		        baoLoi = URLEncoder.encode("Xóa thất bại", "UTF-8");
		        response.sendRedirect(request.getContextPath() + "/admin/tacgia.jsp?msg=xoa_that_bai&baoLoi=" + baoLoi);
		    }
		}


}
