package pl.mentoring.t4_simple_object_pool.implementation;

import pl.mentoring.t4_simple_object_pool.Validator;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCConnectionValidator implements Validator {

    @Override
    public boolean isValid(Object connection) {
        if (connection == null) {
            return false;
        }

        try {
            Connection con = (Connection) connection;
            return !con.isClosed();
        } catch (SQLException se) {
            return false;
        }
    }

    @Override
    public void invalidate(Object connection) {
        try {
            Connection con = (Connection) connection;
            con.close();
        } catch (SQLException ignored) {
            // Exception is ignored
        }
    }
}
