package kr.co.leem.libs.components;

import kr.co.leem.domains.integration.ftp.Ftp;
import kr.co.leem.libs.integration.ftp.FtpSessionFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.integration.ftp.session.FtpSession;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2015-04-20.
 */
@Component
public class FtpClientComponent {
	/**
	 * FTP 접속 후 FtpSession을 반환함.
	 *
	 * @param ftp
	 * @return
	 * @throws Exception
	 */
	private FtpSession getFtpSession(Ftp ftp) throws Exception {
		FtpSessionFactory ftpSessionFactory = new FtpSessionFactory();
		ftpSessionFactory.setHost(ftp.getFtpHost());
		ftpSessionFactory.setPort(ftp.getFtpPort());
		ftpSessionFactory.setUsername(ftp.getFtpUsername());
		ftpSessionFactory.setPassword(ftp.getFtpPassword());
		ftpSessionFactory.setClientMode(0);
		ftpSessionFactory.setFileType(2);
		ftpSessionFactory.setBufferSize(100000);

		return ftpSessionFactory.getSession();
	}

	/**
	 * 지정된 경로의 파일 목록을 반환함.
	 *
	 * @param ftp
	 * @param path
	 * @return
	 */
	public FTPFile[] getList(Ftp ftp, String path) {
		FtpSession ftpSession = null;
		FTPFile[] result = null;

		try {
			ftpSession = getFtpSession(ftp);

			path = StringUtils.defaultIfEmpty(path, "/");

			result = ftpSession.list(path);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftpSession != null && ftpSession.isOpen()) {
				ftpSession.close();
			}
		}

		return result;
	}

	/**
	 * FTP로부터 파일을 읽어옴.
	 *
	 * @param ftp
	 * @param ftpFilePath
	 * @param savePath
	 */
	public void read(Ftp ftp, String ftpFilePath, String savePath, String saveFileName) {
		FtpSession ftpSession = null;
		FileOutputStream fos = null;

		try {
			File file = new File(savePath);

			if (file.exists() == false) {
				file.mkdir();
			}

			ftpSession = getFtpSession(ftp);
			fos = new FileOutputStream(savePath + saveFileName);
			ftpSession.read(ftpFilePath, fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (ftpSession != null && ftpSession.isOpen()) {
				ftpSession.close();
			}
		}
	}

	/**
	 * FTP에 파일을 저장함.
	 *
	 * @param ftp
	 * @param ftpFilePath
	 * @param ftpSavePath
	 */
	public void write(Ftp ftp, String ftpFilePath, String ftpSavePath) {
		FtpSession ftpSession = null;
		FileInputStream fis = null;
		
		try {
			ftpSession = getFtpSession(ftp);
			
			File file = new File(ftpFilePath);
			fis = new FileInputStream(file);
			
			ftpSession.write(fis, ftpSavePath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (ftpSession != null && ftpSession.isOpen()) {
				ftpSession.close();
			}
		}
	}
}