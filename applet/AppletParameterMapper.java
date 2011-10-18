package com.example.applet;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * アプレット・サーブレット間の通信で使用するValueObjectクラスです。
 *
 * @version 1.0
 * <dd>更新履歴（日付 更新者 変更内容）
 * <dd>2009/11/16 m.yamaoka 新規作成
 */
public class AppletParameterMapper implements Serializable {

	private static final long serialVersionUID = 1L;

	/** リクエスト種別：セッション情報取得 */
	public static final String REQUEST_TYPE_GET_SESSION_ATTRIBUTE = "GET_SESSION_ATTRIBUTE";
	/** リクエスト種別：セッション情報設定 */
	public static final String REQUEST_TYPE_SET_SESSION_ATTRIBUTE = "SET_SESSION_ATTRIBUTE";
	/** リクエスト種別：セッション情報削除 */
	public static final String REQUEST_TYPE_REMOVE_SESSION_ATTRIBUTE = "REMOVE_SESSION_ATTRIBUTE";

	/** パラメータマップキー：プログラムID */
	private static final String PARAMETER_MAP_KEY_PROGRAM_ID = "_PARAMETER_MAP_KEY_PROGRAM_ID";
	/** パラメータマップキー：コンテキストパスURL */
	private static final String PARAMETER_MAP_KEY_CONTEXT_PATH_URL = "_PARAMETER_MAP_KEY_CONTEXT_PATH_URL";
	/** パラメータマップキー：リクエスト種別 */
	private static final String PARAMETER_MAP_KEY_REQUEST_TYPE = "_PARAMETER_MAP_KEY_REQUEST_TYPE";


	//==================================================
	//インスタンス変数
	//==================================================

	/** パラメータマップ */
	private ConcurrentHashMap<String, String> parameterMap = null;


	/**
	 * コンストラクタ。
	 */
	public AppletParameterMapper(CommonAppletVo vo) {
		this.parameterMap = new ConcurrentHashMap<String, String>();
		setProgramID(vo.getProgramID());
		setContextPathURL(vo.getContextPathURL());
	}

	/**
	 * コンストラクタ。
	 * @ParameterString パラメータ文字列
	 */
	public AppletParameterMapper(String sParameterString) throws UnsupportedEncodingException {
		this.parameterMap = new ConcurrentHashMap<String, String>();
		// パラメータ文字列をパラメータマップに変換
		String[] parameter = sParameterString.split("&");
		for (int i1 = 0; i1 < parameter.length; i1++) {
			String[] val = parameter[i1].split("=");
			String sKey = URLDecoder.decode(val[0], AppletPropertyConstant.URL_ENCODE);
			String sValue = (val.length == 1 ? "" : URLDecoder.decode(val[1], AppletPropertyConstant.URL_ENCODE));
			this.parameterMap.putIfAbsent(sKey, sValue);
		}
	}


	//==================================================
	//get系メソッド
	//==================================================

	/**
	 * パラメータマップを取得します。
	 * @return パラメータマップ
	 */
	public ConcurrentHashMap<String, String> getParameterMap() {
		return this.parameterMap;
	}

	/**
	 * パラメータマップからパラメータを取得します。
	 * @return パラメータ
	 */
	public String getParameter(String sKey) {
		return getParameterMap().get(sKey);
	}

	/**
	 * プログラムIDを取得します。
	 * @return プログラムID
	 */
	public String getProgramID() {
		return getParameterMap().get(PARAMETER_MAP_KEY_PROGRAM_ID);
	}

	/**
	 * コンテキストパスURLを取得します。
	 * @return コンテキストパスURL
	 */
	public String getContextPathURL() {
		return getParameterMap().get(PARAMETER_MAP_KEY_CONTEXT_PATH_URL);
	}

	/**
	 * リクエスト種別を取得します。
	 * @return リクエスト種別
	 */
	public String getRequestType() {
		return getParameterMap().get(PARAMETER_MAP_KEY_REQUEST_TYPE);
	}

	//==================================================
	//set系メソッド
	//==================================================

	/**
	 * パラメータを設定します。
	 * @param sKey キー
	 * @param sValue 値
	 */
	public void setParameter(String sKey, String sValue) {
		getParameterMap().putIfAbsent(sKey, sValue);
	}

	/**
	 * プログラムIDを設定します。
	 * @param sProgramID プログラムID
	 */
	public void setProgramID(String sProgramID) {
		getParameterMap().putIfAbsent(PARAMETER_MAP_KEY_PROGRAM_ID, sProgramID);
	}

	/**
	 * コンテキストパスURLを設定します。
	 * @param sContextPathURL コンテキストパスURL
	 */
	public void setContextPathURL(String sContextPathURL) {
		getParameterMap().putIfAbsent(PARAMETER_MAP_KEY_CONTEXT_PATH_URL, sContextPathURL);
	}

	/**
	 * リクエスト種別を設定します。
	 * @param sRequestType リクエスト種別
	 */
	public void setRequestType(String sRequestType) {
		getParameterMap().putIfAbsent(PARAMETER_MAP_KEY_REQUEST_TYPE, sRequestType);
	}

	//==================================================
	//その他メソッド
	//==================================================

	/**
	 * パラメータ文字列を取得する。
	 * @return 文字列
	 */
	public String getParameterString() throws UnsupportedEncodingException {
		String sResult = "";
		for (Map.Entry<String, String> e : getParameterMap().entrySet()) {
			sResult += "&" + URLEncoder.encode(e.getKey(), AppletPropertyConstant.URL_ENCODE)
					+  "=" + URLEncoder.encode(e.getValue(), AppletPropertyConstant.URL_ENCODE);
		}
		return sResult.substring(1);
	}


	/**
	 * サーブレットに渡すパラメータのエンコード文字列を取得する。
	 * @param sValue 値
	 * @return エンコード文字列
	 */
	public static String getEncodeToServlet(String sValue) {
		String sResult = sValue;	// 変換不要のため、文字列をそのまま返す
		return sResult;
	}


	/**
	 * アプレットから受け取るパラメータのエンコード文字列を取得する。
	 * @param sValue 値
	 * @return エンコード文字列
	 */
	public static String getEncodeFromApplet(String sValue) {
		String sResult = sValue;	// 変換不要のため、文字列をそのまま返す
		return sResult;
	}

}
