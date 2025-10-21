import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;
import org.langapp.documents.controller.DocumentController;

import static io.javalin.apibuilder.ApiBuilder.*;

void main() {
    final var OPT_PORT_STRING = Optional.ofNullable(System.getenv("PORT"));
    final var PORT_INT = OPT_PORT_STRING
            .map(Integer::parseInt)
            .orElse(8080);
    final var OPT_ENV = Optional.ofNullable(System.getenv("ENV"));
    final var STATIC_PATH = OPT_ENV
            .filter(env -> env.equals("PROD"))
            .map(_ -> "/static")
            .orElse("/home/nabrain/projects/java/langapp/src/main/resources/static");
    final var LOCATION = OPT_ENV
            .filter(env -> env.equals("PROD"))
            .map(_ -> Location.CLASSPATH)
            .orElse(Location.EXTERNAL);
    final var GZIP_LEVEL = 6;
    var app = Javalin.create(config -> {
                config.staticFiles.add(STATIC_PATH, LOCATION);
                config.fileRenderer(new JavalinJte());
                config.http.gzipOnlyCompression(GZIP_LEVEL);
                config.router.apiBuilder(() -> {
                    path("/documents", () -> {
                        post(DocumentController::create);
                        path("/{id}", () -> {
                            get(DocumentController::findById);
                        });
                    });
                });
            })
            .start(PORT_INT);
}
