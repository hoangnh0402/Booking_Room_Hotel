package com.hit.common.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@UtilityClass
public class FileUtils {

    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    private static final Path RESOURCES_PATH = CURRENT_FOLDER.resolve(Paths.get("src/main/resources"));

    /**
     * Zip a folder or multiple folder
     *
     * @param sourceFolderPaths Mảng các đường dẫn của folder cần zip (trong phạm vi folder resources),
     *                          Example: ["upload/xxx/xxx", "upload/xxx/xxx"]
     * @return byte[]
     */
    public static byte[] zipFolder(String... sourceFolderPaths) throws IOException {
        List<File> fileList = new ArrayList<>();
        for (String pathFolder : sourceFolderPaths) {
            File sourceFolder = new File(RESOURCES_PATH.resolve(pathFolder).toString());
            fileList.add(sourceFolder);
        }

        if (CollectionUtils.isNotEmpty(fileList)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
            try {
                for (File fileFolder : fileList) {
                    addToZip(fileFolder.getParentFile().getPath(), zipOutputStream, fileFolder);
                }
                zipOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            } catch (IOException ioe) {
                throw new IOException("Something went wrong, file cannot be compressed", ioe);
            }
        }
        return new byte[0];
    }

    /**
     * Zip a file or multiple file
     *
     * @param filePaths Mảng các đường dẫn của file cần zip (trong phạm vi folder resources),
     *                  Example: ["upload/xxx/fileName.xxx", "upload/xxx/fileName.xxx"]
     * @return byte[]
     */
    public static byte[] zipFileByPath(String... filePaths) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        try {
            for (String pathFile : filePaths) {
                File file = getFileByPath(pathFile);
                addToZip(file.getParentFile().getPath(), zipOutputStream, file);
            }
            zipOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException ioe) {
            throw new IOException("Something went wrong, file cannot be compressed", ioe);
        }
    }

    /**
     * Save file upload to Resources
     *
     * @param newFileName   Tên file mới để lưu
     * @param uploadPath    Vị trí cần lưu (trong phạm vi folder resources), example: "upload/xxx/xxx"
     * @param multipartFile File cần lưu
     * @return String
     */
    public static String saveFile(String newFileName, String uploadPath, MultipartFile multipartFile) throws IOException {
        Path path = RESOURCES_PATH.resolve(Paths.get(uploadPath));
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        String newFile = newFileName + fileType;
        Path filePath;
        try (InputStream inputStream = multipartFile.getInputStream()) {
            filePath = path.resolve(newFile);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName);
        }
        return newFile;
    }

    /**
     * Check folder in Resources
     *
     * @param folderPath Đường dẫn của folder (trong phạm vi folder resources), example: "upload/xxx/xxx"
     * @return boolean
     */
    public static boolean isFolderNotExists(String folderPath) {
        Path path = RESOURCES_PATH.resolve(Paths.get(folderPath));
        return !Files.exists(path);
    }

    /**
     * Lấy ra file ở trong thư mục resources theo đường dẫn
     *
     * @param pathFile Đường dẫn file cần lấy (trong phạm vi folder resources), example: "upload/xxx/fileName.xxx"
     * @return File
     */
    public static File getFileByPath(String pathFile) {
        Path path = RESOURCES_PATH.resolve(Paths.get(pathFile));
        return path.toFile();
    }

    /**
     * Lấy ra dữ liệu file ở trong thư mục resources theo đường dẫn
     *
     * @param pathFile - đường dẫn file cần lấy (trong phạm vi folder resources), example: "upload/xxx/fileName.xxx"
     * @return byte[]
     */
    @SneakyThrows
    public static byte[] getBytesFileByPath(String pathFile) {
        Path path = RESOURCES_PATH.resolve(Paths.get(pathFile));
        return Files.readAllBytes(path);
    }

    /**
     * Tạo và thêm các entry là file hoặc folder vào zip
     *
     * @param basePath Base path file
     * @param toAdd    File đưa vào entry
     */
    private static void addToZip(String basePath, ZipOutputStream zos, File toAdd) throws IOException {
        if (toAdd.isDirectory()) {
            // Nếu file là folder thì lấy tất cả các file và tiếp tục add
            File[] files = toAdd.listFiles();
            if (files != null) {
                for (File file : files) {
                    addToZip(basePath, zos, file);
                }
            }
        } else {
            // Bỏ phần basePath để lấy name file
            String name = toAdd.getPath().substring(basePath.length() + 1);
            ZipEntry entry = new ZipEntry(name);
            zos.putNextEntry(entry);
            // Copy file vào entry trong zip output vừa put
            Files.copy(Paths.get(toAdd.getPath()), zos);
            zos.closeEntry();
        }
    }
}
