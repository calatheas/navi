package com.calathea.navi.core.controller;

import com.calathea.navi.model.CarVo;
import com.calathea.navi.model.TruckVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cors")
public class CorsErrorController {
    @GetMapping()
    public String errorCors(Model model) {
        CarVo car = new TruckVo("cors-car");
        model.addAttribute("car", car);
        return "cors-error";
    }
}
