package websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.PathParam;
import database.TinNhanDAO;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat/{userId}")
public class ChatServer {
    private static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        sessions.put(userId, session);
        System.out.println(userId + " đã kết nối.");
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userId") String senderId) throws IOException {
        if ("admin".equals(senderId)) {
            // admin gửi → định dạng: receiverId::message
            String[] parts = message.split("::", 2);
            if (parts.length == 2) {
                String receiverId = parts[0].trim();
                String msgContent = parts[1].trim();

                // Gửi tới người nhận
                Session receiverSession = sessions.get(receiverId);
                if (receiverSession != null && receiverSession.isOpen()) {
                    receiverSession.getBasicRemote().sendText("admin::" + msgContent);
                }

                // Gửi lại cho admin để hiển thị trên giao diện
                session.getBasicRemote().sendText("admin::" + msgContent);

                // Lưu vào CSDL
                TinNhanDAO.luuTinNhan("admin", receiverId, msgContent);
            } else {
                System.err.println("Sai định dạng tin nhắn từ admin: " + message);
            }
        } else {
            // user gửi → gửi cho admin
            Session adminSession = sessions.get("admin");
            if (adminSession != null && adminSession.isOpen()) {
                adminSession.getBasicRemote().sendText(senderId + "::" + message); // gửi tên người gửi kèm nội dung
            }

            // Gửi lại cho chính user
            session.getBasicRemote().sendText(senderId + "::" + message);

            // Lưu tin nhắn
            TinNhanDAO.luuTinNhan(senderId, "admin", message);
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        sessions.remove(userId);
        System.out.println(userId + " đã ngắt kết nối.");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Lỗi WebSocket: " + throwable.getMessage());
    }
}
