package net.codingme.ssm.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import net.codingme.ssm.po.SysPermission;
import net.codingme.ssm.po.SysPermissionExample;

/**
 * 
 * <p>Description：权限操作接口</p>
 * 
 * @author  NiuJinpeng
 * @date    2018年1月14日下午9:11:14
 * @version 1.0
 */
public interface SysPermissionMapper {
    int countByExample(SysPermissionExample example);

    int deleteByExample(SysPermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    List<SysPermission> selectByExample(SysPermissionExample example);

    SysPermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysPermission record, @Param("example") SysPermissionExample example);

    int updateByExample(@Param("record") SysPermission record, @Param("example") SysPermissionExample example);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);
}