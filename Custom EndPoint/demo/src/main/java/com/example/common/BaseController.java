package com.example.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 控制器基类
 */
public abstract class BaseController {
	protected Logger logger = LogManager.getLogger(this.getClass());

	/** 返回成功数据 */
	protected <T> Result<T> response(T data) {
		return ResponseUtil.getSuccessResult(data);
	}

	/**
	 * 返回操作成功
	 *
	 * @return
	 */
	protected Result<Object> response() {
		return ResponseUtil.getSuccessResult(null);
	}


	/** MethodArgumentNotValidException异常处理 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public void exceptionHandler(HttpServletResponse response, MethodArgumentNotValidException ex) {
		List<String> errorMsgs = new ArrayList<String>();
		//解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errorMsgs.add(error.getDefaultMessage());
		}
		String msg = org.springframework.util.StringUtils.collectionToDelimitedString(errorMsgs, ",");
		logger.debug(msg, ex);
		Result<?> result = ResponseUtil.getErrorResult(msg);
		returnResult(response, result);
	}

	private void returnResult(HttpServletResponse response, Result result){
		ServletOutputStream servletOutputStream = null;
		try {
			response.setContentType("application/json;charset=UTF-8");
			servletOutputStream = response.getOutputStream();
			byte[] bytes = JSON.toJSONBytes(result, SerializerFeature.DisableCircularReferenceDetect);
			servletOutputStream.write(bytes);
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		} finally {
			if (servletOutputStream != null) {
				try {
					servletOutputStream.close();
				} catch (IOException e2) {
					logger.error(e2.getMessage(), e2);
				}
			}
		}
	}

	/** BindException异常处理 */
	@ExceptionHandler(BindException.class)
	public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, BindException ex) {
		List<String> errorMsgs = new ArrayList<String>();
		//解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
		for (FieldError error : ex.getFieldErrors()) {
			errorMsgs.add(error.getDefaultMessage());
		}
		String msg = org.springframework.util.StringUtils.collectionToDelimitedString(errorMsgs, ",");
		Result<?> result = ResponseUtil.getErrorResult(msg);
		returnResult(response, result);
	}

	/** BusinessException异常处理 */
	@ExceptionHandler(BusinessException.class)
	public void exceptionHandler(HttpServletResponse response, BusinessException ex) {
		if (ex.getCause() != null) {
			logger.error(ex.getMessage(), ex);
		} else {
			logger.debug(ex.getMessage());
		}
		returnResult(response, ResponseUtil.getBusinessResult(ex));
	}
}
