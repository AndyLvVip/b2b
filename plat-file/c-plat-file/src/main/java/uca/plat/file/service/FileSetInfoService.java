package uca.plat.file.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uca.base.redis.StdStringRedisTemplate;
import uca.plat.file.domain.FileSetInfo;
import uca.plat.file.repository.FileSetInfoRepository;
import uca.platform.json.StdObjectMapper;

/**
 * Created by andy.lv
 * on: 2019/1/24 17:16
 */
@Service
public class FileSetInfoService {

    private final FileSetInfoRepository fileSetInfoRepository;

    private final StdStringRedisTemplate stdStringRedisTemplate;

    private final StdObjectMapper stdObjectMapper;

    public FileSetInfoService(FileSetInfoRepository fileSetInfoRepository
                              , StdStringRedisTemplate stdStringRedisTemplate
                              , StdObjectMapper stdObjectMapper
    ) {
        this.fileSetInfoRepository = fileSetInfoRepository;
        this.stdStringRedisTemplate = stdStringRedisTemplate;
        this.stdObjectMapper = stdObjectMapper;
    }

    @Transactional
    public FileSetInfo create(FileSetInfo fileSetInfo, String createdBy) {
        FileSetInfo result = new FileSetInfo();
        result.setFileSrcRemark(fileSetInfo.getFileSrcRemark());
        this.fileSetInfoRepository.insert(result, createdBy);
        return result;
    }

    public FileSetInfo fetch(String id) {
        return this.fileSetInfoRepository.findById(id);
    }

    @Transactional
    public FileSetInfo edit(FileSetInfo fileSetInfo, String updatedBy) {
        FileSetInfo result = this.fileSetInfoRepository.findById(fileSetInfo.getId());
        result.setVersion(fileSetInfo.getVersion());
        this.fileSetInfoRepository.update(result, updatedBy);
        return result;
    }

    public void initFileSetInfoIfNotExists(String fileSetInfoId, String fileSrcRemark, String createdBy) {
        stdStringRedisTemplate.getWithLock(fileSetInfoId, 60 * 60, () -> {
            FileSetInfo fileSetInfo = fileSetInfoRepository.findById(fileSetInfoId);
            if (null == fileSetInfo) {
                fileSetInfo = new FileSetInfo();
                fileSetInfo.setId(fileSetInfoId);
                fileSetInfo.setFileSrcRemark(fileSrcRemark);
                this.fileSetInfoRepository.insert(fileSetInfo, createdBy);
            }
            return stdObjectMapper.toJson(fileSetInfo);
        });
    }

}
