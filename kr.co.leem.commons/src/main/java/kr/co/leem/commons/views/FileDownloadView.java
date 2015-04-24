package kr.co.leem.commons.views;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

@SuppressWarnings("rawtypes") 
public class FileDownloadView extends AbstractView {
	@Override
	protected void renderMergedOutputModel(Map arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) throws Exception {
		File file = (File)arg0.get("downloadFile");

		String origFileName = (String) arg0.get("origFileName");

		arg2.setContentType("application/octet-stream");
		arg2.setContentLength((int)file.length());
		arg2.setHeader("Content-Disposition", "attachment; fileName=\"" + origFileName + "\";");
		arg2.setHeader("Content-Transfer-Encoding", "binary");

		OutputStream out = arg2.getOutputStream();

		FileInputStream fis = null;
		
		try {
			fis= new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		} finally {
			if (fis!=null) {
				try {
					fis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		out.flush();
	}
}