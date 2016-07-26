package ueditor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jinweihe on 16/7/25.
 */
@Controller
@RequestMapping("/ueditor")
public class UEditorController {

    @RequestMapping(value="/dispatch")
    public void dispatch(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "upfile", required=false) MultipartFile upfile,
            @RequestParam(value = "action") String action) {
        
    	switch (action) {
		case "config":
			
			break;

		default:
			break;
		}
    	
    }

}
