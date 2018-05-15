package com.example.controller;

import com.example.common.BaseController;
import com.example.common.BusinessException;
import com.example.common.Result;
import com.example.dto.NameDto;
import com.example.entity.User;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin
@RestController
@Api(value = "UserController", description = "用户管理")
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    UserService userService;


    @GetMapping(value = "/findByName")
    @ApiOperation(value = "根据用户姓名查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<List<User>> findByName(@Validated NameDto name){
        List<User> list = userService.findByName(name.getName());
        if(list.size() == 0){
            throw new BusinessException("找不到用户");
        }
        return response(list);
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "分页查询所有用户", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<Page<User>> findAll(@RequestParam(value = "pageIndex", required = false) Integer  pageIndex,
        @RequestParam(value = "pageSize", required = false) Integer pageSize){
        PageRequest pageable = null;
        if(pageSize != null && pageIndex != null){
            pageable = new PageRequest(pageIndex, pageSize);
        }
        return response(userService.findAll(pageable));
    }
}
