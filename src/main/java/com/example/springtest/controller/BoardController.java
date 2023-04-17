package com.example.springtest.controller;

import com.example.springtest.domain.BoardDAO;
import com.example.springtest.domain.InputForm;
import com.example.springtest.service.BoardService;
import com.example.springtest.service.BoardServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;
    private String macAddr;

    @PostConstruct
    private void getMacAddr() {
        String result = "";
        InetAddress ip;

        try {
            ip = InetAddress.getLocalHost();

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
                result = sb.toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e){
            e.printStackTrace();
        }

        this.macAddr =  result;
    }
    @GetMapping("/addForm")
    public String addForm(Model model) {
        model.addAttribute(new InputForm());
        model.addAttribute("macAddr", macAddr);
        return "board/addForm";
    }

    @PostMapping("/addForm")
    public String addFormResult(@ModelAttribute InputForm inputForm,@CookieValue(name = "memberId",required = false) Long memberId) throws IOException {
        if (inputForm.getMultipartFile().isEmpty()) {
            service.saveTextBoard(inputForm,memberId);
        }
        else{
            service.saveFileBoard(inputForm,memberId);
        }
        return "redirect:/boards";
    }

    @GetMapping("/board/{boardId}")
    public String showDetailBoard(@PathVariable long boardId, Model model) {
        BoardDAO boardById = service.getBoardById(boardId);
        log.info("board, {}", boardById);
        model.addAttribute("board",boardById);
        model.addAttribute("macAddr", macAddr);
        return "board/ShowItem";
    }

    @GetMapping("/boards")
    public String showAllBoards(Model model) {
        List<BoardDAO> boards = service.getAllBoard();
        model.addAttribute("boards", boards);
        model.addAttribute("macAddr", macAddr);
        return "board/BoardList";
    }

    @GetMapping("/board/update/{boardNumber}")
    @ResponseBody
    public String boardUpdate(@PathVariable Long boardNumber,Model model){
        log.info("boardNumber : {}", boardNumber);
        return "200 : se";
    }

    @GetMapping("/board/remove/{boardNumber}")
    @ResponseBody
    public String boardRemove(@PathVariable Long boardNumber){
        log.info("boardNumber : {}", boardNumber);

        return "200 : se";
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
        log.info("path : {}", contentDisposition);
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
