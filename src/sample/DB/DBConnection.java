package sample.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {

    private final String dburl = "jdbc:postgresql://localhost:5432/SoundEquipment";
    private final String username = "postgres";
    private final String password = "123";
    private Connection connect;


    DBConnection() {
    }

    Connection getConnection() {
        try {
            this.connect = DriverManager.getConnection(dburl , username, password);

        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return this.connect;
    }

    void close(Connection connect, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (connect != null) {
                connect.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            if (rs != null) {
                rs.close();
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public void close(Connection connect, PreparedStatement pstmt) {
        try {
            this.close(connect, pstmt, null);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void close(PreparedStatement pstmt) {
        try {
            this.close(null, pstmt, null);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

}

