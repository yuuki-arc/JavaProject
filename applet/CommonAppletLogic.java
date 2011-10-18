package com.example.applet;

import com.example.log.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * アプレット共通ロジッククラスです。
 *
 * @version 1.0
 * <dd>更新履歴（日付 更新者 変更内容）
 * <dd>2009/11/16 m.yamaoka 新規作成
 */
public class CommonAppletLogic {

	/**
	 * コンストラクタ。
	 */
	public CommonAppletLogic() {
	}

	/**
	 * AppletからServletへリクエストするメソッドを提供します。
	 * JavaScriptから直接メソッドを呼び出すことも可能です。
	 * @param param リクエストパラメータを保持したオブジェクト
	 * @return リクエスト結果オブジェクト
	 */
	public static Object requestServlet(AppletParameterMapper prm) {

		Object result = null;

		try {
			// サーブレットを呼び出すURLオブジェクトを生成
			URL url = new URL(prm.getContextPathURL() + "/AppletRequestServlet.applet");
			// 接続オブジェクトを生成
			URLConnection con = url.openConnection();
			con.setRequestProperty("CONTENT_TYPE", "application/octet-stream");
			con.setDoOutput(true);
			con.setUseCaches(false);

			// Servletへリクエストを送る
			DataOutputStream oos = null;
			try {
				oos = new DataOutputStream( con.getOutputStream() );
				String sParameter = AppletParameterMapper.getEncodeToServlet(prm.getParameterString());
				oos.writeUTF(sParameter);
			} finally {
				if (oos != null) {
					try {oos.flush();} catch (Exception e) {}
					try {oos.close();} catch (Exception e) {}
				}
			}

			// 結果を取得する
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream( con.getInputStream() );
				result = ois.readObject();
			} finally {
				if (ois != null) {try{ois.close();} catch (Exception e) {}}
			}

			// 結果が例外の場合は例外遷移
			if (result instanceof Exception) {
				throw (Exception)result;
			}

		} catch (UnsupportedEncodingException e) {
			String sMessage = "AppletからServletへのリクエストで例外が発生しました。：" + e.getMessage();
			throw new AppletRequestServletException(sMessage, e);
		} catch (MalformedURLException e) {
			String sMessage = "AppletからServletへのリクエストで例外が発生しました。：" + e.getMessage();
			throw new AppletRequestServletException(sMessage, e);
		} catch (IOException e) {
			String sMessage = "AppletからServletへのリクエストで例外が発生しました。：" + e.getMessage();
			throw new AppletRequestServletException(sMessage, e);
		} catch (ClassNotFoundException e) {
			String sMessage = "AppletからServletへのリクエストで例外が発生しました。：" + e.getMessage();
			throw new AppletRequestServletException(sMessage, e);
		} catch (Exception e) {
			String sMessage = "AppletからServletへのリクエストで例外が発生しました。：" + e.getMessage();
			throw new AppletRequestServletException(sMessage, e);
		}

		return result;
	}


	/**
	 * セッション情報を取得します。
	 * @param commonVo アプレット共通情報
	 * @param sKey セッションキー
	 * @return セッション情報（Object型）
	 */
	public static Object getSessionAttribute(CommonAppletVo commonVo, String sSessionKey) {

		// リクエストオブジェクトを生成
		AppletParameterMapper prm = new AppletParameterMapper(commonVo);
		prm.setRequestType(AppletParameterMapper.REQUEST_TYPE_GET_SESSION_ATTRIBUTE);

		// セッションキーをセット
		prm.setParameter("sessionKey", sSessionKey);

		// サーブレットを呼び出す
		Object result = requestServlet(prm);

		return result;
	}


	/**
	 * セッション情報を設定します。
	 * @param commonVo アプレット共通情報
	 * @param sKey セッションキー
	 * @param sValue 値
	 */
	public static void setSessionAttribute(CommonAppletVo commonVo, String sSessionKey, String sValue) {

		// リクエストオブジェクトを生成
		AppletParameterMapper prm = new AppletParameterMapper(commonVo);
		prm.setRequestType(AppletParameterMapper.REQUEST_TYPE_SET_SESSION_ATTRIBUTE);

		// セッションキーをセット
		prm.setParameter("sessionKey", sSessionKey);
		prm.setParameter("value", sValue);

		// サーブレットを呼び出す
		requestServlet(prm);
	}


	/**
	 * セッション情報を削除します。
	 * @param commonVo アプレット共通情報
	 * @param sKey セッションキー
	 */
	public static void removeSessionAttribute(CommonAppletVo commonVo, String sSessionKey) {

		// リクエストオブジェクトを生成
		AppletParameterMapper prm = new AppletParameterMapper(commonVo);
		prm.setRequestType(AppletParameterMapper.REQUEST_TYPE_REMOVE_SESSION_ATTRIBUTE);

		// セッションキーをセット
		prm.setParameter("sessionKey", sSessionKey);

		// サーブレットを呼び出す
		requestServlet(prm);
	}


	/**
	 * エラー発生時のログを出力します。
	 * @param vo アプレット共通情報のValueObject
	 * @param log ログ出力クラス
	 * @param sMessage エラーメッセージ
	 */
	public static void outErrorLog(CommonAppletVo vo, Log log, String sMessage) {
		outErrorLog(vo, log, sMessage, null);
	}


	/**
	 * エラー発生時のログを出力します。
	 * @param vo アプレット共通情報のValueObject
	 * @param log ログ出力クラス
	 * @param sMessage エラーメッセージ
	 * @param e エラー発生時の例外クラス
	 */
	public static void outErrorLog(CommonAppletVo vo, Log log, String sMessage, Exception e) {
		if (e == null) {
			log.error(sMessage);
		} else {
			log.error(sMessage, e);
		}
	}


	/**
	 * 例外のメッセージを取得します。
	 * @param ex 例外 {@link Exception}
	 */
	public static String getExceptionMessage(Exception ex) {

		if ( ex == null ) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		getCause(sb, ex);

		StackTraceElement[] stack = ex.getStackTrace();
		for (int i1=0; i1<stack.length; i1++ ) {
			sb.appendLine(stack[i1].toString());
		}
		return sb.toString();
	}
	public static void getCause(StringBuilder sb, Throwable ex) {
		if ( ex == null ) {
			return;
		}
		sb.appendLine(ex.toString());
		getCause(sb, ex.getCause());
	}

}
