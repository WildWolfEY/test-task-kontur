package ru.home.textRedactor.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.home.textRedactor.core.Printer;
import ru.home.textRedactor.core.Searcher;

import java.util.Map;
import java.util.Set;

/**
 * @author Elena Demeneva
 */
@RestController
public class TextRedactorController {

    @Autowired
    private ApplicationContext appContext;

    @GetMapping("/get")
    public Set<Map.Entry<String, Integer>> start(@RequestParam(value = "prefix") String prefix) {
        Searcher searcher = (Searcher) appContext.getBean("searcher");
        Set<Map.Entry<String, Integer>> map =searcher.getTopWords(prefix);
        Printer printer = (Printer) appContext.getBean("printer");
        printer.print(prefix,map);
        return map;
    }

}
