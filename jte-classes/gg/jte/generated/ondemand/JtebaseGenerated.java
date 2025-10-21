package gg.jte.generated.ondemand;
import gg.jte.Content;
@SuppressWarnings("unchecked")
public final class JtebaseGenerated {
	public static final String JTE_NAME = "base.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,2,17,17,17,18,18,18,19,19,22,22,22,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Content content) {
		jteOutput.writeContent("\n<!doctype html>\n<html lang=\"en\">\n<head>\n    <script src=\"/js/fixi.js\"></script>\n    <script src=\"/js/ext-fixi.js\"></script>\n    <link href=\"/css/styles.css\" rel=\"stylesheet\">\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\"\n          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n    <title>Lang app</title>\n</head>\n<body>\n    ");
		if (content != null) {
			jteOutput.writeContent("\n        ");
			jteOutput.setContext("body", null);
			jteOutput.writeUserContent(content);
			jteOutput.writeContent("\n    ");
		}
		jteOutput.writeContent("\n</body>\n</html>\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Content content = (Content)params.getOrDefault("content", null);
		render(jteOutput, jteHtmlInterceptor, content);
	}
}
