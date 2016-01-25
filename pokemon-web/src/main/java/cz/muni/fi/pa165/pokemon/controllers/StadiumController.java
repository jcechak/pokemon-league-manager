package cz.muni.fi.pa165.pokemon.controllers;

import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.facade.BadgeFacade;
import cz.muni.fi.pa165.pokemon.facade.PokemonFacade;
import cz.muni.fi.pa165.pokemon.facade.StadiumFacade;
import cz.muni.fi.pa165.pokemon.facade.TrainerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;


/**
 * Controller for stadium administration
 * @author Dominika Talianova
 */
@Controller
@RequestMapping("/menu")
public class StadiumController {

    public static final String STADIUM_URI = "/menu/stadium";

    @Autowired
    private StadiumFacade stadiumFacade;

    @Autowired
    private TrainerFacade trainerFacade;

    @Autowired
    private BadgeFacade badgeFacade;

    private Map<Long, TrainerDTO> stadiumsAndTrainers;

    public void setStadiumFacade(StadiumFacade stadiumFacade){
        this.stadiumFacade = stadiumFacade;
    }

    @RequestMapping(value = "/stadium/list")
    public String showList(Model model, HttpServletRequest request){
        Collection<StadiumDTO> stadiumsDTO = new ArrayList<>();
        String filter = request.getParameter("filterTrainer");
        if(filter == null) {
            stadiumsDTO = stadiumFacade.findAll();
        }else{
            Collection<TrainerDTO> leaders = trainerFacade.findAllTrainersWithName(filter);
            if(leaders.size() == 0){
                stadiumsDTO = stadiumFacade.findAll();
            }else{
                for(TrainerDTO t: leaders) {
                    stadiumsDTO.add(stadiumFacade.findStadiumByLeader(t));
                }
            }
        }

        Map<StadiumDTO,String> trainersNamesMap = new HashMap<>();

        for(StadiumDTO s: stadiumsDTO){
            trainersNamesMap.put(s,trainerFacade.findTrainerById(s.getStadiumLeaderId()).getName());
        }

        stadiumsAndTrainers = new HashMap<>();
        for(StadiumDTO s:stadiumsDTO){
            System.out.println("DEBUG "+s);
            stadiumsAndTrainers.put(s.getId(),trainerFacade.findTrainerById(s.getStadiumLeaderId()));
        }
        model.addAttribute("stadiums", stadiumsDTO);
        model.addAttribute("stadiumsAndTrainers", stadiumsAndTrainers);
        model.addAttribute("trainerNamesMap", trainersNamesMap);
        return STADIUM_URI + "/list";
    }


    @RequestMapping(value = "/stadium/new", method = RequestMethod.GET)
    public String newProduct(Model model){
        Collection<PokemonType> typeList = new ArrayList<>();
        for(PokemonType type : PokemonType.values()){
            typeList.add(type);
        }

        Collection<TrainerDTO> trainersWithoutStadium = new ArrayList<>();
        for(TrainerDTO t:trainers()){
            if(t.getStadium()==null){
                trainersWithoutStadium.add(t);
            }
        }
        model.addAttribute("trainersWithoutStadium", trainersWithoutStadium);
        model.addAttribute("typeList", typeList);
        model.addAttribute("new", new StadiumDTO());
        System.out.println("DEBUG stadium new called");
        return STADIUM_URI + "/new";
    }

    @ModelAttribute("stadiums")
    public Collection<StadiumDTO> stadiums(){
        return stadiumFacade.findAll();
    }

    @ModelAttribute("trainers")
    public Collection<TrainerDTO> trainers(){
        return trainerFacade.findAllTrainers();
    }


    @RequestMapping(value = "/stadium/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("new") StadiumDTO formBean,BindingResult bindingResult, Model model,
                         RedirectAttributes redirectAttributes, UriComponentsBuilder uriComponentsBuilder){
        if(bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                System.out.println("ObjectError: {}" + ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                System.out.println("FieldError: {}" + fe);
            }
            redirectAttributes.addFlashAttribute("alert_error", "Stadium was not created.");
            return STADIUM_URI + "/new";
        }

        Long id = stadiumFacade.createStadium(formBean);
        System.out.println("create stadium " + formBean.toString());
        redirectAttributes.addFlashAttribute("alert_success", "Stadium was created");
        return "redirect:" + uriComponentsBuilder.path("/menu/stadium/view/{id}").buildAndExpand(id).encode().toUriString();

    }

    @RequestMapping(value = "/stadium/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model){
        System.out.println("DEBUG " + id + ' ' + model);
        model.addAttribute("stadium", stadiumFacade.findById(id));
        return STADIUM_URI + "/view";

    }

    @RequestMapping(value = "/stadium/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable long id, Model model){

        Collection<PokemonType> typeList = new ArrayList<>();
        for(PokemonType type : PokemonType.values()){
            typeList.add(type);
        }

        Collection<TrainerDTO> trainersWithoutStadium = new ArrayList<>();
        for(TrainerDTO t:trainers()){

            if(t.getStadium()==null){
                trainersWithoutStadium.add(t);
            }
        }
        trainersWithoutStadium.add(trainerFacade.findTrainerById(stadiumFacade.findById(id).getStadiumLeaderId()));
        model.addAttribute("trainersWithoutStadium", trainersWithoutStadium);
        model.addAttribute("typeList", typeList);
        System.out.println("DEBUG " + id + ' ' + model);
        model.addAttribute("stadium", stadiumFacade.findById(id));
        System.out.println("DEBUG " + stadiumFacade.findById(id).toString());
        return STADIUM_URI + "/edit";
    }

    @RequestMapping(value = "/stadium/update", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("update") StadiumDTO formBean, BindingResult bindingResult,
                       Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriComponentBuilder){
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                System.out.println("ObjectError: {}" + ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                System.out.println("FieldError: {}" + fe);
            }
            model.addAttribute("stadium", stadiumFacade.findById(formBean.getId()));
            return STADIUM_URI + "/edit";
        }

        stadiumFacade.updateStadium(formBean);
        System.out.println("update stadium" + formBean.toString());
        redirectAttributes.addFlashAttribute("alert_success", "Stadium was updated successfully.");
        return "redirect:"+uriComponentBuilder.path(STADIUM_URI + "/list").toUriString();
    }


    @ModelAttribute("userName")
    public String userName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return name;
    }

    @RequestMapping(value = "/stadium/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriComponentsBuilder,
                         RedirectAttributes redirectAttributes){
        StadiumDTO tempStadium = stadiumFacade.findById(id);
        TrainerDTO tempTrainer = trainerFacade.findTrainerById(tempStadium.getStadiumLeaderId());
        tempTrainer.setStadium(null);
        Collection<BadgeDTO> badges = badgeFacade.getAllBadges();
        for(BadgeDTO b : badges) {
            if(b.getStadiumId().equals(tempStadium.getId())) {
                badgeFacade.removeBadge(b);
            }
        }

        stadiumFacade.deleteStadium(tempStadium);
        redirectAttributes.addFlashAttribute("alert_success", "Stadium was deleted.");
        return "redirect:"+uriComponentsBuilder.path(STADIUM_URI + "/list").toUriString();
    }


}
