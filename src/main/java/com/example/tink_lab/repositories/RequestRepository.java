package com.example.tink_lab.repositories;

import com.example.tink_lab.configs.DatabaseConfig;
import com.example.tink_lab.models.RequestDTO;
import com.example.tink_lab.models.RequestLog;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;

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
    private Connection conn;

    /**
     * <b>Подготовленное выражение для создания в БД записи о запросе</b>
     * <p>Запрос: <code>INSERT INTO requests (ip_address, source_text, translated_text) VALUES (?, ?, ?)</code></p>
     */
    private PreparedStatement addStatement;

    /**
     * Выражение для получения всех логов запросов
     */
    private Statement selectAllStatement;

    /**
     * Конструктор при создании объекта также создает подключение и стандартную таблицу
     */
    public RequestRepository() {
        try {
            DatabaseConfig dbConf = new DatabaseConfig();
            Class.forName(dbConf.driverClassName).getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(dbConf.url, dbConf.user, dbConf.password);
            var createStatement = conn.createStatement();
            createStatement.execute("CREATE TABLE requests (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "ip_address VARCHAR(15)," +
                    "source_text TEXT," +
                    "translated_text TEXT)");
            addStatement = conn.prepareStatement("INSERT INTO requests (ip_address, source_text, translated_text) VALUES (?, ?, ?)");
            selectAllStatement = conn.createStatement();
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * <b>Создает в БД запись о запросе</b>
     * @param requestLog Лог запроса на перевод
     */
    public void SaveRequest(RequestLog requestLog) throws SQLException {
        addStatement.setString(1, requestLog.GetIP());
        addStatement.setString(2, requestLog.GetSourceText());
        addStatement.setString(3, requestLog.GetTranslatedText());
        addStatement.execute();
    }

    /**
     * <b>Возвращает список логов всех запросов из БД.</b>
     * <p>NOTE! Пустой список возвращается либо при отсутствии данных, либо при ошибке</p>
     * @return Список всех запросов.
     */
    public LinkedList<RequestDTO> GetAllRequests() throws SQLException {
        var list = new LinkedList<RequestDTO>();
        var resultSet = selectAllStatement.executeQuery("SELECT * FROM requests");
        while (resultSet.next()) {
            var ip = resultSet.getString("ip_address");
            var sourceText = resultSet.getString("source_text");
            var translatedText = resultSet.getString("translated_text");
            list.add(new RequestDTO(sourceText, translatedText));
        }
        return list;
    }
}
