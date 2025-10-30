@import org.langapp.documents.dto.processor.selection.WordSelection

@param int documentId
@param WordSelection wordSelection

!{var fromWord = wordSelection.content();}
<div
class="f">
    <span>${fromWord}</span>
    <div
    id="toWordList"
    fx-action="/translations?fromWord=${fromWord}&fromLang=NO&toLang=EN"
    fx-trigger="fx:inited">
        <span>Loading translations...</span>
    </div>
    <label
    for="textfield"></label>
    <input
    id="textfield"
    type="text">
    @template.pages.translations.levels(documentId = documentId, reloading = false, currentLevel = 3, selection = wordSelection)
</div>