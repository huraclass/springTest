package com.example.springtest.controller;

import com.example.springtest.domain.BoardDAO;
import com.example.springtest.domain.InputForm;
import com.example.springtest.service.BoardService;
import com.example.springtest.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    @GetMapping("/addForm")
    public String addForm(Model model) {
        model.addAttribute(new InputForm());
        return "board/addForm";
    }

    @PostMapping("/addForm")
    public String addFormResult(@ModelAttribute InputForm inputForm) throws IOException {
        //todo 저장할떄 무조건 autoINcramasklsdtajkl 걸어야댐 무조건 안걸면 버그남 하라면 하라고
        log.info("진입");
        if (inputForm.getMultipartFile().isEmpty()) {
            service.saveTextBoard(inputForm);
        }
        else{
            service.saveFileBoard(inputForm);
        }
        //리다이렉션 시켜서 상세페이지로 전환
        return "redirect:/boards";
    }

    @GetMapping("/board/{boardId}")
    public String showDetailBoard(@PathVariable long boardId, Model model) {
        BoardDAO boardById = service.getBoardById(boardId);
        log.info("board, {}", boardById);
        model.addAttribute("board",boardById);
        return "board/ShowItem";
    }

    @GetMapping("/boards")
    public String showAllBoards(Model model) {
        List<BoardDAO> boards = service.getAllBoard();
        model.addAttribute("boards", boards);
        return "board/BoardList";
    }


    @GetMapping("/attach/{boardNumber}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long boardNumber) throws MalformedURLException {
        log.info("rest 진입");
        BoardDAO item = service.getBoardById(boardNumber);
        String storeFileName = item.getServerSaveFileName();
        String uploadFileName = item.getRealFileName();

        UrlResource resource = new UrlResource("file:" + getFullPath(storeFileName));

        log.info("uploadFileName={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
        log.info("path : {}",contentDisposition);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        log.info("fileName : {}",fileName);
        return fileDir + fileName;
    }

}
