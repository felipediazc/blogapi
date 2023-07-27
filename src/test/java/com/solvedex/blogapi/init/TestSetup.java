package com.solvedex.blogapi.init;

import com.solvedex.blogapi.BlogapiApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BlogapiApplication.class)
@AutoConfigureMockMvc
public class TestSetup {

    @Autowired
    DataSource dataSource;

    private static Boolean dbInitialized = false;

    public void setupDb() {
        if (!dbInitialized) {
            try (Connection conn = dataSource.getConnection()) {
                log.info("************************************ INSTALLING DATABASE DATA *************************** dbInitialized : {}", dbInitialized);
                ScriptUtils.executeSqlScript(conn, new ClassPathResource("blogtest.sql"));
                dbInitialized = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
