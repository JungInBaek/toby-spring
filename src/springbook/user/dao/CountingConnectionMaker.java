package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
    int counter = 0;
    private ConnectionMaker realConnectionMaker;

    //  생성자
    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }

    //  커넥션 반환
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        this.counter++;

        return realConnectionMaker.makeConnection();
    }

    //  카운터 반환
    public int getCounter() {
        return this.counter;
    }
}
