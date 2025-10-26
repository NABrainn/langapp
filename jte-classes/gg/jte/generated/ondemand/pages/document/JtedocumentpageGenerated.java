package gg.jte.generated.ondemand.pages.document;
import java.util.List;
import org.langapp.documents.dto.processor.Paragraph;
@SuppressWarnings("unchecked")
public final class JtedocumentpageGenerated {
	public static final String JTE_NAME = "pages/document/document-page.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,3,6,6,8,8,11,14,15,15,15,15,15,3,4,4,4,4};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String title, List<Paragraph> paragraphs) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.JtebaseGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <script\n    src=\"/js/document-page/selectable-div.js\"></script>\n    ");
				gg.jte.generated.ondemand.pages.document.JtedocumentpagebodyGenerated.render(jteOutput, jteHtmlInterceptor, 1, title, paragraphs);
				jteOutput.writeContent("\n    ");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String title = (String)params.get("title");
		List<Paragraph> paragraphs = (List<Paragraph>)params.get("paragraphs");
		render(jteOutput, jteHtmlInterceptor, title, paragraphs);
	}
}
