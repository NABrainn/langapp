package gg.jte.generated.ondemand.pages.document;
@SuppressWarnings("unchecked")
public final class JtedocumentpageGenerated {
	public static final String JTE_NAME = "pages/document/document-page.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,3,3,5,5,11,11,11,13,13,13,16,16,16,16,16,0,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String title, String content) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.JtebaseGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n        <div\n        id=\"document-page\">\n            <div\n            id=\"document\">\n                <span\n                id=\"title\">");
				jteOutput.setContext("span", null);
				jteOutput.writeUserContent(title);
				jteOutput.writeContent("</span>\n                <div\n                id=\"content\">");
				jteOutput.setContext("div", null);
				jteOutput.writeUserContent(content);
				jteOutput.writeContent("</div>\n            </div>\n        </div>\n    ");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String title = (String)params.get("title");
		String content = (String)params.get("content");
		render(jteOutput, jteHtmlInterceptor, title, content);
	}
}
