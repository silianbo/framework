/**
 * 
 */
package com.github.sunflowerlb.framework.usage.service.inner;

import org.springframework.stereotype.Service;

/**
 * 
 * @author lb
 */
@Service
public class TestService {

    public void sleep(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
