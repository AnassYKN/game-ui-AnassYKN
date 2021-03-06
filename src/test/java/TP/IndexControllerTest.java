package TP;

import TP.controller.IndexController;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.junit.Assert.*;

public class IndexControllerTest {

    @Test
    void controllerShouldBeAnnotated(){
        assertNotNull(IndexController.class.getAnnotation(Controller.class));
    }


    @Test
    void index_shouldBeAnnotated() throws NoSuchMethodException {
        var indexMethod = IndexController.class.getMethod("index");
        var getMapping = indexMethod.getAnnotation(GetMapping.class);

        assertNotNull(getMapping);
        assertArrayEquals(new String[]{"/"}, getMapping.value());
    }


    @Test
    void registerNewTrainer_shouldBeAnnotated() throws NoSuchMethodException {
        var registerMethod = IndexController.class.getDeclaredMethod("registerNewTrainer", String.class);
        var getMapping = registerMethod.getAnnotation(PostMapping.class);

        assertNotNull(getMapping);
        assertArrayEquals(new String[]{"/registerTrainer"}, getMapping.value());
    }
}
