package com.example.applet;

import com.example.log.Log;
import com.example.log.LogFactory;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;



/**
 * アプレット・サーブレット間の通信時の処理を行うFilterクラスです。
 *
 * @version 1.0
 * <dd>更新履歴（日付 更新者 変更内容）
 * <dd>2009/11/06 m.yamaoka 新規作成
 */
public class AppletParameterTranslateFilter implements Filter {

	// LogClass
	private static final Log log = LogFactory.getLog(AppletParameterTranslateFilter.class.getName());


    /**
     * コンストラクタ。
     */
    public AppletParameterTranslateFilter() {
    }


    /**
     * Filterの初期化処理を行います。
     * @param config FilterConfig
     */
    public void init(FilterConfig config) throws ServletException {
    }

    /**
     * Filterの終了処理を行います。
     */
    public void destroy() {
    }

    /**
     * HTTP リクエストの処理。
     * @param request ServletRequest
     * @param response ServletResponse
     * @param chain FilterChain
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest)request;

		DataInputStream dis = null;
		AppletParameterMapper prm = null;

		// シリアライズされたリクエストオブジェクトを、ディシリアライズする
		try {
			dis = new DataInputStream( req.getInputStream() );
			try {
				String sParameterString = AppletParameterMapper.getEncodeFromApplet(dis.readUTF());
				prm = new AppletParameterMapper(sParameterString);
			} catch (EOFException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw new ServletException(e.getMessage(), e);
		} finally {
			dis.close();
		}

		// Appletから渡されたオブジェクトをリクエストに保持する
		req.setAttribute("REQUEST_PARAM_APPLET_PARAM", prm);

		chain.doFilter(request, response);
    }

}
