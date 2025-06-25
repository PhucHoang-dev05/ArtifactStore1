<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thông báo hệ thống</title>
<!-- Bootstrap CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    body {
        background: linear-gradient(135deg, #e0f7fa, #f1f8e9);
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100vh;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    .alert-custom {
        padding: 2rem;
        border-radius: 1rem;
        box-shadow: 0 0.75rem 1.5rem rgba(0, 0, 0, 0.1);
        max-width: 600px;
        width: 100%;
        text-align: center;
        animation: fadeIn 0.8s ease-in-out;
    }
    @keyframes fadeIn {
        from {opacity: 0; transform: translateY(-20px);}
        to {opacity: 1; transform: translateY(0);}
    }
</style>
</head>
<body>

<% 
    String xacDinh = (String) request.getAttribute("xacDinh");
    String message = "";
    String icon = "";
    String alertClass = "alert-success"; // default green

    if ("trangdoithongtin".equals(xacDinh)) {
        message = "Thông tin của bạn đã được cập nhật thành công!";
        icon = "bi bi-check-circle-fill";
    } else if ("doimatkhau".equals(xacDinh)) {
        message = "Bạn đã thay đổi mật khẩu thành công!";
        icon = "bi bi-shield-lock-fill";
    } else {
        message = "🎉 Chúc mừng! Bạn đã đăng ký tài khoản thành công. Vui lòng quay lại trang đăng nhập.";
        icon = "bi bi-person-plus-fill";
    }
%>

<div class="alert <%= alertClass %> alert-custom">
    <h3><i class="<%= icon %>" style="font-size: 2rem;"></i></h3>
    <h4 class="mt-3"><%= message %></h4>
    <hr>
    <p>Bạn sẽ được chuyển về trang chủ sau vài giây...</p>
</div>

<!-- Bootstrap Icons CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

<script>
    setTimeout(function(){
        window.location.href = 'index.jsp';
    }, 4000);
</script>
</body>
</html>
