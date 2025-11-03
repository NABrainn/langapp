import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;
import org.langapp.documents.controller.DocumentController;
import org.langapp.documents.controller.TranslationController;
import org.langapp.users.controller.UserController;

import static io.javalin.apibuilder.ApiBuilder.*;

void main() {
    final var PORT = Optional.ofNullable(System.getenv("PORT"))
            .map(Integer::parseInt)
            .orElse(8080);
    final var STATIC_FILE_PATH = Optional.ofNullable(System.getenv("ENV"))
            .filter(env -> env.equals("PROD"))
            .map(_ -> "/static")
            .orElse("/home/nabrain/projects/langapp/src/main/resources/static");
    final var STATIC_FILE_LOCATION = Optional.ofNullable(System.getenv("ENV"))
            .filter(env -> env.equals("PROD"))
            .map(_ -> Location.CLASSPATH)
            .orElse(Location.EXTERNAL);
    final var GZIP_LEVEL = 6;
    var app = Javalin.create(config -> {
                config.staticFiles.add(STATIC_FILE_PATH, STATIC_FILE_LOCATION);
                config.fileRenderer(new JavalinJte());
                config.http.gzipOnlyCompression(GZIP_LEVEL);
                config.router.apiBuilder(() -> {
                    path("/documents", () -> {
                        post(DocumentController::create);
                        path("/{id}", () -> {
                            get("/reload", DocumentController::findById);
                            get("/", DocumentController::findById);
                        });
                    });
                    path("/translations", () -> {
                        get("/", TranslationController::translationList);
                        get("/wordForm", TranslationController::wordTranslationForm);
                        get("/phraseForm", TranslationController::phraseTranslationForm);
                        put("/level", TranslationController::updateLevel);
                    });
                    path("/users", () -> {
                        post("/", UserController::create);
                    });
                });
            })
            .start(PORT);
}
