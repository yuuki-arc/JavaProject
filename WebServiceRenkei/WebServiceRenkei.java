package com.example.WebServiceRenkei;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;


/**
 * Webサービス連携用のI/Fクラスです。
 */
public class WebServiceRenkei {

	/** 結果コード - 成功 */
	public static final String RESULT_CODE_SUCCESS = "0";

	/** 結果コード - 失敗：ユーザーエラー */
	public static final String RESULT_CODE_FAILURE_USER = "-9001";
	/** 結果コード - 失敗：システムエラー */
	public static final String RESULT_CODE_FAILURE_SYSTEM = "-9999";

	/** URLエンコード - UTF-8 */
	public static final String URL_ENCODE_UTF8 = "UTF-8";


	/**
	 * Webサービスに連携して連携結果を取得します。
	 * @param sParam1 パラメータ1
	 * @param sParam2 パラメータ2
	 * @exception Exception 例外クラス
	 * @return 結果コード
	 */
	public static String postWebService(String sParam1, String sParam2) throws Exception {
		String sResultCode = "";
		String sPostParam = "p1=" + URLEncoder.encode(sParam1, URL_ENCODE_UTF8)
						  + "&p2=" + URLEncoder.encode(sParam2, URL_ENCODE_UTF8);
		sResultCode = requestWebService("Service/AccessName1", sPostParam);
		return sResultCode;
	}

	/**
	 * Webサービスに連携して連携結果を取得します。
	 * @param sParam1 パラメータ1
	 * @exception Exception 例外クラス
	 * @return 結果コード
	 */
	public static String postWebService(String sParam1) throws Exception {
		String sResultCode = "";
		String sPostParam = "p1=" + URLEncoder.encode(sParam1, URL_ENCODE_UTF8);
		sResultCode = requestWebService("Service/AccessName2", sPostParam);
		return sResultCode;
	}

	/**
	 * Webサービスとの連携処理を行います。
	 * @param sAccessName Webサービス連携アクセス名
	 * @param sPostParam POSTパラメータ
	 * @exception Exception 例外クラス
	 * @return 結果コード
	 */
	private static String requestWebService(String sAccessName, String sPostParam) throws Exception {

		// Webサービスと連携（POST送信）
		HttpURLConnection uc = null;
		OutputStream os = null;
		PrintStream ps = null;
		try {
			// HTTPリクエストをオープン
			String sRootUrl = "http://www.example.com/service/";
			URL url = new URL(sRootUrl + sAccessName);	// WebサービスURL（＝ルートURL＋アクセス名）
			uc = (HttpURLConnection)url.openConnection();

			//データをPOST
			uc.setDoOutput(true);
			uc.setRequestProperty("Accept-Language", "ja");
			os = uc.getOutputStream();

			ps = new PrintStream(os);
			ps.print(sPostParam);

		} catch (MalformedURLException e) {
			Sytem.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			Sytem.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		} catch (IOException e) {
			Sytem.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		} catch (Exception e) {
			Sytem.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		} finally {
			try { if (ps != null) {ps.close(); ps = null;} } catch (Exception e) {}
			try { if (os != null) {os.close(); os = null;} } catch (Exception e) {}
		}

		// レスポンスを受け取って結果を返す
		String sResultCode = "";
		try {
			// 通信成功の場合、レスポンスをXMLドキュメントに変換して処理を行う
			if (uc.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputSource inSrc = new InputSource(uc.getInputStream());
				XPath xpath = XPathFactory.newInstance().newXPath();

				// Response要素を取得
				String sExpression = "//Response";
				Node responseNode = (Node) xpath.evaluate(sExpression, inSrc, XPathConstants.NODE);
				// Response要素の中のresult要素を取得
				Node resultNode = (Node) xpath.evaluate("result", responseNode, XPathConstants.NODE);

				// 結果コードをセット
				sResultCode = resultNode.getTextContent();

			} else {
				// レスポンスコードが200以外の場合はエラー
				String sStatus = uc.getResponseCode() + " " + uc.getResponseMessage();
				throw new Exception("Webサービスとの通信中に問題が発生したため、正常に終了できませんでした。HTTP Status Code = [" + sStatus + "]");
			}
		} catch (XPathExpressionException e) {
			Sytem.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		} catch (DOMException e) {
			Sytem.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		} catch (IOException e) {
			Sytem.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		} catch (Exception e) {
			Sytem.out.println(e.getMessage());
			throw e;
		}

		return sResultCode;
	}

	/**
	 * 結果コードをメッセージに変換します。
	 * @param sResultCode
	 * @return 結果メッセージ
	 */
	public static String getResultMessage(String sResultCode) {
		String sResultMessage = "";
		if (RESULT_CODE_SUCCESS.equals(sResultCode)) {
			sResultMessage = "成功";
		} else if (RESULT_CODE_FAILURE_USER.equals(sResultCode)) {
			sResultMessage = "ユーザーエラー";
		} else if (RESULT_CODE_FAILURE_SYSTEM.equals(sResultCode)) {
			sResultMessage = "システムエラー";
		} else if (!"".equals(sResultCode)){
			sResultMessage = "エラーコードが定義されていません。";
		}
		return sResultMessage;
	}

}
