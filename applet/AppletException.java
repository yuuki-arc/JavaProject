package com.example.applet;



/**
 * applet パッケージおよびサブパッケージで発生した例外クラスです。
 *
 * @version 1.0
 * <dd>更新履歴（日付 更新者 変更内容）
 * <dd>2009/11/16 m.yamaoka 新規作成
 */
public class AppletException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    private String sMessage;


	/**
	 * 指定された詳細メッセージを持つ例外を構築します。
	 * @param msg エラーメッセージ
	 */
	public AppletException(String msg) {
		this(msg, null);
	}


	/**
	 * 原因となった例外をラップし、指定された詳細メッセージを持つ例外を構築します。
	 * @param msg エラーメッセージ
	 * @param ex 例外オブジェクト
	 */
	public AppletException(String msg, Throwable ex) {
		super(msg, ex);
		setMessage(msg);
	}


    /**
     * メッセージを返します。
     * @return メッセージ
     */
    public final String getMessage() {
        return this.sMessage;
    }

    /**
     * メッセージを設定します。
     * @param sMessage メッセージ
     */
    protected final void setMessage(String sMessage) {
        this.sMessage = sMessage;
    }

}
