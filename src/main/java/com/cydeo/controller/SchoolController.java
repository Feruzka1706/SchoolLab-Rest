package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TeacherDTO;
import com.cydeo.service.AddressService;
import com.cydeo.service.ParentService;
import com.cydeo.service.StudentService;
import com.cydeo.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final ParentService parentService;
    private final AddressService addressService;

    public SchoolController(TeacherService teacherService,
                            StudentService studentService,
                            ParentService parentService,
                            AddressService addressService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.parentService = parentService;
        this.addressService = addressService;
    }


    @GetMapping("/teachers")
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(teacherService.findAll());
    }

    @GetMapping("/students")
    public ResponseEntity<ResponseWrapper> getAllStudents() {

        return ResponseEntity
                .ok(new ResponseWrapper("Students are successfully retrieved.",
                        studentService.findAll()));
    }


    /*
    Create a parents end point where status code is 202
    additional header has "Parent", "Returned"
    and following body structure
    "Parents are successfully retrieved." message
    code: 202
    success:true
    and parent data.
     */
    @GetMapping("/parents")
    public ResponseEntity<ResponseWrapper> getAllParents(){

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Parent", "Returned")
                .body(new ResponseWrapper(true,
                        "Parents are successfully retrieved.",
                        HttpStatus.ACCEPTED.value(),
                        parentService.findAll()));

    }


    /*
    Create an endpoint for individual address information
    /address/1 2 3
    return status code 200
    "address .. is successfully retrieved" message
    success true
    and address information
     */

    @GetMapping("/address/{id}")
    public ResponseEntity<ResponseWrapper> getAddressById(@PathVariable("id") Long id ) throws Exception {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper(true,
                        "address "+ id +" is successfully retrieved",
                        HttpStatus.OK.value(), addressService.findById(id)));
    }



    @GetMapping("/address")
    public ResponseEntity<ResponseWrapper> getAllAddress(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper(true,
                        "addresses  are successfully retrieved",
                        HttpStatus.OK.value(), addressService.findAll()));
    }

    /*
    Homework:
    Create an endpoint to update individual address information
    return updated address directly
     */


}
