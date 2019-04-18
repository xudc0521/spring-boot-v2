package com.xudc.router.handler;

import com.xudc.router.exceptions.CheckException;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * 设置优先级小于-1即可，因为默认的异常处理器DefaultErrorWebExceptionHandler优先级是-1
 * @see ErrorWebFluxAutoConfiguration#errorWebExceptionHandler(org.springframework.boot.web.reactive.error.ErrorAttributes)
 * @author xudc
 */
@Component
@Order(-2)
public class ExceptionHandler implements WebExceptionHandler {

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ServerHttpResponse response = exchange.getResponse();
		// 设置响应头400
		response.setStatusCode(HttpStatus.BAD_REQUEST);
		// 设置返回类型
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
		// 异常信息
		String errorMsg = toStr(ex);
		DataBuffer db = response.bufferFactory().wrap(errorMsg.getBytes());
		return response.writeWith(Mono.just(db));
	}

	private String toStr(Throwable ex) {
		// 已知异常
		if (ex instanceof CheckException) {
			CheckException e = (CheckException) ex;
			return e.getFieldName() + ": invalid value [" + e.getFieldValue() + "]";
		}
		// 未知异常, 需要打印堆栈, 方便定位
		else {
			ex.printStackTrace();
			return ex.toString();
		}
	}
}
