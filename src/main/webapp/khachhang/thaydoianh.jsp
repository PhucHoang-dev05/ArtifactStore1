<%@page import="model.KhachHang"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Thay đổi ảnh</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    .avatar-preview {
        width: 180px;
        height: 180px;
        object-fit: cover;
        border-radius: 50%;
        border: 2px solid #ccc;
    }
</style>
</head>
<body>
<jsp:include page="../header.jsp" />

<%
    Object obj = session.getAttribute("khachHang");
    KhachHang khachHang = null;
    if (obj != null) khachHang = (KhachHang) obj;

    if (khachHang == null) {
%>
    <div class="container text-center mt-5">
        <h3 class="text-danger">Bạn chưa đăng nhập vào hệ thống. Vui lòng quay lại trang chủ!</h3>
    </div>
<%
    } else {
        String baoLoi = (String) request.getAttribute("baoLoi");
        Boolean thanhCong = (Boolean) request.getAttribute("thanhCong");
        if (baoLoi == null) baoLoi = "";
        if (thanhCong == null) thanhCong = false;
        String duongDanAnh = khachHang.getDuongDanAnh();
%>

<div class="container mt-5 mb-5">
    <div class="text-center mb-4">
        <h1>THÔNG TIN TÀI KHOẢN</h1>
    </div>

    <form action="<%=request.getContextPath()%>/khach-hang-thay-doi-anh" method="post" enctype="multipart/form-data">
        <div class="row justify-content-center">
            <div class="col-md-6 col-sm-12 border rounded p-4 shadow-sm">

                <div class="text-center mb-4">
                    <h4>Ảnh đại diện</h4>
                    <img id="avatarPreview" src="<%=request.getContextPath()%>/avatar/<%=duongDanAnh%>" 
                         alt="Ảnh Avatar" class="avatar-preview"/>
                </div>

                <div class="mb-3">
                    <label for="duongDanAnh" class="form-label">Chọn ảnh mới</label>
                    <input type="file" class="form-control" id="duongDanAnh" name="duongDanAnh" accept="image/*" onchange="previewAvatar(event)">
                </div>

                <input class="btn btn-primary w-100 mt-3" type="submit" value="Lưu thông tin" name="submit" />

                <% if (!baoLoi.equals("")) { %>
                <div class="alert <%= thanhCong ? "alert-success" : "alert-danger" %> mt-3">
                    <%= baoLoi %>
                </div>
                <% } %>

            </div>
        </div>
    </form>
</div>

<% } %>
<jsp:include page="../footer.jsp" />

<!-- JavaScript: Avatar preview -->
<script>
function previewAvatar(event) {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = function(e) {
        const img = document.getElementById("avatarPreview");
        img.src = e.target.result;
    };
    if (file) {
        reader.readAsDataURL(file);
    }
}
</script>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
