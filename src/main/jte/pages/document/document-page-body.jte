@import java.util.List
@import org.langapp.documents.dto.processor.*
@import org.langapp.documents.dto.processor.Paragraph
@import org.langapp.documents.dto.processor.selection.SelectionStrategy

@param int documentId
@param String title
@param List<Paragraph> paragraphs
@param SelectionStrategy selection

<div
fx-method="GET"
fx-action="/documents/${documentId}/reload"
fx-trigger="selectPhrase"
fx-target="#content"
ext-fx-vals="js:{...document.getElementById('document-page').details}"
id="document-page"
is="selectable-div">
    <div
    id="document">
        <span
        id="title">${title}</span>
        @template.pages.document.document-page-content(documentId = documentId, title = title, paragraphs = paragraphs, selection = selection)
    </div>
</div>