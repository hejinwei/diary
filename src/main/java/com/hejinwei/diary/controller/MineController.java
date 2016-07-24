package com.hejinwei.diary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by jinweihe on 16/7/24.
 */
@Controller
public class MineController {

    @RequestMapping("/mine")
    public ModelAndView mine() {

        ModelAndView mav = new ModelAndView("template/mine/list.html");

        return mav;
    }

}
