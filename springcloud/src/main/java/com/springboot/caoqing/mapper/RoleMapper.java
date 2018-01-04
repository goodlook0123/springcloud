package com.springboot.caoqing.mapper;

import com.springboot.caoqing.bean.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "roleMapper")
public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    List<Role> findRoleByModele(String moduleName);
}