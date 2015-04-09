package kr.co.leem.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2015-03-18.
 */
@Controller
@RequestMapping(value = "main")
public class MainController {
	@RequestMapping(value = "index")
	public String mainIndex() throws Exception {
		return "main/index";
	}
}
