package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.TinNhan;

public class TinNhanDAO {
    public static void luuTinNhan(String nguoiGui, String nguoiNhan, String noiDung) {
       
        PreparedStatement ps = null;

        try {
        	Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO TinNhan(nguoiGui, nguoiNhan, noiDung) VALUES (?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, nguoiGui);
            ps.setString(2, nguoiNhan);
            ps.setString(3, noiDung);
            ps.executeUpdate();
        	JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    public static ArrayList<TinNhan> layTinNhanGiua(String user1, String user2) {
    	ArrayList<TinNhan> ds = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM tinnhan WHERE " +
                         "(nguoigui = ? AND nguoinhan = ?) OR (nguoigui = ? AND nguoinhan = ?) " +
                         "ORDER BY thoigian ASC";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, user1);
            st.setString(2, user2);
            st.setString(3, user2);
            st.setString(4, user1);
            
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                TinNhan tn = new TinNhan();
                tn.setId(rs.getInt("id"));
                tn.setNguoiGui(rs.getString("nguoigui"));
                tn.setNguoiNhan(rs.getString("nguoinhan"));
                tn.setNoiDung(rs.getString("noidung"));
                tn.setThoiGian(rs.getTimestamp("thoigian"));
                ds.add(tn);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    
    public static ArrayList<String> layDanhSachKhachHangDaChatVoiAdmin() {
    	ArrayList<String> danhSach = new ArrayList<>();
        String sql = "SELECT DISTINCT IF(nguoiGui = 'admin', nguoiNhan, nguoiGui) AS khachHang " +
                     "FROM TinNhan WHERE nguoiGui = 'admin' OR nguoiNhan = 'admin'";
        try (
            Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next()) {
                danhSach.add(rs.getString("khachHang"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }



    
}
