package com.example.tink_lab.repositories;

import com.example.tink_lab.configs.DatabaseConfig;
import com.example.tink_lab.models.RequestLog;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <b>Используется для обращения к одной конкретной базе данных</b>
 * <p>Класс имеет следующую функциональность:</p>
 * <ul>
 *     <li>Создание подключения к БД</li>
 *     <li>Создание таблицы по-умолчанию</li>
 *     <li>Создание следующих подготовленных выражений:<ul>
 *         <li>Добавление записи о запросе</li>
 *     </ul></li>
 * </ul>
 * <p>Все перечисленные функции имеют перехват ошибок с выводом информации</p>
 */
@Repository
public class RequestRepository {
    /**
     * Подготовленное выражение для создания в БД записи о запросе
     */
    private PreparedStatement addStatement;

    /**
     * Конструктор при создании объекта также создает подключение и стандартную таблицу
     */
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

    /**
     * <b>Создает в БД запись о запросе</b>
     * @param requestLog Лог запроса на перевод
     */
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
