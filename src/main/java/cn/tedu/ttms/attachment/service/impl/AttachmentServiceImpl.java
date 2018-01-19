package cn.tedu.ttms.attachment.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.tedu.ttms.attachment.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;

import cn.tedu.ttms.attachment.dao.AttachmentDao;
import cn.tedu.ttms.attachment.entity.Attachment;
import cn.tedu.ttms.attachment.service.AttachmentService;
import cn.tedu.ttms.common.exception.ServiceException;

@Service
public class AttachmentServiceImpl implements AttachmentService {
	@Autowired
	private AttachmentDao attachmentDao;
	
	/**实现文件上传
	 *		1)将文件存储到服务器
	 *		2)将文件信息存储到数据库
	 */
	@Override
	public Attachment findObjectByDigest(String digest) {
		 
		return attachmentDao.findObjectByDigest(digest);
	}
	
	@Transactional(rollbackFor=ServiceException.class)
	@Override
	public void uploadObject(String title, MultipartFile mFile) {
		//1.验证参数的有效性
		if(StringUtils.isEmpty(title)) throw new ServiceException("title不能为空");
		if(mFile == null) throw new ServiceException("请选择上传文件");
		if(mFile.isEmpty()) throw new ServiceException("文件内容不能空");
		//2.判定文件是否已上传(根据摘要信息,判定数据库是否有对应记录)
		//2.1根据mFile内容生成摘要信息(MD5)
		String digest = null;
		try {
			byte bytes[] = mFile.getBytes();
			//创建文件摘要(相同文件内容摘要是一样的)
			digest = DigestUtils.md5DigestAsHex(bytes);
			System.out.println("digest=" + digest);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("文件摘要创建失败");
		}
		//2.2根据摘要查询记录判定是否已经上传
		Attachment attach= attachmentDao.findObjectByDigest(digest);
		if(attach != null) throw new ServiceException("文件已上传");
		//3.假如文件不在则上传文件到数据库
		Attachment atta = new Attachment();
		atta.setTitle(title);
		atta.setFileName(mFile.getOriginalFilename());
		atta.setFileDisgest(digest);
		System.out.println(mFile.getContentType());
		atta.setContentType(mFile.getContentType());
		String path = "/Users/xiangdan"+mFile.getOriginalFilename();
		atta.setFilePath(path);
		attachmentDao.insertObject(atta);
		
		//4.实现上传操作
		try {
			mFile.transferTo(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException("上传失败");
		}
		
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<Attachment> findObjects() {
		// TODO Auto-generated method stub
		return attachmentDao.findObjects();
	}
	
	
}
