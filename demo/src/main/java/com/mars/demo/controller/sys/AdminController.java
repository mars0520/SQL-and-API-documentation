package com.mars.demo.controller.sys;

import com.mars.demo.base.bean.ResponseResult;
import com.mars.demo.bean.dto.PageInfoDTO;
import com.mars.demo.bean.entity.SysMenu;
import com.mars.demo.bean.entity.SysRole;
import com.mars.demo.bean.entity.SysUser;
import com.mars.demo.controller.BaseController;
import com.mars.demo.util.ParamCheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @description 管理模块(用户/角色/菜单/权限)
 * @author xzp
 * @date 2020/12/29
 **/
@RestController
@CrossOrigin(originPatterns = "*", maxAge = 3600)
@Api(tags = "管理模块(用户/角色/菜单/权限)")
@RequestMapping("/api/admin")
public class AdminController extends BaseController {

    @PostMapping("/update/password")
    @ApiOperation(value = "修改用户密码", notes = "修改用户密码")
    public ResponseResult updatePassword(@RequestBody SysUser sysUser){
        ParamCheckUtil.notNull(sysUser.getId(), "用户ID");
        ParamCheckUtil.notNullInScope(sysUser.getPassword(), "用户密码", 6, 32);
        adminService.updatePassword(sysUser);
        return ResponseResult.responseOk();
    }

    @PostMapping("/save/user")
    @ApiOperation(value = "保存用户信息", notes = "保存用户信息")
    public ResponseResult saveUser(@RequestBody @Validated({SysUser.SaveEntity.class}) SysUser sysUser){
        adminService.saveUser(sysUser);
        return ResponseResult.responseOk();
    }

    @PostMapping("/save/role")
    @ApiOperation(value = "保存角色信息", notes = "保存角色信息")
    public ResponseResult saveRole(@RequestBody @Validated({SysRole.SaveEntity.class}) SysRole sysRole){
        adminService.saveRole(sysRole);
        return ResponseResult.responseOk();
    }

    @PostMapping("/save/menu")
    @ApiOperation(value = "保存菜单信息", notes = "保存菜单信息")
    public ResponseResult saveMenu(@RequestBody @Validated({SysMenu.SaveEntity.class}) SysMenu sysMenu){
        adminService.saveMenu(sysMenu);
        return ResponseResult.responseOk();
    }

    @DeleteMapping("/remove")
    @ApiOperation(value = "根据类型删除数据", notes = "请求参数 type:类型(1用户/2角色/3菜单),id:主键")
    public ResponseResult remove(Integer type, Integer id){
        validationAdminType(type);
        adminService.remove(type, id);
        return ResponseResult.responseOk();
    }

    @GetMapping("/getInfo")
    @ApiOperation(value = "根据类型查看详情", notes = "请求参数 type:类型(1用户/2角色/3菜单),id:主键")
    public ResponseResult getInfo(Integer type, Integer id){
        validationAdminType(type);
        return ResponseResult.responseOk(adminService.getInfo(type, id));
    }

    @GetMapping("/listUser")
    @ApiOperation(value = "获取用户列表", notes = "请求参数 currentPage:当前页,pageSize:每页条数")
    public ResponseResult listUser(Integer currentPage, Integer pageSize){
        PageInfoDTO pageInfo = new PageInfoDTO(currentPage, pageSize);
        return ResponseResult.responseOk(adminService.listUser(pageInfo.getCurrentPage(), pageInfo.getPageSize()));
    }

    @GetMapping("/listRole")
    @ApiOperation(value = "获取角色列表", notes = "请求参数 currentPage:当前页,pageSize:每页条数")
    public ResponseResult listRole(Integer currentPage, Integer pageSize){
        PageInfoDTO pageInfo = new PageInfoDTO(currentPage, pageSize);
        return ResponseResult.responseOk(adminService.listRole(pageInfo.getCurrentPage(), pageInfo.getPageSize()));
    }

    @GetMapping("/listMenu")
    @ApiOperation(value = "获取菜单权限列表", notes = "请求参数 category:分类(0菜单/1权限),currentPage:当前页,pageSize:每页条数")
    public ResponseResult listMenu(Integer category, Integer currentPage, Integer pageSize){
        PageInfoDTO pageInfo = new PageInfoDTO(currentPage, pageSize);
        ParamCheckUtil.notNullInScope(category, "分类", 0, 3);
        return ResponseResult.responseOk(adminService.listMenu(category, pageInfo.getCurrentPage(), pageInfo.getPageSize()));
    }

    @GetMapping("/listUserRole")
    @ApiOperation(value = "获取用户角色联动列表", notes = "请求参数 currentPage:当前页,pageSize:每页条数")
    public ResponseResult listUserRole(Integer currentPage, Integer pageSize){
        PageInfoDTO pageInfo = new PageInfoDTO(currentPage, pageSize);
        return ResponseResult.responseOk(adminService.listUserRole(pageInfo.getCurrentPage(), pageInfo.getPageSize()));
    }

    @GetMapping("/listRoleMenu")
    @ApiOperation(value = "获取角色菜单权限联动列表", notes = "请求参数 category:分类(0菜单/1权限),currentPage:当前页,pageSize:每页条数")
    public ResponseResult listRoleMenu(Integer category, Integer currentPage, Integer pageSize){
        PageInfoDTO pageInfo = new PageInfoDTO(currentPage, pageSize);
        ParamCheckUtil.notNullInScope(category, "分类", 0, 1);
        return ResponseResult.responseOk(adminService.listRoleMenu(category, pageInfo.getCurrentPage(), pageInfo.getPageSize()));
    }



}
