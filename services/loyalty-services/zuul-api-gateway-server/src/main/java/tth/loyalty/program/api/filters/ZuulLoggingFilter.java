package tth.loyalty.program.api.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ZuulLoggingFilter extends ZuulFilter{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Object run() throws ZuulException { 
		//log the details of the request
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		log.info("request: {}, request uri: {}" + request, request.getRequestURI());
		
		return request;
	}

	@Override
	public boolean shouldFilter() { 
		//execute for every request
		return true;
	}

	@Override
	public int filterOrder() { 
		return 1;
	}

	@Override
	public String filterType() {
		// when should it filter - prefer before and error
		return "pre";
	}

}
