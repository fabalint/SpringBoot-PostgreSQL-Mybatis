package com.example.campaign.filter.errorhandler.v1;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.example.campaign.filter.errorhandler.v1.dto.ErrorBean;

@RestController
@ResponseBody()
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

	private final static String ERROR_PATH = "/error";
	@Autowired
	private ErrorAttributes errorAttributes;

	private ErrorAttributeOptions errorAttributeOptions;

	/**
	 * Controller for the Error Controller
	 * 
	 * @param errorAttributes
	 * 
	 * @param errorAttributes
	 */
	public ErrorController() {
		this.errorAttributeOptions = ErrorAttributeOptions.of(//
				ErrorAttributeOptions.Include.MESSAGE, //
				ErrorAttributeOptions.Include.EXCEPTION//
		);

	}

//	/**
//	 * Supports the HTML Error View
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = ERROR_PATH, produces = "text/html")
//	public ModelAndView errorHtml(HttpServletRequest request) {
//		return new ModelAndView("/errors/error", getErrorAttributes(request, false));
//	}

	/**
	 * Supports other formats like JSON, XML
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ERROR_PATH)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> error(WebRequest request) {
		Map<String, Object> body = getErrorAttributes(request);
		HttpStatus status = getStatus(request);

		return new ResponseEntity<Map<String, Object>>(body, status);
	}

	/**
	 * Returns the path of the error page.
	 *
	 * @return the error path
	 */
	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

	private Map<String, Object> getErrorAttributes(WebRequest request) {
		return new ErrorBean(this.errorAttributes.getErrorAttributes(request, errorAttributeOptions));
	}

	private HttpStatus getStatus(WebRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code",
				WebRequest.SCOPE_REQUEST);
		if (statusCode != null) {
			try {
				return HttpStatus.valueOf(statusCode);
			} catch (Exception ex) {
			}
		}
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
