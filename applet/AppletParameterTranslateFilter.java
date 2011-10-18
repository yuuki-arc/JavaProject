package com.example.applet;

import com.example.log.Log;
import com.example.log.LogFactory;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;



/**
 * �A�v���b�g�E�T�[�u���b�g�Ԃ̒ʐM���̏������s��Filter�N���X�ł��B
 *
 * @version 1.0
 * <dd>�X�V�����i���t �X�V�� �ύX���e�j
 * <dd>2009/11/06 m.yamaoka �V�K�쐬
 */
public class AppletParameterTranslateFilter implements Filter {

	// LogClass
	private static final Log log = LogFactory.getLog(AppletParameterTranslateFilter.class.getName());


    /**
     * �R���X�g���N�^�B
     */
    public AppletParameterTranslateFilter() {
    }


    /**
     * Filter�̏������������s���܂��B
     * @param config FilterConfig
     */
    public void init(FilterConfig config) throws ServletException {
    }

    /**
     * Filter�̏I���������s���܂��B
     */
    public void destroy() {
    }

    /**
     * HTTP ���N�G�X�g�̏����B
     * @param request ServletRequest
     * @param response ServletResponse
     * @param chain FilterChain
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest)request;

		DataInputStream dis = null;
		AppletParameterMapper prm = null;

		// �V���A���C�Y���ꂽ���N�G�X�g�I�u�W�F�N�g���A�f�B�V���A���C�Y����
		try {
			dis = new DataInputStream( req.getInputStream() );
			try {
				String sParameterString = AppletParameterMapper.getEncodeFromApplet(dis.readUTF());
				prm = new AppletParameterMapper(sParameterString);
			} catch (EOFException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw new ServletException(e.getMessage(), e);
		} finally {
			dis.close();
		}

		// Applet����n���ꂽ�I�u�W�F�N�g�����N�G�X�g�ɕێ�����
		req.setAttribute("REQUEST_PARAM_APPLET_PARAM", prm);

		chain.doFilter(request, response);
    }

}
