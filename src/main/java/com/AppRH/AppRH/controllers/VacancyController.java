package com.AppRH.AppRH.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.models.Candidate;
import com.AppRH.AppRH.models.Vacancy;
import com.AppRH.AppRH.repository.CandidateRepository;
import com.AppRH.AppRH.repository.VacancyRepository;

@Controller
public class VacancyController {

    /*
     * intermediario para camada de dominio e a camada de mapeamento de dados, ele
     * recebe um objeto e retorna algo
     */
    private VacancyRepository vr;
    private CandidateRepository cr;

    // CADASTRAR VAGA
    @RequestMapping(value = "/registerVacancy", method = RequestMethod.GET)
    public String form() {
        return "vacancy/formVacancy";
    }

    @RequestMapping(value = "/registerVacancy", method = RequestMethod.POST)
    public String form(@Valid Vacancy vacancy, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("Mensagem", "Verifique os campos ...");
            return "redirect:/registerVacancy";
        }
        vr.save(vacancy);
        attributes.addFlashAttribute("Mensagem", "Vaga Cadastrada com Sucesso");

        return "redirect:/registerVacancy";
    }

    // LISTAR VAGAS

    @RequestMapping("/vacancys")
    public ModelAndView vacancysList() {
        ModelAndView mv = new ModelAndView("vacancy/vacancyList");
        Iterable<Vacancy> vacancys = vr.findAll();
        mv.addObject("vacancys", vacancys);
        return mv;

    }

    //
    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public ModelAndView VacancyDetails(@PathVariable("code") long code) {
        Vacancy vacancy = vr.findByCode(code);
        ModelAndView mv = new ModelAndView("vacancy/vacancyDetails");
        mv.addObject("vacancy", vacancy);

        Iterable<Candidate> candidates = cr.findByVacancy(vacancy);
        mv.addObject("candidates", candidates);
        return mv;
    }

    // DELETAR VAGA

    @RequestMapping("/deleteVacancy")
    public String deleteVacancy(long code) {
        Vacancy vacancy = vr.findByCode(code);
        vr.delete(vacancy);
        return "redirect:/vacancys";
    }

    public String vacancyDetailsPost(@PathVariable("code") long code, @Valid Candidate candidate, BindingResult result,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os Campos ...");
            return "redirect:/{code}";
        }
        // Rg duplicado
        if (cr.findByRg(candidate.getRg()) != null) {
            attributes.addFlashAttribute("mensagem erro", "RG Duplicado");
            return "redirect:/{code}";
        }
        // SAlvando candidato
        Vacancy vacancy = vr.findByCode(code);
        candidate.setVacancy(vacancy);
        cr.save(candidate);
        attributes.addFlashAttribute("mensaggem", "Candidato Salvo com Sucesso");

        return "redirect:/{code}";

    }

    // Deleta Candidato pelo RG
    @RequestMapping("/deleteCandidate")
    public String deleteCandidate(String rg) {
        Candidate candidate = cr.findByRg(rg);
        Vacancy vacancy = candidate.getVacancy();
        String code = "" + vacancy.getCode();

        cr.delete(candidate);
        return "redirect:/" + code;

    }
    // Metodods que atualizam vagas 
    //formulario edição vagas
    @RequestMapping(value = "/edit-vacancy", method = RequestMethod.GET)
    public ModelAndView EditVacancy (long code) {
        Vacancy vacancy = vr.findByCode(code);
        ModelAndView mv = new ModelAndView("Vaga/update-vacancy");
        mv.addObject("vacancy", vacancy);
        return mv;
        
    }
    //update vaga
    @RequestMapping(value = "/edit-vacancy", method = RequestMethod.POST)
    public String updateVacancy(@Valid Vacancy vacancy, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos ...");
            return "redirect:/edit-vacancy?code=" + vacancy.getCode();
        }
        vr.save(vacancy);
        attributes.addFlashAttribute("mensagem", "Vaga atualizada com sucesso!");
        return "redirect:/vacancys";
    }

    


}
