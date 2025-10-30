@import org.langapp.documents.dto.processor.selection.PhraseSelection
@import org.langapp.documents.dto.processor.selection.SelectionStrategy
@import org.langapp.documents.dto.processor.selection.WordSelection
@import java.util.function.IntFunction

@param int documentId
@param boolean reloading
@param int currentLevel
@param SelectionStrategy selection

!{var reloadDocumentParams =
selection instanceof WordSelection wordSelection ?
    "?selection=word&wordId=" + wordSelection.wordId() + "&content=" + wordSelection.content() :
selection instanceof PhraseSelection phraseSelection ?
    "?selection=phrase&startId=" + phraseSelection.startId() + "&endId=" + phraseSelection.endId() + "&content=" + phraseSelection.content() + "&rawContent=" + phraseSelection.rawContent() :
"";}
!{IntFunction<String> style = (level) -> level == currentLevel ? "selected" : "next";}
!{IntFunction<String> changeLevelParams =
selection instanceof WordSelection wordSelection ?
    (id) -> "?content=" + wordSelection.content() + "&nextLevel=" + id + "&documentId=" + documentId + "&selection=word&wordId=" + wordSelection.wordId() :
selection instanceof PhraseSelection phraseSelection ?
    (id) -> "?content=" + phraseSelection.content() + "&nextLevel=" + id + "&documentId=" + documentId + "&selection=phrase&startId=" + phraseSelection.startId() + "&endId=" + phraseSelection.endId() + "&rawContent=" + phraseSelection.rawContent() :
    (id) -> "";}
@if(reloading)
    <div
    id="levels"
    fx-action="/documents/${documentId}/reload${reloadDocumentParams}"
    fx-target="#content"
    fx-trigger="fx:inited">
        <span
        class="${style.apply(1)}"
        fx-method="PUT"
        fx-target="#levels"
        fx-action="/translations/level${changeLevelParams.apply(1)}">1</span>
        <span
        class="${style.apply(2)}"
        fx-method="PUT"
        fx-target="#levels"
        fx-action="/translations/level${changeLevelParams.apply(2)}">2</span>
        <span
        class="${style.apply(3)}"
        fx-method="PUT"
        fx-target="#levels"
        fx-action="/translations/level${changeLevelParams.apply(3)}">3</span>
        <span
        class="${style.apply(4)}"
        fx-method="PUT"
        fx-target="#levels"
        fx-action="/translations/level${changeLevelParams.apply(4)}">4</span>
        <span
        class="${style.apply(5)}"
        fx-method="PUT"
        fx-target="#levels"
        fx-action="/translations/level${changeLevelParams.apply(5)}">5</span>
    </div>
@else
    <div
    id="levels">
        <span
        class="${style.apply(1)}"
        fx-method="PUT"
        fx-target="#levels"
        fx-action="/translations/level${changeLevelParams.apply(1)}">1</span>
        <span
        class="${style.apply(2)}"
        fx-method="PUT"
        fx-target="#levels"
        fx-action="/translations/level${changeLevelParams.apply(2)}">2</span>
        <span
        class="${style.apply(3)}"
        fx-method="PUT"
        fx-target="#levels"
        fx-action="/translations/level${changeLevelParams.apply(3)}">3</span>
        <span
        class="${style.apply(4)}"
        fx-method="PUT"
        fx-target="#levels"
        fx-action="/translations/level${changeLevelParams.apply(4)}">4</span>
        <span
        class="${style.apply(5)}"
        fx-method="PUT"
        fx-target="#levels"
        fx-action="/translations/level${changeLevelParams.apply(5)}">5</span>
    </div>
@endif