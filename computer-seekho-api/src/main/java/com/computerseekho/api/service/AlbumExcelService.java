//package com.computerseekho.api.service;
//
//import com.computerseekho.api.entity.Album;
//import com.computerseekho.api.entity.AlbumType;
//import com.computerseekho.api.entity.Image;
//import com.computerseekho.api.entity.ProgramCode;
//import com.computerseekho.api.repository.AlbumRepository;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.InputStream;
//import java.util.*;
//
//@Service
//public class AlbumExcelService {
//
//    @Autowired
//    private AlbumRepository albumRepository;
//
//    public List<Image> parseExcel(MultipartFile file) throws Exception {
//
//        List<Image> images = new ArrayList<>();
//
//        try (InputStream is = file.getInputStream();
//             Workbook workbook = new XSSFWorkbook(is)) {
//
//            Sheet sheet = workbook.getSheetAt(0);
//            Iterator<Row> rows = sheet.iterator();
//
//            // Skip header row
//            if (rows.hasNext()) {
//                rows.next();
//            }
//
//            while (rows.hasNext()) {
//
//                Row row = rows.next();
//
//                // -------- Read AlbumType --------
//                AlbumType albumType =
//                        AlbumType.valueOf(row.getCell(0).getStringCellValue().trim());
//
//                // -------- Read ProgramCode (nullable) --------
//                ProgramCode programCode = null;
//                Cell programCell = row.getCell(1);
//                if (programCell != null && !programCell.getStringCellValue().isBlank()) {
//                    programCode =
//                            ProgramCode.valueOf(programCell.getStringCellValue().trim());
//                }
//
//                // IMPORTANT: make it final for lambda
//                final ProgramCode finalProgramCode = programCode;
//
//                // -------- Read Album Name --------
//                String albumName = row.getCell(2).getStringCellValue().trim();
//
//                // -------- Find or Create Album --------
//                Album album = albumRepository
//                        .findByAlbumTypeAndAlbumIsActive(albumType, true)
//                        .stream()
//                        .filter(a ->
//                                a.getAlbumName().equalsIgnoreCase(albumName) &&
//                                        Objects.equals(a.getProgramCode(), finalProgramCode)
//                        )
//                        .findFirst()
//                        .orElseGet(() -> {
//
//                            Album a = new Album();
//                            a.setAlbumType(albumType);
//                            a.setProgramCode(finalProgramCode);
//                            a.setAlbumName(albumName);
//                            a.setAlbumDescription(
//                                    row.getCell(3) != null
//                                            ? row.getCell(3).getStringCellValue()
//                                            : null
//                            );
//                            a.setAlbumIsActive(
//                                    row.getCell(4).getNumericCellValue() == 1
//                            );
//
//                            return albumRepository.save(a);
//                        });
//
//                // -------- Create Image --------
//                Image image = new Image();
//                image.setAlbum(album);
//                image.setImagePath(row.getCell(5).getStringCellValue());
//                image.setIsAlbumCover(row.getCell(6).getNumericCellValue() == 1);
//                image.setImageIsActive(row.getCell(7).getNumericCellValue() == 1);
//
//                images.add(image);
//            }
//        }
//
//        return images;
//    }
//}
package com.computerseekho.api.service;

import com.computerseekho.api.entity.Album;
import com.computerseekho.api.entity.AlbumType;
import com.computerseekho.api.entity.Image;
import com.computerseekho.api.entity.ProgramCode;
import com.computerseekho.api.repository.AlbumRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
public class AlbumExcelService {

    @Autowired
    private AlbumRepository albumRepository;

    public List<Image> parseExcel(MultipartFile file) throws Exception {

        List<Image> images = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Skip header row
            if (rows.hasNext()) rows.next();

            while (rows.hasNext()) {

                Row row = rows.next();
                if (row == null || row.getCell(0) == null) continue;

                String albumTypeStr = getString(row.getCell(0));
                if (albumTypeStr == null || albumTypeStr.equalsIgnoreCase("album_type")) {
                    continue;
                }

                AlbumType albumType = AlbumType.valueOf(albumTypeStr);

                // -------- Program Code (nullable) --------
                ProgramCode programCode = null;
                String programStr = getString(row.getCell(1));
                if (programStr != null && !programStr.isBlank()) {
                    programCode = ProgramCode.valueOf(programStr);
                }

                // IMPORTANT: make effectively final for lambda
                final ProgramCode finalProgramCode = programCode;

                String albumName = getString(row.getCell(2));
                if (albumName == null) continue;

                Album album;

                /* ================================
                   CASE 1: programCode == NULL
                   Only albumType matters
                   ================================ */
                if (finalProgramCode == null) {

                    Optional<Album> existingAlbum =
                            albumRepository.findByAlbumTypeAndProgramCodeIsNull(albumType);

                    album = existingAlbum.orElseGet(() -> {
                        Album a = new Album();
                        a.setAlbumType(albumType);
                        a.setProgramCode(null);
                        a.setAlbumName(albumName);
                        a.setAlbumDescription(getString(row.getCell(3)));
                        a.setAlbumIsActive(getBoolean(row.getCell(4)));
                        return albumRepository.save(a);
                    });

                }
                /* ================================
                   CASE 2: programCode != NULL
                   albumType + programCode + albumName
                   ================================ */
                else {

                    album = albumRepository
                            .findByAlbumTypeAndProgramCodeAndAlbumName(
                                    albumType, finalProgramCode, albumName
                            )
                            .orElseGet(() -> {
                                Album a = new Album();
                                a.setAlbumType(albumType);
                                a.setProgramCode(finalProgramCode);
                                a.setAlbumName(albumName);
                                a.setAlbumDescription(getString(row.getCell(3)));
                                a.setAlbumIsActive(getBoolean(row.getCell(4)));
                                return albumRepository.save(a);
                            });
                }

                // -------- Image --------
                Image image = new Image();
                image.setAlbum(album);
                image.setImagePath(getString(row.getCell(5)));
                image.setIsAlbumCover(getBoolean(row.getCell(6)));
                image.setImageIsActive(getBoolean(row.getCell(7)));

                images.add(image);
            }
        }

        return images;
    }

    // ================= HELPER METHODS =================

    private String getString(Cell cell) {
        if (cell == null) return null;
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

    private boolean getBoolean(Cell cell) {
        if (cell == null) return false;
        return cell.getNumericCellValue() == 1;
    }
}

