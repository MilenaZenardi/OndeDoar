package com.ondedoar.cotroller;

import com.ondedoar.dto.InstituicaoRecordDto;
import com.ondedoar.model.ImagemModel;
import com.ondedoar.model.InstituicaoModel;
import com.ondedoar.service.ImagemService;
import com.ondedoar.service.InstituicaoService;
import jakarta.validation.ConstraintViolationException;
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

@Controller
@RequestMapping("/instituicao")
public class InstituicaoController {

    @Autowired
    InstituicaoService instituicaoService;

    @Autowired
    ImagemService imagemService;

    @GetMapping("/create")
    public String create() {
        return "instituicao/cadastro";
    }

    @PostMapping("/create")
    public String createInstituicao(Model model, @ModelAttribute InstituicaoRecordDto instituicaoRecordDto,
                                    BindingResult bindingResult,
                                    @RequestParam("imagens") List<MultipartFile> imagens) {

        try {
            InstituicaoModel instituicaoModel = new InstituicaoModel();

            BeanUtils.copyProperties(instituicaoRecordDto, instituicaoModel);
            instituicaoService.save(instituicaoModel);

            for (MultipartFile imagem : imagens) {
                if (!imagem.isEmpty()) {
                    try {
                        // Defina um diretório onde você deseja salvar as imagens
                        String diretorioDestino = System.getProperty("user.dir") + "/src/main/resources/static/uploads";

                        // Obtenha o nome original do arquivo
                        String nomeArquivo = imagem.getOriginalFilename();

                        // Crie o caminho completo para o arquivo no sistema de arquivos
                        String caminhoCompleto = diretorioDestino + "/" + nomeArquivo;

                        // Salve o arquivo no sistema de arquivos
                        byte[] bytes = imagem.getBytes();
                        Path path = Paths.get(caminhoCompleto);
                        Files.write(path, bytes);

                        // Crie uma entrada em ImagemModel
                        ImagemModel imagemModel = new ImagemModel();
                        imagemModel.setNomeArquivo(nomeArquivo);
                        imagemModel.setCaminhoArquivo(caminhoCompleto);
                        imagemModel.setInstituicao(instituicaoModel);

                        // Salve a imagem no banco de dados
                        imagemService.save(imagemModel);

                    } catch (IOException e) {
                        e.printStackTrace();

                        model.addAttribute("erro", "Erro ao fazer upload de uma ou mais imagens");
                        return "instituicao/cadastro";
                    }
                }
            }
            model.addAttribute("message", "Instituição em processo de validação, aguarde aprovação!");
        } catch (ConstraintViolationException e) {
            model.addAttribute("erro", "CNPJ inválido. Por favor, insira um CNPJ válido.");
            return "instituicao/cadastro";
        }

        return "instituicao/cadastro";
    }

    @GetMapping()
    public String getAllInstituicao(Model model) {

        List<InstituicaoModel> instituicoes = instituicaoService.getAll();

        model.addAttribute("instituicoes", instituicoes);

        return "instituicao/listar";
    }
    /*

@GetMapping("/id/{id}")
    public ResponseEntity<InstituicaoModel> getByIdInstituicao(@PathVariable(value = "id") int id) {
        Optional<InstituicaoModel> instituicaoModel = instituicaoService.getById(id);
        var instituicao = instituicaoModel.get();

        if (instituicaoModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(instituicao);
        }
        return ResponseEntity.status(HttpStatus.OK).body(instituicao);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<InstituicaoModel>> getCategoriaInstituicao(@PathVariable(value = "category") InstituicaoCategoria categoria) {

        Optional<List<InstituicaoModel>> instituicoes = Optional.ofNullable(instituicaoService.getCategoria(categoria));
        if (instituicoes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.status(HttpStatus.OK).body(instituicaoService.getCategoria(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstituicaoModel> updateInstituicao(@PathVariable(value = "id") int id, @RequestBody @Valid InstituicaoModel instituicaoModel) {
        Optional<InstituicaoModel> instituicaoMod = instituicaoService.getById(id);

        if (instituicaoMod.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(instituicaoModel);
        }

        return ResponseEntity.status(HttpStatus.OK).body(instituicaoService.update(instituicaoModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInstituicao(@PathVariable(value = "id") int id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(instituicaoService.delete(id));
    }*/
}
