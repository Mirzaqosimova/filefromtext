package uz.pdp.readfrompdfandimage.controller;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.readfrompdfandimage.entity.OcrModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/convert")
public class ImageController {



    @PostMapping("/image")
    public String DoOCR(@RequestParam("DestinationLanguage") String destinationLanguage,
                        @RequestParam("Image") MultipartFile image) throws IOException, TesseractException {

        OcrModel request = new OcrModel();
        request.setDestinationLanguage(destinationLanguage);
        request.setImage(image);

        Tesseract tesseract = new Tesseract();

        try {

            BufferedImage in = ImageIO.read(image.getInputStream());

            BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = newImage.createGraphics();
            g.drawImage(in, 0, 0, null);
            g.dispose();
            tesseract.setPageSegMode(1);
            tesseract.setOcrEngineMode(1);
            tesseract.setLanguage(request.getDestinationLanguage());
            tesseract.setDatapath(" recources/tesseract/tessdata");
            String result = tesseract.doOCR(newImage);

            return result;

        } catch (TesseractException | IOException e) {
            System.err.println(e.getMessage());
            return "Error while reading image";
        }


    }



    @PostMapping("/pdf")
    public String pdf(@RequestParam("file") MultipartFile file) throws IOException {


        String text;

        try (final PDDocument document = PDDocument.load(file.getInputStream())) {
            final PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(document);
        } catch (final Exception ex) {

            text = "Error parsing PDF";
        }
return text;
    }









}
