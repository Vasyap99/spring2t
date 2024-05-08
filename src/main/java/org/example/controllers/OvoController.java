package org.example.controllers;

import org.example.OvoService;
import org.example.models.OvoZayavka;
import org.example.models.User;
import org.example.repo.OvoZayavkaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Scanner;

@Controller
public class OvoController {
    @Autowired
    OvoZayavkaRepository ozr;

    @Autowired
    OvoService ovs;

    @GetMapping("/ovo")
    public String ovo(@ModelAttribute("z") OvoZayavka zy) {
        return "ovo";
    }/**/

    @PostMapping("/ovo")
    public String register2(@ModelAttribute("z") @Valid OvoZayavka zy, BindingResult br){
        ovs.saveZayavka(zy);
        boolean peredano=false;
        try {
            FileInputStream fis = new FileInputStream("c://__kko//ovo//cl_addrs.dat");
            Scanner sc=new Scanner(fis);
            while(sc.hasNextLine()) {
                String s1=sc.nextLine();
                try {
                    Socket s = new Socket(s1, 5555);
                    InputStream is = s.getInputStream();
                    OutputStream os = s.getOutputStream();
                    Writer w = new OutputStreamWriter(os, Charset.forName("windows-1251"));
                    w.write(".OVO zayavka.");
                    w.write('\n');
                    w.write(zy.getTitle());
                    w.write('\n');
                    w.write("" + zy.getText().length());
                    w.write('\n');
                    w.write(zy.getText());
                    w.flush();
                    is.read();
                    peredano=true;
                    s.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch(Exception ee){
        }
        if(peredano)
            return "/ovo-success";
        else
            return "/ovo-fail";
    }
}
