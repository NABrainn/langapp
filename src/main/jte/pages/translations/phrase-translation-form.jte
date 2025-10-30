@import org.langapp.documents.dto.processor.selection.PhraseSelection

@param PhraseSelection phraseSelection
@param int documentId
!{var fromPhrase = phraseSelection.content();}

<div
class="f">
    <span>${fromPhrase}</span>
    <div
    id="toWordList"
    fx-action="/translations?fromWord=${fromPhrase}&fromLang=NO&toLang=EN"
    fx-trigger="fx:inited">
        <span>Loading translations...</span>
    </div>
    <label
    for="textfield"></label>
    <input
    id="textfield"
    type="text">
    @template.pages.translations.levels(documentId = documentId, reloading = false, currentLevel = 3, selection = phraseSelection)
</div>