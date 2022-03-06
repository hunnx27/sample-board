package com.twodollar.sample;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;

@RestController
public class IndexController {


    @GetMapping("/")
    void root(HttpServletResponse response) throws IOException {
        response.sendRedirect("/pages/board/list.html");
    }


}