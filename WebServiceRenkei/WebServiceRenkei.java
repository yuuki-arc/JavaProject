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
 * Web�T�[�r�X�A�g�p��I/F�N���X�ł��B
 */
public class WebServiceRenkei {

	/** ���ʃR�[�h - ���� */
	public static final String RESULT_CODE_SUCCESS = "0";

	/** ���ʃR�[�h - ���s�F���[�U�[�G���[ */
	public static final String RESULT_CODE_FAILURE_USER = "-9001";
	/** ���ʃR�[�h - ���s�F�V�X�e���G���[ */
	public static final String RESULT_CODE_FAILURE_SYSTEM = "-9999";

	/** URL�G���R�[�h - UTF-8 */
	public static final String URL_ENCODE_UTF8 = "UTF-8";


	/**
	 * Web�T�[�r�X�ɘA�g���ĘA�g���ʂ��擾���܂��B
	 * @param sParam1 �p�����[�^1
	 * @param sParam2 �p�����[�^2
	 * @exception Exception ��O�N���X
	 * @return ���ʃR�[�h
	 */
	public static String postWebService(String sParam1, String sParam2) throws Exception {
		String sResultCode = "";
		String sPostParam = "p1=" + URLEncoder.encode(sParam1, URL_ENCODE_UTF8)
						  + "&p2=" + URLEncoder.encode(sParam2, URL_ENCODE_UTF8);
		sResultCode = requestWebService("Service/AccessName1", sPostParam);
		return sResultCode;
	}

	/**
	 * Web�T�[�r�X�ɘA�g���ĘA�g���ʂ��擾���܂��B
	 * @param sParam1 �p�����[�^1
	 * @exception Exception ��O�N���X
	 * @return ���ʃR�[�h
	 */
	public static String postWebService(String sParam1) throws Exception {
		String sResultCode = "";
		String sPostParam = "p1=" + URLEncoder.encode(sParam1, URL_ENCODE_UTF8);
		sResultCode = requestWebService("Service/AccessName2", sPostParam);
		return sResultCode;
	}

	/**
	 * Web�T�[�r�X�Ƃ̘A�g�������s���܂��B
	 * @param sAccessName Web�T�[�r�X�A�g�A�N�Z�X��
	 * @param sPostParam POST�p�����[�^
	 * @exception Exception ��O�N���X
	 * @return ���ʃR�[�h
	 */
	private static String requestWebService(String sAccessName, String sPostParam) throws Exception {

		// Web�T�[�r�X�ƘA�g�iPOST���M�j
		HttpURLConnection uc = null;
		OutputStream os = null;
		PrintStream ps = null;
		try {
			// HTTP���N�G�X�g���I�[�v��
			String sRootUrl = "http://www.example.com/service/";
			URL url = new URL(sRootUrl + sAccessName);	// Web�T�[�r�XURL�i�����[�gURL�{�A�N�Z�X���j
			uc = (HttpURLConnection)url.openConnection();

			//�f�[�^��POST
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

		// ���X�|���X���󂯎���Č��ʂ�Ԃ�
		String sResultCode = "";
		try {
			// �ʐM�����̏ꍇ�A���X�|���X��XML�h�L�������g�ɕϊ����ď������s��
			if (uc.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputSource inSrc = new InputSource(uc.getInputStream());
				XPath xpath = XPathFactory.newInstance().newXPath();

				// Response�v�f���擾
				String sExpression = "//Response";
				Node responseNode = (Node) xpath.evaluate(sExpression, inSrc, XPathConstants.NODE);
				// Response�v�f�̒���result�v�f���擾
				Node resultNode = (Node) xpath.evaluate("result", responseNode, XPathConstants.NODE);

				// ���ʃR�[�h���Z�b�g
				sResultCode = resultNode.getTextContent();

			} else {
				// ���X�|���X�R�[�h��200�ȊO�̏ꍇ�̓G���[
				String sStatus = uc.getResponseCode() + " " + uc.getResponseMessage();
				throw new Exception("Web�T�[�r�X�Ƃ̒ʐM���ɖ�肪�����������߁A����ɏI���ł��܂���ł����BHTTP Status Code = [" + sStatus + "]");
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
	 * ���ʃR�[�h�����b�Z�[�W�ɕϊ����܂��B
	 * @param sResultCode
	 * @return ���ʃ��b�Z�[�W
	 */
	public static String getResultMessage(String sResultCode) {
		String sResultMessage = "";
		if (RESULT_CODE_SUCCESS.equals(sResultCode)) {
			sResultMessage = "����";
		} else if (RESULT_CODE_FAILURE_USER.equals(sResultCode)) {
			sResultMessage = "���[�U�[�G���[";
		} else if (RESULT_CODE_FAILURE_SYSTEM.equals(sResultCode)) {
			sResultMessage = "�V�X�e���G���[";
		} else if (!"".equals(sResultCode)){
			sResultMessage = "�G���[�R�[�h����`����Ă��܂���B";
		}
		return sResultMessage;
	}

}
