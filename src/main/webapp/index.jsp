<%@page import="model.TheLoai"%>
<%@page import="database.TheLoaiDAO"%>
<%@page import="model.KhachHang"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="java.util.List" %>
<%@ page import="model.SanPham" %>
<%@ page import="database.SanPhamDAO" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang chủ</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
	integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.min.js"
	integrity="sha384-7VPbUDkoPSGFnVtYi0QogXtr74QeVeeIs99Qfg5YCF+TidwNdjvaKZX19NZ/e6oz"
	crossorigin="anonymous"></script>
</head>
<body>


	<!-- header -->
	<%@include file ="header.jsp"%>


	<!-- Page content -->
	<div class="container mt-4">
		<div class="row">

<%
    SanPhamDAO dao = new SanPhamDAO();
    List<SanPham> ds ;
        
    TheLoaiDAO tlDao = new TheLoaiDAO();
    List<TheLoai> dsTheLoai = tlDao.selectAll();
    String maTheLoai = request.getParameter("matheloai");
    String giamGiaParam = request.getParameter("giamgia");
    
    if (giamGiaParam != null && giamGiaParam.equals("true")) {
        ds = dao.getSanPhamDangGiamGia(); // dùng hàm mới bên trên
    } else if (maTheLoai != null && !maTheLoai.isEmpty()) {
        ds = dao.getByMaTheLoai(maTheLoai);
    } else {
        ds = dao.getAll();
    }
%>

<div class="container mt-4">
<%
    String msg = request.getParameter("msg");
    String baoLoi = request.getParameter("baoLoi");
    if (msg != null && baoLoi != null) {
        String alertType = "info"; // Mặc định
        switch (msg) {
            case "them_thanh_cong":
                alertType = "success";
                break;
            case "loi":
                alertType = "danger";
                break;
            case "canhbao":
                alertType = "warning";
                break;
        }
%>
    <div class="alert alert-<%= alertType %> alert-dismissible fade show mt-3" role="alert">
        <%= baoLoi %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
<%
    }
%>


    <div class="row">
        <!-- Menu Left -->
        <div class="col-lg-3">
        
        <div class="card shadow-sm mb-4">
    <div class="card-header bg-primary text-white">
        <h5 class="mb-0">Thể Loại</h5>
    </div>
    <div class="list-group list-group-flush">
        <!-- Hiển thị tất cả -->
        <a href="index.jsp" class="list-group-item list-group-item-action">
            Tất cả
        </a>

        <!-- Danh sách các thể loại từ CSDL -->
        <%
            for (TheLoai tl : dsTheLoai) {
        %>
            <a href="index.jsp?matheloai=<%=tl.getMaTheLoai()%>" class="list-group-item list-group-item-action">
                <%= tl.getTenTheLoai() %>
            </a>
        <% } %>
    </div>
