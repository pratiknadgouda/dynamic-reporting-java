package com.hls.reports.serviceImpl;
 
import java.io.FileOutputStream;
 
import org.springframework.stereotype.Service;
 
import com.hls.reports.service.PDFConvertorService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
 
@Service
public class PDFConvertorServiceImpl implements PDFConvertorService {
 
	@Override
	public String htmlToPdfConvertor(String processedHtml,String templateName) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			String path= System.getProperty("user.dir") +templateName+ ".pdf";
			System.out.println("path="+path);
			PdfWriter pdfwriter = new PdfWriter(byteArrayOutputStream);
			DefaultFontProvider defaultFont = new DefaultFontProvider(false, true, false);
			ConverterProperties converterProperties = new ConverterProperties();
			converterProperties.setFontProvider(defaultFont);
			HtmlConverter.convertToPdf(processedHtml, pdfwriter, converterProperties);
			FileOutputStream fout = new FileOutputStream(path);
			byteArrayOutputStream.writeTo(fout);
			byteArrayOutputStream.close();
			byteArrayOutputStream.flush();
			fout.close();
			return path;

		} catch(Exception ex) {
			ex.printStackTrace();
			return "";
		}

	}
}