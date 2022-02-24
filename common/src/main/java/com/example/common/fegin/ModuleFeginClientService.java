package com.example.common.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("microservice-provider-user")
public interface ModuleFeginClientService {

    @RequestMapping(value = "/simple/{id}", method = RequestMethod.GET)
    public void getUserById(@PathVariable("id") Long id);

}