</div>

        </div>

        <!-- Sản phẩm -->
        <div class="col-lg-9">
            <div class="row">
                <% for (SanPham sp : ds) { %>
                   <div class="col-lg-4 col-md-6 mb-4 d-flex align-items-stretch">
    <div class="card shadow rounded-4 w-100">
        <a href="#">
            <img class="card-img-top" src="images/sanpham/<%=sp.getTenAnh()%>" alt="" style="height: 270px; object-fit: cover;">
        </a>
        <div class="card-body d-flex flex-column">
            <h5 class="card-title"><%=sp.getTenSanPham()%></h5>
            <p class="text-secondary mb-1">Tác giả: <%= sp.getTacGia().getHoVaTen()%></p>
            <p class="text-secondary mb-1">Thể loại: <%= sp.getTheLoai().getTenTheLoai() %></p>
             <p class="text-secondary mb-2">Số lượng: <%= sp.getSoLuong() %></p>
          
            <% if (sp.getGiaBan() < sp.getGiaGoc()) { %>
                <h5 class="text-success fw-bold">Giá bán: <%=sp.getGiaBan()%> đ</h5>
                <h6 class="text-muted"><del>Giá gốc: <%=sp.getGiaGoc()%> đ</del></h6>
            <% } else { %>
                <h5 class="text-primary fw-bold">Giá bán: <%=sp.getGiaBan()%> đ</h5>
                <h6 class="text-muted">Giá gốc: <%=sp.getGiaGoc()%> đ</h6>
            <% } %>
<%
    KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
    String makh = null;
    if (khachHang != null) {
        makh = khachHang.getMaKhachHang(); // ✅ Gán giá trị
    }
%>
<% if (makh != null) { %>
    <button class="btn btn-warning mt-auto" data-bs-toggle="modal" data-bs-target="#addToCartModal<%=sp.getMaSanPham()%>">
        Mua Hàng Ngay
    </button>
<% } else { %>
    <a href="<%=request.getContextPath()%>/khachhang/dangnhap.jsp" class="btn btn-secondary mt-auto">Đăng nhập để mua</a>
<% } %>
        </div>
        
<%
  double giamGia = 0;
  boolean coGiamGia = sp.getGiaBan() < sp.getGiaGoc();
  if (coGiamGia && sp.getGiaGoc() > 0) {
      giamGia = 100.0 * (sp.getGiaGoc() - sp.getGiaBan()) / sp.getGiaGoc();  // ✅ Đúng
  }
%>

<!-- Modal -->
<div class="modal fade" id="addToCartModal<%=sp.getMaSanPham()%>" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content rounded-4 shadow">
      <div class="modal-header">
        <h5 class="modal-title">Đơn Hàng của bạn</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <p>Sản phẩm: <strong><%=sp.getTenSanPham()%></strong></p>
        <p>Giá gốc: <del><%=sp.getGiaGoc()%></del> đ</p>
        <p>Giá bán: <strong id="giaban<%=sp.getMaSanPham()%>"><%=sp.getGiaBan()%></strong> đ</p>

      <% if (coGiamGia) { %>
  <p>Giảm giá: <span class="text-success">-<%=String.format("%.1f", giamGia)%>%</span></p>
  <p>Tiết kiệm: 
  <span class="text-success" id="tietkiem<%=sp.getMaSanPham()%>">
    <%= String.format("%,.0f", sp.getGiaGoc() - sp.getGiaBan()) %> đ
  </span>
</p>

<% } %>

        <form action="DonHangServlet" method="post">
        <input type="hidden" name="hanhDong" value="them"/>
         <input type="hidden" name="makhachhang" value="<%=makh%>">
          <input type="hidden" name="masanpham" value="<%=sp.getMaSanPham()%>">
         
          
     
          <div class="mb-3">
            <label for="soluong<%=sp.getMaSanPham()%>">Số lượng:</label>
            <input type="number" name="soluong" id="soluong<%=sp.getMaSanPham()%>" class="form-control"
                   min="1" value="1" oninput="capNhatTongTien<%=sp.getMaSanPham()%>()">
          </div>
          
<div class="form-group">
    <label for="hinhthucthanhtoan"><strong>Hình thức thanh toán:</strong></label>
    <select class="form-control mb-3" name="hinhthucthanhtoan" id="hinhthucthanhtoan" required>
        <option value="COD">Thanh toán khi nhận hàng (COD)</option>
        <option value="Chuyển khoản">Chuyển khoản</option>
    </select>
</div>

          <p>Tổng tiền: <strong id="tongtien<%=sp.getMaSanPham()%>"><%=sp.getGiaBan()%></strong> đ</p>

          <div class="modal-footer">
            <button type="submit" class="btn btn-primary">Xác nhận</button>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<script>
  function capNhatTongTien<%=sp.getMaSanPham()%>() {
    const giaBan = <%=sp.getGiaBan()%>;
    const giaGoc = <%=sp.getGiaGoc()%>; // ✅ Thêm dòng này
    const soLuong = parseInt(document.getElementById("soluong<%=sp.getMaSanPham()%>").value) || 0;
  
    const tongTien = giaBan * soLuong;
    document.getElementById("tongtien<%=sp.getMaSanPham()%>").innerText = tongTien.toLocaleString();
    
    // Nếu có giảm giá thì tính tiền tiết kiệm
    if (giaBan < giaGoc) {
      const tietKiem = soLuong * (giaGoc - giaBan);
      document.getElementById("tietkiem<%=sp.getMaSanPham()%>").innerText = tietKiem.toLocaleString() + " đ";
    } else {
      document.getElementById("tietkiem<%=sp.getMaSanPham()%>").innerText = "";
    }
  }
                
  
</script>
<!-- kết thúc -->
        
        <div class="card-footer bg-white border-0">
            <small class="text-muted">&#9733; &#9733; &#9733; &#9733; &#9734;</small>
        </div>
    </div>
</div>

                <% } %>
            </div>
        </div>
    </div>
</div>


		
<!-- Footer -->
 	<%@include file = "footer.jsp" %>
	
</body>
</html>