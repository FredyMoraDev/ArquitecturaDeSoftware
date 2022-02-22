package uia.com.apimvcrest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HolaController {

    @GetMapping("/hola")
    public String saludos()
    {
        return "Hola UIA ..asdasdasdasda.";
    }
}