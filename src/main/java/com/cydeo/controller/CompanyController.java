package com.cydeo.controller;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String getListOfCompanies(Model model) {
        model.addAttribute("companies", companyService.getListOfCompanies());
        return "company/company-list";
    }

    @GetMapping("/create")
    public String createCompany(Model model){
        model.addAttribute("newCompany",new CompanyDTO());

        model.addAttribute("countries", companyService.retrieveCountryList());

        return"company/company-create";
    }


    @PostMapping("/create")
    public String saveCompany( @Valid @ModelAttribute("newCompany") CompanyDTO companyDTO, BindingResult bindingResult
            ,Model model) {

        model.addAttribute("countries", companyService.retrieveCountryList());

        if (companyService.existByTitle(companyDTO)) {
            bindingResult.rejectValue("title", "", "This title already exists.");
        }

        if (bindingResult.hasErrors()) {
            return "/company/company-create";
        }

        companyService.createCompany(companyDTO);

        return "redirect:/companies/list";
    }


    @GetMapping("/update/{id}")
    public String editCompanyForm(@PathVariable("id") Long id,Model model) {
        model.addAttribute("company",companyService.findById(id));

        model.addAttribute("countries", companyService.retrieveCountryList());

        return "company/company-update";
    }

    @PostMapping("/update/{id}")
    public String updateCompany(@PathVariable("id") Long id, @Valid @ModelAttribute("company")  CompanyDTO companyDTO, BindingResult bindingResult,
                                Model model) {
        model.addAttribute("countries", companyService.retrieveCountryList());

        if (companyService.existByTitleForUpdate(companyDTO)) {
            bindingResult.rejectValue("title", "", "This title already exists.");
        }


        if (bindingResult.hasErrors()) {
            return "/company/company-update";
        }

        companyService.updateCompany(id,companyDTO);
        return "redirect:/companies/list";
    }
    // Activate and Deactivate functions
    @GetMapping("/activate/{id}")
    public String activateCompany(@PathVariable Long id) {
        companyService.changeCompanyStatus(id, CompanyStatus.ACTIVE);
        return "redirect:/companies/list";
    }

    @GetMapping("/deactivate/{id}")
    public String deactivateCompany(@PathVariable Long id) {
        companyService.changeCompanyStatus(id, CompanyStatus.PASSIVE);
        return "redirect:/companies/list";
    }


}
