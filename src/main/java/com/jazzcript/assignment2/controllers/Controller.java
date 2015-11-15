package com.jazzcript.assignment2.controllers;

import com.jazzcript.assignment2.db.DBWorker;
import com.jazzcript.assignment2.models.BookModel;

import com.jazzcript.assignment2.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

/**
 * Emre Bilir
 */
@org.springframework.stereotype.Controller
@RequestMapping("/")
@EnableCaching
public class Controller {

    @Autowired
    DBWorker dbWorker;

    public static final String TOKEN_NAME="token";

    public static final String LISTBOOK_MODEL_NAME="books";

    //Path and filename are same
    public static final String SECURITY_FAIL_PATH="secfailed";
    public static final String INVALID_DATA_FAIL_PATH="failed";
    public static final String ADDBOOK_PATH="addbook";
    public static final String LISTBOOK_PATH="listbook";


    @RequestMapping(method = RequestMethod.GET)
    public String showIndex(){


        return "index";
    }


    @RequestMapping(value = ADDBOOK_PATH,method = RequestMethod.GET)
    public ModelAndView addBook(HttpSession session, HttpServletRequest request){
        ModelAndView modelAndView= new ModelAndView(ADDBOOK_PATH);

        if(session.getAttribute(TOKEN_NAME)==null){
           String csrfToken=Utils.generateToken();
            session.setAttribute(TOKEN_NAME,csrfToken);
            modelAndView.addObject(TOKEN_NAME,csrfToken);
        }else{
            modelAndView.addObject(TOKEN_NAME,session.getAttribute(TOKEN_NAME));

        }

        return modelAndView;
    }

    @RequestMapping(value = ADDBOOK_PATH,method = RequestMethod.POST)
    public String addBookWorker(HttpServletRequest request, HttpServletResponse response, HttpSession session){


        if(session.getAttribute(TOKEN_NAME)==null || !session.getAttribute(TOKEN_NAME).equals(request.getParameter(TOKEN_NAME)))
            return SECURITY_FAIL_PATH;

        Map<String,String[]> paramMap=request.getParameterMap();


if(paramMap!=null && Utils.checkValidData(paramMap)) {

    String bookName =paramMap.get("name")[0];
    String author = paramMap.get("author")[0];
    String isbn = paramMap.get("isbn")[0];
    float price = Float.parseFloat(paramMap.get("price")[0]);
    BookModel bookModel = new BookModel( bookName, author, isbn, price);

    dbWorker.addBook(bookModel);


    return "redirect:"+ADDBOOK_PATH;
}
        else return INVALID_DATA_FAIL_PATH;
    }



    @RequestMapping(value = LISTBOOK_PATH,method = RequestMethod.GET)
    public ModelAndView showBooks(){

        ModelAndView modelAndView=new ModelAndView(LISTBOOK_PATH);

        List<BookModel> bookModels= dbWorker.getBooks();

         modelAndView.addObject(LISTBOOK_MODEL_NAME,bookModels);

        System.out.println(bookModels.size());

        return modelAndView;

    }




}
