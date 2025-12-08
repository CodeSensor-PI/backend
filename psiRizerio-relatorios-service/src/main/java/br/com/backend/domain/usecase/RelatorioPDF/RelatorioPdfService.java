package br.com.backend.domain.usecase.RelatorioPDF;

import br.com.backend.domain.entity.Relatorio;
import br.com.backend.infraestructure.repository.RelatorioRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.time.ZoneId;

@Service
public class RelatorioPdfService {

    private final RelatorioRepository relatorioRepository;

    public RelatorioPdfService(RelatorioRepository relatorioRepository) {
        this.relatorioRepository = relatorioRepository;
    }

    public byte[] gerarRelatorioPdf(Integer fkPaciente) {
        List<Relatorio> relatorios = relatorioRepository.findByFkPaciente(fkPaciente);

        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PDPage page = new PDPage();
            document.addPage(page);


            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                InputStream logoStream = getClass().getClassLoader().getResourceAsStream("jessicaRizerioLogo.png");
                if (logoStream == null) {
                    throw new RuntimeException("Arquivo de imagem não encontrado: jessicaRizerioLogo.png");
                }

                PDImageXObject image = PDImageXObject.createFromByteArray(document, logoStream.readAllBytes(), "jessicaRizerioLogo");

                contentStream.setStrokingColor(198, 168, 250);

                float pageWidth = page.getMediaBox().getWidth();
                float pageHeight = page.getMediaBox().getHeight();

                contentStream.setLineWidth(2);
                contentStream.addRect(10, 10, pageWidth - 20, pageHeight - 20);
                contentStream.stroke();

                float imageWidth = 100;
                float imageHeight = 100;
                float xPosition = pageWidth - imageWidth - 20;
                float yPosition = pageHeight - imageHeight - 20;

                contentStream.drawImage(image, xPosition, yPosition, imageWidth, imageHeight);

                // Adicionar título geral "Relatórios"
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                String titulo = "Relatórios";
                float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(titulo) / 1000 * 18;
                contentStream.beginText();
                contentStream.newLineAtOffset((pageWidth - titleWidth) / 2, pageHeight - 50);
                contentStream.showText(titulo);
                contentStream.endText();

                // Adicionar conteúdo alinhado à esquerda
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                yPosition = pageHeight - 100; // Posição inicial do texto
                contentStream.beginText();
                contentStream.setLeading(16f);
                contentStream.newLineAtOffset(40, yPosition);

                contentStream.showText("Relatórios do Paciente ID: " + fkPaciente);
                contentStream.newLine();
                yPosition -= 16; // Atualizar posição vertical
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "BR"));

                for (Relatorio relatorio : relatorios) {
                    // Adicionar borda ao redor de cada relatório
                    float relatorioBoxHeight = 60; // Altura do bloco do relatório
                    yPosition -= relatorioBoxHeight + 10; // Atualizar posição vertical para o próximo bloco
                    contentStream.endText(); // Finalizar texto antes de desenhar a borda
                    contentStream.addRect(30, yPosition, pageWidth - 60, relatorioBoxHeight);
                    contentStream.stroke();

                    // Adicionar conteúdo do relatório
                    contentStream.beginText();
                    contentStream.newLineAtOffset(40, yPosition + relatorioBoxHeight - 15);

                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.showText("ID: " + relatorio.getId());
                    contentStream.newLine();

                    contentStream.setFont(PDType1Font.HELVETICA, 12);

                    // Converter Date para LocalDate
                    LocalDate data = relatorio.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    String dataFormatada = data.format(formatter);
                    contentStream.showText("Data: " + dataFormatada);
                    contentStream.newLine();

                    String conteudo = relatorio.getConteudo();
                    int maxLineLength = 80; // Número máximo de caracteres por linha
                    while (conteudo.length() > maxLineLength) {
                        contentStream.showText(conteudo.substring(0, maxLineLength));
                        contentStream.newLine();
                        conteudo = conteudo.substring(maxLineLength);
                    }
                    contentStream.showText(conteudo);
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            document.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }
}