package com.example.applet;



/**
 * Applet����Servlet�ւ̃��N�G�X�g���ɔ���������O�p�̗�O�N���X�ł��B
 *
 * @version 1.0
 * <dd>�X�V�����i���t �X�V�� �ύX���e�j
 * <dd>2009/11/16 m.yamaoka �V�K�쐬
 */
public class AppletRequestServletException extends AppletException {

	private static final long serialVersionUID = 1L;

	/**
	 * �w�肳�ꂽ�ڍ׃��b�Z�[�W������O���\�z���܂��B
	 * @param msg �G���[���b�Z�[�W
	 */
	public AppletRequestServletException(String msg) {
		super(msg, null);
	}


	/**
	 * �����ƂȂ�����O�����b�v���A�w�肳�ꂽ�ڍ׃��b�Z�[�W������O���\�z���܂��B
	 * @param msg �G���[���b�Z�[�W
	 * @param ex ��O�I�u�W�F�N�g
	 */
	public AppletRequestServletException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
