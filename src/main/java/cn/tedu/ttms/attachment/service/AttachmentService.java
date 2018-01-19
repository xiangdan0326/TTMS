package cn.tedu.ttms.attachment.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.tedu.ttms.attachment.entity.Attachment;

public interface AttachmentService {
	/**用于实现文件上传*/
	void uploadObject(String title , MultipartFile mFile);
	 
	List<Attachment> findObjects();
	
	Attachment findObjectByDigest(String digest);
}
