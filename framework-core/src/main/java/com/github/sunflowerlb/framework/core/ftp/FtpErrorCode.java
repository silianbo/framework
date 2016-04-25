package com.github.sunflowerlb.framework.core.ftp;

import com.github.sunflowerlb.framework.core.exception.BusinessException;

public enum FtpErrorCode  {

    CHANGE_DIRECTORY_FAILURE(46003,"change directory [%s] failure."),
    FTP_CONNECT_FAILURE(46004,"ftp connect failure."),
    FTP_LOGIN_FAILURE(46005,"ftp login failure."),
    FTP_DISCONNECT_FAILURE(46006,"ftp disconnect failure."),
    FTP_UPLOAD_FAILURE(46007,"ftp upload [%s] failure."),
    FTP_DOWNLOAD_FAILURE(46008,"ftp download [%s] failure."),
    FTP_FILE_NOT_FOUND(46009,"ftp upload [%s] failure."),
    FTP_CREATE_REMOTE_DIRECTORY_FAILURE(46011,"ftp create reomte directory [%s] failure.");
    private int code;
    private String message;

    private FtpErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException exp() {
        return new BusinessException(code, message);
    }

    public BusinessException exp(Object... args) {
        String formatReason = String.format(message, args);
        return new BusinessException(code, formatReason);
    }

    public BusinessException expMsg(String message, Object... args) {
        String formatReason = String.format(message, args);
        return new BusinessException(code, formatReason);
    }

	public BusinessException exp(Throwable t, Object... args) {
		String formatReason = String.format(message, args);
		return new BusinessException(code, formatReason,t);
	}

	public BusinessException expMsg(String message, Throwable t,
			Object... args) {
		String formatReason = String.format(message, args);
		return new BusinessException(code, formatReason,t);
	}
}
