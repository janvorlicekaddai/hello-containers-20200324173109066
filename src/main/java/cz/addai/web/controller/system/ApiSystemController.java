package cz.addai.web.controller.system;

import cz.addai.service.WatsonSessionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Manifest;

@RestController
public class ApiSystemController {

    @Resource
    private WatsonSessionService watsonSessionService;

    /**
     * Handling GET request to retrieve details from MANIFEST.MF file
     *
     * @return implementation details
     */
    @RequestMapping(value="/system/api", method = RequestMethod.GET)
    @ResponseBody
    public Map<Object, Object> getBuildNumber(HttpServletRequest request) throws IOException {

        ServletContext context = request.getSession().getServletContext();
        InputStream manifestStream = context.getResourceAsStream("/META-INF/MANIFEST.MF");
        Manifest manifest = new Manifest(manifestStream);
        Map<Object, Object> map = new HashMap<>(manifest.getMainAttributes());
        //map.put("Environment", cebConfig.getCebEnvironment());

        watsonSessionService.openSession();
//        CreateSessionOptions options = new CreateSessionOptions.Builder()
//                .assistantId(adamConfig.getWatsonAssistantId()).build();
//        ServiceCall<SessionResponse> serviceCall = assistantService.createSession(options);
//        var response = serviceCall.execute();
//
//        response.getStatusCode();
//
//        System.out.println(response);
//
//        MessageInput mi = new MessageInput.Builder().text("Hi").build();
//
//        MessageOptions options = new MessageOptions.Builder("<workspaceId>")
//                .input(input)
//                .build();
//        assistantService.createSession()

        return map;
    }
}
