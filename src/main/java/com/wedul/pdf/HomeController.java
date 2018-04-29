package com.wedul.pdf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/download/pdf", method = RequestMethod.GET)
	public ModelAndView downloadPdf() {
	  ModelAndView modelAndView = new ModelAndView("pdfView", "fileName", "test.pdf");
	  return modelAndView;
	}
	
}
