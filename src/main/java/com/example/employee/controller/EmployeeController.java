package com.example.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;

import org.springframework.ui.Model;



@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
@GetMapping("/")
public String viewHomepage(Model model) {
	return findPaginated(1, "firstName","asc", model);
}
@GetMapping("/showNewEmployeeForm")
public String showNewEmployeeForm(Model model) {
	try {
	Employee employee = new Employee();
	model.addAttribute("employee",employee);}
	catch(IllegalArgumentException e) {
		model.addAttribute("error",e.getMessage());
	}
	return "new_employee";
}
@PostMapping("/saveEmployee")
public String saveEmployee(@ModelAttribute("employee") Employee employee, Model model) {
    try {
        employeeService.saveEmployee(employee);
        return "redirect:/";
    } catch (Exception e) {
        model.addAttribute("error", "Email must be unique and not null!");
        return "new_employee"; // stay on same page
    }
}
@GetMapping("/showFormForUpdate/{id}")
public String showFromUpdate(@PathVariable(value="id")long id,Model model) {
	Employee employee=employeeService.getEmployeeById(id);
	model.addAttribute("employee",employee);
	return "update_employee";
}
@GetMapping("/deleteEmployee/{id}")
public String deleteEmployee(@PathVariable(value = "id") long id) {

    // call delete employee method 
    this.employeeService.deleteEmployeeById(id);
    return "redirect:/";
}
@GetMapping("/page/{pageNo}")
public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
		 @RequestParam("sortField") String sortField,
	        @RequestParam("sortDir") String sortDir,
	        Model model) {
    int pageSize = 5;

    Page < Employee > page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
    List < Employee > listEmployees = page.getContent();
//Pagination
    model.addAttribute("currentPage", pageNo);
    model.addAttribute("totalPages", page.getTotalPages());
    model.addAttribute("totalItems", page.getTotalElements());
    //Sorting
    model.addAttribute("sortField", sortField);
    model.addAttribute("sortDir", sortDir);
    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    
    model.addAttribute("listEmployees", listEmployees);
    return "index";
}
}
