package com.example.moudle1.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moudle1.service.ModuleFirstService;

@RestController
@RequestMapping("/moudle-first")
public class ModuleFirstController {

    private final ModuleFirstService moduleFirstService;

    public ModuleFirstController(final ModuleFirstService moduleFirstService) {
        this.moduleFirstService = moduleFirstService;
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
        moduleFirstService.fileConvert(httpServletRequest, httpServletResponse);
    }


}
