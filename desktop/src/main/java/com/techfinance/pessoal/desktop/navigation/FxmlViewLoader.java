package com.techfinance.pessoal.desktop.navigation;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.net.URL;

@Singleton
public class FxmlViewLoader {

    private static final String FXML_BASE_PATH = "/com/techfinance/pessoal/desktop/fxml/";

    private final Injector injector;

    @Inject
    public FxmlViewLoader(Injector injector) {
        this.injector = injector;
    }

    public LoadedView load(String fxml) {
        try {
            URL fxmlUrl = getClass().getResource(FXML_BASE_PATH + fxml);

            if (fxmlUrl == null) {
                throw new IllegalStateException("FXML não encontrado: " + fxml);
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            loader.setControllerFactory(injector::getInstance);

            Parent root = loader.load();
            return new LoadedView(root, loader.getController());

        } catch (Exception exception) {
            throw new RuntimeException("Erro ao carregar FXML: " + fxml, exception);
        }
    }

    public record LoadedView(Parent root, Object controller) {}
}
