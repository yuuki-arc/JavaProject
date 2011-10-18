package com.example.applet;

import java.io.Serializable;

/**
 * �A�v���b�g���ʏ���ValueObject�N���X�ł��B
 *
 * @version 1.0
 * <dd>�X�V�����i���t �X�V�� �ύX���e�j
 * <dd>2009/11/16 m.yamaoka �V�K�쐬
 */
public class CommonAppletVo implements Serializable {

	private static final long serialVersionUID = 1L;

	//==================================================
	//�C���X�^���X�ϐ�
	//==================================================

	/** �v���O����ID */
	private String sProgramID = "";

	/** �R���e�L�X�g�p�XURL */
	private String sContextPathURL = "";


	/**
	 * �R���X�g���N�^�B
	 */
	public CommonAppletVo() {
	}


	//==================================================
	//get�n���\�b�h
	//==================================================

	/**
	 * �v���O����ID���擾���܂��B
	 * @return �v���O����ID
	 */
	public String getProgramID() {
		return this.sProgramID;
	}

	/**
	 * �R���e�L�X�g�p�XURL���擾���܂��B
	 * @return �R���e�L�X�g�p�XURL
	 */
	public String getContextPathURL() {
		return this.sContextPathURL;
	}

	//==================================================
	//set�n���\�b�h
	//==================================================

	/**
	 * �v���O����ID��ݒ肵�܂��B
	 * @param sProgramID �v���O����ID
	 */
	public void setProgramID(String sProgramID) {
		this.sProgramID = sProgramID;
	}

	/**
	 * �R���e�L�X�g�p�XURL��ݒ肵�܂��B
	 * @param sContextPathURL �R���e�L�X�g�p�XURL
	 */
	public void setContextPathURL(String sContextPathURL) {
		this.sContextPathURL = sContextPathURL;
	}

}
