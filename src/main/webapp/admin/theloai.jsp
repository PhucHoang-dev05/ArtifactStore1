<%@page import="java.util.List"%>
<%@page import="model.TheLoai"%>
<%@page import="database.TheLoaiDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Thể Loại</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #eef2f7;
        }
        .card-custom {
            background-color: #ffffff;
            border-radius: 15px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
            padding: 30px;
            margin-top: 40px;
        }
        .table thead {
            background-color: #0d6efd;
            color: white;
        }
    </style>
</head>
<body>

<div class="container position-relative">
    <!-- Nút quay về admin.jsp -->
    <a href="<%= request.getContextPath() %>/admin.jsp"
       class="btn btn-info text-white position-absolute top-0 end-0 rounded-4 shadow px-3 py-2 mt-3">
        🏠 Về lại Quản lý Sản phẩm
    </a>

    <h2 class="text-center mt-4 fw-bold">📚 Quản Lý Thể Loại</h2>

    <!-- Form thêm thể loại -->
    <div class="card card-custom">
        <form action="<%=request.getContextPath()%>/TheLoaiServlet" method="post">
            <input type="hidden" name="hanhDong" value="them-the-loai">

            <div class="row g-3 align-items-end">
                <div class="col-md-4">
                    <label class="form-label">Mã Thể Loại</label>
                    <input type="text" class="form-control" name="matheloai" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Tên Thể Loại</label>
                    <input type="text" class="form-control" name="tentheloai" required>
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary w-100">➕ Thêm Thể Loại</button>
                </div>
            </div>
        </form>
    </div>

    <!-- Thông báo -->
    <%
        String msg = request.getParameter("msg");
        String baoLoi = request.getParameter("baoLoi");
        if (msg != null && baoLoi != null) {
    %>
        <div class="alert <%= msg.contains("thanh_cong") ? "alert-success" : "alert-danger" %> alert-dismissible fade show mt-3" role="alert">
            <%= baoLoi %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    <% } %>

    <!-- Bảng thể loại -->
    <div class="card card-custom table-section">
        <h5 class="mb-3">📋 Danh Sách Thể Loại</h5>
        <div class="table-responsive">
            <table class="table table-bordered table-hover bg-white">
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Mã Thể Loại</th>
                        <th>Tên Thể Loại</th>
                        <th>Thao Tác</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<TheLoai> ds = new TheLoaiDAO().selectAll();
                        int stt = 1;
                        if (ds != null && !ds.isEmpty()) {
                            for (TheLoai tl : ds) {
                    %>
                    <tr>
                        <td><%= stt++ %></td>
                        <td><%= tl.getMaTheLoai() %></td>
                        <td><%= tl.getTenTheLoai() %></td>
                        <td>
                            <a href="<%=request.getContextPath()%>/admin/suaTheLoai.jsp?id=<%= tl.getMaTheLoai() %>" class="btn btn-warning btn-sm">✏️ Sửa</a>
                            <a href="<%=request.getContextPath()%>/TheLoaiServlet?hanhDong=xoa&id=<%= tl.getMaTheLoai() %>" class="btn btn-danger btn-sm" onclick="return confirm('Xóa thể loại này?')">🗑️ Xóa</a>
                        </td>
                    </tr>
                    <%  } 
                        } else {
                    %>
                    <tr>
                        <td colspan="4" class="text-center">Chưa có thể loại nào.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
