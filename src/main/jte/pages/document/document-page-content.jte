@import java.util.List
@import org.langapp.documents.dto.processor.*
@import org.langapp.documents.dto.processor.TranslatedWord
@import org.langapp.documents.dto.processor.SelectedWord
@import org.langapp.documents.dto.processor.Paragraph
@import org.langapp.documents.dto.processor.Phrase
@import org.langapp.documents.dto.processor.Word
@import org.langapp.documents.dto.processor.selection.PhraseSelection
@import org.langapp.documents.dto.processor.selection.SelectionStrategy
@import org.langapp.documents.dto.processor.selection.WordSelection

@param int documentId
@param String title
@param List<Paragraph> paragraphs
@param SelectionStrategy selection

@if(selection instanceof WordSelection wordSelection)
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
                            id = "u" + phrase.id(),
                            content = newPhrase.rawContent(),
                            className = "u p n",
                            fx = false)
                        @elseif(phrase instanceof TranslatedPhrase translatedPhrase)
                            @template.pages.document.phrase(
                            id = "u" + phrase.id(),
                            content = translatedPhrase.rawContent(),
                            className = "u p t",
                            bgc = "var(--recognized)",
                            fx = true,
                            fxMethod = "GET",
                            fxAction = "/documents/%s/reload?selection=phrase&startId=%s&endId=%s&content=%s&rawContent=%s".formatted(documentId, translatedPhrase.id(), translatedPhrase.id() + translatedPhrase.size() - 1, translatedPhrase.content(), translatedPhrase.rawContent()),
                            fxTarget = "#content")
                        @endif
                    @elseif(unit instanceof Word word)
                        @if(word instanceof NewWord newWord)
                            @template.pages.document.word(
                            id = "u" + word.id(),
                            content = newWord.rawContent(),
                            className = "u w n",
                            fx = true,
                            fxMethod = "GET",
                            fxAction = "/documents/" + documentId + "/reload?selection=word&wordId=" + word.id() + "&content=" + word.content(),
                            fxTarget = "#content")
                        @elseif(word instanceof TranslatedWord translatedWord)
                            @template.pages.document.word(
                            id = "u" + word.id(),
                            content = translatedWord.rawContent(),
                            className = "u w t",
                            fx = true,
                            fxMethod = "GET",
                            fxAction = "/documents/" + documentId + "/reload?selection=word&wordId=" + word.id() + "&content=" + word.content(),
                            fxTarget = "#content")
                        @elseif(word instanceof SelectedWord selectedWord)
                            @template.pages.document.word(
                            id = "u" + word.id(),
                            content = selectedWord.rawContent(),
                            className = "u w s",
                            fx = false,
                            form = @`@template.pages.translations.word-translation-form(documentId = documentId, wordSelection = wordSelection)`)
                        @elseif(word instanceof InvalidWord invalidWord)
                            @template.pages.document.word(
                            id = "u" + word.id(),
                            content = invalidWord.rawContent(),
                            className = "u w i",
                            fx = false)
                        @endif
                    @endif
                @else
                    Document has no content
                @endfor
            </div>
        @endfor
    </div>
