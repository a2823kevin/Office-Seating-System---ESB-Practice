package com.a2823kevin.backend.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {
    private final DataSource dataSource;

    List<String> tableCreatingOrder = List.of("SeatingChart", "Employee");
    Map<String, String> tableInsertSQL = Map.of(
        "SeatingChart", "DB/seating_chart.sql", 
        "Employee", "DB/employee.sql"
    );

    @Override
    public void run(String... args) throws Exception {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        // use "$$" to avoid conflict of procedures's ";"
        databasePopulator.setSeparator("$$");

        // init tables & procedures
        databasePopulator.addScript(new ClassPathResource("DB/create_tables.sql"));
        databasePopulator.addScript(new ClassPathResource("DB/create_procedures.sql"));

        // check if tables exist, if not, insert datas
        for (String tableName: tableCreatingOrder) {
            System.out.println(tableName);
            try {
                if (!isTableExist(tableName)) {
                    databasePopulator.addScript(new ClassPathResource(tableInsertSQL.get(tableName)));
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        databasePopulator.execute(dataSource);
    }
    
    private boolean isTableExist(String tableName) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName.toUpperCase(), new String[]{"TABLE"});
            return resultSet.next();
        }
    }
}
