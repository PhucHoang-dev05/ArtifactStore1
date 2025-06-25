<%@page import="database.TacGiaDAO"%>
<%@page import="model.TacGia"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.KhachHang"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
 <%
 String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	+ request.getContextPath();
 %>
	<!-- Navbar -->
	<nav class="navbar navbar-expand-lg">
		<div class="container-fluid">
			<a class="navbar-brand" href="#"> <img
				src="img/slider/logo.png"
				alt="Bootstrap" height="120">
			</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
	<li class="nav-item">
		<a class="nav-link active" aria-current="page" href="<%=request.getContextPath()%>/index.jsp" style="font-size: 25px;">Trang chủ</a>
	</li>
	<li class="nav-item">
		<a class="nav-link" href="index.jsp?giamgia=true" style="font-size: 25px;">Combo giảm giá</a>
	</li>
	<%
    ArrayList<TacGia> danhSachTacGia = new TacGiaDAO().selectAll();
%>
	<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" role="button"
       data-bs-toggle="dropdown" aria-expanded="false" style="font-size: 25px;">Tác Giả</a>
    <ul class="dropdown-menu">
        <% for (TacGia tg : danhSachTacGia) { %>
            <li>
                <a class="dropdown-item" href="<%= request.getContextPath() %>/index.jsp" style="font-size: 20px;">
                    <%= tg.getHoVaTen() %>
                </a>
            </li>
        <% } %>
    </ul>
</li>
</ul>

				<form class="d-flex" role="search">
					<input class="form-control me-2" type="search"
						placeholder="Nội dung tìm kiếm" aria-label="Search">
					<button class="btn btn-outline-success" type="submit">Tìm</button>
					<!-- nhập -->
						<%
						Object obj1 = session.getAttribute("khachHang");
						KhachHang khachHang1 = null;
						if (obj1!=null)
							khachHang1 = (KhachHang)obj1;
						
						if(khachHang1==null){
					%>
						<a class="btn btn-primary ms-2" style="white-space: nowrap;" href="<%=request.getContextPath()%>/khachhang/dangnhap.jsp">
							Đăng nhập
						</a>
					<%} else { %>
					<ul class="navbar-nav me-auto mb-2 mb-lg-0 bg-infor ">
					<li class="nav-item dropdown dropstart"><a
						class="nav-link dropdown-toggle" href="#" role="button"
						data-bs-toggle="dropdown" aria-expanded="false"> Tài khoản</a>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="<%=request.getContextPath()%>/user/donhangcuatoi.jsp">Đơn hàng của tôi</a></li>
							<li><a class="dropdown-item" href="<%=request.getContextPath()%>/khachhang/thaydoianh.jsp">Thay đổi avatar</a></li>
							<li><a class="dropdown-item" href="<%=request.getContextPath()%>/khachhang/thaydoithongtin.jsp">Thay đổi thông tin</a></li>
							<li><a class="dropdown-item" href="<%=request.getContextPath()%>/khachhang/doimatkhau.jsp">Đổi mật khẩu</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="<%=request.getContextPath()%>/khach-hang?hanhDong=dang-xuat">Thoát tài khoản</a></li>
						</ul></li>
				</ul>				
					
				<% } %>
				</form>
			</div>
		</div>
	</nav>
	<!-- End Navbar -->