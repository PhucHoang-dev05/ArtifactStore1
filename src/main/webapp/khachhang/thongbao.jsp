<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chúc mừng</title>
<!-- Bootstrap CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    body {
        background-color: #eafaf1;
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100vh;
    }
    .alert-custom {
        padding: 2rem;
        border-radius: 1rem;
        box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.1);
        max-width: 500px;
        width: 100%;
    }
</style>
</head>
<body>
    <div class="alert alert-success text-center alert-custom">
        <h4 class="alert-heading">🎉 Chúc mừng!</h4>
        <p><%= request.getAttribute("baoLoi") != null ? request.getAttribute("baoLoi") : "Bạn đã đăng nhập thành công!" %></p>
        <hr>
        <p class="mb-0">Bạn sẽ được chuyển về trang chủ sau 5 giây...</p>
    </div>

    <script>
        setTimeout(function(){
            window.location.href = 'index.jsp';
        }, 3500);
    </script>
</body>
</html>
