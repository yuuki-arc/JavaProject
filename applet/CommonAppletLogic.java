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
 * �A�v���b�g���ʃ��W�b�N�N���X�ł��B
 *
 * @version 1.0
 * <dd>�X�V�����i���t �X�V�� �ύX���e�j
 * <dd>2009/11/16 m.yamaoka �V�K�쐬
 */
public class CommonAppletLogic {

	/**
	 * �R���X�g���N�^�B
	 */
	public CommonAppletLogic() {
	}

	/**
	 * Applet����Servlet�փ��N�G�X�g���郁�\�b�h��񋟂��܂��B
	 * JavaScript���璼�ڃ��\�b�h���Ăяo�����Ƃ��\�ł��B
	 * @param param ���N�G�X�g�p�����[�^��ێ������I�u�W�F�N�g
	 * @return ���N�G�X�g���ʃI�u�W�F�N�g
	 */
	public static Object requestServlet(AppletParameterMapper prm) {

		Object result = null;

		try {
			// �T�[�u���b�g���Ăяo��URL�I�u�W�F�N�g�𐶐�
			URL url = new URL(prm.getContextPathURL() + "/AppletRequestServlet.applet");
			// �ڑ��I�u�W�F�N�g�𐶐�
			URLConnection con = url.openConnection();
			con.setRequestProperty("CONTENT_TYPE", "application/octet-stream");
			con.setDoOutput(true);
			con.setUseCaches(false);

			// Servlet�փ��N�G�X�g�𑗂�
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

			// ���ʂ��擾����
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream( con.getInputStream() );
				result = ois.readObject();
			} finally {
				if (ois != null) {try{ois.close();} catch (Exception e) {}}
			}

			// ���ʂ���O�̏ꍇ�͗�O�J��
			if (result instanceof Exception) {
				throw (Exception)result;
			}

		} catch (UnsupportedEncodingException e) {
			String sMessage = "Applet����Servlet�ւ̃��N�G�X�g�ŗ�O���������܂����B�F" + e.getMessage();
			throw new AppletRequestServletException(sMessage, e);
		} catch (MalformedURLException e) {
			String sMessage = "Applet����Servlet�ւ̃��N�G�X�g�ŗ�O���������܂����B�F" + e.getMessage();
			throw new AppletRequestServletException(sMessage, e);
		} catch (IOException e) {
			String sMessage = "Applet����Servlet�ւ̃��N�G�X�g�ŗ�O���������܂����B�F" + e.getMessage();
			throw new AppletRequestServletException(sMessage, e);
		} catch (ClassNotFoundException e) {
			String sMessage = "Applet����Servlet�ւ̃��N�G�X�g�ŗ�O���������܂����B�F" + e.getMessage();
			throw new AppletRequestServletException(sMessage, e);
		} catch (Exception e) {
			String sMessage = "Applet����Servlet�ւ̃��N�G�X�g�ŗ�O���������܂����B�F" + e.getMessage();
			throw new AppletRequestServletException(sMessage, e);
		}

		return result;
	}


	/**
	 * �Z�b�V���������擾���܂��B
	 * @param commonVo �A�v���b�g���ʏ��
	 * @param sKey �Z�b�V�����L�[
	 * @return �Z�b�V�������iObject�^�j
	 */
	public static Object getSessionAttribute(CommonAppletVo commonVo, String sSessionKey) {

		// ���N�G�X�g�I�u�W�F�N�g�𐶐�
		AppletParameterMapper prm = new AppletParameterMapper(commonVo);
		prm.setRequestType(AppletParameterMapper.REQUEST_TYPE_GET_SESSION_ATTRIBUTE);

		// �Z�b�V�����L�[���Z�b�g
		prm.setParameter("sessionKey", sSessionKey);

		// �T�[�u���b�g���Ăяo��
		Object result = requestServlet(prm);

		return result;
	}


	/**
	 * �Z�b�V��������ݒ肵�܂��B
	 * @param commonVo �A�v���b�g���ʏ��
	 * @param sKey �Z�b�V�����L�[
	 * @param sValue �l
	 */
	public static void setSessionAttribute(CommonAppletVo commonVo, String sSessionKey, String sValue) {

		// ���N�G�X�g�I�u�W�F�N�g�𐶐�
		AppletParameterMapper prm = new AppletParameterMapper(commonVo);
		prm.setRequestType(AppletParameterMapper.REQUEST_TYPE_SET_SESSION_ATTRIBUTE);

		// �Z�b�V�����L�[���Z�b�g
		prm.setParameter("sessionKey", sSessionKey);
		prm.setParameter("value", sValue);

		// �T�[�u���b�g���Ăяo��
		requestServlet(prm);
	}


	/**
	 * �Z�b�V���������폜���܂��B
	 * @param commonVo �A�v���b�g���ʏ��
	 * @param sKey �Z�b�V�����L�[
	 */
	public static void removeSessionAttribute(CommonAppletVo commonVo, String sSessionKey) {

		// ���N�G�X�g�I�u�W�F�N�g�𐶐�
		AppletParameterMapper prm = new AppletParameterMapper(commonVo);
		prm.setRequestType(AppletParameterMapper.REQUEST_TYPE_REMOVE_SESSION_ATTRIBUTE);

		// �Z�b�V�����L�[���Z�b�g
		prm.setParameter("sessionKey", sSessionKey);

		// �T�[�u���b�g���Ăяo��
		requestServlet(prm);
	}


	/**
	 * �G���[�������̃��O���o�͂��܂��B
	 * @param vo �A�v���b�g���ʏ���ValueObject
	 * @param log ���O�o�̓N���X
	 * @param sMessage �G���[���b�Z�[�W
	 */
	public static void outErrorLog(CommonAppletVo vo, Log log, String sMessage) {
		outErrorLog(vo, log, sMessage, null);
	}


	/**
	 * �G���[�������̃��O���o�͂��܂��B
	 * @param vo �A�v���b�g���ʏ���ValueObject
	 * @param log ���O�o�̓N���X
	 * @param sMessage �G���[���b�Z�[�W
	 * @param e �G���[�������̗�O�N���X
	 */
	public static void outErrorLog(CommonAppletVo vo, Log log, String sMessage, Exception e) {
		if (e == null) {
			log.error(sMessage);
		} else {
			log.error(sMessage, e);
		}
	}


	/**
	 * ��O�̃��b�Z�[�W���擾���܂��B
	 * @param ex ��O {@link Exception}
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
