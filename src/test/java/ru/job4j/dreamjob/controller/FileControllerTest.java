package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.service.FileService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileControllerTest {

    private FileService fileService;
    private FileController fileController;
    private MultipartFile testFile;

    @BeforeEach
    public void initServices() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
        testFile = new MockMultipartFile("testFile.img", new byte[] {1, 2, 3});
    }

    @Test
    public void whenRequestFileByIdThenGetResponseEntityOk() throws Exception {
        int expectedId = 1;
        var fileIdCaptor =  ArgumentCaptor.forClass(int.class);
        var fileDto = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());
        when(fileService.getFileById(fileIdCaptor.capture())).thenReturn(Optional.of(fileDto));

        var response = fileController.getById(expectedId);
        var actualId = fileIdCaptor.getValue();

        assertThat(expectedId).isEqualTo(actualId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(fileDto.getContent());
    }

    @Test
    public void whenRequestFileByIdThenGetResponseEntityNotFound() throws Exception {
        int expectedId = 2;
        var fileIdCaptor =  ArgumentCaptor.forClass(int.class);
        when(fileService.getFileById(fileIdCaptor.capture())).thenReturn(Optional.empty());

        var response = fileController.getById(expectedId);
        var actualId = fileIdCaptor.getValue();

        assertThat(expectedId).isEqualTo(actualId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}