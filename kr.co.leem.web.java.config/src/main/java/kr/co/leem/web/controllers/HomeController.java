package kr.co.leem.web.controllers;

import kr.co.leem.web.services.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2015-04-08.
 */
@Controller
public class HomeController {
	@RequestMapping(value = {"/", "login"})
	public String home() throws Exception {
		return "login";
	}
}