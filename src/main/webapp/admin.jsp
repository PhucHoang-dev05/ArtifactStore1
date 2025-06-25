<%@page import="database.TheLoaiDAO"%>
<%@page import="model.TheLoai"%>
<%@page import="database.TacGiaDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, model.SanPham, database.SanPhamDAO" %>
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ADMIN</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
     body {
            background-color: #f1f3f5;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .container {
            margin-top: 50px;
        }

        .table-wrapper {
            background-color: #fff;
            border-radius: 12px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            overflow: hidden;
        }

        .table thead {
            background-color: #2c3e50;
            color: #fff;
        }

        .table th, .table td {
            vertical-align: middle;
            text-align: center;
        }

        .btn-edit {
            background-color: #f1c40f;
            color: white;
        }

        .btn-delete {
            background-color: #e74c3c;
            color: white;
        }

        .btn-edit:hover {
            background-color: #d4ac0d;
        }

        .btn-delete:hover {
            background-color: #c0392b;
        }

        .title {
            text-align: center;
            font-size: 2rem;
            margin-bottom: 25px;
            font-weight: bold;
            color: #2c3e50;
        }
    </style>
</head>
<body>
<div class="container py-5">

<a href="<%= request.getContextPath() %>/index.jsp" 
   class="btn btn-primary btn-lg position-absolute top-0 end-0 m-4 rounded-4 shadow-lg px-4 py-2">
   🏠 Trang chủ của User
</a>

    <h2 class="mb-4">Quản lý Sản phẩm</h2>
<%
    String baoLoi = request.getParameter("baoLoi");
    if (baoLoi == null) baoLoi = "";
    if (!baoLoi.trim().isEmpty()) {
%>
    <div class="alert alert-info alert-dismissible fade show my-3" role="alert">
        <%= baoLoi %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
<%
    }
%>
<div class="mb-4 text-end d-flex justify-content-end gap-2 flex-wrap">
    <a href="<%=request.getContextPath()%>/admin/doanhthu.jsp" class="btn btn-success">
        <i class="bi bi-bar-chart"></i> Xem Doanh Thu Bán Hàng
    </a>
      <a href="<%=request.getContextPath()%>/admin/messages.jsp" class="btn text-white" style="background-color: #6610f2;">
        💬 Chat
    </a>
    <a href="<%=request.getContextPath()%>/admin/tacgia.jsp" class="btn btn-warning text-white">
        <i class="bi bi-person-plus"></i> Thêm Tác giả
    </a>
    <a href="<%=request.getContextPath()%>/admin/theloai.jsp" class="btn btn-info text-white">
        <i class="bi bi-tags"></i> Thêm Thể loại
    </a>
</div>


    <!-- Form thêm sản phẩm -->
    <form action="SanPhamServlet" method="post" class="row g-3 border rounded p-3 mb-4">
      	<input type="hidden" name="hanhDong" value="them-san-pham"/>
        <div class="col-md-2">
            <input type="text" name="masanpham" class="form-control" placeholder="Mã sản phẩm" required>
        </div>
        <div class="col-md-2">
            <input type="text" name="tensanpham" class="form-control" placeholder="Tên sản phẩm" required>
        </div>
        <%
    TacGiaDAO tgDAO = new TacGiaDAO();
    ArrayList<model.TacGia> dsTacGia = tgDAO.selectAll();
%>
       <div class="col-md-2">
    <select name="matacgia" class="form-select" required>
        <option value="">-- Chọn tác giả --</option>
        <% for (model.TacGia tg : dsTacGia) { %>
            <option value="<%= tg.getMaTacGia() %>">
                <%= tg.getMaTacGia() %> - <%= tg.getHoVaTen() %>
            </option>
        <% } %>
    </select>
</div>

 <%
    TheLoaiDAO tldao = new TheLoaiDAO();
    ArrayList<TheLoai> dsTheLoai = tldao.selectAll();
%>
       <div class="col-md-2">
    <select name="matheloai" class="form-select" required>
        <option value="">-- Chọn Thể Loại --</option>
        <% for (TheLoai tl : dsTheLoai) { %>
          <option value="<%= tl.getMaTheLoai() %>">
    <%= tl.getMaTheLoai() %> - <%= tl.getTenTheLoai() %>
</option>
        <% } %>
    </select>
