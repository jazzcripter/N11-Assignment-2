package com.jazzcript.assignment2.utils;

import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.Map;

/**
 * Created by rashakol on 15.11.2015.
 */
public class Utils {


    public static boolean checkValidData(Map<String,String[]> reqParams) {


        if (reqParams == null)
            return false;

        String bookName = reqParams.get("name") != null ? reqParams.get("name")[0] : null;
        String author = reqParams.get("author") != null ? reqParams.get("author")[0] : null;
        String isbn = reqParams.get("isbn") != null ? reqParams.get("isbn")[0] : null;
        String price = reqParams.get("isbn") != null ? reqParams.get("price")[0] : null;

        return !(bookName == null || author == null || isbn == null || price == null) &&
                !(bookName.length() == 0 || author.length() == 0 || isbn.length() == 0 || !isNumeric(price));


    }

    public static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }


    private static final SecureRandom random = new SecureRandom();

    public static String generateToken(){
        final byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64Utils.encodeToString(bytes);
    }
}
