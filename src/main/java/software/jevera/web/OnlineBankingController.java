package software.jevera.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import software.jevera.service.bankaccount.BankAccountService;

@Controller
public class OnlineBankingController {

    @RequestMapping(value = "/helloworld", method = RequestMethod.GET)
    public String helloworld(Model model, @RequestParam("name") String name){
        model.addAttribute("name", name);
        return "helloworld";
    }

}
