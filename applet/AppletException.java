package com.example.applet;



/**
 * applet �p�b�P�[�W����уT�u�p�b�P�[�W�Ŕ���������O�N���X�ł��B
 *
 * @version 1.0
 * <dd>�X�V�����i���t �X�V�� �ύX���e�j
 * <dd>2009/11/16 m.yamaoka �V�K�쐬
 */
public class AppletException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    private String sMessage;


	/**
	 * �w�肳�ꂽ�ڍ׃��b�Z�[�W������O���\�z���܂��B
	 * @param msg �G���[���b�Z�[�W
	 */
	public AppletException(String msg) {
		this(msg, null);
	}


	/**
	 * �����ƂȂ�����O�����b�v���A�w�肳�ꂽ�ڍ׃��b�Z�[�W������O���\�z���܂��B
	 * @param msg �G���[���b�Z�[�W
	 * @param ex ��O�I�u�W�F�N�g
	 */
	public AppletException(String msg, Throwable ex) {
		super(msg, ex);
		setMessage(msg);
	}


    /**
     * ���b�Z�[�W��Ԃ��܂��B
     * @return ���b�Z�[�W
     */
    public final String getMessage() {
        return this.sMessage;
    }

    /**
     * ���b�Z�[�W��ݒ肵�܂��B
     * @param sMessage ���b�Z�[�W
     */
    protected final void setMessage(String sMessage) {
        this.sMessage = sMessage;
    }

}
