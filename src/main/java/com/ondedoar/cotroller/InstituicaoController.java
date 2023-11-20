package com.ondedoar.cotroller;

import com.ondedoar.dto.InstituicaoRecordDto;
import com.ondedoar.exception.ValidationUtils;
import com.ondedoar.model.ImagemModel;
import com.ondedoar.model.InstituicaoModel;
import com.ondedoar.service.ImagemService;
import com.ondedoar.service.InstituicaoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/instituicao")
public class InstituicaoController {

    @Autowired
    InstituicaoService instituicaoService;

    @Autowired
    ImagemService imagemService;

    @GetMapping("/create")
    public String create(@RequestParam(value = "id", required = false) Integer id, Model model) {

        if (id != null) {
            Optional<InstituicaoModel> instituicaoModel = instituicaoService.getById(id);
            model.addAttribute("instituicao", instituicaoModel);
            model.addAttribute("id", instituicaoModel.get().getId());
        }

        return "instituicao/form";
    }

    @PostMapping("/create")
    public String createInstituicao(Model model, @ModelAttribute @Valid InstituicaoRecordDto instituicaoRecordDto,  BindingResult bindingResult,
                                    @RequestParam("imagens") List<MultipartFile> imagens, @RequestParam(value = "id", required = false) Integer id) {

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = ValidationUtils.getErrorMessages(bindingResult);
            model.addAttribute("errorMessage", errorMessages);
            return "instituicao/form";
        }

        InstituicaoModel instituicaoModel = new InstituicaoModel();
        BeanUtils.copyProperties(instituicaoRecordDto, instituicaoModel);

        if (id != null) {
            Optional<InstituicaoModel> instituicaoModelUpdate = instituicaoService.getById(id);
            if (instituicaoModelUpdate.isPresent()) {
                InstituicaoModel instituicaoExistente = instituicaoModelUpdate.get();
                instituicaoModel.setId(instituicaoExistente.getId());
            }
        }

        instituicaoService.save(instituicaoModel);

        for (MultipartFile imagem : imagens) {
            if (!imagem.isEmpty()) {
                try {

                    String diretorioDestino = System.getProperty("user.dir") + "/src/main/resources/static/uploads";
                    String nomeArquivo = imagem.getOriginalFilename();
                    String caminhoCompleto = diretorioDestino + "/" + nomeArquivo;

                    byte[] bytes = imagem.getBytes();
                    Path path = Paths.get(caminhoCompleto);
                    Files.write(path, bytes);

                    ImagemModel imagemModel = new ImagemModel();
                    imagemModel.setNomeArquivo(nomeArquivo);
                    imagemModel.setCaminhoArquivo(caminhoCompleto);
                    imagemModel.setInstituicao(instituicaoModel);

                    imagemService.save(imagemModel);

                } catch (IOException e) {
                    e.printStackTrace();

                    model.addAttribute("errorMessage", "Erro ao fazer upload de uma ou mais imagens");
                }
            }
        }
        model.addAttribute("successMessage", "Instituição aguardando validação, aguarde aprovação!");

        return "instituicao/form";
    }

    @GetMapping()
    public String getAllInstituicoesAtivas(Model model) {
        List<InstituicaoModel> instituicoes = instituicaoService.getAllActives();
        model.addAttribute("instituicoes", instituicoes);

        return "instituicao/list";
    }

    @GetMapping({"/delete/{id}"})
    public String deleteInstituicao(@PathVariable Integer id, Model model) {
        this.instituicaoService.delete(id);
        model.addAttribute("successMessage", "Instituição excluída com sucesso!");
        return "redirect:/instituicao";
    }
}
