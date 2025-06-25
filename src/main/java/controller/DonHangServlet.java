package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ChiTietDonHangDAO;
import database.DonHangDAO;
import database.KhachHangDAO;
import database.SanPhamDAO;
import model.ChiTietDonHang;
import model.DonHang;
import model.KhachHang;
import model.SanPham;

/**
 * Servlet implementation class DonHangServlet
 */
@WebServlet("/DonHangServlet")
public class DonHangServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DonHangServlet() {
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

	        if ("them".equals(hanhDong)) {
	            themDonHang(request, response);
	        } /*else if ("sua".equals(hanhDong)) {
	            xoaDonHang(request, response);
	        } */
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

	  private void themDonHang(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 
		  String maKhachHang = request.getParameter("makhachhang");
		  String maSanPham = request.getParameter("masanpham");
		 
		    // Lấy thông tin khách hàng từ database
		    KhachHang khachHang = new KhachHangDAO().selectById(
		        new KhachHang(maKhachHang, "", "", "", "", "", "", "", null, "", "", false)
		    );

		    // Lấy địa chỉ mua và nhận từ KhachHang
		    String diaChiMua = khachHang.getDiaChiMuaHang();
		    String diaChiNhan = khachHang.getDiaChiNhanHang(); // giả sử bạn có tách 2 trường

		    String maDonHang = "DH" + System.currentTimeMillis();
	        String maChiTiet = "CT" + System.currentTimeMillis();
	        
	        KhachHang kh = new KhachHangDAO().selectById(new KhachHang(maKhachHang));
	        SanPham sp = new SanPhamDAO().selectById(new SanPham(maSanPham));
		    
		    String hinhThucThanhToan = request.getParameter("hinhthucthanhtoan");
		    
		    String trangThaiThanhToan;

		    if ("Chuyển khoản".equalsIgnoreCase(hinhThucThanhToan)) {
		        trangThaiThanhToan = "Đã thanh toán";
		    } else {
		        trangThaiThanhToan = "Chưa thanh toán";
		    }
		    
		    double giaGoc = sp.getGiaGoc();
	        double giaBan = sp.getGiaBan();
	        double giamGia = (sp.getGiaGoc() > sp.getGiaBan()) ? (sp.getGiaGoc() - sp.getGiaBan()) : 0;

	        int soLuong = Integer.parseInt(request.getParameter("soluong"));
	        double thue = 0.1 * giaBan * soLuong;
	        
	        double soTienDaThanhToan = 0;
	        if ("Chuyển khoản".equalsIgnoreCase(hinhThucThanhToan)) {
	            soTienDaThanhToan = giaBan * soLuong;
	        } else {
	            soTienDaThanhToan = 0;
	        }
	        double tongTien = giaBan * soLuong;
		   
		    double soTienConThieu = 0;

		    Date ngayDat = new Date(System.currentTimeMillis());
		    Date ngayGiao = new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000); // 3 ngày sau

		    
		    DonHang dh = new DonHang(maDonHang, kh, diaChiMua, diaChiNhan, hinhThucThanhToan, trangThaiThanhToan,
	                soLuong, soTienDaThanhToan, tongTien, ngayDat, ngayGiao);
		    
		    int kq1 = new DonHangDAO().insert(dh);

		    ChiTietDonHang ct = new ChiTietDonHang(maChiTiet, dh, sp, soLuong, giaGoc, giamGia, giaBan, thue, tongTien);
		    int kq2 = new ChiTietDonHangDAO().insert(ct);
		    
		    int soLuongConLai = sp.getSoLuong() - soLuong;

		    int kq3 = 0;
		    if (soLuongConLai >= 0) {
		        kq3 = new SanPhamDAO().updateSoLuong(soLuongConLai, maSanPham);
		    } else {
		        System.out.println("Không đủ hàng tồn kho để đặt mua!");
		        System.out.println("...");
		    	String baoLoi = URLEncoder.encode("Không đủ hàng tồn kho để đặt mua!", "UTF-8");
	        	response.sendRedirect("index.jsp?msg=loi_so_luong&baoLoi=" + baoLoi);
	        	 return; // Dừng xử lý tiếp
		       		    }
		    // Chuyển hướng hoặc hiện thông báo
	        if (kq1 > 0 && kq2 > 0 && kq3 > 0) {
            	String baoLoi = URLEncoder.encode("Thêm thành công", "UTF-8");
		        	response.sendRedirect("index.jsp?msg=them_thanh_cong&baoLoi=" + baoLoi);

	        } else {
            	String baoLoi = URLEncoder.encode("Thêm thất bại", "UTF-8");
		        	response.sendRedirect("index.jsp?msg=them_that_bai&baoLoi=" + baoLoi);

	        }
	  }

}
