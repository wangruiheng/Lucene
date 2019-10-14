package com.lucene.doc.util;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Searcher {

	public static Map<String, Object> search(String indexDir, String q) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Directory dir = FSDirectory.open(Paths.get(indexDir));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher is = new IndexSearcher(reader);
		IKAnalyzer analyzer = new IKAnalyzer();
		QueryParser parser = new QueryParser("neirong", analyzer);
		Query query = parser.parse("\""+q+"\"");
		long start = System.currentTimeMillis();
		Sort sort = new Sort(new SortField[] { new SortField("id", SortField.Type.INT, false) });
		TopDocs hits = is.search(query, Integer.MAX_VALUE, sort);
		long end = System.currentTimeMillis();
		int totalHits = hits.totalHits;
		String qyfirsttime = "";
		if(totalHits>0){
			map.put("totalnum", totalHits);
			map.put("firsttime", is.doc(hits.scoreDocs[0].doc).get("riqi"));
			map.put("biaoti",is.doc(hits.scoreDocs[0].doc).get("biaoti"));
			map.put("banmiantu",is.doc(hits.scoreDocs[0].doc).get("banmiantu"));
			map.put("qihao",is.doc(hits.scoreDocs[0].doc).get("qihao"));
			map.put("times",(end - start)+"毫秒");
			Document doc = new Document();
			for(ScoreDoc scoreDoc:hits.scoreDocs){
				doc = new Document();
				doc=is.doc(scoreDoc.doc);
				if("头版".equals(doc.get("banming"))){
					qyfirsttime = doc.get("riqi");
						break;
				}else if("004".equals(doc.get("banci")) ||  "005".equals(doc.get("banci")) ||  "006".equals(doc.get("banci")) ){
					qyfirsttime = doc.get("riqi");
					break;
				}
			}	
			map.put("qyfirsttime",qyfirsttime);
		}else{
			map.put("totalnum", 0);
			map.put("firsttime", 0);
			map.put("neirong","");
			map.put("biaoti","");
			map.put("qihao","");
			map.put("times",(end - start)+"毫秒");
			map.put("qyfirsttime",qyfirsttime);
			map.put("banmiantu","");
		}
		reader.close();
		return map;
	}
	
	
	
	
	public static Map<String, Object> search4(String indexDir, String q) throws Exception {
		ArrayList<String> arrayList = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		Directory dir = FSDirectory.open(Paths.get(indexDir));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher is = new IndexSearcher(reader);
		IKAnalyzer analyzer = new IKAnalyzer(true);
		QueryParser parser = new QueryParser("nianfen", analyzer);
		Query query = parser.parse("\""+q+"\"");
		long start = System.currentTimeMillis();
		Sort sort = new Sort(new SortField[] { new SortField("id", SortField.Type.INT, false) });
		TopDocs hits = is.search(query, Integer.MAX_VALUE, sort);
		long end = System.currentTimeMillis();
		int totalHits = hits.totalHits;
		long n = 0;
		if(totalHits>0){
			map.put("totalnum", totalHits);
			Document doc = new Document();
			for(ScoreDoc scoreDoc:hits.scoreDocs){
				doc = new Document();
				doc=is.doc(scoreDoc.doc);
				if(doc.get("zishu") != null && !"".equals(doc.get("zishu"))){
					n = n + Long.valueOf(doc.get("zishu"));
				}
				arrayList.add(doc.get("qihao"));
			}
			//刊登文章篇数总和
			map.put("doctotalnum", totalHits);
			//发表文字总和
			map.put("docwordtotalnum", n);
			map.put("times",(end - start)+"毫秒");
			arrayList = new ArrayList<>(new HashSet<String>(arrayList));
			map.put("banmiantotalnum",arrayList.size());
		}else{
			map.put("totalnum", totalHits);
			//刊登文章篇数总和
			map.put("doctotalnum", totalHits);
			//发表文字总和
			map.put("docwordtotalnum", n);
			
			map.put("times",(end - start)+"毫秒");
			
			map.put("banmiantotalnum",arrayList.size());
		}
		reader.close();
		return map;
	}
	
	public static Map<String, Object> search3(String indexDir, String q) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Map<String, Long>> map2 = new HashMap<String, Map<String, Long>>();
		Directory dir = FSDirectory.open(Paths.get(indexDir));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher is = new IndexSearcher(reader);
		IKAnalyzer analyzer = new IKAnalyzer();
		QueryParser parser = new QueryParser("neirong", analyzer);
		Query query = parser.parse("\""+q+"\"");
		long start = System.currentTimeMillis();
		Sort sort = new Sort(new SortField[] { new SortField("id", SortField.Type.INT, false) });
		TopDocs hits = is.search(query, Integer.MAX_VALUE, sort);
		long end = System.currentTimeMillis();
		int totalHits = hits.totalHits;
		String qyfirsttime = "";
		String nianfen = "";
		if(totalHits>0){
			map.put("totalnum", totalHits);
			map.put("firsttime", is.doc(hits.scoreDocs[0].doc).get("riqi"));
			map.put("biaoti",is.doc(hits.scoreDocs[0].doc).get("biaoti"));
			map.put("banmiantu",is.doc(hits.scoreDocs[0].doc).get("banmiantu"));
			map.put("qihao",is.doc(hits.scoreDocs[0].doc).get("qihao"));
			map.put("times",(end - start)+"毫秒");
			Document doc = new Document();
			for(ScoreDoc scoreDoc:hits.scoreDocs){
				doc = new Document();
				doc=is.doc(scoreDoc.doc);
				if("头版".equals(doc.get("banming"))){
					qyfirsttime = doc.get("riqi");
						break;
				}else if("004".equals(doc.get("banci")) ||  "005".equals(doc.get("banci")) ||  "006".equals(doc.get("banci")) ){
					qyfirsttime = doc.get("riqi");
					break;
				}
			}	
			//对所有结果进行分组处理
			for(ScoreDoc scoreDoc:hits.scoreDocs){
					doc = new Document();
					doc=is.doc(scoreDoc.doc);
				    nianfen = doc.get("nianfen");
				    long cp = 1;
				    TokenStream tokenStream = null;
				    try {
				    	    tokenStream = analyzer.tokenStream("neirong", new StringReader(doc.get("neirong")));
							CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
							tokenStream.reset();
							
				            while (tokenStream.incrementToken()) {
				            	if(charTermAttribute.toString().indexOf(q) != -1){
				            		cp++;
				            	}
				            }
					} catch (Exception e) {
						// TODO: handle exception
					}finally {
						tokenStream.close();
					}
				    if(map2.containsKey(nianfen)){
				    //key 存在
				    	Map<String, Long> map3 = map2.get(nianfen);
				    	long gaozigroupsum = map3.get("gaozigroupsum");//多少篇稿子
				    	long zishugroupsum = map3.get("zishugroupsum");//多少字数
				    	long cishugroupsum = map3.get("cishugroupsum");//多少次数
				    	map3.put("gaozigroupsum", gaozigroupsum+1);
				    	map3.put("zishugroupsum", zishugroupsum+Long.valueOf(doc.get("zishu")));
				    	map3.put("cishugroupsum", cishugroupsum+cp);
				    	map2.put(nianfen, map3);
			    	}else{
			    	//不存在
			    		Map<String, Long> map3 = new HashMap<String, Long>();
			    		map3.put("gaozigroupsum", 1l);
				    	map3.put("zishugroupsum", Long.valueOf(doc.get("zishu")));
				    	map3.put("cishugroupsum", cp);
				    	map2.put(nianfen, map3);
			    	}
			}
			map.put("qyfirsttime",qyfirsttime);
			map.put("countdata", map2);
		}else{
			map.put("totalnum", 0);
			map.put("firsttime", 0);
			map.put("neirong","");
			map.put("biaoti","");
			map.put("qihao","");
			map.put("times",(end - start)+"毫秒");
			map.put("qyfirsttime",qyfirsttime);
			map.put("countdata", map2);
		}
		reader.close();
		return map;
	}
	
	public static Map<String, Object> search2(String indexDir, String q) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Directory dir = FSDirectory.open(Paths.get(indexDir));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher is = new IndexSearcher(reader);
		IKAnalyzer analyzer = new IKAnalyzer(true);
		QueryParser parser = new QueryParser("zuozhe", analyzer);
		Query query = parser.parse("\""+q+"\"");
		long start = System.currentTimeMillis();
		Sort sort = new Sort(new SortField[] { new SortField("id", SortField.Type.INT, false) });
		TopDocs hits = is.search(query, Integer.MAX_VALUE, sort);
		long end = System.currentTimeMillis();
		int totalHits = hits.totalHits;
		long n = 0;
		long m = 0;
		String zzfirsttime = "";
		if(totalHits>0){
			map.put("totalnum", totalHits);
			map.put("firsttime", is.doc(hits.scoreDocs[0].doc).get("riqi"));
			map.put("biaoti",is.doc(hits.scoreDocs[0].doc).get("biaoti"));
			map.put("banmiantu",is.doc(hits.scoreDocs[0].doc).get("banmiantu"));
			map.put("qihao",is.doc(hits.scoreDocs[0].doc).get("qihao"));
			map.put("times",(end - start)+"毫秒");
			Document doc = new Document();
			for(ScoreDoc scoreDoc:hits.scoreDocs){
				doc = new Document();
				doc=is.doc(scoreDoc.doc);
				if(doc.get("zishu") != null && !"".equals(doc.get("zishu"))){
					n = n + Long.valueOf(doc.get("zishu"));
				}
				if("头版".equals(doc.get("banming"))){
					m++;
					if(m==1){
						zzfirsttime = doc.get("riqi");
					}
				}else if("004".equals(doc.get("banci")) ||  "005".equals(doc.get("banci")) ||  "006".equals(doc.get("banci")) ){
					m++;
					if(m==1){
						zzfirsttime = doc.get("riqi");
					}
				}
				
			}
			//35年间名字出现次数总和
			map.put("nametotalnum", getTotalFreqMap(indexDir, "neirong", q));
			//刊登文章篇数总和
			map.put("doctotalnum", totalHits);
			//发表文字总和
			map.put("docwordtotalnum", n);
			//头版次数
			map.put("docfrontpagetotalnum", m);
			//第一次头版时间
			map.put("zzfirsttime", zzfirsttime);
			map.put("times",(end - start)+"毫秒");
		}else{
			//35年间名字出现次数总和
			map.put("nametotalnum", getTotalFreqMap(indexDir, "neirong", q));
			//刊登文章篇数总和
			map.put("doctotalnum", totalHits);
			//发表文字总和
			map.put("docwordtotalnum", n);
			//头版次数
			map.put("docfrontpagetotalnum", m);
			//第一次头版时间
			map.put("zzfirsttime", zzfirsttime);
			
			map.put("times",(end - start)+"毫秒");
		}
		reader.close();
		return map;
	}

	/**
	 * 获得词频map
	 * @throws ParseException
	 */
	public static long getTotalFreqMap(String indexDir, String field,String q) {
		long n = 0;
		try {
			Directory dir = FSDirectory.open(Paths.get(indexDir));
			IndexReader reader = DirectoryReader.open(dir);
			List<LeafReaderContext> leaves = reader.leaves();
			for (LeafReaderContext leafReaderContext : leaves) {
				LeafReader leafReader = leafReaderContext.reader();
				Terms terms = leafReader.terms(field);
				if(terms== null){
					continue;
				}
				TermsEnum iterator = terms.iterator();
				if(iterator== null){
					continue;
				}
				BytesRef term = null;
				while ((term = iterator.next()) != null) {
					String text = term.utf8ToString();
					if(q.equals(text)){
						n = n + iterator.totalTermFreq();
					}
				}
			}
			reader.close();
			return n;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return n;
	}
	
	
	/**
	 * 获得词频map
	 * @throws ParseException
	 */
	public static Map<String, Object> getTotalFreqMapsss(String indexDir, String field) {
		Map<String, Object> map = new HashMap<>();
		try {
			Directory dir = FSDirectory.open(Paths.get(indexDir));
			IndexReader reader = DirectoryReader.open(dir);
			List<LeafReaderContext> leaves = reader.leaves();
			for (LeafReaderContext leafReaderContext : leaves) {
				LeafReader leafReader = leafReaderContext.reader();
				Terms terms = leafReader.terms(field);
				TermsEnum iterator = terms.iterator();
				BytesRef term = null;
				while ((term = iterator.next()) != null) {
					String text = term.utf8ToString();
					long totalTermFreq = iterator.totalTermFreq();
					map.put(text, totalTermFreq);
				}
			}
			reader.close();
			return map;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}	
	
}
