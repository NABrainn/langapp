package gg.jte.generated.ondemand.pages.document;
import java.util.List;
import org.langapp.documents.dto.processor.Paragraph;
import org.langapp.documents.dto.processor.Phrase;
import org.langapp.documents.dto.processor.Word;
@SuppressWarnings("unchecked")
public final class JtedocumentpageGenerated {
	public static final String JTE_NAME = "pages/document/document-page.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,5,5,5,5,8,8,10,10,16,16,16,19,19,21,21,21,21,23,23,23,23,23,23,23,23,23,24,24,24,24,25,25,26,26,26,27,27,28,28,28,29,29,30,30,30,32,32,34,34,38,38,38,38,38,5,6,6,6,6};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String title, List<Paragraph> paragraphs) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.JtebaseGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n        <div\n        id=\"document-page\">\n            <div\n            id=\"document\">\n                <span\n                id=\"title\">");
				jteOutput.setContext("span", null);
				jteOutput.writeUserContent(title);
				jteOutput.writeContent("</span>\n                <div\n                id=\"content\">\n                    ");
				for (var paragraph : paragraphs) {
					jteOutput.writeContent("\n                        <div\n                        id=\"paragraph-");
					jteOutput.setContext("div", "id");
					jteOutput.writeUserContent(paragraph.id());
					jteOutput.setContext("div", null);
					jteOutput.writeContent("\"\n                        class=\"paragraph\"\n                       ");
					var __jte_html_attribute_0 = paragraph.size();
					if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
						jteOutput.writeContent(" data-size=\"");
						jteOutput.setContext("div", "data-size");
						jteOutput.writeUserContent(__jte_html_attribute_0);
						jteOutput.setContext("div", null);
						jteOutput.writeContent("\"");
					}
					jteOutput.writeContent(">\n                            ");
					boolean __jte_for_loop_entered_2 = false;
					for (var unit : paragraph.units()) {
						__jte_for_loop_entered_2 = true;
						jteOutput.writeContent("\n                                ");
						if (unit instanceof Phrase phrase) {
							jteOutput.writeContent("\n                                    ");
							jteOutput.setContext("div", null);
							jteOutput.writeUserContent(phrase.rawContent());
							jteOutput.writeContent("\n                                ");
						} else if (unit instanceof Word word) {
							jteOutput.writeContent("\n                                    ");
							jteOutput.setContext("div", null);
							jteOutput.writeUserContent(word.rawContent());
							jteOutput.writeContent("\n                                ");
						}
						jteOutput.writeContent("\n                            ");
					}
					if (!__jte_for_loop_entered_2) {
						jteOutput.writeContent("\n                                Document has no content\n                            ");
					}
					jteOutput.writeContent("\n                        </div>\n                    ");
				}
				jteOutput.writeContent("\n                </div>\n            </div>\n        </div>\n    ");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String title = (String)params.get("title");
		List<Paragraph> paragraphs = (List<Paragraph>)params.get("paragraphs");
		render(jteOutput, jteHtmlInterceptor, title, paragraphs);
	}
}
