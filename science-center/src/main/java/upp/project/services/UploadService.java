package upp.project.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {
	
	@Autowired
	ProcessService processService;

	private Map<String, String> files = new HashMap<String, String>();

	public void upload(MultipartFile file, String processInstanceId) throws Exception {
		
		System.out.println("PAP | uploading file service");
		
		//check if folder already exists
		if(Files.exists(Paths.get("uploaded-papers/" + processInstanceId))) {
			FileSystemUtils.deleteRecursively(Paths.get("uploaded-papers/" + processInstanceId).toFile());
		}
		try {
			//create the folder
			Files.createDirectory(Paths.get("uploaded-papers/" + processInstanceId));
		} 
		catch (IOException e) {
			throw e;
		}
		
		//save the file
		try {
			Files.copy(file.getInputStream(), Paths.get("uploaded-papers/" + processInstanceId).resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			files.put(processInstanceId, file.getOriginalFilename());
		} 
		catch (Exception e) {
			throw e;
		}
		String filePath = "https://localhost:9997/upload/" + processInstanceId + "/" + file.getOriginalFilename();
		
		processService.setProcessVariable(processInstanceId, "file_path", filePath);
	}

	public Resource download(String processInstanceId, String fileName) throws Exception {
		
		System.out.println("PAP | downloading file service");
		
		try {
			Path file = Paths.get("uploaded-papers/" + processInstanceId + "/" + fileName);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException();
			}
		} 
		catch (MalformedURLException e) {
			throw e;
		}
	}
}
