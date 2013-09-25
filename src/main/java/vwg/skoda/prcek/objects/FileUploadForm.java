package vwg.skoda.prcek.objects;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

@Async
public class FileUploadForm {
	
	// ZDROJ: http://viralpatel.net/blogs/spring-mvc-multiple-file-upload-example/
	
	private List<MultipartFile> files;

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	
	
}
