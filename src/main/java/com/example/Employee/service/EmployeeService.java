package com.example.Employee.service;

import com.example.Employee.model.Employee;
import com.example.Employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository repository;

    public List<Employee> getAllEmployee(){
        List<Employee> list=     repository.findAll();
        return list;
    }
    public void saveEmployee(Employee employee){
        repository.save(employee);
    }
    public Employee getEmpById(long id){
       Optional<Employee> optional = repository.findById(id);
       Employee employee;
        if (optional.isPresent()){
            employee=optional.get();
        }else {
            throw new RuntimeException("data for given id not found");
        }
return employee;
    }
    public void deleteEmployee(long id){
        repository.deleteById(id);
    }

    public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending():Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1,pageSize,sort);
       return repository.findAll(pageable);
    }
    public List<Employee> getEmpByName(String keyword) {
        return repository.findAllByNameContainingIgnoreCase(keyword);
    }
}
