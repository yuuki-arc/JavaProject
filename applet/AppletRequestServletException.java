package com.example.applet;



/**
 * AppletからServletへのリクエスト時に発生した例外用の例外クラスです。
 *
 * @version 1.0
 * <dd>更新履歴（日付 更新者 変更内容）
 * <dd>2009/11/16 m.yamaoka 新規作成
 */
public class AppletRequestServletException extends AppletException {

	private static final long serialVersionUID = 1L;

	/**
	 * 指定された詳細メッセージを持つ例外を構築します。
	 * @param msg エラーメッセージ
	 */
	public AppletRequestServletException(String msg) {
		super(msg, null);
	}


	/**
	 * 原因となった例外をラップし、指定された詳細メッセージを持つ例外を構築します。
	 * @param msg エラーメッセージ
	 * @param ex 例外オブジェクト
	 */
	public AppletRequestServletException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
