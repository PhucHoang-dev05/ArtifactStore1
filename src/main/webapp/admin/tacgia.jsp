<%@page import="java.util.ArrayList"%>
<%@page import="database.TacGiaDAO"%>
<%@page import="model.TacGia"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Tác Giả</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .form-section {
            background-color: #fff;
            padding: 25px;
            border-radius: 10px;
            margin-top: 30px;
            box-shadow: 0 0 10px rgba(0,0,0,0.05);
        }
        .table-section {
            margin-top: 30px;
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

    <h2 class="mt-4 mb-3 fw-bold">✍️ Quản lý Tác Giả</h2>

    <!-- Form thêm tác giả -->
    <div class="form-section">
        <form action="<%=request.getContextPath()%>/TacGiaServlet" method="post">
            <input type="hidden" name="hanhDong" value="them-tac-gia">
            <div class="row g-3">
                <div class="col-md-3">
                    <label class="form-label">Mã Tác Giả</label>
                    <input type="text" class="form-control" name="matacgia" required>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Họ và Tên</label>
                    <input type="text" class="form-control" name="hovaten">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Ngày Sinh</label>
                    <input type="date" class="form-control" name="ngaysinh">
                </div>
                <div class="col-md-3 d-flex align-items-end">
                    <button type="submit" class="btn btn-warning w-100">➕ Thêm Tác Giả</button>
                </div>
                <div class="col-md-12">
                    <label class="form-label">Tiểu Sử</label>
                    <textarea class="form-control" name="tieusu" rows="3"></textarea>
                </div>
            </div>
        </form>
    </div>

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

    <!-- Bảng danh sách tác giả -->
    <h3 class="mb-3 mt-4">📚 Danh Sách Tác Giả</h3>
    <div class="table-responsive table-section">
        <table class="table table-bordered table-hover bg-white">
            <thead class="table-dark">
                <tr>
                    <th>Mã Tác Giả</th>
                    <th>Họ và Tên</th>
                    <th>Ngày Sinh</th>
                    <th>Tiểu Sử</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<TacGia> dsTacGia = new TacGiaDAO().selectAll();
                    if (dsTacGia != null && !dsTacGia.isEmpty()) {
                        for (TacGia tg : dsTacGia) {
                %>
                <tr>
                    <td><%= tg.getMaTacGia() %></td>
                    <td><%= tg.getHoVaTen() %></td>
                    <td><%= tg.getNgaySinh() %></td>
                    <td><%= tg.getTieuSu() %></td>
                    <td>
                        <a href="<%=request.getContextPath()%>/admin/suaTacGia.jsp?id=<%= tg.getMaTacGia() %>" class="btn btn-warning btn-sm mb-2 mt-2">✏️ Sửa</a>
                        <a href="<%=request.getContextPath()%>/TacGiaServlet?hanhDong=xoa&id=<%= tg.getMaTacGia() %>" class="btn btn-danger btn-sm ms-1 mt-0" onclick="return confirm('Xóa Tác Giả này?')">🗑️ Xóa</a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5" class="text-center">Chưa có dữ liệu tác giả nào.</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
