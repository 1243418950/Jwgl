package jwgl;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Jwgl {
	public static String jwurl="http://jwgl.fafu.edu.cn";
	public static String code="(qingyue22222222222222222)";//����Ϥ���뷽ʽ�������ɴ������
	public static String refer="";//referer��ַ
	public static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) throws MalformedURLException, IOException {
		System.out.println("������ѧ�ţ�");
		String xh= sc.nextLine();
		System.out.println("���������룺");
		String psd= sc.nextLine();
		getCourse(xh,psd);
	}
	private static void getCourse(String xh,String psd) throws MalformedURLException, IOException {
		//�α�html��ȡ�����ƣ�ֻ�ɹ۲�Ч��
		Document doc=login(xh,psd);
		HttpURLConnection kb =getCon(jwurl+"/"+code+"/"+doc.select("a:contains(ѧ�����˿α�)").attr("href"));
		Map<String,String> kvkb = new  HashMap<String,String>();
		kvkb.put("Referer", refer);
		Document dockb=getDoc(sendGet(kb, kvkb)) ;
		
		for(Element elet:dockb.select("td:contains(��)")){
			System.out.println(elet.text());
		}
	}
	private static Document login(String xh,String psd) throws MalformedURLException, IOException {
		//��½ģ�飬����document��jsoup�⣩
		HttpURLConnection sy =getCon(jwurl+"/"+code+"/default2.aspx");
		Map<String,String> kv1 = new  HashMap<String,String>();
		Document sydoc =getDoc(sendGet(sy, kv1));
		Map<String,String> kvp = new  HashMap<String,String>();
		String osp ="__VIEWSTATE="+sydoc.select("[name=__VIEWSTATE]").val()
				+ "&__VIEWSTATEGENERATOR="+sydoc.select("[name=__VIEWSTATEGENERATOR]").val()
				+"&txtUserName="+xh+"&Textbox1="
				+ "&TextBox2="+psd+"&txtSecretCode="+getCheckcode(code)+"&RadioButtonList1=%D1%A7%C9%FA"
				+ "&Button1=&lbLanguage=&hidPdrs=&hidsc=";
		kvp.put("Content-Type", "application/x-www-form-urlencoded");
		HttpURLConnection syp =getCon(jwurl+"/"+code+"/default2.aspx");
		Document docp= getDoc(sendPost(syp, kvp, osp));
	    refer= syp.getURL().toString();
		return docp;
	}
	public static String getCheckcode(String code) throws MalformedURLException, IOException{
		//��֤���ȡ���������ڸ�Ŀ¼ˢ�²鿴
		HttpURLConnection huc= getCon(jwurl+"/"+code+"/CheckCode.aspx");
		InputStream fis = huc.getInputStream();
		FileOutputStream fos = new FileOutputStream("CheckCode.gif");
		byte b[]=new byte[1024];
		int len = 0;
		while((len=fis.read(b))!=-1){
			fos.write(b, 0, len);
		}
		fos.flush();fos.close();  //��֤��ͼƬ�����������Ŀ¼
		System.out.println("��������֤�룺");//�ֶ�������֤��
		
		return sc.nextLine();
	}
	public static HttpURLConnection getCon(String url) throws MalformedURLException, IOException{
		//��ȡhttp���ӣ����ı���ַת����httpurlconnection

		HttpURLConnection huc=(HttpURLConnection)new URL(url).openConnection();
		
		return huc;
	}

	private static InputStream sendGet(HttpURLConnection huc,Map<String,String> kv) throws IOException{
		//����get������������
		for(String key:kv.keySet()){
			huc.setRequestProperty(key, kv.get(key));
		}
		huc.connect();
		return huc.getInputStream();
		
	}
	public static InputStream sendPost(HttpURLConnection huc,Map<String,String> kv,String os) throws IOException{
		//����post������������
		huc.setRequestMethod("POST");
		huc.setDoOutput(true);
		huc.setDoInput(true);
		for(String key:kv.keySet()){
			huc.setRequestProperty(key, kv.get(key));
		}
		PrintWriter pt = new PrintWriter(huc.getOutputStream());
	    pt.write(os);
	    pt.flush();pt.close();
		return huc.getInputStream();
	}
	private static Document getDoc(InputStream ins) throws IOException{
		//��������ת����document��jsoup�⣩
		 BufferedReader br=new BufferedReader(new InputStreamReader(ins));
		    String line ="";
		    String rest="";
		    while((line=br.readLine())!=null){
		    	rest+=line;
		    }
			return Jsoup.parse(rest);
	   
	}
	
}
