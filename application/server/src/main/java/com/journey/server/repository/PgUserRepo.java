package com.journey.server.repository;

import com.journey.server.entity.UserEntity;
import com.journey.server.exceptions.LoginConflictException;
import com.journey.server.exceptions.WrongPasswordException;
import com.journey.server.service.IUserRepo;
import com.journey.server.utils.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Репозиторий используется для работы с таблицей users базы данных PostgreSQL
 * Для подключения используется драйвер JDBC
 */
@Repository
public class PgUserRepo implements IUserRepo {
    private final Connection conn = ConnectionManager.open();

    // https://postgrespro.ru/docs/postgresql/14/errcodes-appendix
    private final String UNIQUE_VIOLATION_STATE = "23505";

    /**
     * Получение информации о пользователе по идентификатору
     * @param id идентификатор пользователя, информацию о котором требуется получить
     * @return объект UserEntity, содержащий информацию о пользователе
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    @Override
    public UserEntity getUserById(int id) throws SQLException {
        UserEntity user = null;

        String getUser = "SELECT user_id, login, hash, name " +
                "FROM public.users WHERE user_id = ?";

        PreparedStatement userQuery = conn.prepareStatement(getUser);
        userQuery.setInt(1, id);
        ResultSet rs = userQuery.executeQuery();

        if (rs.next()) {
            user = UserEntity.builder()
                    .id(rs.getInt("user_id"))
                    .login(rs.getString("login"))
                    .hash(rs.getString("hash"))
                    .name(rs.getString("name"))
                    .build();
        }

        return user;
    }

    /**
     * Создание новой записи о пользователе в базе данных
     * @param user сущность UserEntity, содержашая информацию о пользователе при его регистрации
     * @return идентификатор, назначенный новому пользователю в базе данных
     * @throws LoginConflictException если пользователь с указанным при регистрации логином уже занят
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    @Override
    public int createUser(UserEntity user)
            throws LoginConflictException, SQLException {
        int id = 0;
        try {
            String userAdd = "INSERT INTO public.users (login, hash, name) " +
                                "VALUES (?, ?, ?)";

            PreparedStatement userInsertion = conn.prepareStatement(userAdd);
            userInsertion.setString(1, user.getLogin());
            userInsertion.setString(2, user.getHash());
            userInsertion.setString(3, user.getName());
            userInsertion.executeUpdate();

            String checkId = "SELECT user_id from public.users where login = ?";
            PreparedStatement check = conn.prepareStatement(checkId);
            check.setString(1, user.getLogin());

            ResultSet rs = check.executeQuery();
            rs.next();
            id = rs.getInt("user_id");
        } catch (SQLException e) {
            e.printStackTrace();

            if (e.getSQLState().equals(UNIQUE_VIOLATION_STATE))
                throw new LoginConflictException("Логин " + user.getLogin() +
                                                " занят другим пользователем");
            else
                throw new SQLException("Не удалось добавить пользователя " +
                                        "в базу данных");
        }

        return id;
    }

    /**
     * Проверка логина и пароля при входе
     * @param login логин пользователя
     * @param hash хеш пароля пользователя (SHA-256)
     * @return идентификатор пользователя, если в БД есть запись с указанными логином и паролем
     * @throws WrongPasswordException при несоответствии введенных логина и пароля
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    @Override
    public int authenticate(String login, String hash)
            throws WrongPasswordException, SQLException {
        int id = 0;
        try {
            String checkPass = "SELECT user_id FROM public.users " +
                    "WHERE login = ? AND hash = ?";

            PreparedStatement passQuery = conn.prepareStatement(checkPass);
            passQuery.setString(1, login);
            passQuery.setString(2, hash);
            ResultSet rs = passQuery.executeQuery();

            if (!rs.next())
                throw new WrongPasswordException("Неверный логин или пароль.");

            id = rs.getInt("user_id");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Не удалось выполнить аутентификацию.");
        }

        return id;
    }
}
