package com.wedul.pdf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Conf {

	@Bean(name = "pdfView")
	public PDFView getPdfView() {
		return new PDFView();
	}
}
