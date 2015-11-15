package com.jazzcript.assignment2.db;

import com.jazzcript.assignment2.cache.CacheConfig;
import com.jazzcript.assignment2.models.BookModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class DBWorker {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
jdbcTemplate=new JdbcTemplate(dataSource);
    }


    @CacheEvict(value = CacheConfig.CACHE_BOOKS,allEntries = true)
    public void addBook(BookModel bookModel){

        String sql="INSERT INTO books(name,author,isbn,price) VALUES(?,?,?,?)";


        jdbcTemplate.update(sql, bookModel.getName(), bookModel.getIsbn(), bookModel.getAuthor(), bookModel.getPrice());

    }

    @Cacheable(CacheConfig.CACHE_BOOKS)
    public List<BookModel> getBooks(){
       simulateSlowService();
        String sql="SELECT * FROM books";


        List<BookModel> bookModels =jdbcTemplate.query(sql, new RowMapper<BookModel>() {
            @Override
            public BookModel mapRow(ResultSet resultSet, int i) throws SQLException {

                BookModel bookModel =new BookModel( resultSet.getString("name"),
                        resultSet.getString("author"),
                        resultSet.getString("isbn"),
                        resultSet.getFloat("price"));


                return bookModel;
            }
        });

        return bookModels;
    }





    private void simulateSlowService() {
        try {
            long time = 5000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }






}