@elseif(selection instanceof PhraseSelection phraseSelection)
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
                            id = "u" + phrase.id(),
                            content = newPhrase.rawContent(),
                            className = "u p n",
                            fx = false)
                        @elseif(phrase instanceof TranslatedPhrase translatedPhrase)
                            @template.pages.document.phrase(
                            id = "u" + phrase.id(),
                            content = translatedPhrase.rawContent(),
                            className = "u p t",
                            bgc = "var(--recognized)",
                            fx = true,
                            fxMethod = "GET",
                            fxAction = "/documents/%s/reload?selection=phrase&startId=%s&endId=%s&content=%s&rawContent=%s".formatted(documentId, translatedPhrase.id(), translatedPhrase.id() + translatedPhrase.size() - 1, translatedPhrase.content(), translatedPhrase.rawContent()),
                            fxTarget = "#content")
                        @elseif(phrase instanceof SelectedPhrase selectedPhrase)
                            !{var form = unit.id() == phraseSelection.startId() ?
@`                          @template.pages.translations.phrase-translation-form(documentId = documentId, phraseSelection = phraseSelection)` :
                            null;}
                            @template.pages.document.phrase(
                            id = "u" + phrase.id(),
                            content = selectedPhrase.rawContent(),
                            className = "u p s",
                            fx = false,
                            form = form)
                        @endif
                    @elseif(unit instanceof Word word)
                        @if(word instanceof NewWord newWord)
                            @template.pages.document.word(
                            id = "u" + word.id(),
                            content = newWord.rawContent(),
                            className = "u w n",
                            fx = true,
                            fxMethod = "GET",
                            fxAction = "/documents/" + documentId + "/reload?selection=word&wordId=" + word.id() + "&content=" + word.content(),
                            fxTarget = "#content")
                        @elseif(word instanceof TranslatedWord translatedWord)
                            @template.pages.document.word(
                            id = "u" + word.id(),
                            content = translatedWord.rawContent(),
                            className = "u w t",
                            fx = true,
                            fxMethod = "GET",
                            fxAction = "/documents/" + documentId + "/reload?selection=word&wordId=" + word.id() + "&content=" + word.content(),
                            fxTarget = "#content")
                        @elseif(word instanceof SelectedWord selectedWord)
                            @template.pages.document.word(
                            id = "u" + word.id(),
                            content = selectedWord.rawContent(),
                            className = "u w s",
                            fx = false)
                        @elseif(word instanceof InvalidWord invalidWord)
                            @template.pages.document.word(
                            id = "u" + word.id(),
                            content = invalidWord.rawContent(),
                            className = "u w i",
                            fx = false)
                        @endif
                    @endif
                @else
                    Document has no content
                @endfor
            </div>
        @endfor
    </div>
@else
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
                            id = "u" + phrase.id(),
                            content = newPhrase.rawContent(),
                            className = "u p n",
                            fx = false)
                        @elseif(phrase instanceof TranslatedPhrase translatedPhrase)
                            @template.pages.document.phrase(
                            id = "u" + phrase.id(),
                            content = translatedPhrase.rawContent(),
                            className = "u p t",
                            bgc = "var(--recognized)",
                            fx = true,
                            fxMethod = "GET",
                            fxAction = "/documents/%s/reload?selection=phrase&startId=%s&endId=%s&content=%s&rawContent=%s".formatted(documentId, translatedPhrase.id(), translatedPhrase.id() + translatedPhrase.size() - 1, translatedPhrase.content(), translatedPhrase.rawContent()),
                            fxTarget = "#content")
                        @elseif(phrase instanceof SelectedPhrase selectedPhrase)
                            @template.pages.document.phrase(
                            id = "u" + phrase.id(),
                            content = selectedPhrase.rawContent(),
                            className = "u p s",
                            fx = false)
                        @endif
                    @elseif(unit instanceof Word word)
                        @if(word instanceof NewWord newWord)
                            @template.pages.document.word(
                            id = "u" + word.id(),
                            content = newWord.rawContent(),
                            className = "u w n",
                            fx = true,
                            fxMethod = "GET",
                            fxAction = "/documents/" + documentId + "/reload?selection=word&wordId=" + word.id() + "&content=" + word.content(),
                            fxTarget = "#content")
                        @elseif(word instanceof TranslatedWord translatedWord)
                            @template.pages.document.word(
                            id = "u" + word.id(),
                            content = translatedWord.rawContent(),
                            className = "u w t",
                            fx = true,
                            fxMethod = "GET",
                            fxAction = "/documents/" + documentId + "/reload?selection=word&wordId=" + word.id() + "&content=" + word.content(),
                            fxTarget = "#content")
                        @elseif(word instanceof SelectedWord selectedWord)
                            @template.pages.document.word(
                            id = "u" + word.id(),
                            content = selectedWord.rawContent(),
                            className = "u w s",
                            fx = false)
                        @elseif(word instanceof InvalidWord invalidWord)
                            @template.pages.document.word(
                            id = "u" + word.id(),
                            content = invalidWord.rawContent(),
                            className = "u w i",
                            fx = false)
                        @endif
                    @endif
                @else
                    Document has no content
                @endfor
            </div>
        @endfor
    </div>
@endif