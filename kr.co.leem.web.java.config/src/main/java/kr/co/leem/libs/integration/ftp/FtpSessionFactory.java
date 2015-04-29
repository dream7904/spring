package kr.co.leem.libs.integration.ftp;

import org.apache.commons.net.ftp.FTPClient;

import java.nio.charset.Charset;

/**
 * Created by Administrator on 2015-04-20.
 */
public class FtpSessionFactory  extends AbstractFtpSessionFactory<FTPClient> {
	public FtpSessionFactory() {

	}

	protected FTPClient createClientInstance() {
		FTPClient ftpClient = new FTPClient();

		ftpClient.setControlEncoding("UTF-8");
		ftpClient.setCharset(Charset.forName("UTF-8"));

		return ftpClient;
	}
}