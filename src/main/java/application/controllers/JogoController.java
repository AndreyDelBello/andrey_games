package application.controllers;
 
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
 
import application.models.Jogo;
import application.models.Plataforma;
import application.repositories.JogoRepository;
import application.repositories.GeneroRepository;
import application.repositories.PlataformaRepository;
 
@Controller
@RequestMapping("/jogos")
public class JogoController {
    @Autowired
    private JogoRepository jogosRepo;

    @Autowired
    private GeneroRepository generosRepo;

    @Autowired
    private PlataformaRepository plataformasRepo;
 
    @RequestMapping("list")
    public String list(Model model) {
        model.addAttribute("jogos", jogosRepo.findAll());
        return "list.jsp"; 
    }
 
    @RequestMapping("insert")
    public String formInsert(Model model) {
        model.addAttribute(attributeName:"generos", generosRepo.findAll());
        model.addAttribute(attributeName:"plataformas", plataformasRepo.findAll());
        return "insert.jsp";
    }
 
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public String saveInsert(@RequestParam("titulo") String titulo,@RequestParam("genero") int generoId,
    @RequestParam("plataformas")int [] plataformas) {
        Jogo jogo = new Jogo();
        jogo.setTitulo(titulo);
        jogo.setGenero(generosRepo.findAll(generoId).get());
        for(int p: plataformas){
            Optional<Plataforma> plataforma = plataformasRepo.findById(p);
            if(plataforma.isPresent())
                jogo.getPlataformas().add(platforma.get());

        }
 
        jogosRepo.save(jogo);
 
        return "redirect:/jogo/list";
    }
 
    @RequestMapping("update/{id}")
    public String formUpdate(Model model, @PathVariable int id) {
        Optional<Jogo> jogo = jogosRepo.findById(id);
        
        if(!jogo.isPresent())
            return "redirect:/jogo/list";
        model.addAttribute("jogo", jogo.get());
        return "/jogo/update.jsp";
    }
 
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String saveUpdate(@RequestParam("nome") String nome, @RequestParam("id") int id) {
        Optional<Jogo> jogo = jogosRepo.findById(id);
        if(!jogo.isPresent())
            return "redirect:/jogo/list";
        jogo.get().setNome(nome);
 
        jogosRepo.save(jogo.get());
 
        return "redirect:/jogo/list";
    }
 
    @RequestMapping("delete/{id}")
    public String formDelete(Model model, @PathVariable int id) {
        Optional<Jogo> jogo = jogosRepo.findById(id);
        if(!jogo.isPresent())
            return "redirect:/jogo/list";
        model.addAttribute("jogo", jogo.get());
        return "/jogo/delete.jsp";
    }
 
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String confirmDelete(@RequestParam("id") int id) {
        jogosRepo.deleteById(id);
        return "redirect:/jogo/list";
    }
}