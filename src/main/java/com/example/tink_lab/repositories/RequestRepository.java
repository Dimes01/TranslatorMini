package com.example.tink_lab.repositories;

import com.example.tink_lab.configs.DatabaseConfig;
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
     * Подготовленное выражение для создания в БД записи о запросе
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
        //TODO: Исправить, чтобы при ошибке подключения объект репозитория не создавался
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
            System.out.println("Connection failed...");
            System.out.println(ex.getMessage());
        }
    }

    /**
     * <b>Создает в БД запись о запросе</b>
     * @param requestLog Лог запроса на перевод
     */
    public void SaveRequest(RequestLog requestLog) {
        // TODO: исправить так, чтобы при неудачной попытке добавления выбрасывало ошибку
        try {
            addStatement.setString(0, requestLog.GetIP());
            addStatement.setString(1, requestLog.GetSourceText());
            addStatement.setString(2, requestLog.GetTranslatedText());
            addStatement.execute();
        } catch (SQLException e) {
            System.out.println("Save request failed...");
        }
    }

    /**
     * <b>Возвращает список логов всех запросов из БД.</b>
     * <p>NOTE! Пустой список возвращается либо при отсутствии данных, либо при ошибке</p>
     * @return Список всех запросов.
     */
    public LinkedList<RequestLog> GetAllRequests() {
        var list = new LinkedList<RequestLog>();
        try {
            var resultSet = selectAllStatement.executeQuery("SELECT * FROM requests");
            while (resultSet.next()) {
                var ip = resultSet.getString(1);
                var sourceText = resultSet.getString(2);
                var translatedText = resultSet.getString(3);
                list.add(new RequestLog(sourceText, translatedText, ip));
            }
        } catch (SQLException e) {
            System.out.println("Select requests failed...");
        }
        return list;
    }
}
