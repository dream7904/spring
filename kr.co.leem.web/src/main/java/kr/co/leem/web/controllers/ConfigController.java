package kr.co.leem.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by Administrator on 2015-03-18.
 */
@Controller
@RequestMapping(value = "mng/config")
public class ConfigController {
	@RequestMapping(value = "user")
	public String users() throws Exception {
		return "mng/config/user";
	}

	@RequestMapping(value = "topMenuGrp")
	public String topMenuGrp() throws Exception {
		return "mng/config/topMenuGrp";
	}
}
