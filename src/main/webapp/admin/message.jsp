<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.TinNhan, model.KhachHang, database.TinNhanDAO, database.KhachHangDAO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    request.setCharacterEncoding("UTF-8");
    String khachHangId = request.getParameter("user");
    KhachHang kh = new KhachHangDAO().selectById(khachHangId);
    List<TinNhan> lichSu = TinNhanDAO.layTinNhanGiua("admin", khachHangId);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chat với <%= kh != null ? kh.getHoVaTen() : khachHangId %></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
   <style>
    .chat-box {
        height: 500px;
        overflow-y: auto;
        padding: 15px;
        background-color: #f8f9fa;
        border-radius: 10px;
        border: 1px solid #dee2e6;
        display: flex;
        flex-direction: column;
    }

    .message-container {
        display: flex;
        flex-direction: column;
        width: 100%;
        margin-bottom: 10px;
    }

    .admin-message {
        align-items: flex-end;
    }

    .user-message {
        align-items: flex-start;
    }

    .sender-name {
        font-size: 0.85em;
        font-weight: 600;
        margin-bottom: 3px;
        color: #495057;
    }

    .message-content {
        max-width: 75%;
        padding: 10px 15px;
        border-radius: 20px;
        word-wrap: break-word;
        font-size: 1rem;
    }

    .admin-message .message-content {
        background-color: #0d6efd;
        color: white;
        text-align: right;
    }

    .user-message .message-content {
        background-color: #e9ecef;
        color: #333;
        text-align: left;
    }

    .message-time {
        font-size: 0.75em;
        color: #6c757d;
        margin-top: 5px;
        text-align: right;
    }

    .message-form {
        margin-top: 15px;
    }
</style>

</head>
<body class="container mt-4">
    <a href="messages.jsp" class="btn btn-outline-secondary mb-3">← Quay lại</a>
    <h4 class="mb-3">Chat với <%= kh != null ? kh.getHoVaTen() : khachHangId %> (ID: <%= khachHangId %>)</h4>

    <div id="chatBox" class="chat-box mb-3">
        <% for (TinNhan tn : lichSu) {
            boolean isFromAdmin = "admin".equals(tn.getNguoiGui());
        %>
        <div class="message-container <%= isFromAdmin ? "admin-message" : "user-message" %>">
    <div class="sender-name"><%= isFromAdmin ? "Admin" : kh.getHoVaTen() %></div>
    <div class="message-content"><%= tn.getNoiDung() %></div>
    <div class="message-time"><%= tn.getThoiGian() != null ? sdf.format(tn.getThoiGian()) : "" %></div>
</div>
        <% } %>
    </div>

    <div class="message-form d-flex">
        <input type="text" id="msg" class="form-control me-2" placeholder="Nhập tin nhắn...">
        <button class="btn btn-primary" onclick="send()">Gửi</button>
    </div>

    <script>
        const socket = new WebSocket("ws://" + location.host + "/ArtifactStore/chat/admin");
        const userId = "<%= khachHangId %>";
        const userName = "<%= kh != null ? kh.getHoVaTen() : khachHangId %>";
        const chatBox = document.getElementById("chatBox");

        window.onload = () => chatBox.scrollTop = chatBox.scrollHeight;

        socket.onmessage = e => {
            const [senderId, msg] = e.data.split("::", 2);
            const isFromUser = senderId === userId;

            const container = document.createElement("div");
            container.className = "message-container " + (isFromUser ? "user-message" : "admin-message");


            const nameDiv = document.createElement("div");
            nameDiv.className = "sender-name";
            nameDiv.textContent = isFromUser ? userName : "Admin";

            const contentDiv = document.createElement("div");
            contentDiv.className = "message-content";
            contentDiv.textContent = msg;

            const timeDiv = document.createElement("div");
            timeDiv.className = "message-time";
            timeDiv.textContent = new Date().toLocaleString('vi-VN');

            container.appendChild(nameDiv);
            container.appendChild(contentDiv);
            container.appendChild(timeDiv);
            chatBox.appendChild(container);
            chatBox.scrollTop = chatBox.scrollHeight;

            if (!isFromUser) document.getElementById("msg").value = "";
        };

        function send() {
            const inp = document.getElementById("msg");
            const m = inp.value.trim();
            if (!m) return;
            const packet = userId + "::" + m;
            socket.send(packet);
            inp.value = "";
        }

        document.getElementById("msg").addEventListener("keypress", function(event) {
            if (event.key === "Enter") {
                event.preventDefault();
                send();
            }
        });
    </script>
</body>
</html>