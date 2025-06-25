package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ChiTietDonHang;
import model.DonHang;
import model.SanPham;

public class ChiTietDonHangDAO implements DAOInterface<ChiTietDonHang> {

	@Override
	public ArrayList<ChiTietDonHang> selectAll() {
		ArrayList<ChiTietDonHang> ketQua = new ArrayList<>();

		try {
			// Bước 1: tạo kết nối đến CSDL
			Connection con = JDBCUtil.getConnection();

			// Bước 2: tạo ra đối tượng statement
			String sql = "SELECT * FROM chitietdonhang";
			PreparedStatement st = con.prepareStatement(sql);

			// Bước 3: thực thi câu lệnh SQL
			System.out.println(sql);
			ResultSet rs = st.executeQuery();

			// Bước 4:

			while (rs.next()) {
				String maChiTietDonHang = rs.getString("machitietdonhang");
				String donhang = rs.getString("madonhang");
				String sanpham = rs.getString("masanpham");
				double soluong = rs.getDouble("soluong");
				double giagoc = rs.getDouble("giagoc");
				double giamgia = rs.getDouble("giamgia");
				double giaban = rs.getDouble("giaban");
				double thuevat = rs.getDouble("thueVAT");
				double tongtien = rs.getDouble("tongtien");

				DonHang dh = new DonHangDAO().selectById(new DonHang(donhang, null, "", "", "", "",0, 0, 0, null, null));
				SanPham sp = new SanPhamDAO().selectById(new SanPham(sanpham, "", null, 0, 0, 0, 0, 0, null, "", "", ""));

				ChiTietDonHang ctdh = new ChiTietDonHang(maChiTietDonHang, dh, sp, soluong, giagoc, giamgia, giaban,
						thuevat, tongtien);
				ketQua.add(ctdh);
			}

			// Bước 5:
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ketQua;
	}

	public ArrayList<ChiTietDonHang> timKiem(String keyword) {
		ArrayList<ChiTietDonHang> ketQua = new ArrayList<>();
	    String sql = "SELECT ctdh.*, dh.*, sp.* " +
	                 "FROM chitietdonhang ctdh " +
	                 "JOIN donhang dh ON ctdh.maDonHang = dh.maDonHang " +
	                 "JOIN sanpham sp ON ctdh.maSanPham = sp.maSanPham " +
	                 "WHERE ctdh.machitietdonhang LIKE ? " +
	                 "   OR dh.madonhang LIKE ? " +
	                 "   OR sp.tensanpham LIKE ?";

	    try (Connection conn = JDBCUtil.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        String kw = "%" + keyword + "%";
	        ps.setString(1, kw);
	        ps.setString(2, kw);
	        ps.setString(3, kw);

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            // Tạo đối tượng DonHang
	            DonHang dh = new DonHang();
	            dh.setMaDonHang(rs.getString("madonhang"));
	            // Có thể set thêm các thông tin khác như khachHang, ngayDatHang nếu cần

	            // Tạo đối tượng SanPham
	            SanPham sp = new SanPham();
	            sp.setMaSanPham(rs.getString("masanpham"));
	            sp.setTenSanPham(rs.getString("tensanpham"));
	            // Có thể set thêm các thông tin khác nếu cần

	            // Tạo đối tượng ChiTietDonHang
	            ChiTietDonHang ctdh = new ChiTietDonHang();
	            ctdh.setMaChiTietDonHang(rs.getString("machitietdonhang"));
	            ctdh.setDonHang(dh);
	            ctdh.setSanPham(sp);
	            ctdh.setSoLuong(rs.getDouble("soluong"));
	            ctdh.setGiaGoc(rs.getDouble("giagoc"));
	            ctdh.setGiamGia(rs.getDouble("giamgia"));
	            ctdh.setGiaBan(rs.getDouble("giaban"));
	            ctdh.setThueVAT(rs.getDouble("thueVAT"));
	            ctdh.setTongTien(rs.getDouble("tongtien"));

	            ketQua.add(ctdh);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return ketQua;
	}




	
	@Override
	public ChiTietDonHang selectById(ChiTietDonHang t) {
		ChiTietDonHang ketQua = null;
		try {
			Connection con = JDBCUtil.getConnection();

			String sql = "SELECT * FROM chitietdonhang WHERE machitietdonhang=?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getMaChiTietDonHang());

			// Bước 3: thực thi câu lệnh SQL
			System.out.println(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String maChiTietDonHang = rs.getString("machitietdonhang");
				String donhang = rs.getString("madonhang");
				String sanpham = rs.getString("masanpham");
				double soluong = rs.getDouble("soluong");
				double giagoc = rs.getDouble("giagoc");
				double giamgia = rs.getDouble("giamgia");
				double giaban = rs.getDouble("giaban");
				double thuevat = rs.getDouble("thueVAT");
				double tongtien = rs.getDouble("tongtien");

				DonHang dh = new DonHangDAO().selectById(new DonHang(donhang, null, "", "", "", "",0, 0, 0, null, null));
				SanPham sp = new SanPhamDAO().selectById(new SanPham(sanpham, "", null, 0, 0, 0, 0, 0, null, "", "", ""));

				ketQua = new ChiTietDonHang(maChiTietDonHang, dh, sp, soluong, giagoc, giamgia, giaban, thuevat,
						tongtien);
				break;
			}
			// Bước 5:
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ketQua;
	}

	@Override
	public int insert(ChiTietDonHang t) {
		int ketQua = 0;
		try {
			// Bước 1: tạo kết nối đến CSDL
			Connection con = JDBCUtil.getConnection();

			// Bước 2: tạo ra đối tượng statement
			String sql = "INSERT INTO chitietdonhang (machitietdonhang, madonhang, masanpham, soluong, giagoc,giamgia,giaban,thueVAT,tongtien) "
					+ " VALUES (?,?,?,?,?,?,?,?,?)";

			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getMaChiTietDonHang());
			st.setString(2, t.getDonHang().getMaDonHang());
			st.setString(3, t.getSanPham().getMaSanPham());
			st.setDouble(4, t.getSoLuong());
			st.setDouble(5, t.getGiaGoc());
			st.setDouble(6, t.getGiamGia());
			st.setDouble(7, t.getGiaBan());
			st.setDouble(8, t.getThueVAT());
			st.setDouble(9, t.getTongTien());
			// Bước 3: thực thi câu lệnh SQL
			ketQua = st.executeUpdate();

			// Bước 4:
			System.out.println("Bạn đã thực thi: " + sql);
			System.out.println("Có " + ketQua + " dòng bị thay đổi!");

			// Bước 5:
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ketQua;
	}

	@Override
	public int insertAll(ArrayList<ChiTietDonHang> arr) {
		int dem = 0;
		for (ChiTietDonHang ChiTietDonHang : arr) {
			dem += this.insert(ChiTietDonHang);
		}
		return dem;
	}

	@Override
	public int delete(ChiTietDonHang t) {
		int ketQua = 0;
		try {
			// Bước 1: tạo kết nối đến CSDL
			Connection con = JDBCUtil.getConnection();

			// Bước 2: tạo ra đối tượng statement
			String sql = "DELETE from chitietdonhang " + " WHERE machitietdonhang=?";

			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getMaChiTietDonHang());

			// Bước 3: thực thi câu lệnh SQL
			System.out.println(sql);
			ketQua = st.executeUpdate();

			// Bước 4:
			System.out.println("Bạn đã thực thi: " + sql);
			System.out.println("Có " + ketQua + " dòng bị thay đổi!");

			// Bước 5:
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ketQua;
	}

	@Override
	public int deleteAll(ArrayList<ChiTietDonHang> arr) {
		int dem = 0;
		for (ChiTietDonHang ChiTietDonHang : arr) {
			dem += this.delete(ChiTietDonHang);
		}
		return dem;
	}

	@Override
	public int update(ChiTietDonHang t) {
		int ketQua = 0;
		try {
			// Bước 1: tạo kết nối đến CSDL
			Connection con = JDBCUtil.getConnection();

			// Bước 2: tạo ra đối tượng statement
			String sql = "UPDATE chitietdonhang SET madonhang=?, masanpham=?, soluong=?, giagoc=?, giamgia=?, giaban=?, thueVAT=?, tongtien=?"
					+ " WHERE machitietdonhang=?";

			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getDonHang().getMaDonHang());
			st.setString(2, t.getSanPham().getMaSanPham());
			st.setDouble(3, t.getSoLuong());
			st.setDouble(4, t.getGiaGoc());
			st.setDouble(5, t.getGiamGia());
			st.setDouble(6, t.getGiaBan());
			st.setDouble(7, t.getThueVAT());
			st.setDouble(8, t.getTongTien());
			st.setString(9, t.getMaChiTietDonHang());

			// Bước 3: thực thi câu lệnh SQL

			System.out.println(sql);
			ketQua = st.executeUpdate();

			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return ketQua;
	}

}