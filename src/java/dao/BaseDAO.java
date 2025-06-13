package dao;

//import utils.*;
import classes.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;

public abstract class BaseDAO {
    protected JDBC db = new JDBC();
}
