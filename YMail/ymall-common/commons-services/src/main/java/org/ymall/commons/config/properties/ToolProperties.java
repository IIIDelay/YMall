package org.ymall.commons.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ruijie.tool")
@Data
public class ToolProperties {
    /**
     * dev及uat默认邮箱前缀  多人使用xxx,xxx,xx,xxxx格式
     */
    private String emailDefault;
    /**
     * dev及uat默认 同时发送给多人，请用 ';'隔离，例：TO:'xxx;xxx'
     */
    private String wechatDefault;
    /**
     * CPU核数，填写数字
     */
    private Integer poolCpuNumber;
    /**
     * 可填属性:io,cpu。CPU密集型 : 指的是程序主要发生cpu的运算，高并发、任务执行时间短的业务。IO密集型: 远程调用RPC，操作数据库等
     */
    private String poolType = "io";
    /**
     * 线程前缀
     */
    private String poolName = "tool-box-";
}
