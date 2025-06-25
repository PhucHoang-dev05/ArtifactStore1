<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="java.util.*, model.ChiTietDonHang, database.ChiTietDonHangDAO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Báo cáo doanh thu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container my-5 position-relative">
    <!-- Nút quay về admin.jsp -->
    <a href="<%= request.getContextPath() %>/admin.jsp"
       class="btn btn-info text-white position-absolute top-0 end-0 rounded-4 shadow px-3 py-2 mt-2">
        Về lại Quản lý Sản phẩm
    </a>

    <h2 class="text-center mb-4 text-primary">Báo Cáo Doanh Thu</h2>

    <!-- Form tìm kiếm -->
    <form class="row g-3 mb-4" method="get" action="doanhthu.jsp">
        <div class="col-md-3">
            <input type="text" class="form-control" name="madonhang" placeholder="Mã đơn hàng"
                   value="<%= request.getParameter("madonhang") != null ? request.getParameter("madonhang") : "" %>">
        </div>
        <div class="col-md-3">
            <input type="text" class="form-control" name="tendonhang" placeholder="Tên Sản phẩm"
                   value="<%= request.getParameter("tendonhang") != null ? request.getParameter("tendonhang") : "" %>">
        </div>
        <div class="col-md-3">
            <input type="date" class="form-control" name="ngaydat">
        </div>
        <div class="col-md-3">
            <button type="submit" class="btn btn-primary w-100">🔍 Tìm kiếm</button>
        </div>
    </form>

    <!-- Bảng doanh thu -->
    <table class="table table-bordered table-hover align-middle">
        <thead class="table-dark text-center">
        <tr>
            <th>Mã CT ĐH</th>
            <th>Mã Đơn Hàng</th>
            <th>Mã Sản Phẩm</th>
            <th>Số lượng</th>
            <th>Giá gốc</th>
            <th>Giảm giá</th>
            <th>Giá bán</th>
            <th>Thuế VAT</th>
            <th class="fw-bold">Tiền lời</th>
            <th class="fw-bold">Tổng tiền</th>
        </tr>
        </thead>
        <tbody class="text-center">
        <%
            ChiTietDonHangDAO dao = new ChiTietDonHangDAO();
            String madonhang = request.getParameter("madonhang") != null ? request.getParameter("madonhang").trim() : "";
            String tendonhang = request.getParameter("tendonhang") != null ? request.getParameter("tendonhang").trim() : "";

            ArrayList<ChiTietDonHang> ds;

            if ((madonhang != null && !madonhang.isEmpty()) || (tendonhang != null && !tendonhang.isEmpty())) {
                String keyword = (madonhang + " " + tendonhang).trim();
                ds = dao.timKiem(keyword);
            } else {
                ds = dao.selectAll();
            }

            double tongDoanhThu = 0;
            double tongLoiNhuan = 0;

            for (ChiTietDonHang ctdh : ds) {
                double tienLoi = (ctdh.getGiaBan() - ctdh.getGiaGoc()) * ctdh.getSoLuong();
                tongDoanhThu += ctdh.getTongTien();
                tongLoiNhuan += tienLoi;
        %>
        <tr>
            <td><%= ctdh.getMaChiTietDonHang() %></td>
            <td><%= ctdh.getDonHang().getMaDonHang() %></td>
            <td><%= ctdh.getSanPham().getMaSanPham() %></td>
            <td><%= ctdh.getSoLuong() %></td>
            <td><%= String.format("%,.0f", ctdh.getGiaGoc()) %></td>
            <td><%= String.format("%,.0f", ctdh.getGiamGia()) %></td>
            <td><%= String.format("%,.0f", ctdh.getGiaBan()) %></td>
            <td><%= String.format("%,.0f", ctdh.getThueVAT()) %></td>
            <td class="text-info fw-semibold"><%= String.format("%,.0f", tienLoi) %></td>
            <td class="text-success fw-bold"><%= String.format("%,.0f", ctdh.getTongTien()) %></td>
        </tr>
        <% } %>
        </tbody>
        <tfoot class="table-primary">
        <tr>
            <td colspan="8" class="text-end fw-bold">Tổng doanh thu:</td>
            <td class="fw-bold text-info"><%= String.format("%,.0f", tongLoiNhuan) %> VNĐ</td>
            <td class="fw-bold text-success"><%= String.format("%,.0f", tongDoanhThu) %> VNĐ</td>
        </tr>
        </tfoot>
    </table>
</div>

</body>
</html>
