	package model;
	
	import java.sql.Date;
	
	public class DonHang {
		private String maDonHang;
		private KhachHang khachHang;
		private String diaChiMuaHang;
		private String diaChiNhanHang;
		private int soLuong;
		private String hinhThucThanhToan;
		private String trangThaiThanhToan;
		private double soTienDaThanhToan;
		private double soTienConThieu;
		private Date ngayDatHang;
		private Date ngayGiaoHang;
	
		public DonHang() {
		}
	
		public DonHang(String maDonHang, KhachHang khachHang, String diaChiMuaHang, String diaChiNhanHang,
				String hinhThucThanhToan, String trangThaiThanhToan, int soLuong, double soTienDaThanhToan, double soTienConThieu,
				Date ngayDatHang, Date ngayGiaoHang) {
			this.maDonHang = maDonHang;
			this.khachHang = khachHang;
			this.diaChiMuaHang = diaChiMuaHang;
			this.diaChiNhanHang = diaChiNhanHang;
			this.hinhThucThanhToan = hinhThucThanhToan;
			this.trangThaiThanhToan = trangThaiThanhToan;
			this.soTienDaThanhToan = soTienDaThanhToan;
			this.soLuong = soLuong;
			this.soTienConThieu = soTienConThieu;
			this.ngayDatHang = ngayDatHang;
			this.ngayGiaoHang = ngayGiaoHang;
		}
	
	
	
		public int getSoLuong() {
			return soLuong;
		}
	
		public void setSoLuong(int soLuong) {
			this.soLuong = soLuong;
		}
	
		public String getMaDonHang() {
			return maDonHang;
		}
	
		public void setMaDonHang(String maDonHang) {
			this.maDonHang = maDonHang;
		}
	
		public KhachHang getKhachHang() {
			return khachHang;
		}
	
		public void setKhachHang(KhachHang khachHang) {
			this.khachHang = khachHang;
		}
	
		public String getDiaChiMuaHang() {
			return diaChiMuaHang;
		}
	
		public void setDiaChiMuaHang(String diaChiMuaHang) {
			this.diaChiMuaHang = diaChiMuaHang;
		}
	
		public String getDiaChiNhanHang() {
			return diaChiNhanHang;
		}
	
		public void setDiaChiNhanHang(String diaChiNhanHang) {
			this.diaChiNhanHang = diaChiNhanHang;
		}
	
	
	
		public String getHinhThucThanhToan() {
			return hinhThucThanhToan;
		}
	
		public void setHinhThucThanhToan(String hinhThucThanhToan) {
			this.hinhThucThanhToan = hinhThucThanhToan;
		}
	
		public String getTrangThaiThanhToan() {
			return trangThaiThanhToan;
		}
	
		public void setTrangThaiThanhToan(String trangThaiThanhToan) {
			this.trangThaiThanhToan = trangThaiThanhToan;
		}
	
		public double getSoTienDaThanhToan() {
			return soTienDaThanhToan;
		}
	
		public void setSoTienDaThanhToan(double soTienDaThanhToan) {
			this.soTienDaThanhToan = soTienDaThanhToan;
		}
	
		public double getSoTienConThieu() {
			return soTienConThieu;
		}
	
		public void setSoTienConThieu(double soTienConThieu) {
			this.soTienConThieu = soTienConThieu;
		}
	
		public Date getNgayDatHang() {
			return ngayDatHang;
		}
	
		public void setNgayDatHang(Date ngayDatHang) {
			this.ngayDatHang = ngayDatHang;
		}
	
		public Date getNgayGiaoHang() {
			return ngayGiaoHang;
		}
	
		public void setNgayGiaoHang(Date ngayGiaoHang) {
			this.ngayGiaoHang = ngayGiaoHang;
		}
	
	
	}