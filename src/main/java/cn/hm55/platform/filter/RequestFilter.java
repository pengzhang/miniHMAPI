package cn.hm55.platform.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 
 * @author zhangpeng
 *
 */
public class RequestFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(final HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

		ServletRequest requestWrapper = null;
		if (request instanceof HttpServletRequest) {
			requestWrapper = new MAPIHttpServletRequestWrapper((HttpServletRequest) request);
		}
		if (requestWrapper == null) {
			chain.doFilter(request, response);
		} else {
			chain.doFilter(requestWrapper, response);
		}

	}
}

class MAPIHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private static Log logger = LogFactory.getLog(MAPIHttpServletRequestWrapper.class);

	private final byte[] body;

	public MAPIHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		body = IOUtils.toByteArray(request.getInputStream());
		logger.info("=============================================");
		logger.info("[request method] " + request.getMethod());
		logger.info("[request url] " + request.getRequestURI());
		logger.info("[userAgent info]" + request.getHeader("User-Agent"));
		logger.info("[params]" + new String(body, "UTF8"));
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);
		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				return bais.read();
			}
		};
	}

}
