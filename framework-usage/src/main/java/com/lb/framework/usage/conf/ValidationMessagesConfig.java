package com.lb.framework.usage.conf;

import com.baidu.disconf.client.common.annotations.DisconfFile;

/**
 * 校验框架错误信息的配置，此处仅仅只是使用了disconf的配置文件更新功能，而没有用它的更新springBean的功能
 * @author lb
 */
@DisconfFile(filename = "ValidationMessages_zh_CN.properties")
public class ValidationMessagesConfig {

}
