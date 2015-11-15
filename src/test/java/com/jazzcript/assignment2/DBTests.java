package com.jazzcript.assignment2;

import com.jazzcript.assignment2.cache.CacheConfig;
import com.jazzcript.assignment2.controllers.Controller;
import com.jazzcript.assignment2.db.DBWorker;
import com.jazzcript.assignment2.models.BookModel;
import com.jazzcript.assignment2.utils.Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.print.Book;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class DBTests {
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    DBWorker dbWorker;


    @Before
    public void setup() throws Exception {

        this.mockMvc = webAppContextSetup(this.wac).build();



    }


    @Test
    public void testCheckValidator(){

        Map<String,String[]> sampleParams=new HashMap<String, String[]>();


        Assert.assertTrue(!Utils.checkValidData(null));


        sampleParams.put("name",new String[]{"kitap"});
        sampleParams.put("author",new String[]{"emre"});
        sampleParams.put("isbn",new String[]{"123kitap"});
        sampleParams.put("price",new String[]{"12.8"});


       Assert.assertTrue(Utils.checkValidData(sampleParams));

        sampleParams.put("name",new String[]{""});
        sampleParams.put("author",new String[]{""});
        sampleParams.put("isbn",new String[]{""});
        sampleParams.put("price",new String[]{"12"});

       Assert.assertTrue(!Utils.checkValidData(sampleParams));

        sampleParams.put("name",new String[]{"kitap"});
        sampleParams.put("author",new String[]{"emre"});
        sampleParams.put("isbn",new String[]{"123kitap"});
        sampleParams.put("price",new String[]{"12.8a"});

       Assert.assertTrue(!Utils.checkValidData(sampleParams));
    }



    @Test
    public void testAddAndList(){

        BookModel sampleBookModel=new BookModel("emre","emre","isbn123",16.8f);

        dbWorker.addBook(sampleBookModel);

        List<BookModel> bookModels=dbWorker.getBooks();

        BookModel receivedBookModel=bookModels.get(bookModels.size()-1);


        Assert.assertTrue(receivedBookModel.getName().equals(receivedBookModel.getName())&&
                receivedBookModel.getAuthor().equals(receivedBookModel.getAuthor())&&
                receivedBookModel.getIsbn().equals(receivedBookModel.getIsbn())&&
                receivedBookModel.getPrice()==receivedBookModel.getPrice());

    }


    @Test
    public void testCSRF() throws Exception {

        final String[] token = new String[1];

        final HttpSession[] session = new HttpSession[1];


        mockMvc.perform(get("/"+Controller.ADDBOOK_PATH)).andExpect(status().isOk()).andExpect(view().name(Controller.ADDBOOK_PATH)).andExpect(model().attributeExists(Controller.TOKEN_NAME)).andDo(new ResultHandler() {
            @Override
            public void handle(MvcResult mvcResult) throws Exception {
                token[0] =(String)mvcResult.getModelAndView().getModel().get(Controller.TOKEN_NAME);
                session[0] =mvcResult.getRequest().getSession();
            }
        });


        Assert.assertNotNull(session[0]);
        Assert.assertNotNull(token[0]);


        mockMvc.perform(post("/"+Controller.ADDBOOK_PATH).param(Controller.TOKEN_NAME,"exactfalsetoken").session((MockHttpSession) session[0])).andExpect(status().isOk()).andExpect(view().name(Controller.SECURITY_FAIL_PATH));

        mockMvc.perform(post("/"+Controller.ADDBOOK_PATH).param(Controller.TOKEN_NAME,token[0]).session((MockHttpSession) session[0])).andExpect(status().isOk()).andExpect(view().name(Controller.INVALID_DATA_FAIL_PATH));


    }





}
