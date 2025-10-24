@import java.util.List
@import org.langapp.documents.dto.processor.Paragraph
@import org.langapp.documents.dto.processor.Phrase
@import org.langapp.documents.dto.processor.Word

@param String title
@param List<Paragraph> paragraphs

@template.base(
    content = @`
        <div
        id="document-page">
            <div
            id="document">
                <span
                id="title">${title}</span>
                <div
                id="content">
                    @for(var paragraph : paragraphs)
                        <div
                        id="paragraph-${paragraph.id()}"
                        class="paragraph"
                        data-size="${paragraph.size()}">
                            @for(var unit : paragraph.units())
                                @if(unit instanceof Phrase phrase)
                                    ${phrase.rawContent()}
                                @elseif(unit instanceof Word word)
                                    ${word.rawContent()}
                                @endif
                            @else
                                Document has no content
                            @endfor
                        </div>
                    @endfor
                </div>
            </div>
        </div>
    `
)