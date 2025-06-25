<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.TinNhan"%>
<%@page import="database.TinNhanDAO"%>
<%@page import="java.text.SimpleDateFormat"%>

<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    String userId = request.getParameter("userId");
    String adminId = "admin";

    ArrayList<TinNhan> danhSachTinNhan = TinNhanDAO.layTinNhanGiua(adminId, userId);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chat với quản trị</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .chat-box {
            height: 500px;
            overflow-y: auto;
            background-color: #f8f9fa;
            border-radius: 10px;
            padding: 15px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }

        .message {
            max-width: 75%;
            padding: 10px 15px;
            border-radius: 20px;
            position: relative;
            word-wrap: break-word;
            margin-bottom: 10px;
            display: flex;
            flex-direction: column;
        }

        .from-admin {
            background-color: #e9ecef;
            align-self: flex-start;
            text-align: left;
        }

        .from-user {
            background-color: #0d6efd;
            color: white;
            align-self: flex-end;
            text-align: right;
        }

        .message-time {
            font-size: 0.75em;
            color: #6c757d;
            margin-top: 5px;
            text-align: right;
        }

        .message-container {
            width: 100%;
            display: flex;
            flex-direction: column;
        }

        .message-container.admin {
            align-items: flex-start;
        }

        .message-container.user {
            align-items: flex-end;
        }

        .sender-name {
            font-size: 0.85em;
            font-weight: 600;
            margin-bottom: 3px;
            color: #495057;
        }
    </style>
</head>
<body>
<div class="container py-4">
    <h4 class="mb-4 text-center">💬 Chat với quản trị</h4>

    <div id="chatBox" class="chat-box mb-3 d-flex flex-column">
        <% for (TinNhan tn : danhSachTinNhan) { 
            boolean isAdmin = tn.getNguoiGui().equals("admin");
            String noiDung = tn.getNoiDung();
            String displayContent = noiDung.contains("::") ? noiDung.split("::")[1] : noiDung;
        %>
            <div class="message-container <%= isAdmin ? "admin" : "user" %>">
                <div class="sender-name"><%= isAdmin ? "Admin" : "Bạn" %></div>
                <div class="message <%= isAdmin ? "from-admin" : "from-user" %>">
                    <%= displayContent %>
                </div>
                <div class="message-time">
                    <%= tn.getThoiGian() != null ? sdf.format(tn.getThoiGian()) : "" %>
                </div>
            </div>
        <% } %>
    </div>

    <form onsubmit="sendMessage(event)" class="d-flex">
        <input type="text" id="messageInput" class="form-control me-2" placeholder="Nhập tin nhắn..." autocomplete="off">
        <button class="btn btn-success">Gửi</button>
    </form>
</div>

<script>
    const userId = "<%= userId %>";
    const socket = new WebSocket("ws://" + window.location.host + "/ArtifactStore/chat/" + userId);
    const chatBox = document.getElementById("chatBox");

    window.onload = () => chatBox.scrollTop = chatBox.scrollHeight;

    socket.onmessage = function(event) {
        const data = event.data;
        let sender = "admin";
        let content = data;

        if (data.includes("::")) {
            const parts = data.split("::");
            sender = parts[0];
            content = parts[1];
        }

        const isAdmin = sender === "admin";

        const container = document.createElement("div");
        container.classList.add("message-container", isAdmin ? "admin" : "user");

        const name = document.createElement("div");
        name.classList.add("sender-name");
        name.textContent = isAdmin ? "Admin" : "Bạn";

        const message = document.createElement("div");
        message.classList.add("message", isAdmin ? "from-admin" : "from-user");
        message.textContent = content;

        const time = document.createElement("div");
        time.classList.add("message-time");
        time.textContent = new Date().toLocaleString('vi-VN');

        container.appendChild(name);
        container.appendChild(message);
        container.appendChild(time);
        chatBox.appendChild(container);
        chatBox.scrollTop = chatBox.scrollHeight;
    };

    function sendMessage(event) {
        event.preventDefault();
        const input = document.getElementById("messageInput");
        const message = input.value.trim();
        if (message !== "") {
            socket.send(message);
            input.value = "";
        }
    }

    document.getElementById("messageInput").addEventListener("keypress", function(event) {
        if (event.key === "Enter") {
            event.preventDefault();
            sendMessage(event);
        }
    });
</script>
</body>
</html>
