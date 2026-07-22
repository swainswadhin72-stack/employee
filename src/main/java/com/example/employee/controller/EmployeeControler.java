package com.example.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.employee.service.EmployeeService;

import org.springframework.ui.Model;



@Controller
public class EmployeeControler {
	
	@Autowired
	private EmployeeService employeeService;
@GetMapping("/")
public String viewHomepage(Model model) {
	model.addAttribute("listEmployees",employeeService.getAllEmployees());
	return "index";
}

}
