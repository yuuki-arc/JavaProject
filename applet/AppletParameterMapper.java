package com.example.applet;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * �A�v���b�g�E�T�[�u���b�g�Ԃ̒ʐM�Ŏg�p����ValueObject�N���X�ł��B
 *
 * @version 1.0
 * <dd>�X�V�����i���t �X�V�� �ύX���e�j
 * <dd>2009/11/16 m.yamaoka �V�K�쐬
 */
public class AppletParameterMapper implements Serializable {

	private static final long serialVersionUID = 1L;

	/** ���N�G�X�g��ʁF�Z�b�V�������擾 */
	public static final String REQUEST_TYPE_GET_SESSION_ATTRIBUTE = "GET_SESSION_ATTRIBUTE";
	/** ���N�G�X�g��ʁF�Z�b�V�������ݒ� */
	public static final String REQUEST_TYPE_SET_SESSION_ATTRIBUTE = "SET_SESSION_ATTRIBUTE";
	/** ���N�G�X�g��ʁF�Z�b�V�������폜 */
	public static final String REQUEST_TYPE_REMOVE_SESSION_ATTRIBUTE = "REMOVE_SESSION_ATTRIBUTE";

	/** �p�����[�^�}�b�v�L�[�F�v���O����ID */
	private static final String PARAMETER_MAP_KEY_PROGRAM_ID = "_PARAMETER_MAP_KEY_PROGRAM_ID";
	/** �p�����[�^�}�b�v�L�[�F�R���e�L�X�g�p�XURL */
	private static final String PARAMETER_MAP_KEY_CONTEXT_PATH_URL = "_PARAMETER_MAP_KEY_CONTEXT_PATH_URL";
	/** �p�����[�^�}�b�v�L�[�F���N�G�X�g��� */
	private static final String PARAMETER_MAP_KEY_REQUEST_TYPE = "_PARAMETER_MAP_KEY_REQUEST_TYPE";


	//==================================================
	//�C���X�^���X�ϐ�
	//==================================================

	/** �p�����[�^�}�b�v */
	private ConcurrentHashMap<String, String> parameterMap = null;


	/**
	 * �R���X�g���N�^�B
	 */
	public AppletParameterMapper(CommonAppletVo vo) {
		this.parameterMap = new ConcurrentHashMap<String, String>();
		setProgramID(vo.getProgramID());
		setContextPathURL(vo.getContextPathURL());
	}

	/**
	 * �R���X�g���N�^�B
	 * @ParameterString �p�����[�^������
	 */
	public AppletParameterMapper(String sParameterString) throws UnsupportedEncodingException {
		this.parameterMap = new ConcurrentHashMap<String, String>();
		// �p�����[�^��������p�����[�^�}�b�v�ɕϊ�
		String[] parameter = sParameterString.split("&");
		for (int i1 = 0; i1 < parameter.length; i1++) {
			String[] val = parameter[i1].split("=");
			String sKey = URLDecoder.decode(val[0], AppletPropertyConstant.URL_ENCODE);
			String sValue = (val.length == 1 ? "" : URLDecoder.decode(val[1], AppletPropertyConstant.URL_ENCODE));
			this.parameterMap.putIfAbsent(sKey, sValue);
		}
	}


	//==================================================
	//get�n���\�b�h
	//==================================================

	/**
	 * �p�����[�^�}�b�v���擾���܂��B
	 * @return �p�����[�^�}�b�v
	 */
	public ConcurrentHashMap<String, String> getParameterMap() {
		return this.parameterMap;
	}

	/**
	 * �p�����[�^�}�b�v����p�����[�^���擾���܂��B
	 * @return �p�����[�^
	 */
	public String getParameter(String sKey) {
		return getParameterMap().get(sKey);
	}

	/**
	 * �v���O����ID���擾���܂��B
	 * @return �v���O����ID
	 */
	public String getProgramID() {
		return getParameterMap().get(PARAMETER_MAP_KEY_PROGRAM_ID);
	}

	/**
	 * �R���e�L�X�g�p�XURL���擾���܂��B
	 * @return �R���e�L�X�g�p�XURL
	 */
	public String getContextPathURL() {
		return getParameterMap().get(PARAMETER_MAP_KEY_CONTEXT_PATH_URL);
	}

	/**
	 * ���N�G�X�g��ʂ��擾���܂��B
	 * @return ���N�G�X�g���
	 */
	public String getRequestType() {
		return getParameterMap().get(PARAMETER_MAP_KEY_REQUEST_TYPE);
	}

	//==================================================
	//set�n���\�b�h
	//==================================================

	/**
	 * �p�����[�^��ݒ肵�܂��B
	 * @param sKey �L�[
	 * @param sValue �l
	 */
	public void setParameter(String sKey, String sValue) {
		getParameterMap().putIfAbsent(sKey, sValue);
	}

	/**
	 * �v���O����ID��ݒ肵�܂��B
	 * @param sProgramID �v���O����ID
	 */
	public void setProgramID(String sProgramID) {
		getParameterMap().putIfAbsent(PARAMETER_MAP_KEY_PROGRAM_ID, sProgramID);
	}

	/**
	 * �R���e�L�X�g�p�XURL��ݒ肵�܂��B
	 * @param sContextPathURL �R���e�L�X�g�p�XURL
	 */
	public void setContextPathURL(String sContextPathURL) {
		getParameterMap().putIfAbsent(PARAMETER_MAP_KEY_CONTEXT_PATH_URL, sContextPathURL);
	}

	/**
	 * ���N�G�X�g��ʂ�ݒ肵�܂��B
	 * @param sRequestType ���N�G�X�g���
	 */
	public void setRequestType(String sRequestType) {
		getParameterMap().putIfAbsent(PARAMETER_MAP_KEY_REQUEST_TYPE, sRequestType);
	}

	//==================================================
	//���̑����\�b�h
	//==================================================

	/**
	 * �p�����[�^��������擾����B
	 * @return ������
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
	 * �T�[�u���b�g�ɓn���p�����[�^�̃G���R�[�h��������擾����B
	 * @param sValue �l
	 * @return �G���R�[�h������
	 */
	public static String getEncodeToServlet(String sValue) {
		String sResult = sValue;	// �ϊ��s�v�̂��߁A����������̂܂ܕԂ�
		return sResult;
	}


	/**
	 * �A�v���b�g����󂯎��p�����[�^�̃G���R�[�h��������擾����B
	 * @param sValue �l
	 * @return �G���R�[�h������
	 */
	public static String getEncodeFromApplet(String sValue) {
		String sResult = sValue;	// �ϊ��s�v�̂��߁A����������̂܂ܕԂ�
		return sResult;
	}

}
