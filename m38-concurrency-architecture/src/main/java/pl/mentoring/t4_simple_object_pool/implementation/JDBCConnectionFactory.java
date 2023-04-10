package pl.mentoring.t4_simple_object_pool.implementation;

import pl.mentoring.t4_simple_object_pool.ObjectFactory;

import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionFactory implements ObjectFactory {

    private final String connectionURL;
    private final String userName;
    private final String password;

    public JDBCConnectionFactory(String connectionURL, String userName, String password) {
        this.connectionURL = connectionURL;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public Object createNew() {
        try {
            return DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Unable to create new connection", e);
        }
    }
}
