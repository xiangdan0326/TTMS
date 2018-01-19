package cn.tedu.ttms.attachment.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.ttms.attachment.entity.Attachment;
import cn.tedu.ttms.attachment.service.AttachmentService;
import cn.tedu.ttms.common.exception.ServiceException;
import cn.tedu.ttms.common.web.JsonResult;

@RequestMapping("/attachment/")
@Controller
public class AttachmentController {
	private Logger log = Logger.getLogger("AttachmentController");
	@Autowired
	@Qualifier("attachmentServiceImpl")
	private AttachmentService attachmentService;
	
	@RequestMapping("attachmentUI")
	public String listUI() {
		return "attachment/attachment";
	}
	
	@RequestMapping("doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects() {
		return new JsonResult(attachmentService.findObjects());
	}
	
	@RequestMapping("doUpload")
	@ResponseBody
	public JsonResult doUpload(String title, MultipartFile mFile) throws IOException{
		log.log(Level.INFO, title+"/"+mFile.getOriginalFilename());
		attachmentService.uploadObject(title, mFile);
		return new JsonResult("upload ok");
	}
	
	@RequestMapping("doDownload")
	@ResponseBody
	public byte[] doDownload(String digest,HttpServletResponse response) throws IOException {
		//1.根据digest查询附件对象,判定附件对象是否存在
  	  Attachment attach=
  	  attachmentService.findObjectByDigest(digest);
  	  if(attach==null)
  	  throw new ServiceException("文件已经不存在");
  	  String fileName=//中文文件名可能会先乱码,所以要进行编码
  	  URLEncoder.encode(attach.getFileName(),"utf-8");
  	  //2.设置下载的响应头(reponse):固定格式
  	  response.setContentType("appliction/octet-stream");
		  response.setHeader("Content-disposition","attachment;filename="+fileName);
  	  //3.实现附件下载(交给浏览器)
  	  Path path=Paths.get(attach.getFilePath());
  	  return Files.readAllBytes(path);//byte[]
  	  //NIO2(AIO)
	}
	
	
	
	
	
	
}











