package hello;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.ServletContextResource;

@SpringBootApplication
@RestController
public class Application {

	@Autowired
	private ServletContext servletContext;

	@RequestMapping("/")
	public String home() {
		return "Hello Docker World - Amit Bondwal test1";
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@RequestMapping(value = "/image-view", method = RequestMethod.GET)
	public String imageView() throws IOException {
		return "image-download";
	}

	@RequestMapping(value = "/image-manual-response", method = RequestMethod.GET)
	public void getImageAsByteArray(HttpServletResponse response) throws IOException {
		
		String rootPath = System.getProperty("user.dir");
		System.out.println("	rootPath	:	"+rootPath);
		String imgPath = rootPath+"deal/images/image-example.jpg";
		//final InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
		//final InputStream in = servletContext.getResourceAsStream(imgPath);
		final InputStream in = servletContext.getResourceAsStream("/image-example.jpg");
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		IOUtils.copy(in, response.getOutputStream());
	}

	@RequestMapping(value = "/image-byte-array", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getImageAsByteArray() throws IOException {
		final InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
		return IOUtils.toByteArray(in);
	}

	@RequestMapping(value = "/image-response-entity", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImageAsResponseEntity() throws IOException {
		ResponseEntity<byte[]> responseEntity;
		final HttpHeaders headers = new HttpHeaders();
		final InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
		byte[] media = IOUtils.toByteArray(in);
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());
		responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping(value = "/image-resource", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Resource> getImageAsResource() {
		
		String rootPath = System.getProperty("user.dir");
		System.out.println("	rootPath	:	"+rootPath);
		String imgPath = rootPath+"deal/images/image-example.jpg";
		
		final HttpHeaders headers = new HttpHeaders();
		Resource resource = new ServletContextResource(servletContext, "/image-example.jpg");
		//Resource resource = new ServletContextResource(servletContext, imgPath);
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
}
