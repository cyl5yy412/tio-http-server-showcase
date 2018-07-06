package org.tio.http.server.showcase.init;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.common.HttpConfig;
import org.tio.http.common.handler.HttpRequestHandler;
import org.tio.http.server.HttpServerStarter;
import org.tio.http.server.handler.DefaultHttpRequestHandler;
import org.tio.http.server.mvc.Routes;
import org.tio.http.server.showcase.HttpServerShowcaseStarter;
import org.tio.server.ServerGroupContext;
import org.tio.utils.SystemTimer;

import com.jfinal.kit.PropKit;

/**
 * @author tanyaowu
 * 2017年7月19日 下午4:59:04
 */
public class HttpServerInit {
	private static Logger log = LoggerFactory.getLogger(HttpServerInit.class);

	public static HttpConfig httpConfig;

	public static HttpRequestHandler requestHandler;

	public static HttpServerStarter httpServerStarter;
	
	public static ServerGroupContext serverGroupContext;

	public static void init() throws Exception {
		long start = SystemTimer.currTime;

		PropKit.use("app.properties");

		int port = PropKit.getInt("http.port");//启动端口
		String pageRoot = PropKit.get("http.page");//html/css/js等的根目录，支持classpath:，也支持绝对路径
		String[] scanPackages = new String[] { HttpServerShowcaseStarter.class.getPackage().getName() };//tio mvc需要扫描的根目录包
		
		
		httpConfig = new HttpConfig(port, null, null, null);
		httpConfig.setPageRoot(pageRoot);

		
		Routes routes = new Routes(scanPackages);
		DefaultHttpRequestHandler requestHandler = new DefaultHttpRequestHandler(httpConfig, routes);
		
		
		httpServerStarter = new HttpServerStarter(httpConfig, requestHandler);
		serverGroupContext = httpServerStarter.getServerGroupContext();
		serverGroupContext.setUseQueueDecode(true);
		httpServerStarter.start();

		long end = SystemTimer.currTime;
		long iv = end - start;
		log.info("Tio Http Server启动完毕,耗时:{}ms,访问地址:http://127.0.0.1:{}", iv, port);
	}

	/**
	 * @param args
	 * @author tanyaowu
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
	}

	/**
	 *
	 * @author tanyaowu
	 */
	public HttpServerInit() {
	}
}
