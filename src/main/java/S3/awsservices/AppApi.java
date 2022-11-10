package S3.awsservices;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.awt.*;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/static")
public class AppApi {

    private final AppService appService;


    public AppApi(AppService appService) {
        this.appService = appService;
    }
    //upload
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Map<String, String> upload(@RequestParam MultipartFile file) throws IOException{
        return appService.upload(file);
    }

    //delete
    @DeleteMapping("/delete")
    Map<String, String> delete(@RequestParam String fileLink) {
        return appService.delete(fileLink);

    }
}
