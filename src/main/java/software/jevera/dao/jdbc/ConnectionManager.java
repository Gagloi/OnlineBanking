package software.jevera.dao.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;

@Component
@RequiredArgsConstructor
public class ConnectionManager {

    private final DataSourceProperties properties;

    @PostConstruct
    @SneakyThrows
    public void init(){
        Class.forName(properties.getDriverClassName());
    }

    @SneakyThrows
    protected Connection createConnection(){
        return DriverManager.getConnection(properties.getUrl(),
                                            properties.getUsername(),
                                            properties.getPassword());
    }

}
