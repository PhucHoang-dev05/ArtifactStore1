//co sua
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.SanPham;
import model.TacGia;
import model.TheLoai;
public class SanPhamDAO implements DAOInterface<SanPham> {

	@Override
	public ArrayList<SanPham> selectAll() {
		ArrayList<SanPham> ketQua = new ArrayList<>();
		try {
			// Bước 1: tạo kết nối đến CSDL
			Connection con = JDBCUtil.getConnection();

			// Bước 2: tạo ra đối tượng statement
			String sql = "SELECT * FROM sanpham";
			PreparedStatement st = con.prepareStatement(sql);

			// Bước 3: thực thi câu lệnh SQL
			System.out.println(sql);
			ResultSet rs = st.executeQuery();

			// Bước 4:
			while (rs.next()) {
				String masanpham = rs.getString("masanpham");
				String tensanpham = rs.getString("tensanpham");
				String matacgia = rs.getString("matacgia");
				int namxuatban = rs.getInt("namxuatban");
				double gianhap = rs.getDouble("gianhap");
				double giagoc = rs.getDouble("giagoc");
				double giaban = rs.getDouble("giaban");
				int soluong = rs.getInt("soluong");
				String matheloai = rs.getString("matheloai");
				String ngonngu = rs.getString("ngonngu");
				String mota = rs.getString("mota");
				String tenAnh = rs.getString("tenanh");

				TacGia tacGia = (new TacGiaDAO().selectById(new TacGia(matacgia, "", null, "")));
				TheLoai theLoai = (new TheLoaiDAO().selectById(new TheLoai(matheloai, "")));

				SanPham sp = new SanPham(masanpham, tensanpham, tacGia, namxuatban, gianhap, giagoc, giaban, soluong,
						theLoai, ngonngu, mota, tenAnh);
				ketQua.add(sp);
			}

			// Bước 5:
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ketQua;
	}

	  public ArrayList<SanPham> getAll() {
		  ArrayList<SanPham> list = new ArrayList<>();
	        try {
	            Connection conn = JDBCUtil.getConnection();
	            String sql = "SELECT * FROM sanpham";
	            PreparedStatement st = conn.prepareStatement(sql);
	            ResultSet rs = st.executeQuery();

	            while (rs.next()) {
	            	String masanpham = rs.getString("masanpham");
					String tensanpham = rs.getString("tensanpham");
					String matacgia = rs.getString("matacgia");
					String matheloai = rs.getString("matheloai");
					double giagoc = rs.getDouble("giagoc");
					double giaban = rs.getDouble("giaban");
	            	int soluong = rs.getInt("soluong");
	            	String tenAnh = rs.getString("tenanh");

	            	TacGia tacGia = (new TacGiaDAO().selectById(new TacGia(matacgia, "", null, "")));
					TheLoai theLoai = (new TheLoaiDAO().selectById(new TheLoai(matheloai, "")));

					SanPham sp = new SanPham(masanpham, tensanpham, tacGia, theLoai, giagoc, giaban, soluong, tenAnh);

	                list.add(sp);
	            }

	            JDBCUtil.closeConnection(conn);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return list;
	    }



	@Override
	public int insert(SanPham t) {
		int ketQua = 0;
		try {
			// Bước 1: tạo kết nối đến CSDL
			Connection con = JDBCUtil.getConnection();

			// Bước 2: tạo ra đối tượng statement
			String sql = "INSERT INTO sanpham (masanpham,tensanpham, matacgia, namxuatban,"
					+ " gianhap, giagoc, giaban, soluong, matheloai, ngonngu, mota, tenanh) "
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getMaSanPham());
			st.setString(2, t.getTenSanPham());
			st.setString(3, t.getTacGia().getMaTacGia());
			st.setInt(4, t.getNamXuatBan());
			st.setDouble(5, t.getGiaNhap());
			st.setDouble(6, t.getGiaGoc());
			st.setDouble(7, t.getGiaBan());
			st.setInt(8, t.getSoLuong());
			st.setString(9, t.getTheLoai().getMaTheLoai());
			st.setString(10, t.getNgonNgu());
			st.setString(11, t.getMoTa());
			st.setString(12, t.getTenAnh());

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
	public int insertAll(ArrayList<SanPham> arr) {
		int dem = 0;
		for (SanPham SanPham : arr) {
			dem += this.insert(SanPham);
		}
		return dem;
	}

	@Override
	public int delete(SanPham t) {
		int ketQua = 0;
		try {
			// Bước 1: tạo kết nối đến CSDL
			Connection con = JDBCUtil.getConnection();

			// Bước 2: tạo ra đối tượng statement
			String sql = "DELETE from sanpham " + " WHERE masanpham=?";

			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getMaSanPham());

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
	public int deleteAll(ArrayList<SanPham> arr) {
		int dem = 0;
		for (SanPham SanPham : arr) {
			dem += this.delete(SanPham);
		}
		return dem;
	}

	@Override
	public int update(SanPham t) {
	    int ketQua = 0;
	    try {
	        Connection con = JDBCUtil.getConnection();

	        String sql = "UPDATE sanpham SET tensanpham=?, matacgia=?, namxuatban=?, gianhap=?, giagoc=?, giaban=?, soluong=?, matheloai=?, mota=? WHERE masanpham=?";
	        PreparedStatement st = con.prepareStatement(sql);

	        st.setString(1, t.getTenSanPham());
	        st.setString(2, t.getTacGia().getMaTacGia());
	        st.setInt(3, t.getNamXuatBan());
	        st.setDouble(4, t.getGiaNhap());
	        st.setDouble(5, t.getGiaGoc());
	        st.setDouble(6, t.getGiaBan());
	        st.setInt(7, t.getSoLuong());
	        st.setString(8, t.getTheLoai().getMaTheLoai());
	        st.setString(9, t.getMoTa());
	        st.setString(10, t.getMaSanPham()); // WHERE masanpham=?

	        ketQua = st.executeUpdate();

	        System.out.println("Bạn đã thực thi: " + sql);
	        System.out.println("Có " + ketQua + " dòng bị thay đổi!");

	        JDBCUtil.closeConnection(con);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return ketQua;
	}


	public ArrayList<SanPham> timKiem(String keyword) {
	    ArrayList<SanPham> ds = new ArrayList<>();
	    try {
	    	Connection con = JDBCUtil.getConnection();
	        String sql = "SELECT * FROM sanpham WHERE masanpham LIKE ? OR tensanpham LIKE ? OR matacgia LIKE ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        String kw = "%" + keyword + "%";
	        stmt.setString(1, kw);
	        stmt.setString(2, kw);
	        stmt.setString(3, kw);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            SanPham sp = new SanPham();
	            sp.setMaSanPham(rs.getString("masanpham"));
	            sp.setTenSanPham(rs.getString("tensanpham"));
	            sp.setGiaBan(rs.getDouble("giaban"));
	            sp.setSoLuong(rs.getInt("soluong"));
	            sp.setMoTa(rs.getString("mota"));
	            sp.setTheLoai(new TheLoaiDAO().selectById(rs.getString("matheloai")));
	            // Nếu cần lấy thông tin tác giả
	            sp.setTacGia(new TacGiaDAO().selectById(rs.getString("matacgia")));
	            ds.add(sp);
	        }
	        con.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return ds;
	}

	@Override
	public SanPham selectById(SanPham t) {

		SanPham ketQua = null;
		try {
			Connection con = JDBCUtil.getConnection();

			String sql = "SELECT * FROM sanpham WHERE masanpham=?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getMaSanPham());

			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String masanpham = rs.getString("masanpham");
				String tensanpham = rs.getString("tensanpham");
				String matacgia = rs.getString("matacgia");
				int namxuatban = rs.getInt("namxuatban");
				double gianhap = rs.getDouble("gianhap");
				double giagoc = rs.getDouble("giagoc");
				double giaban = rs.getDouble("giaban");
				int soluong = rs.getInt("soluong");
				String matheloai = rs.getString("matheloai");
				String ngonngu = rs.getString("ngonngu");
				String mota = rs.getString("mota");
				String tenAnh = rs.getString("tenanh");

				TacGia tacGia = (new TacGiaDAO().selectById(new TacGia(matacgia, "", null, "")));
				TheLoai theLoai = (new TheLoaiDAO().selectById(new TheLoai(matheloai, "")));

				ketQua = new SanPham(masanpham, tensanpham, tacGia, namxuatban, gianhap, giagoc, giaban, soluong,
						theLoai, ngonngu, mota, tenAnh);
				break;
			}

			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ketQua;
	}


	public SanPham selectById(String id) {

		SanPham ketQua = null;
		try {
			Connection con = JDBCUtil.getConnection();

			String sql = "SELECT * FROM sanpham WHERE masanpham=?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, id);

			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String masanpham = rs.getString("masanpham");
				String tensanpham = rs.getString("tensanpham");
				String matacgia = rs.getString("matacgia");
				int namxuatban = rs.getInt("namxuatban");
				double gianhap = rs.getDouble("gianhap");
				double giagoc = rs.getDouble("giagoc");
				double giaban = rs.getDouble("giaban");
				int soluong = rs.getInt("soluong");
				String matheloai = rs.getString("matheloai");
				String ngonngu = rs.getString("ngonngu");
				String mota = rs.getString("mota");
				String tenAnh = rs.getString("tenanh");

				TacGia tacGia = (new TacGiaDAO().selectById(new TacGia(matacgia, "", null, "")));
				TheLoai theLoai = (new TheLoaiDAO().selectById(new TheLoai(matheloai, "")));

				ketQua = new SanPham(masanpham, tensanpham, tacGia, namxuatban, gianhap, giagoc, giaban, soluong,
						theLoai, ngonngu, mota, tenAnh);
				break;
			}

			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ketQua;
	}

	public ArrayList<SanPham> getByMaTheLoai(String maTheLoai) {
	    ArrayList<SanPham> ds = new ArrayList<>();
	    try {
	        Connection con = JDBCUtil.getConnection();
	        String sql = "SELECT * FROM sanpham WHERE matheloai = ?";
	        PreparedStatement st = con.prepareStatement(sql);
	        st.setString(1, maTheLoai);

	        ResultSet rs = st.executeQuery();
	        while (rs.next()) {
	            String maSanPham = rs.getString("masanpham");
	            String tenSanPham = rs.getString("tensanpham");
	            String maTacGia = rs.getString("matacgia");
	            String maTL = rs.getString("matheloai");
	            double giaBan = rs.getDouble("giaban");
	            double giaGoc = rs.getDouble("giagoc");
	            int soLuong = rs.getInt("soluong");
	            String tenAnh = rs.getString("tenanh");

	            // Lấy tác giả và thể loại từ DAO (nếu bạn có các DAO tương ứng)
	            TacGiaDAO tacGiaDAO = new TacGiaDAO();
	            TacGia tacGia = tacGiaDAO.selectById(maTacGia);

	            TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
	            TheLoai theLoai = theLoaiDAO.selectById(maTL);

	            // Tạo đối tượng SanPham
	            SanPham sp = new SanPham(maSanPham, tenSanPham, tacGia, theLoai, giaBan, giaGoc, soLuong, tenAnh);
	            ds.add(sp);
	        }

	        JDBCUtil.closeConnection(con);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return ds;
	}

	public ArrayList<SanPham> getSanPhamDangGiamGia() {
	    ArrayList<SanPham> ds = new ArrayList<>();
	    try {
	        Connection con = JDBCUtil.getConnection();
	        // Lấy sản phẩm có giaban < giagoc
	        String sql = "SELECT * FROM sanpham WHERE giaban < giagoc";
	        PreparedStatement st = con.prepareStatement(sql);

	        ResultSet rs = st.executeQuery();
	        while (rs.next()) {

	            String maSanPham = rs.getString("masanpham");
	            String tenSanPham = rs.getString("tensanpham");
	            String maTacGia = rs.getString("matacgia");
	            String maTL = rs.getString("matheloai");
	            double giaBan = rs.getDouble("giaban");
	            double giaGoc = rs.getDouble("giagoc");
	            int soLuong = rs.getInt("soluong");
	            String tenAnh = rs.getString("tenanh");

	            TacGiaDAO tacGiaDAO = new TacGiaDAO();
	            TacGia tacGia = tacGiaDAO.selectById(maTacGia);

	            TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
	            TheLoai theLoai = theLoaiDAO.selectById(maTL);
	            SanPham sp = new SanPham(maSanPham, tenSanPham, tacGia, theLoai, giaGoc, giaBan, soLuong, tenAnh);


	            ds.add(sp);
	        }

	        JDBCUtil.closeConnection(con);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return ds;
	}

	public int updateSoLuong(int soLuongConLai, String maSanPham) {
	    int ketQua = 0;
	    try {
	        Connection con = JDBCUtil.getConnection();

	        String sql = "UPDATE sanpham SET soluong = ? WHERE masanpham = ?";

	        PreparedStatement st = con.prepareStatement(sql);
	        st.setInt(1, soLuongConLai);
	        st.setString(2, maSanPham);

	        ketQua = st.executeUpdate();

	        System.out.println("Đã cập nhật số lượng sản phẩm: " + maSanPham + " còn lại là " + soLuongConLai);
	        System.out.println("Có " + ketQua + " dòng bị thay đổi!");

	        JDBCUtil.closeConnection(con);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return ketQua;
	}

}