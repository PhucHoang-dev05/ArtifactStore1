<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.TinNhan, database.TinNhanDAO, database.KhachHangDAO, model.KhachHang" %>
<!DOCTYPE html>
<html>

<head>
    <title>Trung tâm tin nhắn Admin</title>
    <!-- Thêm Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .chatBox {
            border: 1px solid #ccc;
            padding: 10px;
            margin-bottom: 20px;
            max-height: 200px;
            overflow-y: auto;
            background: #f9f9f9;
            border-radius: 10px;
        }
        .tinNhan {
            margin: 5px 0;
        }
        .admin {
            text-align: right;
            color: blue;
        }
        .khach {
            text-align: left;
            color: green;
        }
        .thoiGian {
            font-size: 0.8em;
            color: gray;
        }
    </style>
</head>

<body class="p-4">
<div class="position-relative">
    <!-- Nút về trang admin.jsp -->
    <a href="<%= request.getContextPath() %>/admin.jsp" 
       class="btn btn-info text-white position-absolute top-0 end-0 m-2 rounded-4 shadow">
        🏠 Về lại Quản lý Sản phẩm
    </a>

    <h2 class="mb-4">📨 Danh sách khách hàng đã nhắn với Admin</h2>

    <%
        List<String> khachHangList = TinNhanDAO.layDanhSachKhachHangDaChatVoiAdmin();
        if (khachHangList.isEmpty()) {
    %>
        <p>Chưa có khách hàng nào gửi tin nhắn.</p>
    <%
        } else {
            for (String maKH : khachHangList) {
                KhachHangDAO khDAO = new KhachHangDAO();
                KhachHang kh = khDAO.selectById(maKH);
    %>
        <div class="chatBox">
            <h4>
                <a href="message.jsp?user=<%= maKH %>">
                    💬 Chat với <%= kh != null ? kh.getHoVaTen() : maKH %> (<%= maKH %>)
                </a>
            </h4>
        </div>
    <%
            }
        }
    %>
</div>
</body>
</html>
