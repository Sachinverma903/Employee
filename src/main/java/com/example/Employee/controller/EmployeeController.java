package com.example.Employee.controller;

import com.example.Employee.model.Employee;
import com.example.Employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.processing.Generated;
import java.util.List;
import java.util.Objects;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeService service;
@RequestMapping("/")
    public String getHomepage(Model model){
        //model.addAttribute("listEmployee" , service.getAllEmployee());
       return findPaginated(1,"name","asc",model);
    }
    @RequestMapping("/showNewEmployeeForm")
 public String showNewEmployeeForm(Model model){
     Employee employee = new Employee();
    model.addAttribute("employee",employee);
    return "new_employee";
 }
    @PostMapping("/saveEmployee")
 public String saveEmployee(@ModelAttribute("employee") Employee employee){
    service.saveEmployee(employee);
    return "redirect:/";
 }
 @GetMapping("/showFormForUpdate/{id}")
 public String getEmpById(@PathVariable long id, Model  model){
    Employee employee = service.getEmpById(id);
    model.addAttribute("employee",employee);
    return "update_employee";
 }
 @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable long id, Model model){
    service.deleteEmployee(id);
    return "redirect:/";
 }
 @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable int pageNo,@RequestParam String sortField,@RequestParam String sortDir, Model model){
 int   pageSize = 5;
    Page<Employee> page = service.findPaginated(pageNo,pageSize,sortField,sortDir);
    List<Employee> listEmployee = page.getContent();
    model.addAttribute("currentPage",pageNo);
    model.addAttribute("totalPages",page.getTotalPages());
    model.addAttribute("totalItems",page.getTotalElements());
    model.addAttribute("listEmployee",listEmployee);

    model.addAttribute("sortDir" ,sortDir);
    model.addAttribute("sortField" ,sortField);
    model.addAttribute("reverseSortDir",sortDir.equals("asc") ? "desc" : "asc");

    return "index";
 }
 @RequestMapping("/search")
    public String search(@RequestParam String keyword, Model model){
   List <Employee> employee1= service.getEmpByName(keyword);
   model.addAttribute("keyword", keyword) ;
   model.addAttribute("employeeList",employee1);
  //return employee1;
     if (employee1.size()==0){
         return "errorPage";
     }
     if(Objects.equals(keyword, "")){
        return "redirect:/";
     }
     else {
         return "searchResult";
     }
 }

}
