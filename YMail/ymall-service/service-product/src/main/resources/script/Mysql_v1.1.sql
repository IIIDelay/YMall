/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

-- product 日志记录表
create table if not exists bus_log
(
    id bigint auto_increment comment '自增id'
    primary key,
    bus_name varchar(100) null comment '业务名称',
    bus_descrip varchar(255) null comment '业务操作描述',
    oper_person varchar(100) null comment '操作人',
    oper_time datetime null comment '操作时间',
    ip_from varchar(50) null comment '操作来源ip',
    param_file varchar(255) null comment '操作参数报文文件'
)
comment '业务操作日志' default charset ='utf8';