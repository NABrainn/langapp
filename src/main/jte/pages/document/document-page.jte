@import java.util.List
@import org.langapp.documents.dto.processor.*
@import org.langapp.documents.dto.processor.TranslatedWord
@import org.langapp.documents.dto.processor.SelectedWord
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
                                    @if(phrase instanceof NewPhrase newPhrase)
                                        @template.pages.document.phrase(
                                            id = "unit-" + phrase.id(),
                                            content = newPhrase.rawContent(),
                                            className = "new-phrase"
                                        )
                                    @elseif(phrase instanceof TranslatedPhrase translatedPhrase)
                                        @template.pages.document.phrase(
                                            id = "unit-" + phrase.id(),
                                            content = translatedPhrase.rawContent(),
                                            className = "translated-phrase",
                                            bgc = "var(--recognized)"
                                        )
                                    @elseif(phrase instanceof SelectedPhrase selectedPhrase)
                                        @template.pages.document.phrase(
                                            id = "unit-" + phrase.id(),
                                            content = selectedPhrase.rawContent(),
                                            className = "selected-phrase"
                                        )
                                    @endif
                                @elseif(unit instanceof Word word)
                                    @if(word instanceof NewWord newWord)
                                        @template.pages.document.word(
                                            id = "unit-" + word.id(),
                                            content = newWord.rawContent(),
                                            className = "new-word"
                                        )
                                    @elseif(word instanceof TranslatedWord translatedWord)
                                        @template.pages.document.word(
                                            id = "unit-" + word.id(),
                                            content = translatedWord.rawContent(),
                                            className = "translated-word"
                                        )
                                    @elseif(word instanceof SelectedWord selectedWord)
                                        @template.pages.document.word(
                                            id = "unit-" + word.id(),
                                            content = selectedWord.rawContent(),
                                            className = "selected-word"
                                        )
                                    @elseif(word instanceof InvalidWord invalidWord)
                                        @template.pages.document.word(
                                            id = "unit-" + word.id(),
                                            content = invalidWord.rawContent(),
                                            className = "invalid-word"
                                        )
                                    @endif
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