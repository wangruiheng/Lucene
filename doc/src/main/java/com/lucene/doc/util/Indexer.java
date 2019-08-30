package com.lucene.doc.util;


import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.lucene.doc.model.DocumentModel;

public class Indexer {
	
	public static Directory dir;
	
	public static IndexWriter getWriter()throws Exception{
		IKAnalyzer analyzer = new IKAnalyzer(true);
		IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
		IndexWriter writer=new IndexWriter(dir, iwc);
		return writer;
	}
	public static IndexWriter getMyWriter()throws Exception{
		WhitespaceAnalyzer wsa = new WhitespaceAnalyzer();   
		IndexWriterConfig iwc=new IndexWriterConfig(wsa);
		IndexWriter writer=new IndexWriter(dir, iwc);
		return writer;
	}
	
	public static void index(String indexDir,DocumentModel documentModel)throws Exception{
		dir=FSDirectory.open(Paths.get(indexDir));
		IndexWriter writer=getWriter();
		try {
			Document doc=new Document();
			doc.add(new NumericDocValuesField("id", DateUtils.getDateTimeInt(DateUtils.getDateToString(documentModel.getRiqi(), "yyyy-MM-dd"))));
			doc.add(new TextField("riqi", StringUtil.format(documentModel.getRiqi()) , Field.Store.YES));
			doc.add(new TextField("neirong",StringUtil.format(documentModel.getNeirong()),Field.Store.YES));
			doc.add(new TextField("zishu",StringUtil.format(documentModel.getZishu()),Field.Store.YES));
			doc.add(new TextField("banci",StringUtil.format(documentModel.getBanci()),Field.Store.YES));
			doc.add(new TextField("banming",StringUtil.format(documentModel.getBanming()),Field.Store.YES));
			doc.add(new TextField("baoming",StringUtil.format(documentModel.getBaoming()),Field.Store.YES));
			doc.add(new TextField("qihao",StringUtil.format(documentModel.getQihao()),Field.Store.YES));
			doc.add(new TextField("banshu",StringUtil.format(documentModel.getBanshu()),Field.Store.YES));
			doc.add(new TextField("biaoti",StringUtil.format(documentModel.getBiaoti()),Field.Store.YES));
			doc.add(new TextField("zuozhe",StringUtil.format(documentModel.getZuozhe()),Field.Store.YES));
			writer.addDocument(doc); 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			writer.close();
		}
		
		
	}
	
	public static void zuozheindex(String indexDir,DocumentModel documentModel)throws Exception{
		dir=FSDirectory.open(Paths.get(indexDir));
		IndexWriter writer=getMyWriter();
		try {
			Document doc=new Document();
			if(documentModel.getZuozhe()!=null && !"".equals(documentModel.getZuozhe())){
				doc.add(new TextField("zuozhe",documentModel.getZuozhe(),Field.Store.YES));
				writer.addDocument(doc); 
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			writer.close();
		}
		
		
	}
	
}
