package ru.frolov.optica3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.frolov.optica3.entity.TestEntity;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    private List<TestEntity> testEntityList = new ArrayList<>();
    {
        testEntityList.add(new TestEntity("testEntity_1", 1));
        testEntityList.add(new TestEntity("testEntity_2", 2));
        testEntityList.add(new TestEntity("testEntity_3", 3));
    }

    // ------------------test--------------------------------------
    @GetMapping(value = "test")
    public String testing(Model model){
        model.addAttribute("testEntitiesList", testEntityList);
        return "test";
    }
}
