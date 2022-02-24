package com.example.module2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.module2.service.ModuleSecondService;

@RestController
@RequestMapping("/module-first")
public class ModuleSecondController {


    private final ModuleSecondService moduleSecondService;

    public ModuleSecondController(final ModuleSecondService moduleFirstService) {
        moduleSecondService = moduleFirstService;
    }

    /**
     * 文件转换.
     *
     * @param httpServletRequest  request
     * @param httpServletResponse response
     */
    @PostMapping("01")
    public void fileConvert(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse)
        throws Exception {
        moduleSecondService.fileConvert(httpServletRequest, httpServletResponse);
    }


}
