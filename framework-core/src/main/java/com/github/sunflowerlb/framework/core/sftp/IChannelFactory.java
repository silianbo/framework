package com.github.sunflowerlb.framework.core.sftp;

import com.jcraft.jsch.Channel;

/**
 * use to get Channel
 * 
 * @author lb
 *
 */
public interface IChannelFactory {

    /**
     * 
     * @return connected channel;otherwise null if get failure.
     */
    public Channel openChannel();

    /**
     * close channel resources.
     */
    public void close(Channel channel);
    
    /**
     * destory the session
     */
    public void destory();
    
    /**
     * init the sftp params
     */
    public void init();
}
