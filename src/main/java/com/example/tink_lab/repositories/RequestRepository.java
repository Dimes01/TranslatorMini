package com.example.tink_lab.repositories;

import com.example.tink_lab.configs.DatabaseConfig;
import com.example.tink_lab.models.RequestLog;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class RequestRepository {
    private PreparedStatement addStatement;

    public RequestRepository() {
        try {
            DatabaseConfig dbConf = new DatabaseConfig();
            Class.forName(dbConf.driverClassName).getDeclaredConstructor().newInstance();
            Connection conn = DriverManager.getConnection(dbConf.url, dbConf.user, dbConf.password);
            var createStatement = conn.createStatement();
            createStatement.execute("CREATE TABLE requests (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "ip_address VARCHAR(15)," +
                    "source_text TEXT," +
                    "translated_text TEXT)");
            addStatement = conn.prepareStatement("INSERT INTO requests (ip_address, source_text, translated_text) VALUES (?, ?, ?)");
        }
        catch(Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex.getMessage());
        }
    }

    public void SaveRequest(RequestLog requestLog) {
        try {
            addStatement.setString(0, requestLog.GetIP());
            addStatement.setString(1, requestLog.GetSourceText());
            addStatement.setString(2, requestLog.GetTranslatedText());
            addStatement.execute();
        } catch (SQLException e) {
            System.out.println("Save request failed...");
        }
    }
}
