package uca.plat.file.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uca.plat.file.domain.FileItemInfo;
import uca.plat.file.repository.FileItemInfoRepository;

import java.util.List;

@RestController
public class FileSetController {

    private final FileItemInfoRepository fileItemInfoRepository;

    public FileSetController(FileItemInfoRepository fileItemInfoRepository) {
        this.fileItemInfoRepository = fileItemInfoRepository;
    }

    @GetMapping("/fileSet/fetchFirstItemInEachGroup")
    public List<FileItemInfo> fetchFirstItemInEachGroup(@RequestParam("fileSetInfoIds") List<String> fileSetInfoIds) {
        return fileItemInfoRepository.fetchFirstFileInEachGroup(fileSetInfoIds);
    }

    @GetMapping("/fileSet/fileItems")
    public List<FileItemInfo> fetchItems(@RequestParam("fileSetInfoIds") List<String> fileSetInfoIds) {
        return fileItemInfoRepository.fetchByFileSetIds(fileSetInfoIds);
    }
}

