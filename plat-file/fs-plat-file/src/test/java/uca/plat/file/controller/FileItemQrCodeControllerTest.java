package uca.plat.file.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uca.plat.file.CustomizationConfiguration;
import uca.plat.file.domain.FileItemInfo;
import uca.plat.file.service.FileUploadService;
import uca.plat.file.vo.QrCodeUploadVo;
import uca.platform.StdStringUtils;
import uca.platform.json.StdObjectMapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uca.plat.file.CustomizationConfiguration.mockUserInfoTokenServices;
import static uca.plat.file.CustomizationConfiguration.restDocument;
import static uca.plat.file.service.ImageFileItemInfoService.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(CustomizationConfiguration.class)
@Transactional
public class FileItemQrCodeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserInfoTokenServices userInfoTokenServices;

    @Autowired
    StdObjectMapper stdObjectMapper;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    FileUploadService fileUploadService;

    @Before
    public void setUp() {
        mockUserInfoTokenServices(userInfoTokenServices);
    }

    @Test
    public void initQrCode() throws Exception {
        QrCodeUploadVo qrCodeUploadVo = new QrCodeUploadVo();
        qrCodeUploadVo.setFileSetInfoId(StdStringUtils.uuid());
        qrCodeUploadVo.setFileSrcRemark("Unit Test");

        this.mockMvc.perform(get("/fileItem/qrCode")
                .header("Authorization", "Bearer " + StdStringUtils.uuid())
                .param("fileSetInfoId", StdStringUtils.uuid())
                .param("fileSrcRemark", "Unit Test")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
        .andExpect(status().isOk())
        .andDo(restDocument(
                requestHeaders(
                        headerWithName("Authorization").description("Oauth2 access token")
                )
                , requestParameters(
                        parameterWithName("fileSrcRemark").description("File source remark")
                        , parameterWithName("fileSetInfoId").description("Id of the file set info")
                )
                , responseFields(
                        fieldWithPath("qrCode").description("Qr Code")
                )
        ))
        ;
    }

    @Test
    public void uploadByQrCode() throws Exception {
        QrCodeUploadVo qrCodeUploadVo = new QrCodeUploadVo();
        qrCodeUploadVo.setFileSetInfoId(StdStringUtils.uuid());
        qrCodeUploadVo.setFileSrcRemark("Unit Test");

        String jsonQrCode = this.mockMvc.perform(get("/fileItem/qrCode")
                .header("Authorization", "Bearer " + StdStringUtils.uuid())
                .param("fileSetInfoId", StdStringUtils.uuid())
                .param("fileSrcRemark", "Unit Test")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()
        ;

        Map<String, String> qrCodeMap = stdObjectMapper.fromJson(jsonQrCode, HashMap.class);
        MockMultipartFile file = new MockMultipartFile("file", "example.txt", MediaType.APPLICATION_OCTET_STREAM_VALUE, "example".getBytes());
        String qrCode = qrCodeMap.get("qrCode");
        String jsonFileItemInfo = this.mockMvc.perform(fileUpload("/fileItem/qrCode/file/{qrCode}", qrCode)
                .file(file)
                .header("Authorization", "Bearer " + StdStringUtils.uuid())
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk())
                .andDo(restDocument(
                        requestHeaders(
                                headerWithName("Authorization").description("Oauth2 access token")
                        )
                        , requestParts(
                                partWithName("file").description("The file to upload")
                        )
                        , pathParameters(
                                parameterWithName("qrCode").description("qrCode")
                        )
                        , responseFields(
                                fieldWithPath("id").description("File item info id")
                                , fieldWithPath("size").description("The size of the file")
                                , fieldWithPath("filePath").description("The path of the file")
                                , fieldWithPath("fileName").description("The name of the file")
                                , fieldWithPath("sequence").description("The sequence of the order")
                                , fieldWithPath("fileSetInfoId").description("Id of the file set info")
                                , fieldWithPath("version").description("")
                        )
                )).andReturn().getResponse().getContentAsString()
                ;

        FileItemInfo fileItemInfo = stdObjectMapper.fromJson(jsonFileItemInfo, FileItemInfo.class);
        assertNotNull(fileItemInfo);
        File destFile = fileUploadService.asFile(fileItemInfo.getFilePath());
        assertTrue(destFile.exists());
        assertTrue(destFile.delete());
    }

    @Test
    public void uploadImgByQrCode() throws Exception {
        QrCodeUploadVo qrCodeUploadVo = new QrCodeUploadVo();
        qrCodeUploadVo.setFileSetInfoId(StdStringUtils.uuid());
        qrCodeUploadVo.setFileSrcRemark("Unit Test");

        String jsonQrCode = this.mockMvc.perform(get("/fileItem/qrCode")
                .header("Authorization", "Bearer " + StdStringUtils.uuid())
                .param("fileSetInfoId", StdStringUtils.uuid())
                .param("fileSrcRemark", "Unit Test")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()
                ;

        Map<String, String> qrCodeMap = stdObjectMapper.fromJson(jsonQrCode, HashMap.class);
        BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        File file = new File("temp.jpg");
        ImageIO.write(bi, "jpg", file);
        assertTrue(file.exists());

        MockMultipartFile mfile = new MockMultipartFile("file", file.getName(), MediaType.APPLICATION_OCTET_STREAM_VALUE, new FileInputStream(file));
        String qrCode = qrCodeMap.get("qrCode");
        String jsonFileItemInfo = this.mockMvc.perform(fileUpload("/fileItem/qrCode/img/{qrCode}", qrCode)
                .file(mfile)
                .header("Authorization", "Bearer " + StdStringUtils.uuid())
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk())
                .andDo(restDocument(
                        requestHeaders(
                                headerWithName("Authorization").description("Oauth2 access token")
                        )
                        , requestParts(
                                partWithName("file").description("The file to upload")
                        )
                        , pathParameters(
                                parameterWithName("qrCode").description("qrCode")
                        )
                        , responseFields(
                                fieldWithPath("id").description("File item info id")
                                , fieldWithPath("size").description("The size of the file")
                                , fieldWithPath("filePath").description("The path of the file")
                                , fieldWithPath("fileName").description("The name of the file")
                                , fieldWithPath("sequence").description("The sequence of the order")
                                , fieldWithPath("fileSetInfoId").description("Id of the file set info")
                                , fieldWithPath("version").description("")
                        )
                ))
                .andReturn().getResponse().getContentAsString()
                ;

        FileItemInfo fileItemInfo = stdObjectMapper.fromJson(jsonFileItemInfo, FileItemInfo.class);
        assertNotNull(fileItemInfo);
        File destFile = fileUploadService.asFile(fileItemInfo.getFilePath());
        assertTrue(destFile.exists());
        Path srcfilePath = Paths.get(destFile.getAbsolutePath()).getParent().resolve(this.fileUploadService.appendSuffix2Filename(destFile.getName(), SUFFIX_SRC));
        File srcfile = new File(srcfilePath.toString());
        assertTrue(srcfile.exists());

        Path file100Path = Paths.get(destFile.getAbsolutePath()).getParent().resolve(this.fileUploadService.appendSuffix2Filename(destFile.getName(), SUFFIX_100_100));
        File file100 = new File(file100Path.toString());
        assertTrue(file100.exists());

        Path file800Path = Paths.get(destFile.getAbsolutePath()).getParent().resolve(this.fileUploadService.appendSuffix2Filename(destFile.getName(), SUFFIX_800_800));
        File file800 = new File(file800Path.toString());
        assertTrue(file800.exists());

        assertTrue(destFile.delete());
        assertTrue(srcfile.delete());
        assertTrue(file100.delete());
        assertTrue(file800.delete());
        assertTrue(file.delete());

    }
}
