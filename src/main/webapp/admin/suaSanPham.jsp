<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.SanPham, model.TacGia, model.TheLoai, database.TacGiaDAO, database.TheLoaiDAO, database.SanPhamDAO" %>
<%
    String maSanPham = request.getParameter("id");
    SanPhamDAO spDAO = new SanPhamDAO();
    SanPham sp = spDAO.selectById(maSanPham);

    TacGiaDAO tgDAO = new TacGiaDAO();
    TheLoaiDAO tlDAO = new TheLoaiDAO();
    java.util.List<TacGia> danhSachTacGia = tgDAO.selectAll();
    java.util.List<TheLoai> danhSachTheLoai = tlDAO.selectAll();
%>

<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sửa Sản Phẩm</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="mb-4">Sửa sản phẩm</h2>
    <form action="<%=request.getContextPath()%>/SanPhamServlet" method="post">
        <input type="hidden" name="hanhDong" value="sua">
        <div class="mb-3">
            <label>Mã sản phẩm</label>
            <input type="text" name="masanpham" class="form-control" value="<%= sp.getMaSanPham() %>" readonly>
        </div>
        <div class="mb-3">
            <label>Tên sản phẩm</label>
            <input type="text" name="tensanpham" class="form-control" value="<%= sp.getTenSanPham() %>">
        </div>
        <div class="mb-3">
            <label>Tác giả</label>
            <select name="matacgia" class="form-select">
                <% for (TacGia tg : danhSachTacGia) { %>
                    <option value="<%= tg.getMaTacGia() %>" <%= tg.getMaTacGia().equals(sp.getTacGia().getMaTacGia()) ? "selected" : "" %> >
                        <%= tg.getHoVaTen() %>
                    </option>
                <% } %>
            </select>
        </div>
        <div class="mb-3">
            <label>Thể loại</label>
            <select name="matheloai" class="form-select">
                <% for (TheLoai tl : danhSachTheLoai) { %>
                    <option value="<%= tl.getMaTheLoai() %>" <%= tl.getMaTheLoai().equals(sp.getTheLoai().getMaTheLoai()) ? "selected" : "" %> >
                        <%= tl.getTenTheLoai() %>
                    </option>
                <% } %>
            </select>
        </div>
        <div class="mb-3">
            <label>Giá nhập</label>
            <input type="number" name="gianhap" class="form-control" value="<%= sp.getGiaNhap() %>">
        </div>
        <div class="mb-3">
            <label>Giá gốc</label>
            <input type="number" name="giagoc" class="form-control" value="<%= sp.getGiaGoc() %>">
        </div>
        
        <div class="mb-3">
            <label>Giá bán</label>
            <input type="number" name="giaban" class="form-control" value="<%= sp.getGiaBan() %>">
        </div>
        <div class="mb-3">
            <label>Số lượng</label>
            <input type="number" name="soluong" class="form-control" value="<%= sp.getSoLuong() %>">
        </div>
        
          <div class="col-md-6">
            <label>Mô tả</label>
            <textarea name="mota" class="form-control" rows="3"><%= sp.getMoTa() %></textarea>
        </div>

         
        <button type="submit" class="btn btn-primary mt-3">Lưu thay đổi</button>
        <a href="<%=request.getContextPath()%>/admin.jsp" class="btn btn-secondary ms-2 mt-3">Quay lại</a>
    </form>
</div>

</body>
</html>
