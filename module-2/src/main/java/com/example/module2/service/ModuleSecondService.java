package com.example.module2.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ModuleSecondService {

    /**
     * 文件转换.
     *
     * @param httpServletRequest  request
     * @param httpServletResponse response
     */
    void fileConvert(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) throws Exception;

}
