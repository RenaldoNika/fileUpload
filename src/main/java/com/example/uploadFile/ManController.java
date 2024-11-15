package com.example.uploadFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class ManController {

    @Value("${upload.dir}")
    private String uploadDir;

    @Autowired
    private ManRepository manRepository;



    @PostMapping("/save")
    public String savePerson(Man person, @RequestParam("emr") String name,
                             @RequestParam("file") MultipartFile file) {
        person.setName(name);
        try {
            Path uploadPath = Paths.get(uploadDir);
            String fileName = file.getOriginalFilename();
            Path path = uploadPath.resolve(fileName);
            System.out.println(path);
            Files.write(path, file.getBytes());
            person.setPhoto(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        manRepository.save(person);

        return "redirect:/success";
    }

    @GetMapping("/all")
    public List<Man> getAllPersons(Model model) {
        List<Man> persons = manRepository.findAll();
        return persons;
    }





}