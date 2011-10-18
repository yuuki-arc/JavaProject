package com.example.applet;

import java.io.Serializable;

/**
 * アプレット共通情報のValueObjectクラスです。
 *
 * @version 1.0
 * <dd>更新履歴（日付 更新者 変更内容）
 * <dd>2009/11/16 m.yamaoka 新規作成
 */
public class CommonAppletVo implements Serializable {

	private static final long serialVersionUID = 1L;

	//==================================================
	//インスタンス変数
	//==================================================

	/** プログラムID */
	private String sProgramID = "";

	/** コンテキストパスURL */
	private String sContextPathURL = "";


	/**
	 * コンストラクタ。
	 */
	public CommonAppletVo() {
	}


	//==================================================
	//get系メソッド
	//==================================================

	/**
	 * プログラムIDを取得します。
	 * @return プログラムID
	 */
	public String getProgramID() {
		return this.sProgramID;
	}

	/**
	 * コンテキストパスURLを取得します。
	 * @return コンテキストパスURL
	 */
	public String getContextPathURL() {
		return this.sContextPathURL;
	}

	//==================================================
	//set系メソッド
	//==================================================

	/**
	 * プログラムIDを設定します。
	 * @param sProgramID プログラムID
	 */
	public void setProgramID(String sProgramID) {
		this.sProgramID = sProgramID;
	}

	/**
	 * コンテキストパスURLを設定します。
	 * @param sContextPathURL コンテキストパスURL
	 */
	public void setContextPathURL(String sContextPathURL) {
		this.sContextPathURL = sContextPathURL;
	}

}
