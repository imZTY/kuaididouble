package com.uc56.uop.client.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ��ȡxml�ļ�������
 * @author liuhaibo
 * @since 2013-12-18
 */
public class ReaderXmlUtil {

	private static final String path_xml_file = "src/main/java/com/uc56/uop/client/xml/XmlFile.xml";
	private static final String path_json_file = "src/main/java/com/uc56/uop/client/xml/JsonFile.xml";
	private static final String DATA_TYPE_XML = "xml";
	private static final String DATA_TYPE_JSON = "json";
	public static String readXml(String dataTpye){
		String result = "";
		SAXReader sr = new SAXReader();
		try {
			if(DATA_TYPE_XML.equals(dataTpye)){
				Document doc = sr.read("kdd-third/kdd-third-uce/"+path_xml_file);
				Element elRoot = doc.getRootElement();
				result = elRoot.asXML();
			}else if(DATA_TYPE_JSON.equals(dataTpye)){
				Document doc = sr.read("kdd-third/kdd-third-uce/"+path_json_file);
				Element elRoot = doc.getRootElement();
				result = elRoot.getText();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
