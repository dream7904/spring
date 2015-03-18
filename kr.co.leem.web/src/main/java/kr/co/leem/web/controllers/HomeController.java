package kr.co.leem.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by Administrator on 2015-03-13.
 */
@Controller
public class HomeController {
	@RequestMapping(value = {"/", "login"})
	public String home() throws Exception {
		return "login";
	}
}
