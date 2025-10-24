@import java.util.List
@import org.langapp.documents.dto.processor.*
@import org.langapp.documents.dto.processor.TranslatedWord
@import org.langapp.documents.dto.processor.SelectedWord
@import org.langapp.documents.dto.processor.Paragraph
@import org.langapp.documents.dto.processor.Phrase
@import org.langapp.documents.dto.processor.Word

@param String title
@param List<Paragraph> paragraphs

<div
fx-method="POST"
fx-action="/foo"
fx-trigger="selectPhrase"
fx-target="#document"
ext-fx-vals="js:{...document.getElementById('content').details}"
id="document-page">
    <div
    id="document">
        <span
        id="title">${title}</span>
        <div
        is="selectable-div"
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
                                    className = "new-phrase",
                                    fx = false
                                )
                            @elseif(phrase instanceof TranslatedPhrase translatedPhrase)
                                @template.pages.document.phrase(
                                    id = "unit-" + phrase.id(),
                                    content = translatedPhrase.rawContent(),
                                    className = "translated-phrase",
                                    bgc = "var(--recognized)",
                                    fx = true,
                                    fxMethod = "PUT",
                                    fxAction = "/translations/update",
                                    fxTarget = "#content"
                                )
                            @elseif(phrase instanceof SelectedPhrase selectedPhrase)
                                @template.pages.document.phrase(
                                    id = "unit-" + phrase.id(),
                                    content = selectedPhrase.rawContent(),
                                    className = "selected-phrase",
                                    fx = false
                                )
                            @endif
                        @elseif(unit instanceof Word word)
                            @if(word instanceof NewWord newWord)
                                @template.pages.document.word(
                                    id = "unit-" + word.id(),
                                    content = newWord.rawContent(),
                                    className = "new-word",
                                    fx = true,
                                    fxMethod = "GET",
                                    fxAction = "/translations/new",
                                    fxTarget = "#content"
                                )
                            @elseif(word instanceof TranslatedWord translatedWord)
                                @template.pages.document.word(
                                    id = "unit-" + word.id(),
                                    content = translatedWord.rawContent(),
                                    className = "translated-word",
                                    fx = true,
                                    fxMethod = "PUT",
                                    fxAction = "/translations/update",
                                    fxTarget = "#content"
                                )
                            @elseif(word instanceof SelectedWord selectedWord)
                                @template.pages.document.word(
                                    id = "unit-" + word.id(),
                                    content = selectedWord.rawContent(),
                                    className = "selected-word",
                                    fx = false
                                )
                            @elseif(word instanceof InvalidWord invalidWord)
                                @template.pages.document.word(
                                    id = "unit-" + word.id(),
                                    content = invalidWord.rawContent(),
                                    className = "invalid-word",
                                    fx = false
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