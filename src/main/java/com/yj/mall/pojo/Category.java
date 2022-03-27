package com.yj.mall.pojo;

import lombok.Data;

import java.util.Date;

/**
 * po(persistent object)
 * pojo(plain ordinary java object)
 * create by 86136
 * 2022/3/17 16:03
 */
@Data
public class Category {
//CREATE TABLE `mall_category` (
//  `id` int NOT NULL AUTO_INCREMENT COMMENT '类别Id',
//  `parent_id` int DEFAULT NULL COMMENT '父类别id当id=0时说明是根节点,一级类别',
//  `name` varchar(50) DEFAULT NULL COMMENT '类别名称',
//  `status` tinyint(1) DEFAULT '1' COMMENT '类别状态1-正常,2-已废弃',
//  `sort_order` int DEFAULT NULL COMMENT '排序编号,同类展示顺序,数值相等则自然排序',
//  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
//  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
//  PRIMARY KEY (`id`)
//) ENGINE=InnoDB AUTO_INCREMENT=100031 DEFAULT CHARSET=utf8mb3;
    private Integer id;
    private Integer parentId;
    private String name;
    private Integer status;
    private Integer sortOrder;
    private Date createTime;
    private Date updateTime;


}
