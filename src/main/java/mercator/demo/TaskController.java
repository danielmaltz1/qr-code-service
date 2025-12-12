package mercator.demo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.springframework.boot.jackson.autoconfigure.JacksonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TaskController {

    @GetMapping("/api/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("a", HttpStatus.OK);
    }

    @GetMapping("/api/qrcode")
    public ResponseEntity<Object> qrCode(@RequestParam("size") String size, @RequestParam("type") String typeOfImage) {
        int sizeInt = Integer.parseInt(size);
        if (150 <= sizeInt && sizeInt <= 350) {
            MediaType mediaType;
            switch (typeOfImage) {
                case "png":
                    mediaType = MediaType.IMAGE_PNG;
                    break;
                case "jpeg":
                    mediaType = MediaType.IMAGE_JPEG;
                    break;
                case "gif":
                    mediaType = MediaType.IMAGE_GIF;
                    break;
                default:
                    return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(
                            Map.of("error", "Only png, jpeg and gif image types are supported")
                    );
            }
            BufferedImage image = getImage(sizeInt);
            return ResponseEntity
                    .ok()
                    .contentType(mediaType)
                    .body(image);
        } else {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(
                    Map.of("error", "Image size must be between 150 and 350 pixels")
            );
        }
    }


    public BufferedImage getImage(int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, size, size);
        return image;
    }


    @Bean
    public HttpMessageConverter<BufferedImage> bufferedImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }


    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return taskList;
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTasks(@PathVariable int id) {
        return new ResponseEntity<>(taskList.get(id - 1), HttpStatus.ACCEPTED);
    }

    private final List<Task> taskList = List.of(
            new Task(1, "task1", "A first test task", false),
            new Task(2, "task2", "A second test task", true)
    );
}

