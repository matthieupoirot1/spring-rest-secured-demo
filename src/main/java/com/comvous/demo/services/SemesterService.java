package com.comvous.demo.services;

import com.comvous.demo.data.models.Semester;
import com.comvous.demo.data.models.User;
import com.comvous.demo.data.models.projections.SemesterWithStudentsDTO;
import com.comvous.demo.data.models.projections.UserDTO;
import com.comvous.demo.data.repositories.SemesterRepository;
import com.comvous.demo.data.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SemesterService extends BaseCrudService<SemesterRepository, Semester> {
    UserRepository userRepository;

    public SemesterService(SemesterRepository semesterRepository, UserRepository userRepository) {
        super(semesterRepository);
        this.userRepository = userRepository;
    }

    public Semester findByName(String name) {
        return super.linkedRepository.findByNameContainsIgnoreCase(name);
    }

    public Semester updateStudents(String name, List<Long> studentsId){
        Semester s = this.findByName(name);
        if(s == null){
            throw new EntityNotFoundException("Cannot find semester named " + name);
        }
        //clear initial data
        s.setStudents(new HashSet<>());
        for(Long studentId : studentsId){
            User studentRef = userRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("No user with id" + studentId));
            s.addStudent(studentRef);
        }
        return this.update(s);
    }

    public List<User> getStudents(Long id) {
        Semester s = super.getById(id);
        return s.getStudents().stream().toList();
    }
}
