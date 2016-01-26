package com.github.sunflowerlb.framework.usage.conf;

import com.baidu.disconf.client.common.annotations.DisconfFile;

/**
 * 错误信息的配置，此处仅仅只是使用了disconf的配置文件更新功能，而没有用它的更新springBean的功能
 * @author zhengxiaohong
 */
@DisconfFile(filename = "ErrorMessages_zh_CN.properties")
public class ErrorMessagesConfig {

}
