<%@page import="model.SanPham"%>
<%@page import="model.ChiTietDonHang"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.DonHang" %>
<%@ page import="database.DonHangDAO" %>
<%@ page import="database.ChiTietDonHangDAO" %>
<%@ page import="model.KhachHang" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đơn Hàng Của Tôi</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<%
    KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
%>

<!-- header -->
<%@include file ="../header.jsp"%>

<div class="container mt-5">
    <h2 class="mb-4 text-center">Đơn Hàng Của Tôi</h2>
    
    <div class="card shadow rounded-4 w-75 mx-auto border-0" style="background-color: #f0f8ff;">
        <div class="row g-0">
            <!-- Ảnh đại diện -->
            <div class="col-md-4 text-center p-4">
                <img src="<%= request.getContextPath() + "/avatar/" + khachHang.getDuongDanAnh() %>"
                     class="img-fluid rounded-circle border border-3"
                     style="width: 150px; height: 150px; object-fit: cover;">
                <h5 class="mt-3 fw-bold"><%= khachHang.getHoVaTen() %></h5>
            </div>

            <!-- Thông tin khách hàng -->
            <div class="col-md-8">
                <div class="card-body">
                    <table class="table table-borderless mb-0">
                        <tr>
                            <th class="text-end w-50">Mã Khách Hàng:</th>
                            <td><%= khachHang.getMaKhachHang() %></td>
                        </tr>
                        <tr>
                            <th class="text-end">Giới Tính:</th>
                            <td><%= khachHang.getGioiTinh() %></td>
                        </tr>
                        <tr>
                            <th class="text-end">Địa Chỉ:</th>
                            <td><%= khachHang.getDiaChi() %></td>
                        </tr>
                        <tr>
                            <th class="text-end">Số Điện Thoại:</th>
                            <td><%= khachHang.getSoDienThoai() %></td>
                        </tr>
                        <tr>
                            <th class="text-end">Email:</th>
                            <td><%= khachHang.getEmail() %></td>
                        </tr>
                        <tr>
                            <th class="text-end">Ngày Sinh:</th>
                            <td><%= khachHang.getNgaySinh() %></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<%
    ArrayList<DonHang> danhSachDonHang = new DonHangDAO().selectAll();
    ArrayList<ChiTietDonHang> danhSachChiTiet = new ChiTietDonHangDAO().selectAll();
%>

<div class="container mt-4">
    <h2 class="mb-4 text-center">Danh Sách Đơn Hàng Đang Đặt</h2>
    <table class="table table-bordered table-striped table-hover">
        <thead class="table-dark text-center">
            <tr>
                <th>Mã Đơn Hàng</th>
                <th>Tên Sản Phẩm</th>
                <th>Địa Chỉ Mua</th>
                <th>Địa Chỉ Nhận</th>
                <th>Hình Thức</th>
                <th>Trạng Thái</th>
                <th>Số Lượng</th>
                <th>Đã Thanh Toán</th>
                <th>Ngày Đặt</th>
                <th>Ngày Giao</th>
                <th>Hành Động</th>
            </tr>
        </thead>
        <tbody>
    <%
        for (DonHang dh : danhSachDonHang) {
            KhachHang khTrongDon = dh.getKhachHang();

            if (
                khTrongDon != null &&
                khachHang.getMaKhachHang().equals(khTrongDon.getMaKhachHang()) &&
                dh.getNgayGiaoHang() != null &&
                dh.getNgayDatHang() != null &&
                dh.getNgayGiaoHang().after(dh.getNgayDatHang())
            ) {
                
                // Lấy danh sách tên sản phẩm theo mã đơn hàng
                ArrayList<String> tenSanPhamTrongDon = new ArrayList<>();
                for (ChiTietDonHang chiTiet : danhSachChiTiet) {
                    if (chiTiet.getDonHang().getMaDonHang().equals(dh.getMaDonHang())) {
                        tenSanPhamTrongDon.add(chiTiet.getSanPham().getTenSanPham());
                    }
                }
    %>
        <tr class="text-center align-middle">
            <td><%= dh.getMaDonHang() %></td>
            <td><%= String.join(", ", tenSanPhamTrongDon) %></td>
            <td><%= dh.getDiaChiMuaHang() %></td>
            <td><%= dh.getDiaChiNhanHang() %></td>
            <td><%= dh.getHinhThucThanhToan() %></td>
            <td><span class="badge bg-warning text-dark">Đang đặt</span></td>
            <td><%= dh.getSoLuong() %></td>
            <td><%= String.format("%,.0f", dh.getSoTienDaThanhToan()) %> ₫</td>
            <td><%= dh.getNgayDatHang() %></td>
            <td><%= dh.getNgayGiaoHang() %></td>
            <td>
               <a href="chat.jsp?maDonHang=<%= dh.getMaDonHang() %>&userId=<%= khachHang.getMaKhachHang() %>" 
   class="btn btn-primary btn-sm">
    Chat với người bán
</a>
            </td>
        </tr>
    <%
            }
        }
    %>
        </tbody>
    </table>
</div>
</body>
</html>
