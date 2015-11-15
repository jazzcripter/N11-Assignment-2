package com.jazzcript.assignment2.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Repository
public class InitDB {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){

        jdbcTemplate=new JdbcTemplate(dataSource);

    }


    @PostConstruct
    public void initialize(){




        String books="CREATE TABLE IF NOT EXISTS  books(id INT AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                "name VARCHAR(200)," +
                "author VARCHAR(200)," +
                "isbn VARCHAR(200)," +
                "price FLOAT )";


        jdbcTemplate.execute(books);



    }

}
