package gg.jte.generated.ondemand.pages.document;
import java.util.List;
import org.langapp.documents.dto.processor.Paragraph;
import org.langapp.documents.dto.processor.selection.SelectionStrategy;
@SuppressWarnings("unchecked")
public final class JtedocumentpageGenerated {
	public static final String JTE_NAME = "pages/document/document-page.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,4,4,4,4,9,9,11,11,14,19,20,20,20,20,20,4,5,6,7,7,7,7};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, int documentId, String title, List<Paragraph> paragraphs, SelectionStrategy selection) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.JtebaseGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <script\n    src=\"/js/document-page/selectable-div.js\"></script>\n    ");
				gg.jte.generated.ondemand.pages.document.JtedocumentpagebodyGenerated.render(jteOutput, jteHtmlInterceptor, documentId, title, paragraphs, selection);
				jteOutput.writeContent("\n    ");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		int documentId = (int)params.get("documentId");
		String title = (String)params.get("title");
		List<Paragraph> paragraphs = (List<Paragraph>)params.get("paragraphs");
		SelectionStrategy selection = (SelectionStrategy)params.get("selection");
		render(jteOutput, jteHtmlInterceptor, documentId, title, paragraphs, selection);
	}
}
