package gg.jte.generated.ondemand.pages.document;
import java.util.List;
import org.langapp.documents.dto.processor.*;
import org.langapp.documents.dto.processor.TranslatedWord;
import org.langapp.documents.dto.processor.SelectedWord;
import org.langapp.documents.dto.processor.Paragraph;
import org.langapp.documents.dto.processor.Phrase;
import org.langapp.documents.dto.processor.Word;
@SuppressWarnings("unchecked")
public final class JtedocumentpageGenerated {
	public static final String JTE_NAME = "pages/document/document-page.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,4,5,6,8,8,8,8,11,11,13,13,19,19,19,22,22,24,24,24,24,26,26,26,26,26,26,26,26,26,27,27,27,27,28,28,29,29,30,34,35,35,36,41,42,42,43,47,48,48,49,49,50,50,51,55,56,56,57,61,62,62,63,67,68,68,69,73,74,74,75,75,76,76,76,78,78,80,80,84,84,84,84,84,8,9,9,9,9};
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
							if (phrase instanceof NewPhrase newPhrase) {
								jteOutput.writeContent("\n                                        ");
								gg.jte.generated.ondemand.pages.document.JtephraseGenerated.render(jteOutput, jteHtmlInterceptor, "unit-" + phrase.id(), newPhrase.rawContent(), "new-phrase", null);
								jteOutput.writeContent("\n                                    ");
							} else if (phrase instanceof TranslatedPhrase translatedPhrase) {
								jteOutput.writeContent("\n                                        ");
								gg.jte.generated.ondemand.pages.document.JtephraseGenerated.render(jteOutput, jteHtmlInterceptor, "unit-" + phrase.id(), translatedPhrase.rawContent(), "translated-phrase", "var(--recognized)");
								jteOutput.writeContent("\n                                    ");
							} else if (phrase instanceof SelectedPhrase selectedPhrase) {
								jteOutput.writeContent("\n                                        ");
								gg.jte.generated.ondemand.pages.document.JtephraseGenerated.render(jteOutput, jteHtmlInterceptor, "unit-" + phrase.id(), selectedPhrase.rawContent(), "selected-phrase", null);
								jteOutput.writeContent("\n                                    ");
							}
							jteOutput.writeContent("\n                                ");
						} else if (unit instanceof Word word) {
							jteOutput.writeContent("\n                                    ");
							if (word instanceof NewWord newWord) {
								jteOutput.writeContent("\n                                        ");
								gg.jte.generated.ondemand.pages.document.JtewordGenerated.render(jteOutput, jteHtmlInterceptor, "unit-" + word.id(), newWord.rawContent(), "new-word");
								jteOutput.writeContent("\n                                    ");
							} else if (word instanceof TranslatedWord translatedWord) {
								jteOutput.writeContent("\n                                        ");
								gg.jte.generated.ondemand.pages.document.JtewordGenerated.render(jteOutput, jteHtmlInterceptor, "unit-" + word.id(), translatedWord.rawContent(), "translated-word");
								jteOutput.writeContent("\n                                    ");
							} else if (word instanceof SelectedWord selectedWord) {
								jteOutput.writeContent("\n                                        ");
								gg.jte.generated.ondemand.pages.document.JtewordGenerated.render(jteOutput, jteHtmlInterceptor, "unit-" + word.id(), selectedWord.rawContent(), "selected-word");
								jteOutput.writeContent("\n                                    ");
							} else if (word instanceof InvalidWord invalidWord) {
								jteOutput.writeContent("\n                                        ");
								gg.jte.generated.ondemand.pages.document.JtewordGenerated.render(jteOutput, jteHtmlInterceptor, "unit-" + word.id(), invalidWord.rawContent(), "invalid-word");
								jteOutput.writeContent("\n                                    ");
							}
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