</div>

		 <div class="col-md-2">
            <input type="number" name="namxuatban" class="form-control" placeholder="Năm xuất bản" step="0.01" required>
        </div>
         <div class="col-md-2">
            <input type="number" name="gianhap" class="form-control" placeholder="Giá nhập" step="0.01" required>
        </div>
         <div class="col-md-2">
            <input type="number" name="giagoc" class="form-control" placeholder="Giá gốc" step="0.01" required>
        </div>
	
        <div class="col-md-2">
            <input type="number" name="giaban" class="form-control" placeholder="Giá bán" step="0.01" required>
        </div>
        <div class="col-md-2">
            <input type="number" name="soluong" class="form-control" placeholder="Số lượng" required>
        </div>
        <div class="col-md-12">
        <textarea name="mota" class="form-control" placeholder="Mô tả sản phẩm" rows="2"></textarea>
        </div>
        <!-- Sửa cái này -->
        <div class="col-md-6">
    <label for="tenanh" class="form-label">Chọn ảnh sản phẩm</label>
    <input type="file" id="tenanh" name="tenanh_file" class="form-control" accept="image/*" onchange="layTenFile()" required>
    <input type="hidden" name="tenanh" id="tenanh_hidden">
</div>

<script>
    function layTenFile() {
        const fileInput = document.getElementById("tenanh");
        const tenFile = fileInput.files[0]?.name || "";
        document.getElementById("tenanh_hidden").value = tenFile;
    }
</script>
        <div class="col-12 text-end">
            <button type="submit" class="btn btn-primary">Thêm sản phẩm</button>
        </div>
    </form>

<!-- Form tìm kiếm -->
<form action="SanPhamServlet" method="get" class="row g-3 mb-4">
 	<input type="hidden" name="hanhDong" value="timKiemSanPham"/>
    <div class="col-md-4">
        <input type="text" class="form-control" name="keyword" placeholder="Tìm theo tên, mã, tác giả...">
    </div>
    <div class="col-md-2">
        <button type="submit" class="btn btn-primary">
            <i class="bi bi-search"></i> Tìm kiếm
        </button>
    </div>
</form>



    <!-- Bảng danh sách sản phẩm -->
    <table class="table table-bordered table-hover">
        <thead class="table-dark">
            <tr>
              <th>Hình ảnh</th> 
                <th>Mã</th>
                <th>Tên</th>
                <th>Tác giả</th>
                 <th>Thể loại</th>
                   <th>Giá gốc</th>
                <th>Giá bán</th>
                <th>Số lượng</th>
                <th>Mô tả</th>
                <th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
           <%
           @SuppressWarnings("unchecked")
           ArrayList<SanPham> ds = (ArrayList<SanPham>) request.getAttribute("dsSanPham");
    if (ds == null) {
        SanPhamDAO spDAO = new SanPhamDAO();
        TacGiaDAO tgDAO2 = new TacGiaDAO();
        TheLoaiDAO tlDAO2 = new TheLoaiDAO();
        ds = spDAO.selectAll();

        // Gắn thông tin tác giả đầy đủ cho mỗi sản phẩm
        for (SanPham sp : ds) {
            if (sp.getTacGia() != null && sp.getTacGia().getMaTacGia() != null) {
                sp.setTacGia(tgDAO2.selectById(sp.getTacGia().getMaTacGia()));
            }
            
        }
    }

    for (SanPham sp : ds) {
%>
            <tr>
           <td style="width: 200px; height: 200px;">
    <img src="<%= request.getContextPath() %>/images/sanpham/<%= sp.getTenAnh() %>" 
         alt="Ảnh" width="100%" height="100%" 
         style="object-fit: cover;" 
         onerror="this.src='images/sanpham/default.jpg';" />
</td>
                <td><%= sp.getMaSanPham() %></td>
            	<td><%= (sp.getTenSanPham() != null) ? sp.getTenSanPham() : "Không có tên sản phẩm" %></td>
               <td><%= (sp.getTacGia() != null) ? sp.getTacGia().getHoVaTen() : "Không có tác giả" %></td>
                <td><%= (sp.getTheLoai() != null) ? sp.getTheLoai().getTenTheLoai() : "Không có thể loại" %></td>
                 <td><%= sp.getGiaGoc() %></td>
                <td><%= sp.getGiaBan() %></td>
                <td><%= sp.getSoLuong() %></td>
         	   <td><%= (sp.getMoTa() != null) ? sp.getMoTa() : "Không có mô tả" %></td>
                <td>
                    <a href="<%=request.getContextPath()%>/admin/suaSanPham.jsp?id=<%= sp.getMaSanPham() %>" class="btn btn-warning btn-sm mb-2 mt-2">Sửa</a>
                    <a href="SanPhamServlet?hanhDong=xoa&id=<%= sp.getMaSanPham() %>" class="btn btn-danger btn-sm" onclick="return confirm('Xóa sản phẩm này?')">Xóa</a>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>