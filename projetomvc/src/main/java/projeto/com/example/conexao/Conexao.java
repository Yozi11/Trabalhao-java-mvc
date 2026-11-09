package projeto.com.example.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/empresa_db";
    private static final String USER = "root"; // Altere se necessário
    private static final String PASS = "";     // Altere se necessário

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}