package com.example.moudle1.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ModuleFirstService {

    /**
     * 文件转换.
     *
     * @param httpServletRequest  request
     * @param httpServletResponse response
     */
    void fileConvert(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) throws Exception;

}
