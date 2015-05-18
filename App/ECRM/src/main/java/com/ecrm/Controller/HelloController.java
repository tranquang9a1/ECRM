package com.ecrm.Controller;

import com.ecrm.DAO.Impl.UserDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HelloController {
    @Autowired
    UserDAOImpl userDAO;


	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("user",userDAO.findAll());
		model.addAttribute("message", "Hello world!");
		return "hello";
	}
}