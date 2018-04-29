package com.wedul.pdf;

import java.io.FileInputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.ElementHandlerPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

@Component
public class PDFView extends AbstractITextPdfView {

    @SuppressWarnings({ "static-access", "deprecation", "unchecked" })
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	// version 7    	
//    	PdfWriter writer2 = PdfWriter.getInstance(document, response.getOutputStream());
//    	String fileName = String.valueOf(model.get("fileName"));
//    	 
//    	// 파일 다운로드 설정
//    	response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
//    	response.setHeader("Content-Transfer-Encoding", "binary");
//    	response.setContentType("application/pdf");
//    	
//    	document.open();
//    	XMLWorkerHelper helper = XMLWorkerHelper.getInstance();
//
//    	// 폰트 설정에서 별칭으로 줬던 "MalgunGothic"을 html 안에 폰트로 지정한다.
//    	String htmlStr = "<html><head><body style='font-family: MalgunGothic;'>"
//    	            + "<p>PDF 안에 들어갈 내용입니다.</p>"
//    	            + "<div><h3>한글, English, 漢字.</h3></div>"
//    	        + "</body></head></html>";
//    	 
//    	HtmlConverter.convertToPdf(htmlStr, response.getOutputStream(), null);
    	
    	
    	// version 5
    	// PdfWriter 생성
    	PdfWriter.getInstance(document, response.getOutputStream());
    	String fileName = String.valueOf(model.get("fileName"));
    	 
    	// 파일 다운로드 설정
    	response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
    	response.setHeader("Content-Transfer-Encoding", "binary");
    	response.setContentType("application/pdf");
    	 
    	// Document 오픈
    	document.open();
    	
    	// CSS
    	CSSResolver cssResolver = new StyleAttrCSSResolver();
    	CssFile cssFile = XMLWorkerHelper.getInstance().getCSS(new FileInputStream(PDFView.class.getClassLoader().getResource("pdf.css").getPath()));
    	cssResolver.addCss(cssFile);
    	     
    	// HTML, 폰트 설정
    	XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
    	fontProvider.register(PDFView.class.getClassLoader().getResource("malgun.ttf").getPath(), "MalgunGothic"); // MalgunGothic은 alias,
    	CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
    	 
    	HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
    	htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
    	 
    	// Pipelines
    	ElementList elements = new ElementList();
        ElementHandlerPipeline end = new ElementHandlerPipeline(elements, null);
    	HtmlPipeline html = new HtmlPipeline(htmlContext, end);
    	CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
    	
    	String htmlStr0 = "<html><head></head><body style='font-family: MalgunGothic;'>"
    			+ "<p>PDF 안에sdf 들어갈 내용입니다.</p>"
    			+ "<div style='text-align:center; font-size:30px;'; ><h3>한글sdf, English, 漢字.</h3></div>"
    			+ "</body></html>";
    	
    	XMLWorker worker = new XMLWorker(css, true);
    	XMLParser xmlParser = new XMLParser(worker, Charset.forName("UTF-8"));
    	
    	for (int i =0 ; i <= 1 ; i++) {
    		// 폰트 설정에서 별칭으로 줬던 "MalgunGothic"을 html 안에 폰트로 지정한다.
    		StringReader strReader;
    		
			strReader = new StringReader(htmlStr0);
    		xmlParser.parse(strReader);
    		
    		PdfPTable table = new PdfPTable(1);
    		PdfPCell cell = new PdfPCell();
    		
    		for (Element element : elements) {
    			cell.addElement(element);
    		}
    		table.addCell(cell);
    		document.add(table);
    		document.newPage();
    	}
    	
		document.close();
    	writer.close();
    	
    	
    	// version 2                
//        String fileName = String.valueOf(model.get("fileName"));
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
//        response.setHeader("Content-Transfer-Encoding", "binary");
//        response.setContentType("application/pdf");
//        
//        StyleSheet css = new StyleSheet();
//        css.loadTagStyle(HtmlTags.H1, "color", "red");
//        
//        HTMLWorker htmlWorker = new HTMLWorker(document);
//        HashMap<String, Object> interfaceProps = new HashMap<String, Object>();
//        
//        DefaultFontProvider dfp = new DefaultFontProvider(PDFView.class.getClassLoader().getResource("malgun.ttf").getPath());
//        interfaceProps.put(HTMLWorker.FONT_PROVIDER, dfp);
//
//        StringReader reader = null; 
//        PdfWriter.getInstance(document, response.getOutputStream());
//        document.open();
//        
//        reader = new StringReader(
//                  new
//                  StringBuffer("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
//                  .append("<html xmlns=\"http://www.w3.org/1999/xhtml\"><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body>").append("<h1>").append("test").
//                  append("</h1>")
//                  .append("테스트 입니다").append("</body></html>").toString());
//        
//        List<Element> objects = htmlWorker.parseToList(reader, css, interfaceProps);
//        for (int k = 0; k < objects.size(); ++k) {
//            document.add((Element) objects.get(k));
//        }
//            
//        document.close();
    }
}