package com.computerseekho.api.service;

import com.computerseekho.api.config.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            // CREATE DIRECTORY IF NOT EXISTS
            Files.createDirectories(this.fileStorageLocation);
            System.out.println("✅ File storage directory created/verified at: " + this.fileStorageLocation);
        } catch (Exception ex) {
            System.err.println("❌ ERROR: Could not create upload directory!");
            ex.printStackTrace();
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * Store file with name mangling
     * Format: staff_{username}_{timestamp}_{uuid}.{extension}
     */
    public String storeStaffImage(MultipartFile file, String staffUsername) {
        try {
            // CHECK IF FILE IS NULL OR EMPTY
            if (file == null || file.isEmpty()) {
                System.out.println("⚠️ No file uploaded, returning null");
                return null;
            }

            // Get original filename and extension
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            System.out.println("📁 Original filename: " + originalFilename);

            String fileExtension = "";

            if (originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            System.out.println("📝 File extension: " + fileExtension);

            // Validate file extension
            if (!isValidImageExtension(fileExtension)) {
                throw new RuntimeException("Invalid file type. Only JPG, JPEG, PNG allowed. Got: " + fileExtension);
            }

            // Generate unique filename: staff_{username}_{timestamp}_{uuid}.{ext}
            String timestamp = String.valueOf(System.currentTimeMillis());
            String uniqueId = UUID.randomUUID().toString().substring(0, 8);
            String newFilename = "staff_" + staffUsername + "_" + timestamp + "_" + uniqueId + fileExtension;

            System.out.println("🆕 New filename: " + newFilename);

            // Check for invalid characters
            if (newFilename.contains("..")) {
                throw new RuntimeException("Filename contains invalid path sequence " + newFilename);
            }

            // Copy file to the target location
            Path targetLocation = this.fileStorageLocation.resolve(newFilename);
            System.out.println("📍 Target location: " + targetLocation);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("✅ File saved successfully!");

            // Return relative path for database storage
            String photoUrl = "/assets/staff/" + newFilename;
            System.out.println("🔗 Photo URL: " + photoUrl);

            return photoUrl;

        } catch (IOException ex) {
            System.err.println("❌ ERROR: Failed to store file");
            ex.printStackTrace();
            throw new RuntimeException("Could not store file. Please try again!", ex);
        }
    }

    /**
     * Delete staff image file
     */
    public boolean deleteStaffImage(String photoUrl) {
        try {
            if (photoUrl == null || photoUrl.isEmpty()) {
                return false;
            }

            // Extract filename from URL
            String filename = photoUrl.substring(photoUrl.lastIndexOf("/") + 1);
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();

            return Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            System.err.println("❌ ERROR: Failed to delete file");
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Validate image file extension
     */
    private boolean isValidImageExtension(String extension) {
        String ext = extension.toLowerCase();
        return ext.equals(".jpg") || ext.equals(".jpeg") || ext.equals(".png");
    }

    /**
     * Get file size in MB
     */
    public double getFileSizeInMB(MultipartFile file) {
        return (double) file.getSize() / (1024 * 1024);
    }

    /**
     * Validate file size (max 5MB)
     */
    public boolean isValidFileSize(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return true; // Allow empty files (optional upload)
        }
        return getFileSizeInMB(file) <= 5.0;
    }
}