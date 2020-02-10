package upp.project.controllers;

import javax.ws.rs.core.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import upp.project.services.UploadService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
public class UploadController {
	
	@Autowired
	UploadService uploadService;

	@PostMapping("/")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("processInstanceId") String processInstanceId) {
		
		System.out.println("PAP | uploading file");
		
		try {
			uploadService.upload(file, processInstanceId);
			
			return ResponseEntity.ok().build();
		}
		catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	
	@GetMapping("/{processInstanceId}/{fileName:.+}")
	public ResponseEntity<?> downloadFile(@PathVariable String processInstanceId, @PathVariable String fileName) {
		
		System.out.println("PAP | downloading file");
		
		try {
			Resource resource = uploadService.download(processInstanceId, fileName);
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		}
		catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}

}
