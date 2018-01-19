package cn.tedu.ttms.attachment.dao;

import java.util.List;

import cn.tedu.ttms.attachment.entity.Attachment;
import cn.tedu.ttms.attachment.entity.Attachment;

public interface AttachmentDao {
	/**负责将附件信息写到数据库*/
	int insertObject(Attachment entity);
	/**查询所有附件*/
	List<Attachment> findObjects();
	/**根据摘要信息查询数据库*/
	Attachment findObjectByDigest(String digest);
	
	
	
}
