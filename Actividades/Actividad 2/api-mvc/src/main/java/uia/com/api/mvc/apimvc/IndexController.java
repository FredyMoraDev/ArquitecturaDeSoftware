package uia.com.api.mvc.apimvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class IndexController {


    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ResponseBody
    public String showIndex(){
        return "Actividad 2 " +
                "de la materia " +
                "Arquitectura de Software";
    }

}
