package co.com.ud.service.adm.impl;

import co.com.ud.adm.dto.UploadArticuloDto;
import co.com.ud.service.adm.IUploadService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@SpringBootTest
public class UploadServiceTest {

    private IUploadService uploadService;
    private static String REPO_PDF = "c:\\repoFiles\\";

    @Before
    public void setUp(){
        uploadService = new UploadService(REPO_PDF);
    }

    @Test
    public void testSaveFileRepositorySUCCESS() throws FileNotFoundException {
        Long idArt = Long.valueOf("1");
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource("base64Text.txt").getFile());
        Scanner sc = new Scanner(file);
        String valor = sc.next();

        Assert.assertTrue(file.exists());
        Assert.assertNotNull(valor);

        Boolean rta = uploadService.saveFileRepository(valor , idArt);
        Assert.assertNotNull(rta);
    }

    @Test
    public void testSaveFileRepositoryFAILED(){
        Long idArt = Long.valueOf("1");
        Boolean rta = uploadService.saveFileRepository("ESTO NO ES UN BASE 64" , idArt);
        Assert.assertEquals(Boolean.FALSE, rta);
    }

    @Test
    public void testPdfToBase64SUCCESS(){
        Long idArt = Long.valueOf(1);
        UploadArticuloDto rta = uploadService.pdfToBase64(idArt);
        Assert.assertNotNull(rta);
    }

    @Test
    public void testPdfToBase64FAILED(){
        Long idArt = Long.valueOf(100000);
        UploadArticuloDto rta = uploadService.pdfToBase64(idArt);
        Assert.assertNull(rta.getBase64());
    }
}