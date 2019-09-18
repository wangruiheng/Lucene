package com.lucene.doc.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucene.doc.model.DocumentModel;
import com.lucene.doc.util.FileOperationUtil;
import com.lucene.doc.util.Indexer;
import com.lucene.doc.util.Searcher;
import com.lucene.doc.util.XmlUtil;

@RestController
@RequestMapping("/doc")
public class DocController {
	
	public  final static String indexDir = "C:\\lucene";
	public  final static String path = "C:\\doc\\app-01_bak";
	/*public  final static String indexDir = "d:\\lucene";
	public  final static String path = "d:\\xml";*/
	
	@RequestMapping("/productionindex")
	public void productionindex(HttpServletRequest request,HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Method", "POST,GET");
		List<File> readList = FileOperationUtil.readList(path);
		for(File f:readList){
			System.out.println("正在生成索引："+f.getAbsolutePath());
			String readTxt = FileOperationUtil.readTxt(f.getAbsolutePath());
			List<DocumentModel> parseXml = XmlUtil.parseXmlDocumentModel(readTxt);
			try {
				for(DocumentModel documentModel:parseXml){
					/*if(StringUtil.isNotNull(documentModel.getZuozhe())){
						String[] split = documentModel.getZuozhe().split("　");
						for(int i=0;split.length>i;i++){
							if(StringUtil.isNotNull(split[i])){
							FileOperationUtil.method2("C:\\a.txt", split[i]+"\r\n");
							}
						}
					}*/
					
					Indexer.index(indexDir,documentModel);
					//Indexer.zuozheindex(indexDir, documentModel);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("报错！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
				System.out.println("报错！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
				System.out.println("报错！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
			}
		}
		System.out.println("完毕！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
		System.out.println("完毕！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
		System.out.println("完毕！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
	}
	@RequestMapping("/searchdocindex")
	public Object searchdocindex(HttpServletRequest request,HttpServletResponse response,@RequestParam String q) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Method", "POST,GET");
		return Searcher.search(indexDir, q);
	}
	
	@RequestMapping("/searchdocindex2")
	public Object searchdocindex2(HttpServletRequest request,HttpServletResponse response,@RequestParam String q) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Method", "POST,GET");
		return Searcher.search2(indexDir, q);
	}
	
/*	@RequestMapping("/indexcount")
	public Object indexcount(HttpServletRequest request,HttpServletResponse response,@RequestParam String field,@RequestParam String q) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Method", "POST,GET");
		return Searcher.getTotalFreqMap(indexDir, field, q);
	}*/
}
