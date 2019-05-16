package crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Crawler {
	// 地址
		private static final String URL = "http://www.sccnn.com/shiliangtuku/";
		// 获取img标签正则
		private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
		// 获取src路径的正则
		private static final String IMGSRC_REG = "[a-zA-z]+://[^\\s]*";
	 
		// 获取html内容
		public static String getHTML(String srcUrl) throws Exception {
			URL url = new URL(srcUrl);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
	 
			String line = null;
			StringBuffer buffer = new StringBuffer();
			while ((line = br.readLine()) != null) {
				buffer.append(line);
				buffer.append("\n");
			}
			br.close();
			isr.close();
			is.close();
			return buffer.toString();
		}
	 
		// 获取image url地址
		public static List<String> getImageURL(String html) {
			Matcher matcher = Pattern.compile(IMGURL_REG).matcher(html);
			List<String> list = new ArrayList<>();
			//int number = 0;
			while (matcher.find()) {
				//number = number + 1;
				//if(number>5) break;
				list.add(matcher.group());
				System.out.println(matcher.group());
			}
			return list;
		}
	 
		// 获取image src地址
		public static List<String> getImageSrc(List<String> listUrl) {
			List<String> listSrc = new ArrayList<String>();
			for (String img : listUrl) {
				Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(img);
				while (matcher.find()) {
					listSrc.add(matcher.group().substring(0,
							matcher.group().length() - 1));
				}
			}
			return listSrc;
		}
	 
		// 下载图片
		private static void Download(List<String> listImgSrc) {
			try {
				// 开始时间
				Date begindate = new Date();
				for (String url : listImgSrc) {
					// 开始时间
					Date begindate2 = new Date();
					String imageName = url.substring(url.lastIndexOf("/") + 1,
							url.length());
					String imagePath = "D:\\Imagecopyright-blockchain\\image\\"+imageName;
					URL uri = new URL(url);
					InputStream in = uri.openStream();
					FileOutputStream fo = new FileOutputStream(new File(imagePath));// 文件输出流
					byte[] buf = new byte[1024];
					int length = 0;
					System.out.println("开始下载:" + url);
					while ((length = in.read(buf, 0, buf.length)) != -1) {
						fo.write(buf, 0, length);
					}
					// 关闭流
					in.close();
					fo.close();
					System.out.println(imageName + "下载完成");
					// 结束时间
					Date overdate2 = new Date();
					double time = overdate2.getTime() - begindate2.getTime();
					System.out.println("耗时：" + time / 1000 + "s");
				}
				Date overdate = new Date();
				double time = overdate.getTime() - begindate.getTime();
				System.out.println("总耗时：" + time / 1000 + "s");
			} catch (Exception e) {
				System.out.println("下载失败");
			}
		}
		 
		public static void main(String[] args) throws Exception {
			String html = getHTML(URL);
			List<String> listUrl = getImageURL(html);
			for(String img : listUrl) {
				System.out.println(img); }
			List<String> listSrc = getImageSrc(listUrl);
			for(String img : listSrc) {
				System.out.println(img); }
			Download(listSrc);
		}
}
