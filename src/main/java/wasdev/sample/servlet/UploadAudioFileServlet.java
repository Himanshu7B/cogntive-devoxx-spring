package wasdev.sample.servlet;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Servlet implementation class SimpleServlet
 */
@Controller
@RequestMapping("/uploadAudioFile")
@MultipartConfig
public class UploadAudioFileServlet extends HttpServlet {

	private static final long serialVersionUID = -5325446104289294461L;
	
	private static final Logger LOGGER = Logger.getLogger(UploadAudioFileServlet.class.getName());
	


	@RequestMapping(method = RequestMethod.GET)
	 protected void doGet(final HttpServletRequest request,
             final HttpServletResponse response) throws ServletException, IOException {
		 doPost(request, response);
	 }

	@RequestMapping(method = RequestMethod.POST)
	public void examplePost(final HttpServletRequest request,
            final HttpServletResponse response, @RequestParam("file") MultipartFile file)throws ServletException, IOException {
		   // Get required parameters
        final String youTubeLink = request.getParameter("link");
        final String documentName = request.getParameter("documentName");
        LOGGER.info("link: " + youTubeLink);
        LOGGER.info("documentName: " + documentName);

        // Get audio file
        File tempFile = createTempFile();
        file.transferTo(tempFile);
        
        LOGGER.info("temp file created: " + tempFile.getName());

        // Process audio file asynchronously
        new ProcessAudioFile().execute(tempFile, documentName, youTubeLink);
        LOGGER.info("Audio file processed");

        // Reply to user
        response.setContentType("text/html");
        response.getWriter().print("Processing");
		//System.out.println(file);
		//return "";
	    // Handle form upload and return a view
	    // ...
	}
	
	/*@RequestMapping(method = RequestMethod.POST, consumes = "multipart/form-data")
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response) throws ServletException, IOException {

        // Get required parameters
        final String youTubeLink = request.getParameter("link");
        final String documentName = request.getParameter("documentName");
        LOGGER.info("link: " + youTubeLink);
        LOGGER.info("documentName: " + documentName);

        // Get audio file
        File tempFile = createTempFile(request.getPart("file"));
        LOGGER.info("temp file created: " + tempFile.getName());

        // Process audio file asynchronously
        new ProcessAudioFile().execute(tempFile, documentName, youTubeLink);
        LOGGER.info("Audio file processed");

        // Reply to user
        response.setContentType("text/html");
        response.getWriter().print("Processing");
    }
*/

    private File createTempFile() throws IOException, ServletException {
        // Retrieves <input type="file" name="file">
      //  InputStream fileContentInputStream = filePart.getInputStream();
        File tempFile = File.createTempFile("temp-file-name", null);
      //  FileUtils.copyInputStreamToFile(fileContentInputStream, tempFile);
        return tempFile;
    }
}
