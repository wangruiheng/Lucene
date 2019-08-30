package com.lucene.doc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lucene.doc.model.DocumentModel;

public class XmlUtil {
	    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
		public static List<DocumentModel> parseXmlDocumentModel(String xml){
	    	try {
	    		Document doc = DocumentHelper.parseText(xml); 
	    		Element rootElt = doc.getRootElement(); 
	    		List elements = rootElt.elements();
	    		Iterator iter =  elements.iterator();
	    		Map<String, Object>   map  = new HashMap<String, Object>();
	    		DocumentModel documentModel=new DocumentModel();
	    		List<DocumentModel> list = new ArrayList<DocumentModel>();
	    		String riqi="";
	           	String banci="";
	           	String banming="";
	           	String baoming="";
	           	String qihao="";
	           	String banshu="";
	            while (iter.hasNext()) {
	            	documentModel=new DocumentModel();
	                Element recordEle = (Element) iter.next();
	                String name = recordEle.getQName().getName();
	                if("大样".equals(name)){
	                	List<Element> elements2 = recordEle.elements();
		                for(Element element:elements2){
		                	String text = element.getText();
			                String qName = element.getQName().getName();
			                if("日期".equals(qName)){
			                	riqi = text;
			                }
			                if("版次".equals(qName)){
			                	banci = text;
			                }
			                if("版名".equals(qName)){
			                	banming = text;
			                }
			                if("报名".equals(qName)){
			                	baoming = text;
			                }
			                if("期号".equals(qName)){
			                	qihao = text;
			                }
			                if("版数".equals(qName)){
			                	banshu = text;
			                }
		                }
		                documentModel.setNeirong("");
			            documentModel.setZuozhe("");
			            documentModel.setBiaoti("");
			            documentModel.setZishu(0);
	                }else if("小样".equals(name)){
	                	String neirong = "";
	                	String zuozhe = "";
	                	String biaoti = "";
	                	long zishu = 0;
	                	List<Element> elements2 = recordEle.elements();
		                for(Element element:elements2){
		                	String text = element.getText();
		                	String qName = element.getQName().getName();
		                	if("内容".equals(qName)){
		                		neirong = text;
			                }
		                	if("作者".equals(qName)){
		                		if(text!=null &&!"".equals(text)){
		                			zuozhe = text;
		                		}
			                }
		                	if("字数".equals(qName)){
		                		if(text!=null && !"".equals(text)){
		                			zishu = Long.valueOf(text);
		                		}
			                }
		                	if("标题".equals(qName)){
		                		if(text!=null && !"".equals(text)){
		                			biaoti = text;
		                		}
			                }
		                }
		                documentModel.setNeirong(neirong);
			            documentModel.setZuozhe(zuozhe);
			            documentModel.setBiaoti(biaoti);
			            documentModel.setZishu(zishu);
		                
	                }
		            documentModel.setBanci(banci);
		            documentModel.setBanming(banming);
		            documentModel.setBaoming(baoming);
		            documentModel.setQihao(qihao);
		            documentModel.setBanshu(banshu);
		            documentModel.setRiqi(riqi);
	                list.add(documentModel);
	            }
	            
	            return list;
			} catch (Exception e) {
				// TODO: handle exception
			}
	    	return null;
	    }

	    public static String getText(Element em, String tag)
	    {
	        if (null == em)
	        {
	            return null;
	        }
	        Element e = em.element(tag);
	        //
	        return null == e ? "" : e.getText();
	    }
}
