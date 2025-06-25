
	package database;

	import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DonHang;
import model.KhachHang;

	public class DonHangDAO implements DAOInterface<DonHang> {

		@Override
		    public ArrayList<DonHang> selectAll() {
		        ArrayList<DonHang> ketQua = new ArrayList<>();
		        Connection con = JDBCUtil.getConnection();
		        String sql = "SELECT * FROM donhang ORDER BY ngaydathang DESC";
		        try {
		            PreparedStatement st = con.prepareStatement(sql);
		            ResultSet rs = st.executeQuery();
		            while (rs.next()) {
		                String maDH = rs.getString(1);
		                String maKH = rs.getString(2);
		                String diachimuahang = rs.getString(3);
		                String diaChiNhanHang = rs.getString(4);
		                String hinhThucThanhToan = rs.getString(5);
		                String trangThai = rs.getString(6);
		                int soLuong = rs.getInt(7);
		                double soTienDaThanhToan = rs.getDouble(8);
		                double soTienConThieu = rs.getDouble(9);
		                Date ngayDatHang = rs.getDate(10);
		                Date ngayGiaoHang = rs.getDate(11);

		                // Sửa phần này
		                KhachHang khachHang = new KhachHangDAO().selectById(new KhachHang(maKH, "", "", "", "", "", "", "", null, "", "", false));
		                DonHang dh = new DonHang(maDH, khachHang, diachimuahang, diaChiNhanHang,hinhThucThanhToan, trangThai,
		                		soLuong, soTienDaThanhToan, soTienConThieu, ngayDatHang, ngayGiaoHang);

		                ketQua.add(dh);
		            }
		            con.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return ketQua;
		    }
		

		@Override
		public DonHang selectById(DonHang t) {
			DonHang ketQua = null;
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT * FROM donhang WHERE madonhang = ?";
			try {
				PreparedStatement st = con.prepareStatement(sql);
				st.setString(1, t.getMaDonHang());
				ResultSet rs = st.executeQuery();
				while (rs.next()) {
					System.out.println("Hello World");
					String maDH = rs.getString(1);
					String maKH = rs.getString(2);
					String diachimuahang = rs.getString(3);
					String diaChiNhanHang = rs.getString(4);
					String hinhThucThanhToan = rs.getString(5);
					String trangThai = rs.getString(6);
					  int soLuong = rs.getInt(7);
					double soTienDaThanhToan = rs.getDouble(8);
					double soTienConThieu = rs.getDouble(9);
					Date ngayDatHang = rs.getDate(10);
					Date ngayGiaoHang = rs.getDate(11);

					KhachHang khachHang = new KhachHangDAO()
							.selectById(new KhachHang(maKH, "", "", "", "", "", "", "", null, "", "", false));
					DonHang dh = new DonHang(maDH, khachHang, diachimuahang, diaChiNhanHang, hinhThucThanhToan, trangThai,
							soLuong, soTienDaThanhToan, soTienConThieu, ngayDatHang, ngayGiaoHang);

					ketQua = dh;
				}
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ketQua;
		}

		public DonHang selectById(String maDonHang) {
		    DonHang ketQua = null;

		    try {
		        Connection con = JDBCUtil.getConnection();
		        String sql = "SELECT * FROM donhang WHERE madonhang = ?";
		        PreparedStatement st = con.prepareStatement(sql);
		        st.setString(1, maDonHang);

		        ResultSet rs = st.executeQuery();
		        if (rs.next()) {
		            String maKH = rs.getString("makhachhang");
		            String diaChiMuaHang = rs.getString("diachimuahang");
		            String diaChiNhanHang = rs.getString("diachinhanhang");
		            String hinhThucThanhToan = rs.getString("hinhthucthanhtoan");
		            String trangThai = rs.getString("trangthai");
		            int soLuong = rs.getInt("soluong");
		            double soTienDaThanhToan = rs.getDouble("sotiendathanhtoan");
		            double soTienConThieu = rs.getDouble("sotienconthieu");
		            Date ngayDatHang = rs.getDate("ngaydathang");
		            Date ngayGiaoHang = rs.getDate("ngaygiaohang");

		            KhachHang khachHang = new KhachHangDAO().selectById(maKH);

		            ketQua = new DonHang(maDonHang, khachHang, diaChiMuaHang, diaChiNhanHang,
		                    hinhThucThanhToan, trangThai, soLuong,
		                    soTienDaThanhToan, soTienConThieu, ngayDatHang, ngayGiaoHang);
		        }

		        JDBCUtil.closeConnection(con);
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return ketQua;
		}


	    @Override
	    public int insert(DonHang t) {
	        int kq = 0;
	        Connection con = JDBCUtil.getConnection();
	        String sql = "INSERT INTO donhang(madonhang, makhachhang, diachimuahang, diachinhanhang,hinhthucthanhtoan, trangthaithanhtoan,soluong, sotiendathanhtoan, sotienconthieu, ngaydathang, ngaygiaohang)"
	                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        try {
	            PreparedStatement st = con.prepareStatement(sql);
	            st.setString(1, t.getMaDonHang());
	            st.setString(2, t.getKhachHang().getMaKhachHang());
	            st.setString(3, t.getDiaChiMuaHang());
	            st.setString(4, t.getDiaChiNhanHang());
	            st.setString(5, t.getHinhThucThanhToan());
	            st.setString(6, t.getTrangThaiThanhToan());
	            st.setInt(7, t.getSoLuong());
	            st.setDouble(8, t.getSoTienDaThanhToan());
	            st.setDouble(9, t.getSoTienConThieu());
	            st.setDate(10, t.getNgayDatHang());
	            st.setDate(11, t.getNgayGiaoHang());

	            kq = st.executeUpdate();
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return kq;
	    }

		@Override
		public int insertAll(ArrayList<DonHang> arr) {
			int kq = 0;
			for (DonHang donHang : arr) {
				kq += this.insert(donHang);
			}
			return kq;
		}

		@Override
		public int delete(DonHang t) {
			int kq = 0;
			Connection con = JDBCUtil.getConnection();
			String sql = "DELETE FROM donhang WHERE madonhang = ?";
			try {
				PreparedStatement st = con.prepareStatement(sql);
				st.setString(1, t.getMaDonHang());
				kq = st.executeUpdate();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return kq;
		}

		@Override
		public int deleteAll(ArrayList<DonHang> arr) {
			int kq = 0;
			for (DonHang t : arr) {
				kq += this.delete(t);
			}
			return kq;
		}

		@Override
		public int update(DonHang t) {
			int kq = 0;
			Connection con = JDBCUtil.getConnection();

			String sql = "UPDATE donhang" + " SET " + "makhachhang=?" + ", diachimuahang=?" + ",diachinhanhang=?"
					+ ",hinhthucthanhtoan=?" + ",trangthaithanhtoan=?" + ",soluong=?"  + ",sotiendathanhtoan=?" + ",sotienconthieu=?" + ",ngaydathang=?"
					+ ",ngaygiaohang=?" + " WHERE madonhang=?";
			try {
				PreparedStatement st = con.prepareStatement(sql);
				st.setString(1, t.getKhachHang().getMaKhachHang());
				st.setString(2, t.getDiaChiMuaHang());
				st.setString(3, t.getDiaChiNhanHang());
				st.setString(4, t.getHinhThucThanhToan());
				st.setString(5, t.getTrangThaiThanhToan());
				st.setInt(6, t.getSoLuong());
				st.setDouble(7, t.getSoTienDaThanhToan());
				st.setDouble(8, t.getSoTienConThieu());
				st.setDate(9, t.getNgayDatHang());
				st.setDate(10, t.getNgayGiaoHang());
				st.setString(11, t.getMaDonHang());

				kq = st.executeUpdate();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return kq;
		}

	}