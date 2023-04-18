package com.journey.server.repository;

import com.journey.server.entity.UserEntity;
import com.journey.server.exceptions.LoginConflictException;
import com.journey.server.service.IUserRepo;
import com.journey.server.utils.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PgUserRepo implements IUserRepo {
    private final Connection conn = ConnectionManager.open();

    // https://postgrespro.ru/docs/postgresql/14/errcodes-appendix
    private final String UNIQUE_VIOLATION_STATE = "23505";

    @Override
    public UserEntity getUserById(int id) {
        return UserEntity.builder().name("Bob").build();
    }

    @Override
    public int createUser(UserEntity user)
            throws LoginConflictException, SQLException {
        int id = 0;
        try {
            String userAdd = "INSERT INTO public.users (login, hash, name) " +
                                "VALUES (?, ?, ?)";

            PreparedStatement wordInsertion = conn.prepareStatement(userAdd);
            wordInsertion.setString(1, user.getLogin());
            wordInsertion.setString(2, user.getHash());
            wordInsertion.setString(3, user.getName());
            wordInsertion.executeUpdate();

            String checkId = "SELECT user_id from public.users where login = ?";
            PreparedStatement check = conn.prepareStatement(checkId);
            check.setString(1, user.getLogin());

            ResultSet rs = check.executeQuery();
            rs.next();
            id = rs.getInt("user_id");
        } catch (SQLException e) {
            e.printStackTrace();

            if (e.getSQLState().equals("23505"))
                throw new LoginConflictException("Логин " + user.getLogin() +
                                                " занят другим пользователем");
            else
                throw new SQLException("Не удалось добавить пользователя " +
                                        "в базу данных");
        }

        return id;
    }
}
