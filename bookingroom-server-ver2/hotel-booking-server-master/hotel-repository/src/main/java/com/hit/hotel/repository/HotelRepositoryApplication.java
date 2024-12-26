package com.hit.hotel.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.common.utils.UploadFileUtils;
import com.hit.hotel.repository.media.entity.Media;
import com.hit.hotel.repository.product.entity.Product;
import com.hit.hotel.repository.product.repository.ProductRepository;
import com.hit.hotel.repository.room.entity.Room;
import com.hit.hotel.repository.room.repository.RoomRepository;
import com.hit.hotel.repository.service.entity.Service;
import com.hit.hotel.repository.service.repository.ServiceRepository;
import com.hit.jpa.AuditorProvider;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Log4j2
@SpringBootApplication(scanBasePackages = "com.hit")
public class HotelRepositoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelRepositoryApplication.class, args);
    }

    @Primary
    @Bean("auditorProviderAdminInit")
    public AuditorProvider auditorProviderAdmin() {
        return () -> "ce8b62e4-19d1-43d0-bc17-e9d12ed00cb4";
    }

    @Bean
    @SuppressWarnings({"all"})
    CommandLineRunner init(ConfigurableApplicationContext ctx, ObjectMapper objectMapper,
                           UploadFileUtils uploadFileUtils, RoomRepository roomRepository,
                           ServiceRepository serviceRepository, ProductRepository productRepository) {
        return args -> {
            //init rooms
            if (roomRepository.count() == 0) {
                Path pathSetup = Paths.get(System.getProperty("user.dir")).resolve("setup");
                Path pathRoomInit = pathSetup.resolve("init/rooms.json");
                File file = pathRoomInit.toFile();
                try {
                    Map<String, List<RoomInitJSON>> roomsMap = objectMapper.readValue(file, new TypeReference<>() {
                    });
                    for (Map.Entry<String, List<RoomInitJSON>> entry : roomsMap.entrySet()) {
                        for (RoomInitJSON roomInit : entry.getValue()) {
                            Room room = objectMapper.convertValue(roomInit, Room.class);
                            for (Media media : room.getMedias()) {
                                media.setUrl(uploadFileUtils.uploadImage(Files.readAllBytes(pathSetup.resolve(media.getUrl()))));
                                media.setRoom(room);
                            }
                            roomRepository.save(room);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }

            //init services & product
            if (serviceRepository.count() == 0) {
                Path pathSetup = Paths.get(System.getProperty("user.dir")).resolve("setup");
                Path pathRoomInit = pathSetup.resolve("init/services.json");
                File file = pathRoomInit.toFile();
                try {
                    List<ServiceInitJSON> services = objectMapper.readValue(file, new TypeReference<>() {
                    });
                    for (ServiceInitJSON serviceInit : services) {
                        Service service = objectMapper.convertValue(serviceInit, Service.class);
                        service.setThumbnail(uploadFileUtils.uploadImage(Files.readAllBytes(pathSetup.resolve(service.getThumbnail()))));
                        for (Product product : service.getProducts()) {
                            String urlThumbnailProduct =
                                    uploadFileUtils.uploadImage(Files.readAllBytes(pathSetup.resolve(product.getThumbnail())));
                            product.setThumbnail(urlThumbnailProduct);
                            product.setService(service);
                        }
                        serviceRepository.save(service);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ctx.getAutowireCapableBeanFactory();
            registry.removeBeanDefinition("auditorProviderAdminInit");
        };
    }

}
