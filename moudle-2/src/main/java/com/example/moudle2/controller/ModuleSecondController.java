package com.example.moudle2.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moudle-first")
public class ModuleSecondController {


    /**
     * 文件转换.
     *
     * @param httpServletResponse response
     */
    @PostMapping("01")
    public void fileConvert(final HttpServletResponse httpServletResponse) {

    }


}
