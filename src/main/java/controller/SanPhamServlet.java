package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.SanPhamDAO;
import database.TacGiaDAO;
import database.TheLoaiDAO;
import model.SanPham;
import model.TacGia;
import model.TheLoai;

/**
 * Servlet implementation class SanPhamServlet1
 */
@WebServlet("/SanPhamServlet")

public class SanPhamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SanPhamServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String hanhDong = request.getParameter("hanhDong");

		    if ("xoa".equals(hanhDong)) {
		        xoaSanPham(request, response);
		    } else if("timKiemSanPham".equals(hanhDong)) {
		    	timKiemSanPham(request, response);
		    } else {
		        response.getWriter().append("Served at: ").append(request.getContextPath());
		    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	  @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

		    request.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html;charset=UTF-8");


	        // Lấy giá trị hành động từ form
	        String hanhDong = request.getParameter("hanhDong");

	        if ("them-san-pham".equals(hanhDong)) {
	            themSanPham(request, response);
	        } else if ("sua".equals(hanhDong)) {
	            suaSanPham(request, response);
	        }
	    else {
	        // Nếu không có action hoặc action không hợp lệ
	        response.getWriter().append("Served at: ").append(request.getContextPath());
	    }
	  }

	    private void themSanPham(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        // Lấy dữ liệu từ form
	        String maSanPham = request.getParameter("masanpham");
	        String tenSanPham = request.getParameter("tensanpham");
	        String maTacGia = request.getParameter("matacgia");
	        String maTheLoai = request.getParameter("matheloai"); // Cần sửa name ở JSP thành "matheloai"
	        double giaBan = Double.parseDouble(request.getParameter("giaban"));
	        int soLuong = Integer.parseInt(request.getParameter("soluong"));
	        String moTa = request.getParameter("mota");
	        String tenAnh = request.getParameter("tenanh");
	        int namXuatBan = Integer.parseInt(request.getParameter("namxuatban"));
	        double giaNhap = Double.parseDouble(request.getParameter("gianhap"));
	        double giaGoc = Double.parseDouble(request.getParameter("giagoc"));

	        TacGiaDAO tgDAO = new TacGiaDAO();
	        TacGia tacgia = tgDAO.selectById(maTacGia);

	        TheLoaiDAO tlDAO = new TheLoaiDAO();
	        TheLoai theloai = tlDAO.selectById(maTheLoai);

	        // Tạo đối tượng sản phẩm
	        SanPham sp = new SanPham(maSanPham, tenSanPham, tacgia,namXuatBan,giaNhap,giaGoc, giaBan,soLuong, theloai, moTa, tenAnh);

	        // Gọi DAO để thêm vào database
	        SanPhamDAO spDAO = new SanPhamDAO();
	        int thanhCong = spDAO.insert(sp);



	        // Chuyển hướng hoặc hiện thông báo
	        if (thanhCong != 0) {
            	String baoLoi = URLEncoder.encode("Thêm thành công", "UTF-8");
		        	response.sendRedirect("admin.jsp?msg=them_thanh_cong&baoLoi=" + baoLoi);

	        } else {
            	String baoLoi = URLEncoder.encode("Thêm thất bại", "UTF-8");
		        	response.sendRedirect("admin.jsp?msg=them_that_bai&baoLoi=" + baoLoi);

	        }


	    }


	    private void xoaSanPham(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String maSanPham = request.getParameter("id"); // Lấy id từ URL

	        if (maSanPham != null && !maSanPham.isEmpty()) {
	            SanPhamDAO spDAO = new SanPhamDAO();
	            SanPham sanPham = spDAO.selectById(maSanPham);
	            int ketQua = spDAO.delete(sanPham);

	            if (ketQua != 0) {
	            	String baoLoi = URLEncoder.encode("Xóa thành công", "UTF-8");
		        	response.sendRedirect("admin.jsp?msg=xoa_thanh_cong&baoLoi=" + baoLoi);

	            } else {
	            	String baoLoi = URLEncoder.encode("Xóa thất bại", "UTF-8");
		        	response.sendRedirect("admin.jsp?msg=xoa_that_bai&baoLoi=" + baoLoi);
	            }
	        } else {
	            response.sendRedirect("admin.jsp?msg=xoa_that_bai_id_trong");
	        }


	    }

	    protected void suaSanPham(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        request.setCharacterEncoding("UTF-8");

	        String maSanPham = request.getParameter("masanpham");
	        String tenSanPham = request.getParameter("tensanpham");
	        String maTacGia = request.getParameter("matacgia");
	        String maTheLoai = request.getParameter("matheloai");
	        double giaNhap = Double.parseDouble(request.getParameter("gianhap"));
	        double giaGoc = Double.parseDouble(request.getParameter("giagoc"));
	        double giaBan = Double.parseDouble(request.getParameter("giaban"));
	        int soLuong = Integer.parseInt(request.getParameter("soluong"));
	        String moTa = request.getParameter("mota");

	        // Tạo đối tượng
	        TacGiaDAO tgDAO = new TacGiaDAO();
	        TacGia tacgia = tgDAO.selectById(maTacGia);

	        TheLoaiDAO tlDAO = new TheLoaiDAO();
	        TheLoai theloai = tlDAO.selectById(maTheLoai);

	        SanPham sp = new SanPham(maSanPham, tenSanPham, tacgia, theloai,giaNhap,giaGoc, giaBan, soLuong, moTa);
	        SanPhamDAO spDAO = new SanPhamDAO();
	        int ketQua = spDAO.update(sp);

	        if (ketQua != 0) {
	            String baoLoi = URLEncoder.encode("Sửa thành công", "UTF-8");
	            response.sendRedirect("admin.jsp?msg=sua_thanh_cong&baoLoi=" + baoLoi);
	        } else {
	            String baoLoi = URLEncoder.encode("Sửa thất bại", "UTF-8");
	            response.sendRedirect("admin.jsp?msg=sua_that_bai&baoLoi=" + baoLoi);
	        }
	    }


	    private void timKiemSanPham(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	    String keyword = request.getParameter("keyword");
        SanPhamDAO dao = new SanPhamDAO();

        ArrayList<SanPham> dsTimKiem;
        if (keyword != null && !keyword.trim().isEmpty()) {
            dsTimKiem = dao.timKiem(keyword);
        } else {
            dsTimKiem = dao.selectAll(); // Trả về toàn bộ nếu không có từ khóa
        }

        request.setAttribute("dsSanPham", dsTimKiem);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
        dispatcher.forward(request, response);
	    }

}
