package controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.TheLoaiDAO;
import model.TheLoai;

/**
 * Servlet implementation class TheLoaiServlet
 */
@WebServlet("/TheLoaiServlet")
public class TheLoaiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TheLoaiServlet() {
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

        String hanhDong = request.getParameter("hanhDong");

        if ("them-the-loai".equals(hanhDong)) {
            themTheLoai(request, response);
        } else if ("xoa".equals(hanhDong)) {
            xoaTheLoai(request, response);
        } else {
            response.getWriter().append("Không có hành động phù hợp.");
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

	 private void themTheLoai(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String maTheLoai = request.getParameter("matheloai");
	        String tenTheLoai = request.getParameter("tentheloai");

	        TheLoai tl = new TheLoai(maTheLoai, tenTheLoai);
	        TheLoaiDAO dao = new TheLoaiDAO();
	        int result = dao.insert(tl);

	        String baoLoi;
	        if (result != 0) {
	            baoLoi = URLEncoder.encode("Thêm thành công", "UTF-8");
	            response.sendRedirect(request.getContextPath() + "/admin/theloai.jsp?msg=them_thanh_cong&baoLoi=" + baoLoi);
	            System.out.println("...");
	        } else {
	            baoLoi = URLEncoder.encode("Thêm thất bại", "UTF-8");
	            response.sendRedirect(request.getContextPath() + "/admin/theloai.jsp?msg=them_that_bai&baoLoi=" + baoLoi);
	        }
	    }

	    private void xoaTheLoai(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String id = request.getParameter("id");
	        TheLoai tl = new TheLoai();
	        tl.setMaTheLoai(id);

	        TheLoaiDAO dao = new TheLoaiDAO();
	        int ketQua = dao.delete(tl);

	        String baoLoi;
	        if (ketQua != 0) {
	            baoLoi = URLEncoder.encode("Xóa thành công", "UTF-8");
	            response.sendRedirect(request.getContextPath() + "/admin/theloai.jsp?msg=xoa_thanh_cong&baoLoi=" + baoLoi);
	        } else {
	            baoLoi = URLEncoder.encode("Xóa thất bại", "UTF-8");
	            response.sendRedirect(request.getContextPath() + "/admin/theloai.jsp?msg=xoa_that_bai&baoLoi=" + baoLoi);
	        }
	    }

}
