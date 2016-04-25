package com.github.sunflowerlb.framework.core.sftp.monitor;

import java.util.concurrent.atomic.AtomicInteger;

import com.jcraft.jsch.SftpProgressMonitor;

public class UploadMonitor implements SftpProgressMonitor {
    private final AtomicInteger transferred = new AtomicInteger();

    public void init(int op, String src, String dest, long max) {

    }

    public boolean count(long count) {
        transferred.incrementAndGet();
        return true;
    }

    public void end() {

    }

}
